package br.com.suelengc.wallpaper.model;


public class Track {

	private String title;
	private String waveformUrl;
	private String permalinkUrl;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWaveformUrl() {
		return waveformUrl;
	}
	public void setWaveformUrl(String waveform_url) {
		this.waveformUrl = waveform_url;
	}
	public String getPermalinkUrl() {
		return permalinkUrl;
	}
	public void setPermalinkUrl(String permalink_url) {
		this.permalinkUrl = permalink_url;
	}
	
	@Override
	public String toString() {
		return "Title: "       + getTitle()        + "\n" + 
			   "Waveform: "    + getWaveformUrl()  + "\n" + 
			   "Link: "        + getPermalinkUrl() + "\n";
	}
}
