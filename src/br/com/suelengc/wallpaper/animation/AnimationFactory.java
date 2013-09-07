package br.com.suelengc.wallpaper.animation;

import android.content.Context;
import br.com.suelengc.wallpaper.setting.Setting;

public class AnimationFactory {

	public static Animation getInstance(Context context, int typeAnimation) {
		if (typeAnimation == Setting.TYPE_ANIMATION_FILL)
			return new FillAnimation(context);
		
		if (typeAnimation == Setting.TYPE_ANIMATION_LINE)
			return new LineAnimation(context);
		
		return null;
	}

}
