package com.example.trailtrackerallthewei;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.gson.Gson;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
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
		resetTrackingControls();

		this.mStartButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				TrailItem trailStart = new TrailItem();
				trailStart.userId = mUserProfile.mId;

				trailStart.startTime = getCurrentTime();

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
				startNewActivity(FinalizeTrailItemActivity.class);
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		resetTrackingControls();
	}

	private void resetTrackingControls() {
		TrailItem item = getActiveTrail();

		this.mStartButton.setEnabled(item == null);
		this.mStopButton.setEnabled(item != null);
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
			startNewActivity(TrailHistoryActivity.class);
		}
		return super.onOptionsItemSelected(item);
	}
}
