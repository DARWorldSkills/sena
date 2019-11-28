package com.casasolarctpi.prosolar2.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.casasolarctpi.prosolar2.R;

import androidx.fragment.app.Fragment;


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
