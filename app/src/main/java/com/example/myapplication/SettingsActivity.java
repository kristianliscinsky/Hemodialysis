package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            //must be number
            androidx.preference.EditTextPreference height = getPreferenceManager().findPreference("height");
            height.setOnBindEditTextListener(new androidx.preference.EditTextPreference.OnBindEditTextListener() {
                @Override
                public void onBindEditText(@NonNull EditText editText) {
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                }
            });

            //must be number
            androidx.preference.EditTextPreference dryWeight = getPreferenceManager().findPreference("dry_weight");
            dryWeight.setOnBindEditTextListener(new androidx.preference.EditTextPreference.OnBindEditTextListener() {
                @Override
                public void onBindEditText(@NonNull EditText editText) {
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                }
            });

            //must be number
            androidx.preference.EditTextPreference weightAfterHD = getPreferenceManager().findPreference("after_HD_weight");
            weightAfterHD.setOnBindEditTextListener(new androidx.preference.EditTextPreference.OnBindEditTextListener() {
                @Override
                public void onBindEditText(@NonNull EditText editText) {
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                }
            });

            //must be number
            androidx.preference.EditTextPreference diureza = getPreferenceManager().findPreference("diureza");
            diureza.setOnBindEditTextListener(new androidx.preference.EditTextPreference.OnBindEditTextListener() {
                @Override
                public void onBindEditText(@NonNull EditText editText) {
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                }
            });

            //must be number
            androidx.preference.EditTextPreference maxUltraFiltration = getPreferenceManager().findPreference("max_UF");
            maxUltraFiltration.setOnBindEditTextListener(new androidx.preference.EditTextPreference.OnBindEditTextListener() {
                @Override
                public void onBindEditText(@NonNull EditText editText) {
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                }
            });

            //must be number
            androidx.preference.EditTextPreference actualPhosphorusValue = getPreferenceManager().findPreference("actual_phosphorus_value");
            actualPhosphorusValue.setOnBindEditTextListener(new androidx.preference.EditTextPreference.OnBindEditTextListener() {
                @Override
                public void onBindEditText(@NonNull EditText editText) {
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                }
            });

            //must be number
            androidx.preference.EditTextPreference actualPotassiumValue = getPreferenceManager().findPreference("actual_potassium_value");
            actualPotassiumValue.setOnBindEditTextListener(new androidx.preference.EditTextPreference.OnBindEditTextListener() {
                @Override
                public void onBindEditText(@NonNull EditText editText) {
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                }
            });

            //hide useless fields
            ListPreference daysOfHD = findPreference("number_of_hemodialysis_perWeek");
            if (daysOfHD.getValue().equals("1")) {
                findPreference("hemodialysis2_day").setVisible(false);
                findPreference("hemodialysis2_time").setVisible(false);
                findPreference("hemodialysis3_day").setVisible(false);
                findPreference("hemodialysis3_time").setVisible(false);
            } else if (daysOfHD.getValue().equals("2")) {
                findPreference("hemodialysis3_day").setVisible(false);
                findPreference("hemodialysis3_time").setVisible(false);
            }

            daysOfHD.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    //reset
                    findPreference("hemodialysis2_day").setVisible(true);
                    findPreference("hemodialysis2_time").setVisible(true);
                    findPreference("hemodialysis3_day").setVisible(true);
                    findPreference("hemodialysis3_time").setVisible(true);

                    if (newValue.toString().equals("1")) {

                        findPreference("hemodialysis2_day").setVisible(false);
                        findPreference("hemodialysis2_time").setVisible(false);
                        findPreference("hemodialysis3_day").setVisible(false);
                        findPreference("hemodialysis3_time").setVisible(false);
                    } else if (newValue.toString().equals("2")) {
                        findPreference("hemodialysis3_day").setVisible(false);
                        findPreference("hemodialysis3_time").setVisible(false);
                    }
                    return true;
                }
            });
        }
    }
}