package com.example.trailtrackerallthewei;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
//import android.location.LocationListener;
import com.google.android.gms.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentActivity;
//import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	private com.google.android.gms.maps.MapFragment mapFragment;
	private GoogleMap googleMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);

		// Getting Google Play availability status
		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getBaseContext());

		mapFragment = (com.google.android.gms.maps.MapFragment) getFragmentManager()
				.findFragmentById(R.id.map);
		googleMap = mapFragment.getMap();
		googleMap.setMyLocationEnabled(true);
		googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		googleMap.setMyLocationEnabled(true);

		GoogleMapOptions options = new GoogleMapOptions();
		options.describeContents();
		mapFragment.newInstance(options);

		/*
		 * //CameraUpdate center = CameraUpdateFactory.newLatLng(new
		 * LatLng(35.2635469, -120.6509188)); CameraUpdate center =
		 * CameraUpdateFactory.newLatLng(new LatLng(40.3887839,-106.7643755));
		 * CameraUpdate zoom = CameraUpdateFactory.zoomTo(13);
		 * googleMap.moveCamera(center); googleMap.animateCamera(zoom);
		 */

		// Showing status
		if (status != ConnectionResult.SUCCESS) { // Google Play Services are
													// not available

			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this,
					requestCode);
			dialog.show();

		} else {// Google Play Services are available

			// Getting reference to the SupportMapFragment of activity_main.xml
			// SupportMapFragment fm = (SupportMapFragment)
			// getSupportFragmentManager().findFragmentById(R.id.map);

			// Getting LocationManager object from System Service
			// LOCATION_SERVICE
			LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

			Criteria criteria = new Criteria();

			// Getting the name of the best provider
			String provider = locationManager.getBestProvider(criteria, true);

			// Getting Current Location
			Location location = locationManager.getLastKnownLocation(provider);

			if (location != null) {
				// Getting latitude of the current location
				double latitude = location.getLatitude();

				// Getting longitude of the current location
				double longitude = location.getLongitude();

				// get altitude
				double altitude = location.getAltitude();
				// Creating a LatLng object for the current location
				LatLng latLng = new LatLng(latitude, longitude);

				// Set polyline for track...
				BuildPolylineTrack1();

				// CameraUpdate center = CameraUpdateFactory.newLatLng(latLng);
				CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(
						35.4919, -120.65652000000001));
				CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
				googleMap.moveCamera(center);
				googleMap.animateCamera(zoom);

				addMarkers();

				// Get back the mutable Polyline
				// Polyline polyline = googleMap.addPolyline(rectOptions);
			}
			// locationManager.requestLocationUpdates(provider, 20000, 0, this);

		}
		// Create the adapter that will return a fragment for each of the three

		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		// mViewPager = (ViewPager) findViewById(R.id.pager);
		// mViewPager.setAdapter(mSectionsPagerAdapter);

		Intent intent = getIntent();
		// String message = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE);

	}

	private void addMarkers() {
		MarkerOptions markerOptions1 = new MarkerOptions()
				.position(new LatLng(35.491460000000004, -120.66156000000001))
				.title("Roberto").snippet("A wild roberto appeared!");

		MarkerOptions markerOptions2 = new MarkerOptions()
				.position(new LatLng(35.4919, -120.65652000000001))
				.title("Mountain Lion")
				.snippet(
						"Saw a mountain lion! It was ferocious and stole my baby!");

		MarkerOptions startMarker = new MarkerOptions()
				.position(new LatLng(35.49098, -120.66246000000001))
				.title("Start")
				.snippet("Started at 7:56am")
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

		MarkerOptions endMarker = new MarkerOptions()
				.position(new LatLng(35.490860000000005, -120.66249))
				.title("End")
				.snippet("Ended at 8:13am")
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

		List<MarkerOptions> markerOptions = new ArrayList<MarkerOptions>();
		markerOptions.add(markerOptions1);
		markerOptions.add(markerOptions2);
		markerOptions.add(startMarker);
		markerOptions.add(endMarker);

		for (int x = 0; x < markerOptions.size(); x = x + 1) {
			googleMap.addMarker(markerOptions.get(x));
		}
	}

	private void BuildPolylineTrack1() {
		// Set polyline for track...
		PolylineOptions rectOptions = new PolylineOptions()
				.add(new LatLng(35.49098, -120.66246000000001))
				.add(new LatLng(35.491510000000005, -120.66231))
				.add(new LatLng(35.491310000000006, -120.66204))
				.add(new LatLng(35.491310000000006, -120.66177))
				.add(new LatLng(35.491690000000006, -120.66195))
				.add(new LatLng(35.491460000000004, -120.66156000000001))
				.add(new LatLng(35.49141, -120.66131000000001))
				.add(new LatLng(35.491780000000006, -120.66155))
				.add(new LatLng(35.491530000000004, -120.66108000000001))
				.add(new LatLng(35.49179, -120.66117000000001))
				.add(new LatLng(35.491530000000004, -120.66089000000001))
				.add(new LatLng(35.49179, -120.66100000000002))
				.add(new LatLng(35.49148, -120.6607))
				.add(new LatLng(35.49134, -120.66037000000001))
				.add(new LatLng(35.490990000000004, -120.65992000000001))
				.add(new LatLng(35.49083, -120.65965000000001))
				.add(new LatLng(35.4909, -120.65947000000001))
				.add(new LatLng(35.49119, -120.65925000000001))
				.add(new LatLng(35.49165, -120.65898000000001))
				.add(new LatLng(35.49192, -120.65885000000002))
				.add(new LatLng(35.49203, -120.65846))
				.add(new LatLng(35.49181, -120.6577))
				.add(new LatLng(35.49175, -120.65732000000001))
				.add(new LatLng(35.4919, -120.65652000000001))
				.add(new LatLng(35.491980000000005, -120.65591))
				.add(new LatLng(35.49159, -120.65569))
				.add(new LatLng(35.491260000000004, -120.6556))
				.add(new LatLng(35.491080000000004, -120.65531000000001))
				.add(new LatLng(35.491130000000005, -120.65492))
				.add(new LatLng(35.49125, -120.65457))
				.add(new LatLng(35.491490000000006, -120.65438))
				.add(new LatLng(35.491530000000004, -120.65486000000001))
				.add(new LatLng(35.49185, -120.65519))
				.add(new LatLng(35.492430000000006, -120.65546))
				.add(new LatLng(35.49284, -120.65582))
				.add(new LatLng(35.492430000000006, -120.65546))
				.add(new LatLng(35.49185, -120.65519))
				.add(new LatLng(35.491530000000004, -120.65486000000001))
				.add(new LatLng(35.491490000000006, -120.65438))
				.add(new LatLng(35.49125, -120.65457))
				.add(new LatLng(35.491130000000005, -120.65492))
				.add(new LatLng(35.491080000000004, -120.65531000000001))
				.add(new LatLng(35.491260000000004, -120.6556))
				.add(new LatLng(35.49159, -120.65569))
				.add(new LatLng(35.491980000000005, -120.65591))
				.add(new LatLng(35.4919, -120.65652000000001))
				.add(new LatLng(35.49175, -120.65732000000001))
				.add(new LatLng(35.49181, -120.6577))
				.add(new LatLng(35.49203, -120.65846))
				.add(new LatLng(35.49192, -120.65885000000002))
				.add(new LatLng(35.49165, -120.65898000000001))
				.add(new LatLng(35.49119, -120.65925000000001))
				.add(new LatLng(35.4909, -120.65947000000001))
				.add(new LatLng(35.49083, -120.65965000000001))
				.add(new LatLng(35.490990000000004, -120.65992000000001))
				.add(new LatLng(35.49134, -120.66037000000001))
				.add(new LatLng(35.49148, -120.6607))
				.add(new LatLng(35.49179, -120.66100000000002))
				.add(new LatLng(35.491530000000004, -120.66089000000001))
				.add(new LatLng(35.49179, -120.66117000000001))
				.add(new LatLng(35.491530000000004, -120.66108000000001))
				.add(new LatLng(35.491780000000006, -120.66155))
				.add(new LatLng(35.49141, -120.66131000000001))
				.add(new LatLng(35.491460000000004, -120.66156000000001))
				.add(new LatLng(35.491690000000006, -120.66195))
				.add(new LatLng(35.491310000000006, -120.66177))
				.add(new LatLng(35.491310000000006, -120.66204))
				.add(new LatLng(35.491510000000005, -120.66231))
				.add(new LatLng(35.490860000000005, -120.66249))

				.width(20).visible(true)
				// .color(Color.argb(0, 255, 145, 0)); .
				.color(Color.RED);

		googleMap.addPolyline(rectOptions);

		// return rectOptions;
	}

	// Define the callback method that receives location updates

	/*
	 * public void onLocationChanged(Location location,PolylineOptions
	 * rectOptions) { // Report to the UI that the location was updated String
	 * msg = "Updated Location: " + Double.toString(location.getLatitude()) +
	 * "," + Double.toString(location.getLongitude());
	 * 
	 * // Getting latitude of the current location double latitude =
	 * location.getLatitude();
	 * 
	 * // Getting longitude of the current location double longitude =
	 * location.getLongitude();
	 * 
	 * // Creating a LatLng object for the current location LatLng latLng = new
	 * LatLng(latitude, longitude); rectOptions.add(latLng);
	 * //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show(); }
	 */

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

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(
				android.support.v4.app.FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		@Override
		public android.support.v4.app.Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			return PlaceholderFragment.newInstance(position + 1);
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		/*
		 * @Override public CharSequence getPageTitle(int position) { Locale l =
		 * Locale.getDefault(); switch (position) { case 0: return
		 * getString(R.string.title_section1).toUpperCase(l); case 1: return
		 * getString(R.string.title_section2).toUpperCase(l); case 2: return
		 * getString(R.string.title_section3).toUpperCase(l); } return null; }
		 */
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends
			android.support.v4.app.Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 * 
		 * private static final String ARG_SECTION_NUMBER = "section_number";
		 * 
		 * /** Returns a new instance of this fragment for the given section
		 * number.
		 */
		public static android.support.v4.app.Fragment newInstance(
				int sectionNumber) {
			android.support.v4.app.Fragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			// args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_maps, container,
					false);
			return rootView;
		}
	}

}
