package com.intheory.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by whitesand on 8/22/17.
 */

public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new CrimeListFragment();
    }
}
