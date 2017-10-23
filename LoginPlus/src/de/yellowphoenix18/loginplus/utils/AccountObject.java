package de.yellowphoenix18.loginplus.utils;

public class AccountObject {
	
	String uuid;
	String password;
	EncryptionType hashtype;
	boolean premium;
	
	public AccountObject(String uuid, String password, EncryptionType hashtype, boolean premium) {
		this.uuid = uuid;
		this.password = password;
		this.hashtype = hashtype;
		this.premium = premium;
	}
	
	public void setUUID(String uuid) {
		this.uuid = uuid;
	}
	
	public String getUUID() {
		return this.uuid;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setHashType(EncryptionType hashtype) {
		this.hashtype = hashtype;
	}
	
	public EncryptionType getHashType() {
		return this.hashtype;
	}
	
	public void setPremium(boolean premium) {
		this.premium = premium;
	}
	
	public boolean getPremium() {
		return this.premium;
	}

}
