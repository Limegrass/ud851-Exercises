package android.example.com.visualizerpreferences;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.XmlRes;
import android.support.v7.preference.PreferenceFragmentCompat;

/**
 * Created by Limegrass on 9/8/2017.
 */

public class SettingsFragment extends PreferenceFragmentCompat{
    // TODO (5) In SettingsFragment's onCreatePreferences method add the preference file using the
    // addPreferencesFromResource method

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_visualizer);

    }

}
