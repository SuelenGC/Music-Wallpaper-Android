package br.com.suelengc.wallpaper.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;

public class Setting {

	public static final int MAX_LIMIT = 42;
	
	public static final String KEY_TYPE_ANIMATION = "typeAnimation";
	public static final int TYPE_ANIMATION_NONE = 0;
	public static final int TYPE_ANIMATION_LINE = 1;
	public static final int TYPE_ANIMATION_FILL = 2;

	public static final String KEY_COLOR_ANIMATION = "colorAnimation";
	public static final int COLOR_ANIMATION_RED = -65536;
	public static final int COLOR_ANIMATION_BLUE = -16776961;
	public static final int COLOR_ANIMATION_GREEN = -16711936;

	public static final String KEY_INTERVAL = "timeToChangeImage";
	public static final int INTERVAL_5_SECONDS = 5000;
	public static final int INTERVAL_30_MINUTES = 180000;
	public static final int INTERVAL_1_HOUR = 360000;

	public static final String KEY_LIMIT_TRACKS = "qtdOfTracks";
	public static final int QTD_TRACKS_1 = 1;
	public static final int QTD_TRACKS_2 = 2;
	public static final int QTD_TRACKS_3 = 3;
	public static final int QTD_TRACKS_4 = 4;
	public static final int QTD_TRACKS_5 = 5;
	public static final int QTD_TRACKS_6 = 6;
	public static final int QTD_TRACKS_7 = 7;
	public static final int QTD_TRACKS_8 = 8;
	public static final int QTD_TRACKS_9 = 9;
	public static final int QTD_TRACKS_10 = 10;
	public static final int QTD_TRACKS_0 = 0;

	public static final String KEY_USER_ID = "userId";
	
	Context context;
	SharedPreferences prefs;

	public Setting(Context context) {
		this.context = context;
		prefs = PreferenceManager.getDefaultSharedPreferences(context);
	}

	//Values
	public int getAnimationTypeValue() {
		return Integer.parseInt(prefs.getString(KEY_TYPE_ANIMATION, "1"));
	}

	public int getColorAnimationValue() {
		int returnValue = Color.RED;
		
		int color = Integer.parseInt(prefs.getString(KEY_COLOR_ANIMATION, "-65536"));;
		
		if (color == COLOR_ANIMATION_RED) {
			returnValue = Color.RED;
		
		} else if (color == COLOR_ANIMATION_BLUE) {
			returnValue = Color.BLUE;
		
		} else if (color == COLOR_ANIMATION_GREEN) {
			returnValue = Color.GREEN;
		}

		return returnValue;
	}

	public int getIntervalValue() {
		
		if (getAnimationTypeValue() != Setting.TYPE_ANIMATION_NONE) {
			return 100;
		} else {
			return Integer.parseInt(prefs.getString(KEY_INTERVAL, "5000"));
		}
	}

	public int getLimitValue() {
		int limit = Integer.parseInt(prefs.getString(KEY_LIMIT_TRACKS, "42"));
		
		if (limit == 0) 
			limit = MAX_LIMIT;

		return limit;
	}
	
	public String getUserId() {
		return prefs.getString(KEY_USER_ID, "User default");
	}
	
	//Summaries
	public String getColorAnimationSummary(int newValue) {
		String summary = "";
		
		if (newValue == Setting.COLOR_ANIMATION_RED) {
			summary = "Red";
			
		} else if (newValue == Setting.COLOR_ANIMATION_BLUE) {
			summary = "Blue";
			
		} else if (newValue == Setting.COLOR_ANIMATION_GREEN) {
			summary = "Green";
		}
		
		return summary;
	}
	
	public String getAnimationTypeSummary(int newValue) {
		String summary = "";

		if (newValue == Setting.TYPE_ANIMATION_FILL) {
			summary = "Fill music";
			
		} else if (newValue == Setting.TYPE_ANIMATION_LINE) {
			summary = "Line on music";
			
		} else if (newValue == Setting.TYPE_ANIMATION_NONE) {
			summary = "None";
		}

		return summary;
	}

	public String getIntervalSummary(int newValue) {
		String summary = "";

		if (getAnimationTypeValue() != TYPE_ANIMATION_NONE) {
			summary = "Animation's default (1 cycle)";

		} else {
			if (newValue == Setting.INTERVAL_1_HOUR) {
				summary = "1 hour";

			} else if (newValue == Setting.INTERVAL_30_MINUTES) {
				summary = "30 minutes";

			} else if (newValue == Setting.INTERVAL_5_SECONDS) {
				summary = "5 seconds";
			}
		}
		return summary;
	}

	public String getLimitSummary(int newValue) {
		String summary = "";
		
		if (newValue == MAX_LIMIT) {
			summary = "All tracks (limited " + Setting.MAX_LIMIT + ")";
			
		} else if (newValue == 1) { 
			summary = "1 track";
			
		} else { 
			summary = newValue + " tracks";
		}
		
		return summary;
	}
	
	public String getUserIdSummary(String newValue) {
		return prefs.getString(KEY_USER_ID, "User default");
	}
}
