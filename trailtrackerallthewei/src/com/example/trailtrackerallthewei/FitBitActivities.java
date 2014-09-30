package com.example.trailtrackerallthewei;

public class FitBitActivities {

	public FitBitBest best;
	public FitBitLifetime lifetime;
	
	public class DateValuePair {
		public String date;
		public String value;
	}
	
	public class FitBitBestItems{
		public DateValuePair caloriesOut;
		public DateValuePair distance;
		public DateValuePair floors;
		public DateValuePair steps;
	}
	
	public class FitBitBest{
		public FitBitBestItems total;
		public FitBitBestItems tracker;
	}
	
	public class FitBitLifeTimeItems{
		public String caloriesOut;
		public String distance;
		public String floors;
		public String steps;
	}
	
	public class FitBitLifetime{
		public FitBitLifeTimeItems total;
		public FitBitLifeTimeItems tracker;
	}

}
