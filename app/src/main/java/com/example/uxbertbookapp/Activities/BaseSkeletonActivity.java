package com.example.uxbertbookapp.Activities;

import android.app.ProgressDialog;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

//Skeleton Activity for all app.

public class BaseSkeletonActivity extends AppCompatActivity {
    private boolean contentViewSet = false;
    DrawerLayout drawerLayout;
    //region Methods for progress dialog
    protected ProgressDialog progressDialog;

    public final void showProgressDialog(String title, String message){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
        }
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        if(!progressDialog.isShowing()){
            progressDialog.show();
        }
    }

    // Progress bar which will show when doing any background task.

    public final void showProgressDialog(){
        showProgressDialog("Loading", "Please Wait");
    }

    @Override
    protected void onStop() {
        super.onStop();
        hideProgressDialog();
    }
    // Progress bar which will hide when background task is completed.

    public final void hideProgressDialog(){
        if(progressDialog!=null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }


}
