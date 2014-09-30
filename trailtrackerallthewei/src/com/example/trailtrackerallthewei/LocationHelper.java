package com.example.trailtrackerallthewei;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

public class LocationHelper {

	private Context mContext;

	public LocationHelper(Context context) {
		this.mContext = context;
	}

	public String getFormattedAddress(double latitude, double longitude) {
		Geocoder geocoder = new Geocoder(this.mContext, Locale.getDefault());
		try {
			List<Address> addresses = geocoder.getFromLocation(latitude,
					longitude, 1);
			
			if (addresses.isEmpty()){
				return null;
			}
			
			Address address = addresses.get(0);

			String currentAddressLine;
			String formattedAddress = "";
			for (int i = 0; (currentAddressLine = address.getAddressLine(i)) != null; i++) {
				formattedAddress += currentAddressLine + ", ";
			}
			
			formattedAddress = formattedAddress.trim();
			return formattedAddress.substring(0, formattedAddress.length() - 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
