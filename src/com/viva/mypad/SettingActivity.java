package com.viva.mypad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;

import com.viva.mypad.Util.Util;

public class SettingActivity extends PreferenceActivity implements OnPreferenceChangeListener, OnPreferenceClickListener
{
    private CheckBoxPreference mAgreePref;
    private SharedPreferences mSharedPref;
    private PreferenceScreen mInfoPref;
    private PreferenceScreen mVersionPref;
    private PreferenceScreen mChangeLogPref;

    @SuppressWarnings("deprecation")
    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        addPreferencesFromResource(R.xml.setting);
        mAgreePref = (CheckBoxPreference)findPreference("log_collect");
        mAgreePref.setOnPreferenceChangeListener(this);

        mInfoPref = (PreferenceScreen)findPreference("development_info");
        mInfoPref.setOnPreferenceClickListener(this);

        mVersionPref = (PreferenceScreen)findPreference("version");
        mVersionPref.setTitle(getResources().getString(R.string.info_app_version) + " " + Util.getAppVersion(this));

        mChangeLogPref = (PreferenceScreen)findPreference("changelog");
        mChangeLogPref.setOnPreferenceClickListener(this);
    }

    public void onResume()
    {
        super.onResume();
        mSharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isAgree = mSharedPref.getBoolean("log_collect", false);
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

    public boolean onPreferenceClick(Preference preference)
    {
        if(preference.equals(mChangeLogPref))
        {
            startActivity(new Intent(this, ChangeLogActivity.class));
        }
        return true;
    }
}
