package com.example.trailtrackerallthewei;

import java.net.MalformedURLException;
import android.content.Context;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.ServiceFilter;

/**
 * Factory for MobileServiceClient
 * @author nestor.reyes
 *
 */
public class MobileServiceClientFactory {
	private static final String APPURL = "https://trailtrackerallthewei.azure-mobile.net/";
	private static final String APPKEY = "bEFiYcJkBUhkOzXCexJHqEwIjPOVwB41";
	
	public static MobileServiceClient Create(Context context, ServiceFilter serviceFilter) throws MalformedURLException{
		// Create the Mobile Service Client instance, using the provided
					// Mobile Service URL and key

					return new MobileServiceClient(
							APPURL,
							APPKEY,
							context).withFilter(serviceFilter);
	}
	
	public static MobileServiceClient Create(Context context) throws MalformedURLException{
		// Create the Mobile Service Client instance, using the provided
					// Mobile Service URL and key

					return new MobileServiceClient(
							APPURL,
							APPKEY,
							context);
	}
}
