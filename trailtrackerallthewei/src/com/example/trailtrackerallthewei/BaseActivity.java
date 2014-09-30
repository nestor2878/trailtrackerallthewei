package com.example.trailtrackerallthewei;

import java.net.MalformedURLException;
import java.util.AbstractMap.SimpleEntry;

import com.google.gson.Gson;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Pair;
import android.widget.Toast;

public abstract class BaseActivity extends Activity {
	protected static final String PREFS_NAME = "TRAILTRACKERALLTHEWAYSETTINGS";
	protected static final String USERID_SETTING = "userId";
	protected static final String ACTIVE_TRAIL_SETTING_KEY = "activeTrail";
	public static final String USERPROFILE_EXTRA_KEY = "userProfile";

	protected UserProfile mUserProfile;

	protected MobileServiceClient mMobileServiceClient;
	protected MobileServiceTable<UserProfile> mUserProfileTable;
	protected MobileServiceTable<TrailItem> mTrailItemTable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initializeMobileServiceClient();
		loadSettings();
	}

	private void initializeMobileServiceClient() {
		try {
			this.mMobileServiceClient = MobileServiceClientFactory.Create(this);
			this.mUserProfileTable = this.mMobileServiceClient
					.getTable(UserProfile.class);
			this.mTrailItemTable = this.mMobileServiceClient
					.getTable(TrailItem.class);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	private void loadSettings() {
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			this.mUserProfile = extras.getParcelable(USERPROFILE_EXTRA_KEY);
		}

		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		String userId = settings.getString(USERID_SETTING, null);

		if (userId == null || this.mUserProfile == null) {
			Intent intent = new Intent(this, LandingActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
		}
	}
	
	protected void setActiveTrail(TrailItem trail){
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		
		Gson gson = new Gson();
		String json = trail == null ? null : gson.toJson(trail);
		editor.putString(ACTIVE_TRAIL_SETTING_KEY, json);
		
		editor.commit();
	}
	
	protected TrailItem getActiveTrail(){
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		String activeTrailJsonString = settings.getString(ACTIVE_TRAIL_SETTING_KEY, null);
		
		if (activeTrailJsonString == null){
			return null;
		}
		
		Gson gson = new Gson();
		return gson.fromJson(activeTrailJsonString, TrailItem.class);
	}
	
	protected <T> void startNewActivity(Class<T> activityClass,String key, TrailItem item){
		Intent intent = new Intent(this, activityClass);
		intent.putExtra(BaseActivity.USERPROFILE_EXTRA_KEY,mUserProfile);
		intent.putExtra(key,item);
		startActivity(intent);
	}
	protected <T> void startNewActivity(Class<T> activityClass){
	
		Intent intent = new Intent(this, activityClass);
		intent.putExtra(BaseActivity.USERPROFILE_EXTRA_KEY,mUserProfile);
		startActivity(intent);
	}
	
	protected void displayException(Exception e){
		if (e == null){
			return;
		}
		Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
	}
	
	protected String getCurrentTime() {
		Time now = new Time();
		now.setToNow();
		return now.format("%d.%m.%Y %H.%M.%S");
		
	}
}
