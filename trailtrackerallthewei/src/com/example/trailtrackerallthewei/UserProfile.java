package com.example.trailtrackerallthewei;

import android.os.Parcel;
import android.os.Parcelable;

public class UserProfile implements Parcelable {
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

	@com.google.gson.annotations.SerializedName("email")
	public String mEmail;

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

	/**
	 * Returns the item id
	 */
	public String getId() {
		return mId;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof UserProfile && ((UserProfile) o).mId == mId;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public UserProfile(Parcel in) {
		this.mId = in.readString();
		this.mEmail = in.readString();
		this.mName = in.readString();
		this.fitBitUserId = in.readString();
		this.oAuthToken= in.readString();
		this.oAuthSecret = in.readString();
		this.oAuthExpiresIn= in.readString();
		this.oAuthRequest= in.readString();
	}

	public static final Parcelable.Creator<UserProfile> CREATOR = new Parcelable.Creator<UserProfile>() {
		public UserProfile createFromParcel(Parcel in) {
			return new UserProfile(in);
		}

		public UserProfile[] newArray(int size) {
			return new UserProfile[size];
		}
	};

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.mId);
		dest.writeString(this.mEmail);
		dest.writeString(this.mName);
		dest.writeString(this.fitBitUserId);
		dest.writeString(this.oAuthToken);
		dest.writeString(this.oAuthSecret);
		dest.writeString(this.oAuthExpiresIn);
		dest.writeString(this.oAuthRequest);

	}

}
