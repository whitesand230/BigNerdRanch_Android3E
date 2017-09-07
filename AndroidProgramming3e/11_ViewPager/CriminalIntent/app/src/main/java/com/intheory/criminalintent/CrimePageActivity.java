package com.intheory.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.UUID;

/**
 * Created by whitesand on 9/6/17.
 */

public class CrimePageActivity extends AppCompatActivity {
    private static final String EXTRA_CRIME_ID =
            "com.intheory.criminalintent.crime_id";

    private ViewPager mViewPager;
    private List<Crime> mCrimes;

    //Chapter 11 Challenge
    private Button mFirstButton;
    private Button mLastButton;

    public static Intent newIntent(Context packageContent, UUID crimeId){
        Intent intent = new Intent(packageContent, CrimePageActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        mViewPager = (ViewPager) findViewById(R.id.crime_view_pager);
        mFirstButton = (Button) findViewById(R.id.first_crime);
        mLastButton = (Button) findViewById(R.id.last_crime);

        mCrimes = CrimeLab.get(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        for(int i =0; i < mCrimes.size(); i++){
            if(mCrimes.get(i).getId().equals(crimeId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(mViewPager.getCurrentItem() == 0){
                    mFirstButton.setVisibility(View.INVISIBLE);
                }
                else
                    mFirstButton.setVisibility(View.VISIBLE);

                if(mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount() -1)
                    mLastButton.setVisibility(View.INVISIBLE);
                else
                    mLastButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mFirstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(0);
            }
        });

        mLastButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(mViewPager.getAdapter().getCount()-1);
            }
        });
    }
}
