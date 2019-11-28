package com.casasolarctpi.myapplication.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.casasolarctpi.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentIncio extends Fragment {


    public FragmentIncio() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment_incio, container, false);


        return view;


    }

}
