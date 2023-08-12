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

public class BottomArticleRequestThread extends Thread{
	private static final String TAG = BottomArticleRequestThread.class.getSimpleName();

	private Handler mHandler;
	private String mSMString= "";
	private String mTitle;
	private int offset;

	public BottomArticleRequestThread(Handler handler,int offset) {
		this.mHandler = handler;
		this.offset = offset;
	}

	public BottomArticleRequestThread(String title,Handler handler,int offset) {
		this.mHandler = handler;
		this.offset = offset;
		this.mTitle = title;
	}

	@Override
	public void run() {
		String url;

		if (mTitle.equals("001")) {
			url = ApiUrl.getMagazineBaseURL()
					+"&userId="+SharedPreferencesUtils.getInstance().getString(Constant.UUID)
					+"&countryCode="+ SharedPreferencesUtils.getInstance().getString(Constant.COUNTRY_CODE)
					+"&language="+SharedPreferencesUtils.getInstance().getString(Constant.LANGUAGE_CODE)
					+ "&limit=" + Constant.NUM_SM_NORMAL_REQUEST + "&offset=" + offset;
		} else {
			url = ApiUrl.getMagazineBaseURL()
					+"&userId="+SharedPreferencesUtils.getInstance().getString(Constant.UUID)
					+"&countryCode="+ SharedPreferencesUtils.getInstance().getString(Constant.COUNTRY_CODE)
					+"&language="+SharedPreferencesUtils.getInstance().getString(Constant.LANGUAGE_CODE)
					+"&category="+mTitle
					+ "&limit=" + Constant.NUM_SM_NORMAL_REQUEST + "&offset=" + offset;
		}
		Log.d(TAG,"bottom url = " +url);

		requestSMAPI(url);

//		if (url != null && url != "") {
//			if (isGettingJsonSucceedFromStartMagazineAPI(url)) {
//				sendMSG(Constant.SUCCESS,Constant.BOTTOM_LOAD,mSMString);
//			} else if (!isGettingJsonSucceedFromStartMagazineAPI(url)) {
//				{
//					sendMSG(Constant.ERR_RETURN,Constant.BOTTOM_LOAD,"");
//				}
//
//			}
//		}
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

				if (errString.startsWith("Unable to resolve host \"contentapi.celltick.com\"")){
					sendMSG(Constant.ERR_RETURN_NO_CONNECTION,Constant.BOTTOM_LOAD, StarNewsApp.getContext().getString(R.string.err_return_no_connection));
				} else if (errString.contains("timed out") || errString.contains("timeout")){
					sendMSG(Constant.ERR_RETURN_TIMEOUT,Constant.BOTTOM_LOAD,StarNewsApp.getContext().getString(R.string.err_return_timeout));
				} else {
					sendMSG(Constant.ERR_RETURN_UNKNOWN,Constant.BOTTOM_LOAD, StarNewsApp.getContext().getString(R.string.err_return_unknown));
				}
			}

			@Override
			public void onResponse(Response response) throws IOException {
				Log.d(TAG,"onResponse");
				String jsonData = response.body().string();
				mSMString = jsonData;
				sendMSG(Constant.SUCCESS,Constant.BOTTOM_LOAD,mSMString);
			}
		});


	}


	public void sendMSG(int what,int flag,String info){
		Message msg = mHandler.obtainMessage();
		msg.what = what;
		msg.arg1 = flag;
		msg.obj = info;
		mHandler.sendMessage(msg);
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

}
