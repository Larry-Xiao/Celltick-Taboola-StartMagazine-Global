package com.celltick.apac.news.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Larryx on 8/6/2018.
 */

public class DialogUtil {

    Context context;
    ProgressDialog progressDialog;

    public DialogUtil(Context context){ this.context = context;}

    public ProgressDialog buildDialog(){
        if (progressDialog == null){
            progressDialog = new ProgressDialog(context);
        }
        return progressDialog;
    }


    public void showSimpleDialog () {
        ProgressDialog progressDialog = buildDialog();
        progressDialog.setMessage("Loading....");
        progressDialog.show();
    }

    public void dismissSimpleDialog (){
        if (progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }



}
