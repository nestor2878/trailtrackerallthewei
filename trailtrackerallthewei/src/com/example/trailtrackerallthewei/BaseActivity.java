package com.example.trailtrackerallthewei;

import java.net.MalformedURLException;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public abstract class BaseActivity extends Activity {
	protected static final String PREFS_NAME = "TRAILTRACKERALLTHEWAYSETTINGS";
	protected static final String USERID_SETTING = "userId";
	public static final String USERPROFILE_EXTRA_KEY = "userProfile";

	protected UserProfile mUserProfile;

	protected MobileServiceClient mMobileServiceClient;
	protected MobileServiceTable<UserProfile> mUserProfileTable;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initializeMobileServiceClient();
		loadSettings();
	}

	private void initializeMobileServiceClient() {
		try {
			this.mMobileServiceClient = MobileServiceClientFactory.Create(this);
			this.mUserProfileTable = this.mMobileServiceClient
					.getTable(UserProfile.class);
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
}
