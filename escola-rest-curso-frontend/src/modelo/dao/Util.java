package modelo.dao;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

public class Util {
    private final String USER_AGENT = "Mozilla/5.0";

//	private  String readAll(Reader rd) throws IOException {
//		StringBuilder sb = new StringBuilder();
//		int cp;
//		while ((cp = rd.read()) != -1) {
//			sb.append((char) cp);
//		}
//		return sb.toString();
//	}
//
//	public  JSONArray jsonArray(String url) throws IOException, JSONException {
//		InputStream is = new URL(url).openStream();
//		try {
//			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
//			String jsonText = readAll(rd);
//			if (jsonText.equals("null"))
//				return null;
//			JSONArray json = new JSONArray(jsonText);
//			return json;
//		} finally {
//			is.close();
//		}
//	}
//	public  JSONObject jsonObj(String url) throws IOException, JSONException {
//		InputStream is = new URL(url).openStream();
//		try {
//			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
//			String jsonText = readAll(rd);
//			if (jsonText.equals("null"))
//				return null;
//			JSONObject json = new JSONObject(jsonText);
//			return json;
//		} finally {
//			is.close();
//		}
//	}
	public String sendPost(String url,String jsonRuim, String method) throws IOException {
		String json=URLEncoder.encode(jsonRuim, "UTF-8");
		URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
    
        con.setRequestMethod(method);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

	
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(json);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
       

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream(),Charset.forName("UTF-8")));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
		return response.toString();
	}
	
	public String sendGet(String url, String method) throws Exception {

	        URL obj = new URL(url);
	        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

	        con.setRequestMethod(method);

	        con.setRequestProperty("User-Agent", USER_AGENT);

	        int responseCode = con.getResponseCode();
	        
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(con.getInputStream(),Charset.forName("UTF-8")));
	        String inputLine;
	        StringBuffer response = new StringBuffer();

	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
	        in.close();

	        return response.toString();

	    }
}
