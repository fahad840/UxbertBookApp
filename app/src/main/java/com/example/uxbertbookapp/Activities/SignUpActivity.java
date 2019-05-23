package com.example.uxbertbookapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uxbertbookapp.R;
import com.example.uxbertbookapp.SessionManager.SessionManager;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

public class SignUpActivity extends BaseSkeletonActivity implements com.mobsandgeeks.saripaar.Validator.ValidationListener {

    @NotEmpty
    @Email
    private EditText emailEditText;
    @NotEmpty
    @Password(min = 6)
    private EditText passwordEditText;

    @NotEmpty
    private EditText fullNameEditText;

    com.mobsandgeeks.saripaar.Validator validator;
    SessionManager sessionManager;
    Button signUpBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initView();
        ViewActions();

    }

    private void initView()
    {
        fullNameEditText=findViewById(R.id.sigup_edit_fullName);
        emailEditText = findViewById(R.id.signUp_edit_Email);
        passwordEditText = findViewById(R.id.signUp_edit_Password);
        signUpBtn = findViewById(R.id.signUp_btn);
        validator = new com.mobsandgeeks.saripaar.Validator(this);
        validator.setValidationListener(this);
        sessionManager = new SessionManager(SignUpActivity.this);


    }

    private void ViewActions()
    {

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });

    }

    @Override
    public void onValidationSucceeded()
    {
        signup();
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

    private void signup()
    {


    }

    public void signinclicked(View view) {
        finish();
    }
}
