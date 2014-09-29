package com.example.trailtrackerallthewei;

public class UserProfile {
	/**
	 * Id
	 */
	@com.google.gson.annotations.SerializedName("id")
	private String mId;
	
	/**
	 * Indicates the google account name attached to this profile if any
	 */
	@com.google.gson.annotations.SerializedName("googleAccountName")
	private String mGoogleAccountName;
	
	/**
	 * Email
	 */
	@com.google.gson.annotations.SerializedName("email")
	private String mEmail;
	
	/**
	 * The password
	 */
	@com.google.gson.annotations.SerializedName("password")
	private String mPassword;
	
	/**
	 * The birthday
	 */
	@com.google.gson.annotations.SerializedName("birthday")
	private String mBirthday;
	
	/**
	 * The name
	 */
	@com.google.gson.annotations.SerializedName("name")
	private String mName;

	/**
	 * ToDoItem constructor
	 */
	public UserProfile() {
	}

	@Override
	public String toString() {
		return getId();
	}

	/**
	 * Initializes a new UserProfile
	 * 
	 */
	public UserProfile(String id, String googleAccountName, String password, String email, String name, String birthday) {
		this.setId(id);
		this.setGoogleAccountName(googleAccountName);
		this.setPassword(password);
		this.setBirthday(birthday);
		this.setName(name);
		this.setEmail(email);
	}

	public String getPasswrod(){
		return this.mPassword;
	}
	
	public final void setPassword(String password) {
		this.mPassword = password;
	}
	
	public String getEmail(){
		return this.mEmail;
	}
	
	public final void setEmail(String email) {
		this.mEmail = email;
	}
	
	public String getBirthday(){
		return this.mBirthday;
	}
	
	public final void setBirthday(String birthday) {
		this.mBirthday = birthday;
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

	public String getGoogleAccountName(){
		return mGoogleAccountName;
	}
	public final void setGoogleAccountName(String googleAccountName){
		mGoogleAccountName = googleAccountName;
	}
	
	@Override
	public boolean equals(Object o) {
		return o instanceof UserProfile && ((UserProfile) o).mId == mId;
	}

}
