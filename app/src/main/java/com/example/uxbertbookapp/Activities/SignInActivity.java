package com.example.uxbertbookapp.Activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uxbertbookapp.DBManager.DBHandler;
import com.example.uxbertbookapp.Model.User;
import com.example.uxbertbookapp.R;
import com.example.uxbertbookapp.SessionManager.SessionManager;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

public class SignInActivity extends BaseSkeletonActivity implements com.mobsandgeeks.saripaar.Validator.ValidationListener {
    @NotEmpty
    @Email
    EditText ed_email;
    @NotEmpty
    @Password(min = 6)
    EditText ed_password;
    Button btn_signIn;
    com.mobsandgeeks.saripaar.Validator validator;
    SessionManager sessionManager;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        initView();
        viewActions();

    }

    private void initView()
    {
        ed_email=findViewById(R.id.sigin_edit_email);
        ed_password=findViewById(R.id.signIn_edit_pass);
        btn_signIn=findViewById(R.id.signIn_btn);
        sessionManager=new SessionManager(SignInActivity.this);
        validator = new com.mobsandgeeks.saripaar.Validator(this);
        validator.setValidationListener(this);

    }

    private void viewActions()
    {


        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validator.validate();

            }
        });



    }

    @Override
    public void onValidationSucceeded() {
        signIn();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }

    }

    public void signupClicked(View view) {
        startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
    }

    private void signIn()
    {
        dbHandler=new DBHandler(SignInActivity.this);
        User user=dbHandler.getUser(ed_email.getText().toString().trim(),ed_password.getText().toString().trim());
       if (user!=null)
       {
           Toast.makeText(getApplicationContext(),"Sign in Successfull",Toast.LENGTH_LONG).show();
           sessionManager.createSession(user.getName(),user.getPassword(),user.getEmail());
           startActivity(new Intent(SignInActivity.this,BookListActivity.class));

       }
       else {
           Toast.makeText(getApplicationContext(),"User not found",Toast.LENGTH_LONG).show();
       }
    }

}
