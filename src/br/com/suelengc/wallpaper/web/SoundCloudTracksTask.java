package br.com.suelengc.wallpaper.web;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.os.AsyncTask;
import br.com.suelengc.wallpaper.model.Track;
import br.com.suelengc.wallpaper.model.TrackConverter;
import br.com.suelengc.wallpaper.setting.Setting;
import br.com.suelengc.wallpaper.web.DowloadImageTrackTask.DowloadImageTrack;

public class SoundCloudTracksTask extends AsyncTask<Void, Void, List<Track>> {
	private Context context;
	private String url = "https://api.soundcloud.com/users/";
	private SoundCloudTracks callback;
	private String userId;
	private int limit;

	public interface SoundCloudTracks {
		public void onResult(Track track);
	}

	public SoundCloudTracksTask(Context context, SoundCloudTracks callBack) {
		this.context = context;
		this.callback = callBack;
	}

	@Override
	protected List<Track> doInBackground(Void... params) {
		List<Track> tracks = null;
		
		try {
			WebClient ws = new WebClient(getUrl());
			String json = ws.get();
			JSONArray jsonArray = new JSONArray(json);

			TrackConverter converter = new TrackConverter();
			tracks = converter.toTracks(jsonArray);

		} catch (JSONException e) {
			e.printStackTrace();
		} 
		return tracks;
	}

	private String getUrl() {
		Setting settings = new Setting(context);
		userId = settings.getUserId();
		limit = settings.getLimitValue();
		
		if (userId.equals("User default")) {
			userId ="pcalcado";
		}
		
		url += userId + "/favorites.json?consumer_key=apigee" + "&limit=" + limit;
		return url; 
	}

	@Override
	protected void onPostExecute(List<Track> tracks) {
		for (Track track : tracks) {
			new DowloadImageTrackTask(context, new DowloadImageTrack() {
				@Override
				public void onResult(Track track) {
					callback.onResult(track);
				}
			}).execute(track);
		}
	}
}
