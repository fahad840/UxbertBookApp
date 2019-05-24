package com.example.uxbertbookapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.uxbertbookapp.Adapter.BookAdapter;
import com.example.uxbertbookapp.DBManager.DBHandler;
import com.example.uxbertbookapp.Model.Book;
import com.example.uxbertbookapp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends Fragment {

    private BookAdapter bookAdapter;
    ArrayList<Book> bookArrayList;
    ListView listView;
    private View rootView;
    DBHandler dbHandler;
    public UpcomingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {
            //view =
            rootView = getActivity().getLayoutInflater().inflate(R.layout.fragment_upcoming, container, false);

        } catch (InflateException e) {
            /* map is already there, just return view as it is */
        }

        listView=rootView.findViewById(R.id.upcomingbooklist);
        getBooks();



        return rootView;
    }


    private void getBooks()
    {
        dbHandler=new DBHandler(getActivity());
        bookArrayList = new ArrayList<>();
        bookArrayList.addAll(dbHandler.getUpcomingBooks());
        bookAdapter= new BookAdapter(getActivity(),bookArrayList);
        listView.setAdapter(bookAdapter);

    }

}
