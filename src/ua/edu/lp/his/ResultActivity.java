package ua.edu.lp.his;

import ua.edu.lp.his.utils.HttpRequestsUnit;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

public class ResultActivity extends Activity {
	
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		final String filePath = getIntent().getStringExtra("filePath");
		textView = (TextView) findViewById(R.id.textView2);
		
		String imei = getDeviceImei();
		SendImeiTask imeiTask = new SendImeiTask();
		imeiTask.execute(new String[] {imei, filePath});

	}

	private String getDeviceImei() {
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}
	
	private class SendPhotoTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			return HttpRequestsUnit.sendPhoto(params[0], params[1]);
		}
		
		@Override
	    protected void onPostExecute(String result) {
			Log.i("HIS", "Result: " + result);
			textView.setText(result);
	    }
	}
	
	private class SendImeiTask extends AsyncTask<String, Void, String> {
		String imei, filePath;

		@Override
		protected String doInBackground(String... params) {
			imei = params[0];
			filePath = params[1];
			return HttpRequestsUnit.sendImei(imei);
		}
		
		@Override
	    protected void onPostExecute(String result) {
			if("True".equals(result)) {
				textView.setText("Sending file " + filePath.substring(filePath.lastIndexOf("/")) + " on server...");
				SendPhotoTask task = new SendPhotoTask();
				task.execute(new String[] {imei, filePath});
			} else {
				textView.setText("Access denied for this device!");
			}
	    }
	}
	
}
