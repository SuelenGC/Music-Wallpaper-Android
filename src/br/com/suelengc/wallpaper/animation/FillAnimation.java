package br.com.suelengc.wallpaper.animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import br.com.suelengc.wallpaper.setting.Setting;

public class FillAnimation implements Animation {
	private int xRight, xLeft;
	private boolean cycleCompleted = true;
	private Context context;
	
	public FillAnimation(Context context) {
		this.context = context;
	}
	
	@Override
	public void execute(Canvas canvas) {
		Paint paint = new Paint();
		paint = new Paint();
		paint.setColor(new Setting(context).getColorAnimationValue());
		paint.setStrokeWidth(20);
		paint.setAlpha(50);
		
		if(xRight <= canvas.getWidth()) {
			xRight += 10;
			canvas.drawRect(0, 0, xRight, canvas.getHeight(), paint);
			
		} else {
			xLeft += 10;
			canvas.drawRect(xLeft, 0, canvas.getWidth(), canvas.getHeight(), paint);
			
			if (xLeft >= xRight) { 
				xRight = xLeft = 0;
				setCycleCompleted(true);
			}
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
