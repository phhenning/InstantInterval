package com.henning.pieter.instantinterval;

import android.os.Bundle;
import android.preference.SwitchPreference;


public class SettingsFragment extends android.preference.PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.pref_settings);

             /*
            SwitchPreference
                This preference will store a boolean into the SharedPreferences.
        */

            // Get the preference widgets reference
//            https://android--code.blogspot.com/2017/11/android-switchpreference.html final SwitchPreference onOffRandomColor = (SwitchPreference) findPreference(R.xml.pref_settings);


        }

//    @Override
//    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
//
//        setPreferencesFromResource(R.xml.settings, rootKey);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        TextView textView = new TextView(getActivity());
//        textView.setText(R.string.hello_blank_fragment);
//        return textView;
//    }

    }

