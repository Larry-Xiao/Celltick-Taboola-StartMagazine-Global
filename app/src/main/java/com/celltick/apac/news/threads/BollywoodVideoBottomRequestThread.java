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

public class BollywoodVideoBottomRequestThread extends Thread{
	private static final String TAG = BollywoodVideoBottomRequestThread.class.getSimpleName();

	private Handler mHandler;
	private String mSMString= "";
	private String mTitle;
	private int offset;

	public BollywoodVideoBottomRequestThread(Handler handler, int offset) {
		this.mHandler = handler;
		this.offset = offset;
	}

	public BollywoodVideoBottomRequestThread(String title, Handler handler, int offset) {
		this.mHandler = handler;
		this.mTitle = title;
		this.offset = offset;
	}

	@Override
	public void run() {

		String url = ApiUrl.getBottomRequestURL(mTitle,offset);
		Log.d(TAG,"bottom url = " +url);
		if (url != null && url != "") {
			if (isGettingJsonSucceedFromStartMagazineAPI(url)) {
				sendMSG(Constant.SUCCESS,Constant.BOTTOM_LOAD,mSMString);
			} else if (!isGettingJsonSucceedFromStartMagazineAPI(url)) {
				{
					sendMSG(Constant.ERR_RETURN,Constant.BOTTOM_LOAD,"");
				}

			}
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
