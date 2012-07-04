package com.viva.mypad;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class SettingActivity extends PreferenceActivity implements OnPreferenceChangeListener
{
    private CheckBoxPreference mAgreePref;

    @SuppressWarnings("deprecation")
    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        addPreferencesFromResource(R.xml.setting);
        mAgreePref = (CheckBoxPreference)findPreference("log_collect");
        mAgreePref.setOnPreferenceChangeListener(this);
    }

    public void onResume()
    {
        super.onResume();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isAgree = sharedPref.getBoolean("log_collect", false);
        mAgreePref.setChecked(isAgree);

        if(isAgree)
        {
            mAgreePref.setSummary(getResources().getString(R.string.setting_summary_agree));
        }
        else
        {
            mAgreePref.setSummary(getResources().getString(R.string.setting_summary_disagree));
        }
    }

    public boolean onPreferenceChange(Preference preference, Object newValue)
    {
        if(preference.equals(mAgreePref))
        {
            if((Boolean)newValue)
            {
                preference.setSummary(getResources().getString(R.string.setting_summary_agree));
            }
            else
            {
            	preference.setSummary(getResources().getString(R.string.setting_summary_disagree));
            }
        }
        return true;
    }
}
