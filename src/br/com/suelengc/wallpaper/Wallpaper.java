package br.com.suelengc.wallpaper;

import java.util.ArrayList;
import java.util.List;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.SurfaceHolder;
import br.com.suelengc.challenge.R;
import br.com.suelengc.wallpaper.model.Track;
import br.com.suelengc.wallpaper.setting.Setting;
import br.com.suelengc.wallpaper.web.InternetConnection;
import br.com.suelengc.wallpaper.web.SoundCloudTracksTask;
import br.com.suelengc.wallpaper.web.SoundCloudTracksTask.SoundCloudTracks;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

public class Wallpaper extends AbstractWallpaper {
	private static Bitmap backgroundImage, defaultWaveform;
	
	@Override
	public Engine onCreateEngine() {
		loadStaticImages();
		return new WallpaperEngine();
	}

	private void loadStaticImages() {
		BitmapFactory.Options options = new Options();
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inSampleSize = 4;
		backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.background2, options);
		defaultWaveform = BitmapFactory.decodeResource(getResources(), R.drawable.waveform, options);
	}

	private class WallpaperEngine extends AbstractEngine {
		private Track currentTrack;
		private int trackIndex;
		private int screenHeight, screenMinimumWidth;
		private List<Track> tracks = new ArrayList<Track>();
		
		private final SoundCloudTracks callback = new SoundCloudTracks() {
			@Override
			public void onResult(Track track) {
				if (tracks.size() == 1) {
					currentTrack = tracks.get(0);
				}
				tracks.add(track);
			}
		};

		@Override
		public void onCreate(SurfaceHolder surfaceHolder) {
			super.onCreate(surfaceHolder);
			
			WallpaperManager wm = WallpaperManager.getInstance(context);
			screenMinimumWidth = wm.getDesiredMinimumWidth();
			
			if (InternetConnection.isOnline(Wallpaper.this) && !isPreview()) {
				loadTracks();
			}
		}

		@Override
		public Bundle onCommand(String action, int x, int y, int z, Bundle extras, boolean resultRequested) {
			if (hasTrack()) {
				if (action.equals(WallpaperManager.COMMAND_TAP) && isCenterScreen(y)) {
					new IntentUtil(Wallpaper.this).openOnBrowser(currentTrack.getPermalinkUrl());
				}
			}
			return super.onCommand(action, x, y, z, extras, resultRequested);
		}

		@Override
		public void drawFrame() {
			SurfaceHolder holder = getSurfaceHolder();
			Canvas canvas = holder.lockCanvas();

			try {
				if (canvas != null) {
					screenHeight = canvas.getHeight();
					drawBackground(canvas);
					
					if (!hasTrack()) {
						if (hasAnimation()) {
							if (animation.isCycleCompleted()) {
								animation.setCycleCompleted(false);
							}
							animation.execute(canvas);
						}
						
						drawDefaultWaveform(canvas);

						if (!InternetConnection.isOnline(Wallpaper.this)) {
							setText(canvas, getString(R.string.without_internet_connection));
						} else {
							setText(canvas, "One - Mettalica");
						}
						
					} else {
						if (hasAnimation()) {
							if (animation.isCycleCompleted()) {
								animation.setCycleCompleted(false);
								currentTrack = getNextTrack();
								trackIndex++;
							}
							animation.execute(canvas);

						} else {
							currentTrack = getNextTrack();
							trackIndex++;
						}
						drawWaveform(canvas);
					}
				}
			} finally {
				if (canvas != null) {
					holder.unlockCanvasAndPost(canvas);
				}
			}
		}

		private void drawDefaultWaveform(Canvas canvas) {
			RectF rectF = new RectF(0, 0, screenMinimumWidth, canvas.getHeight());
			canvas.drawBitmap(defaultWaveform, null, rectF, null);
		}

		private boolean hasAnimation() {
			return (typeAnimation != Setting.TYPE_ANIMATION_NONE && animation != null);
		}

		private void loadTracks() {
			new SoundCloudTracksTask(Wallpaper.this, callback).execute();
		}

		private boolean isCenterScreen(int y) {
			int top = (this.screenHeight / 2 - 40);
			int bottom = (this.screenHeight / 2 + 40);

			return y > top && y < bottom;
		}

		private boolean hasTrack() {
			return tracks.size() > 0 && UrlImageViewHelper.getCachedBitmap(tracks.get(0).getWaveformUrl()) != null;
		}

		private void drawBackground(Canvas canvas) {
			RectF rectF = new RectF(0, 0, screenMinimumWidth, canvas.getHeight());
			canvas.drawBitmap(backgroundImage, null, rectF, null);
		}

		private void drawWaveform(Canvas canvas) {
			if (currentTrack != null) {
				Bitmap image = UrlImageViewHelper.getCachedBitmap(currentTrack.getWaveformUrl());

				if (image != null) {
					RectF rectF = new RectF(0, 0, screenMinimumWidth, canvas.getHeight());
					canvas.drawBitmap(image, null, rectF, null);
					setText(canvas, currentTrack.getTitle());
				}
			}
		}

		private void setText(Canvas canvas, String text) {
			Paint p = new Paint();
			p.setTextSize(20);
			p.setAntiAlias(true);
			p.setColor(Color.WHITE);

			float w = p.measureText(text, 0, text.length());
			int offset = (int) w / 2;
			int x = canvas.getWidth() / 2 - offset;
			int y = canvas.getHeight() / 2;

			canvas.drawText(text, x, y, p);
		}

		private Track getNextTrack() {
			if (trackIndex >= tracks.size())
				trackIndex = 0;

			return tracks.get(trackIndex);
		}
	}
}