package com.example.trailtrackerallthewei;

import com.google.gson.Gson;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
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
	private MobileServiceTable<TrailStart> mTrailStartTable;
	private MobileServiceTable<TrailStop> mTrailStopTable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trail_tracking);

		this.mStartButton = (Button) findViewById(R.id.startTrail);
		this.mStopButton = (Button) findViewById(R.id.stopTrail);
		this.mStopButton.setEnabled(false);

		this.mTrailStartTable = super.mMobileServiceClient
				.getTable(TrailStart.class);
		this.mTrailStopTable = super.mMobileServiceClient
				.getTable(TrailStop.class);

		this.mStartButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				TrailStart trailStart = new TrailStart();
				trailStart.userId = mUserProfile.mId;
				
				trailStart.time =GetCurrentTime();

				LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
				Location location = lm
						.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				double longitude = location.getLongitude();
				double latitude = location.getLatitude();
				trailStart.longitude = String.valueOf(longitude);
				trailStart.latitude = String.valueOf(latitude);

				mTrailStartTable.insert(trailStart,
						new TableOperationCallback<TrailStart>() {

							@Override
							public void onCompleted(TrailStart created,
									Exception exception,
									ServiceFilterResponse serviceFilterResponse) {

								displayException(exception);
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
				TrailStop trailStop = new TrailStop();
				trailStop.userId = mUserProfile.mId;
				trailStop.time = GetCurrentTime();

				LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
				Location location = lm
						.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				double longitude = location.getLongitude();
				double latitude = location.getLatitude();
				trailStop.longitude = String.valueOf(longitude);
				trailStop.latitude = String.valueOf(latitude);

				TrailStart trailStart = getActiveTrail();
				trailStop.trailStartId = trailStart.id;
				trailStop.calroiesBurnt = "23";
				trailStop.heartRate = "98";

				mTrailStopTable.insert(trailStop,
						new TableOperationCallback<TrailStop>() {

							@Override
							public void onCompleted(TrailStop created,
									Exception exception,
									ServiceFilterResponse serviceFilterResponse) {
								displayException(exception);
								
								Gson gson = new Gson();
								Toast.makeText(getApplicationContext(), gson.toJson(created),Toast.LENGTH_LONG).show();
								mStartButton.setEnabled(true);
								mStopButton.setEnabled(false);
							}
						});
			}
		});
	}

	private String GetCurrentTime(){
		Time now = new Time();
		now.setToNow();
		return now.format2445();
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
		return super.onOptionsItemSelected(item);
	}
}
