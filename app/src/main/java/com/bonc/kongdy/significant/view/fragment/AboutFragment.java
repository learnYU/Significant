package com.bonc.kongdy.significant.view.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.bonc.kongdy.basketball.R;


public class AboutFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.about);




    }
}
