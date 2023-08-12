package com.celltick.apac.news.threads;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.celltick.apac.news.util.ApiUrl;
import com.celltick.apac.news.util.Constant;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class BollywoodVideoHeaderRequestThread extends Thread{
	private static final String TAG = BollywoodVideoHeaderRequestThread.class.getSimpleName();

	private Handler mHandler;
	private String mSMFWCString = "";
	private String mTitle;
	private int mRequestNum;
	private String URL;
	private String tmStp;

	public BollywoodVideoHeaderRequestThread(String title, Handler handler) {
		this.mHandler = handler;
		this.mTitle = title;
	}

	public BollywoodVideoHeaderRequestThread(String title, Handler handler, int requestNum) {
		this.mHandler = handler;
		this.mTitle = title;
		this.mRequestNum = requestNum;
	}

	public BollywoodVideoHeaderRequestThread(String title, Handler handler, int requestNum, String timeStamp) {
		this.mHandler = handler;
		this.mTitle = title;
		this.mRequestNum = requestNum;
		this.tmStp = timeStamp;
	}

	@Override
	public void run() {

		if (mRequestNum == 0) {
			URL = ApiUrl.getMagazineBaseURL(mTitle)+"&limit="+Constant.NUM_SM_NORMAL_REQUEST+"&offset=0";
		} else {
			URL = ApiUrl.getMagazineBaseURL(mTitle)+"&limit="+Constant.NUM_SM_NORMAL_REQUEST +"&offset=0";
		}

		Log.d(TAG,"URL="+URL);
		if (isGettingJsonSucceedFromStartMagazineAPI(URL)) {
			sendMSG(Constant.SUCCESS,Constant.HEADER_LOAD, mSMFWCString);
		} else if (!isGettingJsonSucceedFromStartMagazineAPI(URL)) {
			sendMSG(Constant.ERR_RETURN,Constant.HEADER_LOAD,"");
		}
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
				mSMFWCString = jsonData;
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
