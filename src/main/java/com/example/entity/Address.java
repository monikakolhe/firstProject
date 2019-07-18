package com.example.entity;

public class Address {
	private String type;
	private String addressInfo;
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the addressInfo
	 */
	public String getAddressInfo() {
		return addressInfo;
	}
	/**
	 * @param addressInfo the addressInfo to set
	 */
	public void setAddressInfo(String addressInfo) {
		this.addressInfo = addressInfo;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Address [type=" + type + ", addressInfo=" + addressInfo + "]";
	}
	
		
	
}
