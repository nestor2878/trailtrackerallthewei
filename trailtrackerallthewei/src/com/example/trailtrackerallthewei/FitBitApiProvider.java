package com.example.trailtrackerallthewei;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import io.oauth.OAuth;
import io.oauth.OAuthData;
import io.oauth.OAuthRequest;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.google.gson.Gson;

public class FitBitApiProvider {

	private UserProfile mUserProfile;
	private Context mContext;

	public FitBitApiProvider(Context context, UserProfile userProfile) {
		this.mContext = context;
		this.mUserProfile = userProfile;
	}

	private OAuthData getOAuthData() {

		OAuth oauth = new OAuth(this.mContext);
		oauth.initialize("fY0HJHCUi_R9JjLIP2XvVungmjE");

		OAuthData oAuthData = new OAuthData(oauth);
		oAuthData.token = this.mUserProfile.oAuthToken;
		oAuthData.secret = this.mUserProfile.oAuthSecret;
		oAuthData.provider = "fitbit";
		oAuthData.expires_in = this.mUserProfile.oAuthExpiresIn;

		try {
			oAuthData.request = new JSONObject(this.mUserProfile.oAuthRequest);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return oAuthData;
	}

	public void GetActivityStats(
			final FitBitApiProviderCallback<FitBitActivities> callback) {

		OAuthData oAuthData = getOAuthData();

		oAuthData.http("https://api.fitbit.com/1/user/-/activities.json",
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
							JSONObject rootJSONObject = new JSONObject(total
									.toString());

							FitBitActivities fitBitActivities = gson.fromJson(
									rootJSONObject.toString(),
									FitBitActivities.class);
							callback.onComplete(fitBitActivities);

						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onError(String message) {
					}
				});
	}
}
