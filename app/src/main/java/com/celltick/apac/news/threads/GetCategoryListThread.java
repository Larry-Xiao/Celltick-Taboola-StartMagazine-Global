package com.celltick.apac.news.threads;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.celltick.apac.news.R;
import com.celltick.apac.news.app.StarNewsApp;
import com.celltick.apac.news.util.ApiUrl;
import com.celltick.apac.news.util.Constant;
import com.celltick.apac.news.util.SharedPreferencesHelper;
import com.celltick.apac.news.util.SharedPreferencesUtils;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class GetCategoryListThread extends Thread{
	private static final String TAG = GetCategoryListThread.class.getSimpleName();

	private Handler mHandler;
	private SharedPreferencesHelper mSPHelper;
	private String mSMString= "";
	private int i;

	public GetCategoryListThread(Handler handler) {
		this.mHandler = handler;
	}

	public GetCategoryListThread(Handler handler, SharedPreferencesHelper mSPHelper) {
		this.mHandler = handler;
		this.mSPHelper = mSPHelper;
	}


	@Override
	public void run() {
//		String url = ApiUrl.getCategoryListURL()
//				+"&countryCode="+ SharedPreferencesUtils.getInstance().getString("countryCode","US")
//				+"&language="+SharedPreferencesUtils.getInstance().getString("languageCode","en");
//		Log.d(TAG,"category list url = " +url);
//		if (url != null && url != "") {
//			if (isGettingCategoryListSucceed(url)) {
//				sendMSG(Constant.STATUS_CATEGORY_LIST_REQ_END,mSMString);
//			} else if (!isGettingCategoryListSucceed(url)) {
//				{
//					sendMSG(Constant.ERR_RETURN,Constant.BOTTOM_LOAD,"");
//				}
//
//			}
//		}

		requestCategoryListAPI(getCategoryListRequestURL());
	}

	public void sendMSG(int what,int flag,String info){
		Message msg = mHandler.obtainMessage();
		msg.what = what;
		msg.arg1 = flag;
		msg.obj = info;
		mHandler.sendMessage(msg);
	}

	public void sendMSG(int what,String info){
		Message msg = mHandler.obtainMessage();
		msg.what = what;
		msg.obj = info;
		mHandler.sendMessage(msg);
	}

	public void sendMSG(int what){
		Message msg = mHandler.obtainMessage();
		msg.what = what;
		mHandler.sendMessage(msg);
	}


	public void requestCategoryListAPI(String requestURL){
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

				if (errString.startsWith("Unable to resolve host \"contentapi.celltick.com\"")){
					sendMSG(Constant.ERR_RETURN_NO_CONNECTION,Constant.HEADER_LOAD, StarNewsApp.getContext().getString(R.string.err_return_no_connection));
				} else if (errString.contains("timed out") || errString.contains("timeout") ){
					sendMSG(Constant.ERR_RETURN_TIMEOUT,Constant.HEADER_LOAD, StarNewsApp.getContext().getString(R.string.err_return_timeout));
				} else {
					sendMSG(Constant.ERR_RETURN_UNKNOWN,Constant.HEADER_LOAD, StarNewsApp.getContext().getString(R.string.err_return_unknown));
				}
			}

			@Override
			public void onResponse(Response response) throws IOException {
				Log.d(TAG,"onResponse");
				String jsonData = response.body().string();
				mSMString = jsonData;
				sendMSG(Constant.SUCCESS,Constant.HEADER_LOAD,mSMString);
			}
		});


	}

	private String getCategoryListRequestURL(){
		return ApiUrl.getCategoryListURL()
				+"&userId="+SharedPreferencesUtils.getInstance().getString(Constant.UUID)
				+"&countryCode="+ SharedPreferencesUtils.getInstance().getString(Constant.COUNTRY_CODE)
				+"&language=" + SharedPreferencesUtils.getInstance().getString(Constant.LANGUAGE_CODE);
	}

	public boolean isGettingCategoryListSucceed(String requestURL){
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

}
