package com.example.uxbertbookapp.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.uxbertbookapp.Fragments.NewFragment;
import com.example.uxbertbookapp.Fragments.OldFragment;
import com.example.uxbertbookapp.Fragments.UpcomingFragment;
import com.example.uxbertbookapp.R;
import com.example.uxbertbookapp.SessionManager.SessionManager;

public class BookListActivity extends AppCompatActivity {

    // All the references of the fragments
    final Fragment fragment1 = new NewFragment();
    final Fragment fragment2 = new OldFragment();
    final Fragment fragment3 = new UpcomingFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;

    //session manager to create or get session.
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        //show home fragment and hide the others.
        fm.beginTransaction().add(R.id.container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.container,fragment1, "1").commit();
        // reference for bottom naviagtion.
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        FloatingActionButton fab = findViewById(R.id.fab);
        sessionManager=new SessionManager(BookListActivity.this);

        //click listeners for bottom navigation
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_new:
                                //show new book fragment
                                fm.beginTransaction().hide(active).show(fragment1).commit();
                                active = fragment1;

                                return  true;
                            case R.id.action_old:

                                //show old book fragment

                                fm.beginTransaction().hide(active).show(fragment2).commit();
                                active = fragment2;
                                return  true;

                            case R.id.action_upcoming:
                                //show upcoming or not released book fragment

                                fm.beginTransaction().hide(active).show(fragment3).commit();
                                active = fragment3;
                                return true;



                        }
                        return false;
                    }
                });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open activity to create new book
                startActivity(new Intent(BookListActivity.this,CreateBookActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.button_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //handle logout button at top
        if (id == R.id.mybutton) {

            //logout the session to create new user or sign in with different account.
            sessionManager.logout();

        }
        return super.onOptionsItemSelected(item);
    }
}
