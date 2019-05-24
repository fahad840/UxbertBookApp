package com.example.uxbertbookapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uxbertbookapp.DBManager.DBHandler;
import com.example.uxbertbookapp.Model.Book;
import com.example.uxbertbookapp.Model.User;
import com.example.uxbertbookapp.R;
import com.example.uxbertbookapp.SessionManager.SessionManager;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.annotation.Digits;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

public class CreateBookActivity extends BaseSkeletonActivity implements com.mobsandgeeks.saripaar.Validator.ValidationListener {
    @NotEmpty
    EditText ed_bookname;
    @NotEmpty
    EditText ed_bookaurthor;
    @NotEmpty
    EditText ed_bookpages;
    Button createbookbtn;
    CheckBox checknotify;
    com.mobsandgeeks.saripaar.Validator validator;
    SessionManager sessionManager;
    DBHandler dbHandler;
    int check=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_book);
        initView();
        viewActions();
    }

    private void initView()
    {
        ed_bookname=findViewById(R.id.create_edit_bookname);
        ed_bookaurthor=findViewById(R.id.create_edit_aurthor);
        ed_bookpages=findViewById(R.id.create_edit_pages);
        createbookbtn=findViewById(R.id.create_book_btn);
        checknotify=findViewById(R.id.checkboxnotify);
        sessionManager=new SessionManager(CreateBookActivity.this);
        validator = new com.mobsandgeeks.saripaar.Validator(this);
        validator.setValidationListener(this);

    }

    private void viewActions()
    {
        createbookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });

    }

    @Override
    public void onValidationSucceeded() {
        createbook();

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

    private void createbook()
    {
        if (checknotify.isChecked())
        {
            check=1;
        }
        dbHandler = new DBHandler(CreateBookActivity.this);

        dbHandler.addBook(new Book(ed_bookname.getText().toString(),ed_bookaurthor.getText().toString(),Integer.valueOf(ed_bookpages.getText().toString()),"upcoming",check));
        Toast.makeText(getApplicationContext(),"Book added in upcoming",Toast.LENGTH_LONG).show();
        finish();
        Intent intent=new Intent(CreateBookActivity.this,BookListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
    }
}
