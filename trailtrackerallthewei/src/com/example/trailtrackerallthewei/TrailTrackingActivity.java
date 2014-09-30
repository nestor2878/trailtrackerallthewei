package com.example.trailtrackerallthewei;

import com.google.gson.Gson;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.os.Build;

public class TrailTrackingActivity extends BaseActivity {

	private Button mStartButton;
	private Button mStopButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trail_tracking);

		this.mStartButton = (Button) findViewById(R.id.startTrail);
		this.mStopButton = (Button) findViewById(R.id.stopTrail);
		this.mStopButton.setEnabled(false);

		this.mStartButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				TrailItem trailStart = new TrailItem();
				trailStart.userId = mUserProfile.mId;

				trailStart.startTime = GetCurrentTime();

				LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
				Location location = lm
						.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				double longitude = location.getLongitude();
				double latitude = location.getLatitude();
				trailStart.startLongitude = String.valueOf(longitude);
				trailStart.startLatitude = String.valueOf(latitude);

				mTrailItemTable.insert(trailStart,
						new TableOperationCallback<TrailItem>() {

							@Override
							public void onCompleted(TrailItem created,
									Exception exception,
									ServiceFilterResponse serviceFilterResponse) {
								if (exception != null) {
									displayException(exception);
									return;
								}
								setActiveTrail(created);
								mStartButton.setEnabled(false);
								mStopButton.setEnabled(true);
							}
						});
			}
		});

		this.mStopButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				TrailItem trail = getActiveTrail();
				trail.stopTime = GetCurrentTime();

				LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
				Location location = lm
						.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				double longitude = location.getLongitude();
				double latitude = location.getLatitude();
				trail.stopLongitude = String.valueOf(longitude);
				trail.stopLatitude = String.valueOf(latitude);

				trail.caloriesBurnt = "23";
				trail.averageHeartRate = "98";

				mTrailItemTable.update(trail,
						new TableOperationCallback<TrailItem>() {

							@Override
							public void onCompleted(TrailItem created,
									Exception exception,
									ServiceFilterResponse serviceFilterResponse) {
								if (exception != null) {
									displayException(exception);
									return;
								}

								Gson gson = new Gson();
								Toast.makeText(getApplicationContext(),
										gson.toJson(created), Toast.LENGTH_LONG)
										.show();
								mStartButton.setEnabled(true);
								mStopButton.setEnabled(false);
							}
						});
			}
		});
	}

	private String GetCurrentTime() {
		Time now = new Time();
		now.setToNow();
		return now.format("%d.%m.%Y %H.%M.%S");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trail_tracking, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		if (id == R.id.menu_history) {
			Intent intent = new Intent(this, TrailHistoryActivity.class);
			intent.putExtra(BaseActivity.USERPROFILE_EXTRA_KEY,
					super.mUserProfile);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}
