package br.com.suelengc.wallpaper.setting;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.view.View;
import br.com.suelengc.challenge.R;
import br.com.suelengc.wallpaper.Wallpaper;

public class SettingActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {

	private Setting settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
		settings = new Setting(this);
		loadSummaryPreferences();
	}

	@Override
	protected void onResume() {
		super.onResume();
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}

	public void onClick(View view) {
		Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
		intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, new ComponentName(this, Wallpaper.class));
		startActivity(intent);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		loadSummary(key);
	}

	@Override
	protected void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}

	private void loadSummaryPreferences() {
		loadSummary(Setting.KEY_TYPE_ANIMATION);
		loadSummary(Setting.KEY_COLOR_ANIMATION);
		loadSummary(Setting.KEY_INTERVAL);
		loadSummary(Setting.KEY_LIMIT_TRACKS);
		loadSummary(Setting.KEY_USER_ID);
	}

	private void loadSummary(String key) {
		String summary = "";

		if (key.equals(Setting.KEY_USER_ID)) {
			summary = settings.getUserIdSummary(settings.getUserId());

		} else if (key.equals(Setting.KEY_COLOR_ANIMATION)) {
			summary = settings.getColorAnimationSummary(settings.getColorAnimationValue());

		} else if (key.equals(Setting.KEY_TYPE_ANIMATION)) {
			summary = settings.getAnimationTypeSummary(settings.getAnimationTypeValue());

		} else if (key.equals(Setting.KEY_INTERVAL)) {
			summary = settings.getIntervalSummary(settings.getIntervalValue());

		} else if (key.equals(Setting.KEY_LIMIT_TRACKS)) {
			summary = settings.getLimitSummary(settings.getLimitValue());
		}

		Preference pref = findPreference(key);
		pref.setSummary(summary);
	}
}
