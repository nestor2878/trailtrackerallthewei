package com.example.trailtrackerallthewei;

import io.oauth.OAuth;
import io.oauth.OAuthCallback;
import io.oauth.OAuthData;
import io.oauth.OAuthRequest;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONObject;

public class LandingActivity extends Activity implements OAuthCallback {
	// implement
	// the
	// OAuthCallback
	// interface
	// to
	// get
	// the
	// right
	// information
	Button fitbitButton;
	TextView fitbitText;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.landing_activity);
		final OAuth o = new OAuth(this);
		o.initialize("fY0HJHCUi_R9JjLIP2XvVungmjE"); // Initialize the oauth key
		// o.initialize("hRMMOd7z3NAuUxDSM6-_TUdWeJI");
		fitbitButton = (Button) findViewById(R.id.fitbit);
		fitbitText = (TextView) findViewById(R.id.fitbitText);
		fitbitButton.setOnClickListener(new View.OnClickListener() { // Listen
																		// the
																		// on
																		// click
																		// event
					@Override
					public void onClick(View v) {
						o.popup("fitbit", LandingActivity.this); // Launch the
																	// pop
																	// up with
																	// the
																	// right
																	// provider
																	// &
																	// callback
					}
				});

	}

	/*
	 * * Get the information*
	 */
	public void onFinished(OAuthData data) {

		OAuth oauth = new OAuth(this);
		oauth.initialize("fY0HJHCUi_R9JjLIP2XvVungmjE");

		OAuthData dd = new OAuthData(oauth);
		dd.token = data.token;
		dd.secret = data.secret;
		dd.provider = "fitbit";
		dd.expires_in = data.expires_in;
		dd.request = data.request;
		String requestAsString = data.request.toString();

		final TextView textview = fitbitText;
		if (!data.status.equals("success")) {
			textview.setTextColor(Color.parseColor("#FF0000"));
			textview.setText("error, " + data.error);
		}
		// You can access the tokens through data.token and data.secret
		textview.setText("loading...");
		textview.setTextColor(Color.parseColor("#00FF00"));
		// Let's skip the NetworkOnMainThreadException for the purpose of this
		// sample.
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.permitAll().build());

		// To make an authenticated request, you can implement OAuthRequest with
		// your prefered way.
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
							FitBitUser user = new FitBitUser();
							user.aboutMe = "abmout";
							String asJson = gson.toJson(user);
							FitBitUser user2 = gson.fromJson(asJson,
									FitBitUser.class);
							JSONObject result = new JSONObject(total.toString());

							JSONObject jsonUserObject = result
									.getJSONObject("user");
							String jsonUserObjectAsString = jsonUserObject
									.toString();

							FitBitUser u = gson.fromJson(
									jsonUserObjectAsString, FitBitUser.class);
							String uu = u.fullName;
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
}
