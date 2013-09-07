package br.com.suelengc.wallpaper.animation;

import android.graphics.Canvas;

public interface Animation {
	public void execute(Canvas canvas);
	public boolean isCycleCompleted();
	public void setCycleCompleted(boolean completed);
}
