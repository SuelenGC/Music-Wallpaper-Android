package br.com.suelengc.wallpaper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class IntentUtil {
	private Context context;
	
	public IntentUtil(Context context) {
		this.context = context;
	}
	
	public void openOnBrowser(String url) {
		Intent intentSite = new Intent(Intent.ACTION_VIEW);
		intentSite.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intentSite.setData(Uri.parse(url));
		context.startActivity(intentSite);
	}
}
