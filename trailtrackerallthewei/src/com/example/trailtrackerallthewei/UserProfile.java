package com.example.trailtrackerallthewei;

public class UserProfile {
	/**
	 * Id
	 */
	@com.google.gson.annotations.SerializedName("id")
	public String mId;
	
	public String fitBitUserId;
	public String oAuthToken;
	public String oAuthSecret;
	public String oAuthExpiresIn;
	public String oAuthRequest;
	
	/**
	 * Email
	 */
	@com.google.gson.annotations.SerializedName("email")
	public String mEmail;
	
	/**
	 * The name
	 */
	@com.google.gson.annotations.SerializedName("name")
	public String mName;

	/**
	 * ToDoItem constructor
	 */
	public UserProfile() {
	}

	@Override
	public String toString() {
		return getId();
	}
	
	public String getEmail(){
		return this.mEmail;
	}
	
	public final void setEmail(String email) {
		this.mEmail = email;
	}

	public String getName(){
		return this.mName;
	}
	
	public final void setName(String name) {
		this.mName = name;
	}

	/**
	 * Returns the item id
	 */
	public String getId() {
		return mId;
	}

	/**
	 * Sets the item id
	 * 
	 * @param id
	 *            id to set
	 */
	public final void setId(String id) {
		mId = id;
	}
	
	@Override
	public boolean equals(Object o) {
		return o instanceof UserProfile && ((UserProfile) o).mId == mId;
	}

}
