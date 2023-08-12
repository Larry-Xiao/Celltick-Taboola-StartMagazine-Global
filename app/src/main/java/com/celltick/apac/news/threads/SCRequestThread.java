package com.celltick.apac.news.threads;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.celltick.apac.news.R;
import com.celltick.apac.news.app.StarNewsApp;
import com.celltick.apac.news.util.ApiUrl;
import com.celltick.apac.news.util.Constant;
import com.celltick.apac.news.util.SharedPreferencesUtils;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SCRequestThread extends Thread{
	private static final String TAG = SCRequestThread.class.getSimpleName();

	private Handler mHandler;
	private String mSMString = "";
	private int flag;
	private String mTitle;
	private int mRequestNum;
	private String URL;
	private String tmStp;

	public SCRequestThread(String title, Handler handler,int flag) {
		this.mHandler = handler;
		this.mTitle = title;
		this.flag = flag;
	}


	@Override
	public void run() {

		URL = getSCRequestBaseURL();
		Log.d(TAG,"URL="+getSCRequestBaseURL());
		requestSMAPI(URL);

	}

	public void sendMSG(int what,int flag,String info){
		Message msg = mHandler.obtainMessage();
		msg.what = what;
		msg.arg1 = flag;
		msg.obj = info;
		mHandler.sendMessage(msg);
	}

	public void requestSMAPI(String requestURL){
		OkHttpClient client = new OkHttpClient();
		client.setConnectTimeout(Constant.CONNECT_TIMEOUT, TimeUnit.SECONDS);
		client.setWriteTimeout(Constant.WRITE_TIMEOUT, TimeUnit.SECONDS);
		client.setReadTimeout(Constant.READ_TIMEOUT, TimeUnit.SECONDS);
		Request request = new Request.Builder().url(requestURL).build();
		Call call = client.newCall(request);
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Request request, IOException e) {
				Log.d(TAG,"onFailure, "+e.getMessage());
				String errString = e.getMessage();
				if (flag == Constant.HEADER_LOAD){
					if (errString.startsWith("Unable to resolve host \"contentapi.celltick.com\"")){
						sendMSG(Constant.SC_HEADER_FAILED,Constant.HEADER_LOAD,StarNewsApp.getContext().getString(R.string.err_return_no_connection));
					} else if (errString.contains("timed out") || errString.contains("timeout")){
						sendMSG(Constant.SC_HEADER_FAILED, Constant.HEADER_LOAD,StarNewsApp.getContext().getString(R.string.err_return_timeout));
					} else {
						sendMSG(Constant.SC_HEADER_FAILED, Constant.HEADER_LOAD,StarNewsApp.getContext().getString(R.string.err_return_unknown));
					}
				} else if (flag == Constant.BOTTOM_LOAD){
					if (errString.startsWith("Unable to resolve host \"contentapi.celltick.com\"")){
						sendMSG(Constant.SC_BOTTOM_FAILED,Constant.BOTTOM_LOAD,StarNewsApp.getContext().getString(R.string.err_return_no_connection));
					} else if (errString.contains("timed out") || errString.contains("timeout")){
						sendMSG(Constant.SC_BOTTOM_FAILED, Constant.BOTTOM_LOAD,StarNewsApp.getContext().getString(R.string.err_return_timeout));
					} else {
						sendMSG(Constant.SC_BOTTOM_FAILED, Constant.BOTTOM_LOAD,StarNewsApp.getContext().getString(R.string.err_return_unknown));
					}
				}

			}

			@Override
			public void onResponse(Response response) throws IOException {
				Log.d(TAG,"onResponse");
				String jsonData = response.body().string();
				mSMString = jsonData;
				if (flag == Constant.HEADER_LOAD){
					sendMSG(Constant.SC_HEADER_SUCCESS,Constant.HEADER_LOAD,mSMString);
				} else if (flag == Constant.BOTTOM_LOAD){
					sendMSG(Constant.SC_BOTTOM_SUCCESS,Constant.BOTTOM_LOAD,mSMString);
				}

			}
		});


	}


	public boolean isGettingJsonSucceedFromStartMagazineAPI(String requestURL){
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(requestURL).build();
		try {
			Response responses = client.newCall(request).execute();
			String jsonData = responses.body().string();

			if (jsonData.contains("totalItems")) {
				mSMString = jsonData;
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;

	}


	private String getSCRequestBaseURL(){
		return ApiUrl.getSCBaseURL()
				+"&userId="+SharedPreferencesUtils.getInstance().getString(Constant.UUID)
				+"&limit=2";
	}

}
