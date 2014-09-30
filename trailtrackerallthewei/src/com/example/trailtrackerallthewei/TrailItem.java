package com.example.trailtrackerallthewei;

import android.os.Parcel;
import android.os.Parcelable;

public class TrailItem implements Parcelable{
	public String id;
	@com.google.gson.annotations.SerializedName("userid")
	public String userId;
	@com.google.gson.annotations.SerializedName("startlatitute")
	public String startLatitude;
	@com.google.gson.annotations.SerializedName("startlongitude")
	public String startLongitude;
	@com.google.gson.annotations.SerializedName("starttime")
	public String startTime;
	@com.google.gson.annotations.SerializedName("stoplatitude")
	public String stopLatitude;
	@com.google.gson.annotations.SerializedName("stoplongitude")
	public String stopLongitude;
	@com.google.gson.annotations.SerializedName("stoptime")
	public String stopTime;
	@com.google.gson.annotations.SerializedName("averageheartrate")
	public String averageHeartRate;
	@com.google.gson.annotations.SerializedName("caloriesburnt")
	public String caloriesBurnt;
	@com.google.gson.annotations.SerializedName("sighting")
	public String sighting;
	@com.google.gson.annotations.SerializedName("notes")
	public String notes;
	
	public TrailItem(){
		
	}
	
	public TrailItem(Parcel in){
		this.id = in.readString();
		this.userId = in.readString();
		this.startLatitude = in.readString();
		this.startLongitude = in.readString();
		this.startTime =in.readString();
		this.stopLatitude=in.readString();
		this.stopLongitude=in.readString();
		this.stopTime=in.readString();
		this.averageHeartRate=in.readString();
		this.caloriesBurnt =in.readString();
		this.sighting =in.readString();
		this.notes = in.readString();
	}
	
	public static final Parcelable.Creator<TrailItem> CREATOR = new Parcelable.Creator<TrailItem>() {
		public TrailItem createFromParcel(Parcel in) {
			return new TrailItem(in);
		}

		public TrailItem[] newArray(int size) {
			return new TrailItem[size];
		}
	};
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(userId);
		dest.writeString(startLatitude);
		dest.writeString(startLongitude);
		dest.writeString(startTime);
		dest.writeString(stopLatitude);
		dest.writeString(stopLongitude);
		dest.writeString(stopTime);
		dest.writeString(averageHeartRate);
		dest.writeString(caloriesBurnt);
		dest.writeString(sighting);
		dest.writeString(notes);
	}
}
