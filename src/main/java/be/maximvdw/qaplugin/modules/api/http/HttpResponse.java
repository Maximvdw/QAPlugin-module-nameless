package be.maximvdw.qaplugin.modules.api.http;

/**
 * HtmlResponse
 * 
 * HTML Utilities
 *
 * @version 07-2014
 * @author Maxim Van de Wynckel (Maximvdw)
 * @site http://www.mvdw-software.com/
 */
public class HttpResponse {
	private String source = "";
	private int code = 0;
	private String[] cookies = new String[0];

	public HttpResponse(String source, int code, String[] cookies) {
		this.source = source;
		this.code = code;
		this.cookies = cookies;
	}

	public String getSource() {
		return source;
	}

	public int getCode() {
		return code;
	}

	public String[] getCookies() {
		return cookies;
	}
}
