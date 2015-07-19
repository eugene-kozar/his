package ua.edu.lp.his.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import ua.edu.lp.his.crypto.CryptoUnit;

public class HttpRequestsUnit {
	
	private static final String serverUrl = "http://192.168.42.32:8080/his";

	public static String sendPhoto(String imei, String filePath) {
		String result = "";
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			byte[] data = new byte[(int) file.length()];
			fis.read(data);
			fis.close();
			data = CryptoUnit.encodeData(data);
			ByteArrayBody body = new ByteArrayBody(data, file.getName());
			
			MultipartEntity multipartEntity = new MultipartEntity();
            multipartEntity.addPart("file", body);
            multipartEntity.addPart("imei", new StringBody(imei));
            
			String url = serverUrl + "/photo";
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);			
			httpPost.setEntity(multipartEntity);
			
            HttpResponse httpResponse = httpClient.execute(httpPost);
            result += EntityUtils.toString(httpResponse.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String sendImei(String imei) {
		String result = "";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(serverUrl + "/imei");
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("imei", imei));
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpClient.execute(httpPost);
			result += EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String makeTestGetRequest() {
		String result = "";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(serverUrl + "/test");
			HttpResponse response = httpClient.execute(httpGet);
			result += EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
