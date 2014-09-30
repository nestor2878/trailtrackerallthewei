package com.example.trailtrackerallthewei;

import com.google.gson.Gson;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FinalizeTrailItemActivity extends BaseActivity {

	private EditText mSighting;
	private EditText mNotes;
	private TrailItem mTrailItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_finalize_trail_item);
		
		this.mSighting = (EditText) findViewById(R.id.editSightingText);
		this.mNotes = (EditText) findViewById(R.id.editNotesText);
		
		initFinalize();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.finalize_trail_item, menu);
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
		if(id == R.id.trail_item_save){
			item.setEnabled(false);
			saveTrailItem();
		}
		return super.onOptionsItemSelected(item);
	}

	private void saveTrailItem() {
		mTrailItem.sighting = mSighting.getText().toString();
		mTrailItem.notes = mNotes.getText().toString();
		mTrailItemTable.update(mTrailItem, new TableOperationCallback<TrailItem>(){

			@Override
			public void onCompleted(TrailItem updated, Exception exception,
					ServiceFilterResponse arg2) {
				if(exception != null){
					displayException(exception);
					return;
				}
				Toast.makeText(getApplicationContext(),
						"saved..", Toast.LENGTH_LONG)
						.show();
				finish();
				
			}});
	}
	
	private void initFinalize(){
		mTrailItem = getActiveTrail();
		mTrailItem.stopTime = getCurrentTime();

		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Location location = lm
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		double longitude = location.getLongitude();
		double latitude = location.getLatitude();
		mTrailItem.stopLongitude = String.valueOf(longitude);
		mTrailItem.stopLatitude = String.valueOf(latitude);

		mTrailItem.caloriesBurnt = "23";
		mTrailItem.averageHeartRate = "98";
	}
}
