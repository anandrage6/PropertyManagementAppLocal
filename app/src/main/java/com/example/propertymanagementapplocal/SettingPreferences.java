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
        public static final String address = "ownerAddress";
        public static final String email = "ownerEmail";
        public static final String upiID = "ownerUpiId";
        public static final String mobile = "ownerMobile";
        public static final String currency = "currencyType";
        public static final String duedate = "RentalDueday";
        private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;


        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.main_preference);
            preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                    if (s.equals(currency)) {
                        Preference currencypref = findPreference(s);
                        currencypref.setSummary(sharedPreferences.getString(s, ""));

                    }
                    if (s.equals(duedate)) {
                        Preference duepref = findPreference(s);
                        duepref.setSummary(sharedPreferences.getString(s, ""));

                    }
                    if (s.equals(address)) {
                        Preference Addpref = findPreference(s);
                        Addpref.setSummary(sharedPreferences.getString(s, ""));

                    }
                    if (s.equals(mobile)) {
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

            Preference nameprf = findPreference(name);
            nameprf.setSummary(getPreferenceScreen().getSharedPreferences().getString(name,""));
            Preference Addprf = findPreference(address);
            Addprf.setSummary(getPreferenceScreen().getSharedPreferences().getString(address,""));
            Preference upiprf = findPreference(upiID);
            upiprf.setSummary(getPreferenceScreen().getSharedPreferences().getString(upiID,""));
            Preference mobprf = findPreference(mobile);
            mobprf.setSummary(getPreferenceScreen().getSharedPreferences().getString(mobile,""));
            Preference Currencyprf = findPreference(currency);
            Currencyprf.setSummary(getPreferenceScreen().getSharedPreferences().getString(currency,""));
            Preference dueprf = findPreference(duedate);
            dueprf.setSummary(getPreferenceScreen().getSharedPreferences().getString(duedate,""));


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
