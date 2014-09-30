package com.example.trailtrackerallthewei;

import static com.microsoft.windowsazure.mobileservices.MobileServiceQueryOperations.val;

import java.util.List;

import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ProgressBar;


public class TrailHistoryActivity extends BaseActivity {

	private ProgressBar mProgressBar;
	private TrailHistoryItemAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trail_history);
		
		mProgressBar = (ProgressBar) findViewById(R.id.loadingProgressBar);
		mProgressBar.setVisibility(ProgressBar.GONE);
		
		mAdapter = new TrailHistoryItemAdapter(this, R.layout.trail_history_item);
		ListView listViewToDo = (ListView) findViewById(R.id.listViewTrailHistoryItem);
		listViewToDo.setAdapter(mAdapter);
		
		refreshItemsFromTable();
	}
	
	private void refreshItemsFromTable(){
		// Get the items that weren't marked as completed and add them in the
				// adapter
				mTrailItemTable.where().field("userId").eq(mUserProfile.mId).execute(new TableQueryCallback<TrailItem>() {

					public void onCompleted(List<TrailItem> result, int count, Exception exception, ServiceFilterResponse response) {
						if (exception == null) {
							mAdapter.clear();

							for (TrailItem item : result) {
								mAdapter.add(item);
							}

						} else {
							displayException(exception);
						}
					}
				});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trail_history, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.menu_refresh) {
			refreshItemsFromTable();
		}
		
		return true;
	}
}
