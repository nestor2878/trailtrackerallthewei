package com.example.trailtrackerallthewei;

public class TrailItem {
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
}
