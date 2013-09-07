package br.com.suelengc.wallpaper.web;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;
import br.com.suelengc.wallpaper.model.Track;

import com.koushikdutta.urlimageviewhelper.UrlImageViewCallback;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

public class DowloadImageTrackTask extends AsyncTask<Track, Void, Track> {
	private Context context;
	private DowloadImageTrack callback;

	public interface DowloadImageTrack {
		public void onResult(Track track);
	}

	public DowloadImageTrackTask(Context context, DowloadImageTrack callback) {
		this.context = context;
		this.callback = callback;
	}

	@Override
	protected Track doInBackground(Track... params) {
		final Track track = params[0];
		UrlImageViewHelper.loadUrlDrawable(context, track.getWaveformUrl(), UrlImageViewHelper.CACHE_DURATION_INFINITE, new UrlImageViewCallback() {
			
			@Override
			public void onLoaded(ImageView imageView, Bitmap loadedBitmap, String url, boolean loadedFromCache) {
				callback.onResult(track);
			}
		});

		return track;
	}
}
