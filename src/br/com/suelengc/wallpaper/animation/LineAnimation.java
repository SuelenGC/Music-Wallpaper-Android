package br.com.suelengc.wallpaper.animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import br.com.suelengc.wallpaper.setting.Setting;

public class LineAnimation implements Animation {
	private int xLine;
	private boolean cycleCompleted = true;
	private Context context;
	
	public LineAnimation(Context context) {
		this.context = context;
	}
	
	@Override
	public void execute(Canvas canvas) {
		Paint paint = new Paint();
		paint = new Paint();
		paint.setColor(new Setting(context).getColorAnimationValue());
		paint.setStrokeWidth(5);
		paint.setAlpha(70);
	
		canvas.drawLine(xLine, 0, xLine, canvas.getHeight(), paint);

		if(xLine >= canvas.getWidth()) {  
			xLine = 0;
			setCycleCompleted(true);
		} else { 
			xLine += 10;
		}
	}

	@Override
	public boolean isCycleCompleted() {
		return cycleCompleted;
	}

	@Override
	public void setCycleCompleted(boolean completed) {
		cycleCompleted = completed;
	}
}
