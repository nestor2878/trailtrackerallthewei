package com.example.trailtrackerallthewei;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

public class FitBitApiProvider {

	public FitBitApiProvider (){
		
	}
	
	public <T> T Get(String url, String fitBitUserId){
		try {
			Class<T> tt = null;
			Gson gson = new Gson();
			JSONObject jsonObject= new JSONObject("");
			return gson.fromJson(jsonObject.toString(), tt);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
