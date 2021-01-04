package com.example.propertymanagementapplocal;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

import androidx.annotation.Nullable;
import androidx.preference.ListPreference;

public class SettingPreferences extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }

    public static class MyPreferenceFragment extends PreferenceFragment {

        public static final String name = "ownerName";
        public static final String Address = "ownerAddress";
        public static final String Email = "ownerEmail";
        public static final String upiID = "ownerUpiId";
        public static final String Mobile = "ownerMobile";
        public static final String Currency = "currencyType";
        public static final String Duedate = "RentalDueday";
        private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;


        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.main_preference);
            preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                    if (s.equals(Currency)) {
                        Preference currencypref = findPreference(s);
                        currencypref.setSummary(sharedPreferences.getString(s, ""));

                    }
                    if (s.equals(Duedate)) {
                        Preference duepref = findPreference(s);
                        duepref.setSummary(sharedPreferences.getString(s, ""));

                    }
                    if (s.equals(Address)) {
                        Preference Addpref = findPreference(s);
                        Addpref.setSummary(sharedPreferences.getString(s, ""));

                    }
                    if (s.equals(Mobile)) {
                        Preference mobpref = findPreference(s);
                        mobpref.setSummary(sharedPreferences.getString(s, ""));

                    }
                    if (s.equals(upiID)) {
                        Preference emailpref = findPreference(s);
                        emailpref.setSummary(sharedPreferences.getString(s, ""));

                    }
                    if(s.equals(name)){
                        Preference namePref = findPreference(s);
                        namePref.setSummary(sharedPreferences.getString(s, ""));

                    }


                }
            };

            //bindSummaryValue(findPreference("ownerName"));


        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(preferenceChangeListener);
            /*
            Preference nameprf = findPreference(name);
            nameprf.setSummary(getPreferenceScreen().getSharedPreferences().getString(name,""));
            Preference Addprf = findPreference(Address);
            Addprf.setSummary(getPreferenceScreen().getSharedPreferences().getString(Address,""));
            Preference upiprf = findPreference(upiID);
            upiprf.setSummary(getPreferenceScreen().getSharedPreferences().getString(upiID,""));
            Preference mobprf = findPreference(Mobile);
            mobprf.setSummary(getPreferenceScreen().getSharedPreferences().getString(Mobile,""));
            Preference Currencyprf = findPreference(Currency);
            Currencyprf.setSummary(getPreferenceScreen().getSharedPreferences().getString(Currency,""));
            Preference dueprf = findPreference(Duedate);
            dueprf.setSummary(getPreferenceScreen().getSharedPreferences().getString(Duedate,""));

             */
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);
        }
    }

    /*public static  void bindSummaryValue(Preference preference){
        preference.setOnPreferenceChangeListener(listener);
        listener.onPreferenceChange(preference, PreferenceManager.getDefaultSharedPreferences(preference.getContext()).getString(preference.getKey(),""));

    }

    private static Preference.OnPreferenceChangeListener listener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String string = newValue.toString();
            if(preference instanceof ListPreference){
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(string);
                preference.setSummary(index >= 0 ? listPreference.getEntries() [index] : null);


            }else if (preference instanceof EditTextPreference){
                preference.setSummary(string);

            }
            return true;
        }
    };*/
}
