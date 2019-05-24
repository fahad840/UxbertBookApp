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
import android.view.MenuItem;
import android.view.View;

import com.example.uxbertbookapp.Fragments.NewFragment;
import com.example.uxbertbookapp.Fragments.OldFragment;
import com.example.uxbertbookapp.Fragments.UpcomingFragment;
import com.example.uxbertbookapp.R;

public class BookListActivity extends AppCompatActivity {

    final Fragment fragment1 = new NewFragment();
    final Fragment fragment2 = new OldFragment();
    final Fragment fragment3 = new UpcomingFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        fm.beginTransaction().add(R.id.container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.container,fragment1, "1").commit();
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        FloatingActionButton fab = findViewById(R.id.fab);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_new:
                                fm.beginTransaction().hide(active).show(fragment1).commit();
                                active = fragment1;
                                return  true;
                            case R.id.action_old:
                                fm.beginTransaction().hide(active).show(fragment2).commit();
                                active = fragment2;
                                return  true;

                            case R.id.action_upcoming:

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
                startActivity(new Intent(BookListActivity.this,CreateBookActivity.class));
            }
        });
    }
}
