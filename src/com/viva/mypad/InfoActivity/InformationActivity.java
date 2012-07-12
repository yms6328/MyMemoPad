package com.viva.mypad.InfoActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

import com.viva.mypad.R;
import com.viva.mypad.Util.Util;

public class InformationActivity extends PreferenceActivity implements OnPreferenceClickListener
{
    private PreferenceScreen mVersionPref;
    private PreferenceScreen mChangeLogPref;
    private PreferenceScreen mContactPref;

    @SuppressWarnings("deprecation")
    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        addPreferencesFromResource(R.xml.info);
        mVersionPref = (PreferenceScreen)findPreference("version");
        mVersionPref.setTitle(getResources().getString(R.string.info_app_version) + " " + Util.getAppVersion(this));

        mChangeLogPref = (PreferenceScreen)findPreference("changelog");
        mChangeLogPref.setOnPreferenceClickListener(this);

        mContactPref = (PreferenceScreen)findPreference("contact");
    }

    public boolean onPreferenceClick(Preference preference)
    {
        if(preference.equals(mChangeLogPref))
        {
            startActivity(new Intent(this, ChangeLogActivity.class));
        }
        else if(preference.equals(mContactPref))
        {
            // send email intent
            Intent intent = new Intent(Intent.ACTION_SEND);
            String[] email = {"jag9123@gmail.com"};
            intent.putExtra(Intent.EXTRA_EMAIL, email);
            intent.setType("message/rfc882");
            Intent.createChooser(intent, getResources().getString(R.string.send_title));
            startActivity(intent);
        }
        return true;
    }

    private void showChangeLog()
    {
        ;
    }
}
