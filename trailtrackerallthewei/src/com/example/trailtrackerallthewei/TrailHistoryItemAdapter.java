package com.example.trailtrackerallthewei;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Adapter to bind a ToDoItem List to a view
 */
public class TrailHistoryItemAdapter extends ArrayAdapter<TrailItem> {

	/**
	 * Adapter context
	 */
	Context mContext;

	/**
	 * Adapter View layout
	 */
	int mLayoutResourceId;

	public TrailHistoryItemAdapter(Context context, int layoutResourceId) {
		super(context, layoutResourceId);

		mContext = context;
		mLayoutResourceId = layoutResourceId;
	}

	/**
	 * Returns the view for a specific item on the list
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;

		final TrailItem currentItem = getItem(position);

		if (row == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			row = inflater.inflate(mLayoutResourceId, parent, false);
		}
		row.setTag(currentItem);
		final TextView startTime = (TextView) row.findViewById(R.id.trailHistoryStart);
		startTime.setText(currentItem.startTime);
		final TextView location = (TextView) row.findViewById(R.id.trailHistoryLocation);
		location.setText(currentItem.startLatitude+","+currentItem.startLongitude);
		
		row.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Toast.makeText(getContext(), "clicked", Toast.LENGTH_LONG).show();
			}
			
		});
		
		return row;
	}

}
