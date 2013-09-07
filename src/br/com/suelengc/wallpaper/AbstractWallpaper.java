package br.com.suelengc.wallpaper;

import android.content.Context;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;
import br.com.suelengc.wallpaper.animation.Animation;
import br.com.suelengc.wallpaper.animation.AnimationFactory;
import br.com.suelengc.wallpaper.setting.Setting;

public abstract class AbstractWallpaper extends WallpaperService {
	
	protected abstract class AbstractEngine extends Engine {
		private Handler handler = new Handler();
		private Runnable iteration = new Runnable() {
			public void run() {
				action();
			}
		};

		private boolean isVisible;
		
		protected Context context;
		protected int interval, typeAnimation, colorAnimation;
		protected Animation animation;
		
		@Override
		public void onCreate(SurfaceHolder surfaceHolder) {
			super.onCreate(surfaceHolder);
			this.context = AbstractWallpaper.this;
		}
		
		@Override
		public void onDestroy() {
			super.onDestroy();
			handler.removeCallbacks(iteration);
		}

		@Override
		public void onVisibilityChanged(boolean visible) {
			isVisible = visible;
			if (visible) {
				getSettings();
				animation = AnimationFactory.getInstance(context, typeAnimation);
				action();
			} else {
				handler.removeCallbacks(iteration);
			}
		}

		@Override
		public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			action();
		}

		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			super.onSurfaceDestroyed(holder);
			isVisible = false;
			handler.removeCallbacks(iteration);
		}

		protected void iteration() {
			handler.removeCallbacks(iteration);
			if (isVisible) {
				handler.postDelayed(iteration, interval);
			}
		}
		
		protected abstract void drawFrame();
		
		private void action() {
			drawFrame();
			iteration();
		}
		
		private void getSettings() {
			typeAnimation = new Setting(context).getAnimationTypeValue();
			colorAnimation = new Setting(context).getColorAnimationValue();
			interval = new Setting(context).getIntervalValue();
		}
	}
}
