package fadhilah.ramadhan.covid19stats.util.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.apache.http.conn.ConnectTimeoutException;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import fadhilah.ramadhan.covid19stats.R;
import fadhilah.ramadhan.covid19stats.component.ProgresDialog;
import fadhilah.ramadhan.covid19stats.util.Constant;
import fadhilah.ramadhan.covid19stats.util.Utility;

public class CallService  extends AsyncTask<Object, String, String> {
	private Context applicationContext;
	private AsyncTaskCompleteListener<Object> callback;
	private ProgresDialog loading;
	private boolean withLoading = true;
	private String result;

	public CallService() {
		// TODO Auto-generated constructor stub
	}

	public CallService(Context applicationContext,
                       AsyncTaskCompleteListener<Object> callback) {
		this.applicationContext = applicationContext;
		this.callback = callback;
	}

	public CallService(Context applicationContext,
                       AsyncTaskCompleteListener<Object> callback, boolean withLoading) {
		this.applicationContext = applicationContext;
		this.callback = callback;
		this.withLoading = withLoading;
	}

	@Override
	protected void onPreExecute() {
		if(withLoading){
			loading = new ProgresDialog(applicationContext);
			loading.show();
		}
	}

	@Override
	protected String doInBackground(Object... params) {
		String url = (String) params[0];
		String method = (String) params[1];

		try {
			System.out.println("url : " + Constant.BASE_URL + url);
			OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder()
					.url(Constant.BASE_URL + url)
					.method(method, null)
					.build();
			Response response = client.newCall(request).execute();

			result = response.body().string();
		}  catch (UnknownHostException e){
			result = "{\"errorCode\":\"true\", \"fullMessage\":\""+applicationContext.getResources().getString(R.string.error_noInternet)+"\"}";
			e.printStackTrace();
		} catch (SocketException e) {
			result = "{\"errorCode\":\"true\", \"fullMessage\":\""+e.getMessage()+"\"}";
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			result = "{\"errorCode\":\"true\", \"fullMessage\":\""+e.getMessage()+"\"}";
			e.printStackTrace();
		} catch (ConnectTimeoutException e) {
			result = "{\"errorCode\":\"true\", \"fullMessage\":\""+e.getMessage()+"\"}";
			e.printStackTrace();
		} catch (Exception e) {
			result = "{\"errorCode\":\"true\", \"fullMessage\":\""+e.getMessage()+"\"}";
			e.printStackTrace();
		}
		return result;
	}


	@Override
	protected void onPostExecute(String result) {
		if (withLoading && loading.isShowing()) {
			try {
				loading.dismiss();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		callback.onTaskComplete(result);

	}

}
