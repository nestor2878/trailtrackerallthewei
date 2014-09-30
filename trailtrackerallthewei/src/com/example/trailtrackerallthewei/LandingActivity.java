package com.example.trailtrackerallthewei;

import io.oauth.OAuth;
import io.oauth.OAuthCallback;
import io.oauth.OAuthData;
import io.oauth.OAuthRequest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.json.JSONObject;

public class LandingActivity extends Activity implements OAuthCallback {
	Button fitbitButton;
	TextView fitbitText;
	private MobileServiceClient mMobileServiceClient;
	private MobileServiceTable<UserProfile> mUserProfileTable;
	private UserProfile mUserProfile;
	public static final String PREFS_NAME = "TRAILTRACKERALLTHEWAYSETTINGS";
	public static final String USERID_SETTING = "userId";

	/** Called when the activity is first created. */
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

	@Override
	protected void onStart() {
		super.onStart();
	}

	public void loadSettings() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		String userId = settings.getString(USERID_SETTING, null);

		if (userId != null) {
			this.mUserProfileTable.lookUp(userId,
					new TableOperationCallback<UserProfile>() {

						@Override
						public void onCompleted(UserProfile userProfile,
								Exception exception,
								ServiceFilterResponse serviceFilterResponse) {

							if (exception != null) {
								Toast.makeText(LandingActivity.this,
										exception.getMessage(),
										Toast.LENGTH_LONG).show();
							}

							Intent intent = new Intent(LandingActivity.this,
									TrailTrackingActivity.class);
							intent.putExtra(BaseActivity.USERPROFILE_EXTRA_KEY,
									userProfile);
							startActivity(intent);
							finish();
						}

					});

		} else {
			connectUser();
		}
	}

	private void saveSettings() {
		if (mUserProfile == null) {
			return;
		}

		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(USERID_SETTING, mUserProfile.mId);
		editor.commit();
	}

	@Override
	protected void onStop() {
		super.onStop();

		saveSettings();
	}

	private void connectUser() {
		setContentView(R.layout.landing_activity);

		final OAuth o = new OAuth(this);
		o.initialize("fY0HJHCUi_R9JjLIP2XvVungmjE");
		fitbitButton = (Button) findViewById(R.id.fitbit);
		fitbitText = (TextView) findViewById(R.id.fitbitText);
		fitbitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				o.popup("fitbit", LandingActivity.this);
			}
		});
	}

	/*
	 * * Get the information*
	 */
	public void onFinished(final OAuthData oAuthData) {
		OAuth oauth = new OAuth(this);
		oauth.initialize("fY0HJHCUi_R9JjLIP2XvVungmjE");

		OAuthData dd = new OAuthData(oauth);
		dd.token = oAuthData.token;
		dd.secret = oAuthData.secret;
		dd.provider = "fitbit";
		dd.expires_in = oAuthData.expires_in;
		dd.request = oAuthData.request;

		final TextView textview = fitbitText;
		if (!oAuthData.status.equals("success")) {
			textview.setTextColor(Color.parseColor("#FF0000"));
			textview.setText("error, " + oAuthData.error);
		}
		// You can access the tokens through data.token and data.secret
		textview.setText("loading...");
		textview.setTextColor(Color.parseColor("#00FF00"));

		// Let's skip the NetworkOnMainThreadException for the purpose of this
		// sample.
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.permitAll().build());

		// To make an authenticated request, you can implement OAuthRequest with
		// your preferred way.
		// Here, we use an URLConnection (HttpURLConnection) but you can use any
		// library.
		dd.http("https://api.fitbit.com/1/user/-/profile.json",
				new OAuthRequest() {
					private URL url;
					private URLConnection con;

					@Override
					public void onSetURL(String _url) {
						try {
							url = new URL(_url);
							con = url.openConnection();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onSetHeader(String header, String value) {
						con.addRequestProperty(header, value);
					}

					@Override
					public void onReady() {
						try {

							BufferedReader r = new BufferedReader(
									new InputStreamReader(con.getInputStream()));
							StringBuilder total = new StringBuilder();
							String line;
							while ((line = r.readLine()) != null) {
								total.append(line);
							}
							Gson gson = new Gson();
							JSONObject userJSONObject = new JSONObject(total
									.toString()).getJSONObject("user");

							FitBitUser fitBitUser = gson.fromJson(
									userJSONObject.toString(), FitBitUser.class);

							ensureUserProfileExists(fitBitUser, oAuthData);

						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onError(String message) {
						textview.setText("error: " + message);
					}
				});
	}

	private void ensureUserProfileExists(final FitBitUser fitBitUser,
			final OAuthData oAuthData) {
		this.mUserProfileTable.where().field("fitBitUserId")
				.eq(fitBitUser.encodedId)
				.execute(new TableQueryCallback<UserProfile>() {
					@Override
					public void onCompleted(List<UserProfile> profiles,
							int count, Exception exception,
							ServiceFilterResponse response) {

						if (profiles.isEmpty()) {
							UserProfile userProfile = new UserProfile();
							userProfile.fitBitUserId = fitBitUser.encodedId;
							userProfile.mName = fitBitUser.fullName;
							userProfile.oAuthToken = oAuthData.token;
							userProfile.oAuthSecret = oAuthData.secret;
							userProfile.oAuthExpiresIn = oAuthData.expires_in;
							userProfile.oAuthRequest = oAuthData.request
									.toString();

							mUserProfileTable.insert(userProfile,
									new TableOperationCallback<UserProfile>() {

										@Override
										public void onCompleted(
												UserProfile entity,
												Exception exception,
												ServiceFilterResponse response) {

											mUserProfile = entity;
											saveSettings();
										}
									});
						} else {
							mUserProfile = profiles.get(0);
							saveSettings();
						}
					}
				});
	}
}
