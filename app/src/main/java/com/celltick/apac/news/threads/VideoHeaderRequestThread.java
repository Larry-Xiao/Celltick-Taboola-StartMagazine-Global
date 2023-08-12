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

public class VideoHeaderRequestThread extends Thread{
	private static final String TAG = VideoHeaderRequestThread.class.getSimpleName();

	private Handler mHandler;
	private String mSMVideoString = "";
	private String mTitle;
	private int mRequestNum;
	private String URL;
	private String tmStp;

	public VideoHeaderRequestThread(String title, Handler handler) {
		this.mHandler = handler;
		this.mTitle = title;
	}

	public VideoHeaderRequestThread(String title, Handler handler, int requestNum) {
		this.mHandler = handler;
		this.mTitle = title;
		this.mRequestNum = requestNum;
	}

	public VideoHeaderRequestThread(String title, Handler handler, int requestNum, String timeStamp) {
		this.mHandler = handler;
		this.mTitle = title;
		this.mRequestNum = requestNum;
		this.tmStp = timeStamp;
	}

	@Override
	public void run() {

		if (mRequestNum == 0) {
			if (mTitle.equals("002")) {
				URL = ApiUrl.getVideoBaseURL()
						+"&userId="+ SharedPreferencesUtils.getInstance().getString(Constant.UUID)
						+"&countryCode="+ SharedPreferencesUtils.getInstance().getString(Constant.COUNTRY_CODE)
						+"&language=" + SharedPreferencesUtils.getInstance().getString(Constant.LANGUAGE_CODE)
						+"&limit="+Constant.NUM_SM_NORMAL_REQUEST+"&offset="+0;
			} else {
				URL = ApiUrl.getVideoURL(mTitle)
						+"&userId="+ SharedPreferencesUtils.getInstance().getString(Constant.UUID)
						+"&countryCode="+ SharedPreferencesUtils.getInstance().getString(Constant.COUNTRY_CODE)
						+"&language=" + SharedPreferencesUtils.getInstance().getString(Constant.LANGUAGE_CODE)
						+"&limit="+Constant.NUM_SM_NORMAL_REQUEST+"&offset="+0;
			}

		} else {
			if (mTitle.equals("002")) {
				URL = ApiUrl.getVideoBaseURL()
						+"&userId="+ SharedPreferencesUtils.getInstance().getString(Constant.UUID)
						+"&countryCode="+ SharedPreferencesUtils.getInstance().getString(Constant.COUNTRY_CODE)
						+"&language=" + SharedPreferencesUtils.getInstance().getString(Constant.LANGUAGE_CODE)
						+"&limit="+Constant.NUM_SM_NORMAL_REQUEST+"&offset="+0;
			} else {
				URL = ApiUrl.getVideoURL(mTitle)
						+"&userId="+ SharedPreferencesUtils.getInstance().getString(Constant.UUID)
						+"&countryCode="+ SharedPreferencesUtils.getInstance().getString(Constant.COUNTRY_CODE)
						+"&language=" + SharedPreferencesUtils.getInstance().getString(Constant.LANGUAGE_CODE)
						+"&limit="+Constant.NUM_SM_NORMAL_REQUEST+"&offset="+0;
			}

		}

		Log.d(TAG,"URL="+URL);
		requestSMVideoAPI(URL);

//		if (isGettingJsonSucceedFromStartMagazineAPI(URL)) {
//			sendMSG(Constant.SUCCESS,Constant.HEADER_LOAD, mSMVideoString);
//		} else if (!isGettingJsonSucceedFromStartMagazineAPI(URL)) {
//			sendMSG(Constant.ERR_RETURN,Constant.HEADER_LOAD,"");
//		}
	}

	public void sendMSG(int what,int flag,String info){
		Message msg = mHandler.obtainMessage();
		msg.what = what;
		msg.arg1 = flag;
		msg.obj = info;
		mHandler.sendMessage(msg);
	}

	public void requestSMVideoAPI(String requestURL){
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
				}
				if (errString.contains("timed out")){
					sendMSG(Constant.ERR_RETURN_TIMEOUT,Constant.HEADER_LOAD, StarNewsApp.getContext().getString(R.string.err_return_timeout));
				}
			}

			@Override
			public void onResponse(Response response) throws IOException {
				Log.d(TAG,"onResponse");
				String jsonData = response.body().string();
				mSMVideoString = jsonData;
				sendMSG(Constant.SUCCESS,Constant.HEADER_LOAD,mSMVideoString);
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
				mSMVideoString = jsonData;
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
