package de.yellowphoenix18.yellowphoenix18stats;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.logging.Level;

import javax.net.ssl.HttpsURLConnection;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class YellowPhoenix18Stats {
	
	public static final double YELLOWPHOENIX18_STATS_VERSION = 0.1;
	
	private static final String URL = "https://stats.yellowphoenix18.de/submit/?type=bukkit";
	
	private File f = new File("plugins/YellowPhoenix18Stats", "config.yml");
	private FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
	
	private final JavaPlugin plugin;
	private String service_id;
	
    private static String serverUUID;
    private boolean logging = false;;
	
	public YellowPhoenix18Stats(String service_id, JavaPlugin plugin) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin can not be null!");
        }
		this.plugin = plugin;
		this.service_id = service_id;
		cfg.addDefault("enabled", true);
		cfg.addDefault("ServerUUID", UUID.randomUUID().toString());
		cfg.addDefault("Logging", false);
		cfg.options().header("YellowPhoenix18Stats collects some data for YellowPhoenix18 to give you an better experience. \n" +
							 "This software has nearly no effect on the server performance!\n" +
							 "For more Infos check out https://stats.yellowphoenix18.de").copyDefaults(true);
		try {
			cfg.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		serverUUID = cfg.getString("ServerUUID");
		logging = cfg.getBoolean("Logging");
		
        if (cfg.getBoolean("enabled") == true) {
            boolean found = false;
            
            for (Class<?> service : Bukkit.getServicesManager().getKnownServices()) {
                try {
                    service.getField("YELLOWPHOENIX18_STATS_VERSION");
                    found = true;
                    break;
                } catch (NoSuchFieldException ignored) { }
            }
            Bukkit.getServicesManager().register(YellowPhoenix18Stats.class, this, plugin, ServicePriority.Normal);
            if (!found) {
                DataSubmitter();
            }
        }
	}
	
	private void DataSubmitter() {
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if(!plugin.isEnabled()) {
					timer.cancel();
					return;
				}
				
				Bukkit.getScheduler().runTask(plugin, new Runnable() {
					@Override
					public void run() {
						submitData();
					}			
				});
			}	
		}, 1000*60*5, 1000*60*15);
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject collectPluginData() {
		JSONObject data = new JSONObject();
		
		String name = plugin.getDescription().getName(); 
		String version = plugin.getDescription().getVersion();
		
		data.put("Name", name);
		data.put("Service-ID", this.service_id);
		data.put("Version", version);
		
		return data;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject collectServerData() {
		JSONObject data = new JSONObject();
		
		int playerAmount = Bukkit.getOnlinePlayers().size();
		boolean onlineMode = Bukkit.getOnlineMode();
		String bukkitVersion = Bukkit.getVersion();
        bukkitVersion = bukkitVersion.substring(bukkitVersion.indexOf("MC: ") + 4, bukkitVersion.length() - 1);
		
		String javaVersion = System.getProperty("java.version");
        String osName = System.getProperty("os.name");
        String osArch = System.getProperty("os.arch");
        String osVersion = System.getProperty("os.version");
        int coreCount = Runtime.getRuntime().availableProcessors();
        long memory = Runtime.getRuntime().totalMemory();
        
        
		
		data.put("ServerUUID", YellowPhoenix18Stats.serverUUID);
		
		data.put("BukkitVersion", bukkitVersion);
		data.put("PlayerAmount", playerAmount);
		data.put("onlineMode", onlineMode);
		
		data.put("JavaVersion", javaVersion);
		data.put("OS", osName);
		data.put("OS-Version", osVersion);
		data.put("OS-Arch", osArch);
		data.put("Core-Count", coreCount);
		data.put("Memory",memory);
		
		return data;
	}
	
	@SuppressWarnings("unchecked")
	private void submitData() {
		JSONObject data = collectServerData();
		
		JSONArray pluginData = new JSONArray();
        for (Class<?> service : Bukkit.getServicesManager().getKnownServices()) {
            try {
                service.getField("YELLOWPHOENIX18_STATS_VERSION"); // Our identifier :)
            } catch (NoSuchFieldException ignored) {
                continue; // Continue "searching"
            }
            // Found one!
            try {
                pluginData.add(service.getMethod("collectPluginData").invoke(Bukkit.getServicesManager().load(service)));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) { 
           	
            }
            data.put("plugins", pluginData);
            
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                    	sendDatatoServer(data);
                    } catch (Exception e) {
                        if(logging == true) {
                            plugin.getLogger().log(Level.WARNING, "Could not submit plugin stats of " + plugin.getName(), e);
                        }
                    }
                }
            }).start();
        }
	}
	
	private void sendDatatoServer(JSONObject data) throws Exception {
		if (data == null) {
            throw new IllegalArgumentException("Data cannot be null!");
        }
        if (Bukkit.isPrimaryThread()) {
            throw new IllegalAccessException("This method must not be called from the main thread!");
        }
        
        HttpsURLConnection connection = (HttpsURLConnection) new URL(URL).openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("User-Agent", "YellowPhoenix18Stats-ServerVersion/" + YELLOWPHOENIX18_STATS_VERSION);
        
        connection.setDoOutput(true);
        PrintStream ps = new PrintStream(connection.getOutputStream());
        ps.print("data=" + data.toJSONString());
        connection.getInputStream();
        
        ps.close();
        connection.getInputStream().close();
	}

}
