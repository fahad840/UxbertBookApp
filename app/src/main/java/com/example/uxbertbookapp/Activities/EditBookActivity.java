package com.example.uxbertbookapp.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uxbertbookapp.DBManager.DBHandler;
import com.example.uxbertbookapp.Model.Book;
import com.example.uxbertbookapp.R;
import com.example.uxbertbookapp.SessionManager.SessionManager;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

public class EditBookActivity extends BaseSkeletonActivity implements com.mobsandgeeks.saripaar.Validator.ValidationListener {
    @NotEmpty
    EditText ed_bookname;
    @NotEmpty
    EditText ed_bookaurthor;
    @NotEmpty
    EditText ed_bookpages;
    Button editbookbtn,deletebtn;
    CheckBox checknotify;
    com.mobsandgeeks.saripaar.Validator validator;
    SessionManager sessionManager;
    DBHandler dbHandler;
    int check=0;
    Book book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);
        initView();
        viewActions();
    }

    private void initView()
    {
        ed_bookname=findViewById(R.id.edit_edit_bookname);
        ed_bookaurthor=findViewById(R.id.edit_edit_aurthor);
        ed_bookpages=findViewById(R.id.edit_edit_pages);
        editbookbtn=findViewById(R.id.edit_book_btn);
        checknotify=findViewById(R.id.edit_checkboxnotify);
        deletebtn=findViewById(R.id.delete_book_btn);
        sessionManager=new SessionManager(EditBookActivity.this);
        dbHandler = new DBHandler(EditBookActivity.this);
        validator = new com.mobsandgeeks.saripaar.Validator(this);
        validator.setValidationListener(this);
        book=dbHandler.getBook(getIntent().getIntExtra("bookId",0));
        ed_bookname.setText(book.getName());
        ed_bookaurthor.setText(book.getAurthor());
        ed_bookpages.setText(String.valueOf(book.getPages()));
        check=book.getNotifiable();
        if (check==1) {
            checknotify.setChecked(true);
        }
        else {

            checknotify.setChecked(false);
        }
    }

    private void viewActions()
    {
        editbookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(EditBookActivity.this)
                        .setTitle("Delete")
                        .setMessage("Do you want to delete this book?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                            public void onClick(DialogInterface dialog, int whichButton) {
                                dbHandler.deletebook(book);
                                Toast.makeText(getApplicationContext(),"Book is Deleted",Toast.LENGTH_LONG).show();
                                finish();
                                Intent intent=new Intent(EditBookActivity.this,BookListActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                startActivity(intent);
                            }})
                        .setNegativeButton(android.R.string.no, null).show();

            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        editbook();

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

    private void editbook()
    {
        if (checknotify.isChecked())
        {
            check=1;
        }

        dbHandler.updatebook(new Book(book.getId(),ed_bookname.getText().toString(),ed_bookaurthor.getText().toString(),Integer.valueOf(ed_bookpages.getText().toString()),book.getStatus(),check));
        Toast.makeText(getApplicationContext(),"Book is Updated",Toast.LENGTH_LONG).show();
        finish();
        startActivity(new Intent(EditBookActivity.this,BookListActivity.class));

    }
}
