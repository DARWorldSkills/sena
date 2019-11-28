package com.casasolarctpi.prosolar2.fragments;


import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.casasolarctpi.prosolar2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentContactanos extends Fragment {

    View view;
    TextView txtLinkMap;

    public FragmentContactanos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fragment_contactanos, container, false);


        inicializarLink();

        return view;
    }

    private void inicializarLink() {

        txtLinkMap = view.findViewById(R.id.txtLinkMap);
        txtLinkMap.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        txtLinkMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                Uri uri = Uri.parse(getString(R.string.link_googlemaps));
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

}
