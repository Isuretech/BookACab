package com.isure.viahero.bookacab.mFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.isure.viahero.bookacab.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AcceptRequestFragment extends Fragment {


    public AcceptRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accept_request, container, false);
    }

}