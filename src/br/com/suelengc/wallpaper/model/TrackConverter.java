package br.com.suelengc.wallpaper.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TrackConverter {
	
	public Track toTrack(JSONObject trackObj) {
		Track track = new Track();

		try {
			track.setTitle(trackObj.getString("title"));
			track.setWaveformUrl(trackObj.getString("waveform_url"));
			track.setPermalinkUrl(trackObj.getString("permalink_url"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return track;
	}

	public List<Track> toTracks(JSONArray trackArray) {
		List<Track> tracks = new ArrayList<Track>();

		try {
			for (int position = 0; position < trackArray.length(); position++) {
				Track track = toTrack((JSONObject) trackArray.get(position));
				tracks.add(track);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return tracks;
	}

}
