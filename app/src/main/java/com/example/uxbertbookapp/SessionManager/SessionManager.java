package com.example.uxbertbookapp.SessionManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.uxbertbookapp.Activities.SignInActivity;
import com.example.uxbertbookapp.Utils.Constants;



public class SessionManager {


    private static final String TAG = SessionManager.class.getSimpleName();
    public static SessionManager instance = null;
    private Context _context;
    SharedPreferences mypref;
    SharedPreferences.Editor editor;

    private static final String PREF_NAME = "UxbertBookApp";
    private static final String IS_LOGGEDIN = "isLoggedIn";



    public SessionManager(Context context){
        this._context = context;
        mypref = _context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        editor = mypref.edit();
        editor.apply();

    }



    public void createSession(String name,String password,String email) {
        editor.putString(Constants.KEYS.email,email);
        editor.putString(Constants.KEYS.password,password);
        editor.putString(Constants.KEYS.fullName,name);

        editor.putBoolean(IS_LOGGEDIN, true);
        editor.commit();
        //    Log.d(TAG, userImage);
    }



    public void logout(){
        editor.clear();
        editor.commit();
        Intent showLogin = new Intent(_context, SignInActivity.class);
        showLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        showLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(showLogin);
    }

    public boolean isLoggedIn(){
        return mypref.getBoolean(IS_LOGGEDIN,false);
    }


    public String getEmail() {
        return mypref.getString(Constants.KEYS.email, "");
    }

    public String getName() {
        return mypref.getString(Constants.KEYS.email, "");
    }
    public String password() {
        return mypref.getString(Constants.KEYS.password, "");
    }


}
