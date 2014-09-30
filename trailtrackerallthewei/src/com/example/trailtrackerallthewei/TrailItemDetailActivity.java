package com.example.trailtrackerallthewei;

import java.util.ArrayList;

import com.google.gson.Gson;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class TrailItemDetailActivity extends BaseActivity {

	public static final String SELECTED_TRAIL_ID = "selectedTrailId";
	private TrailItem mTrailItem;
	private ArrayList<TextView> textViews;
	private TextView mDetailTextViewFitBitActivities;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trail_item_detail);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			this.mTrailItem = extras.getParcelable(SELECTED_TRAIL_ID);
		}

		textViews = new ArrayList<TextView>();

		textViews.add((TextView) findViewById(R.id.detailTextView1));
		textViews.add((TextView) findViewById(R.id.detailTextView2));
		textViews.add((TextView) findViewById(R.id.detailTextView3));
		textViews.add((TextView) findViewById(R.id.detailTextView4));
		textViews.add((TextView) findViewById(R.id.detailTextView5));
		textViews.add((TextView) findViewById(R.id.detailTextView6));
		textViews.add((TextView) findViewById(R.id.detailTextView7));
		textViews.add((TextView) findViewById(R.id.detailTextView8));
		textViews.add((TextView) findViewById(R.id.detailTextView9));
		textViews.add((TextView) findViewById(R.id.detailTextView10));
		mDetailTextViewFitBitActivities = (TextView) findViewById(R.id.detailTextViewFitBitActivities);

		initializeView();
		initializeFitBitActivities();
	}

	private void initializeFitBitActivities() {
		FitBitApiProvider fitBitApiProvider = new FitBitApiProvider(this,
				super.mUserProfile);
		fitBitApiProvider
				.GetActivityStats(new FitBitApiProviderCallback<FitBitActivities>() {

					@Override
					public void onComplete(FitBitActivities data) {
						// TODO Auto-generated method stub
						Gson gson = new Gson();
						mDetailTextViewFitBitActivities.setText(gson
								.toJson(data));
					}

				});
	}

	private void initializeView() {
		LocationHelper locationHelper = new LocationHelper(this);

		textViews.get(0).setText(mTrailItem.startTime);
		textViews.get(1).setText(mTrailItem.stopTime);
		textViews.get(2).setText(
				mTrailItem.startLatitude + ", " + mTrailItem.startLongitude);
		textViews.get(3).setText(
				locationHelper.getFormattedAddress(
						Double.parseDouble(mTrailItem.startLatitude),
						Double.parseDouble(mTrailItem.startLongitude)));
		textViews.get(4).setText(
				mTrailItem.stopLatitude + ", " + mTrailItem.stopLongitude);
		textViews.get(5).setText(
				locationHelper.getFormattedAddress(
						Double.parseDouble(mTrailItem.stopLatitude),
						Double.parseDouble(mTrailItem.stopLongitude)));
		textViews.get(6).setText(mTrailItem.averageHeartRate);
		textViews.get(7).setText(mTrailItem.caloriesBurnt);
		textViews.get(8).setText(mTrailItem.sighting);
		textViews.get(9).setText(mTrailItem.notes);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trail_item_detail, menu);
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
