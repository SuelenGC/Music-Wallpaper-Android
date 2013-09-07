package br.com.suelengc.wallpaper.web;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class WebClient {
	private final String url;

	public WebClient(String url) {
		this.url = url;
	}
	
	public String get() {
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet get = new HttpGet(url);
			get.setHeader("Content-type", "application/json");
			HttpResponse response = httpClient.execute(get);
			String jsonDeResposta = EntityUtils.toString(response.getEntity());
			return jsonDeResposta;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
