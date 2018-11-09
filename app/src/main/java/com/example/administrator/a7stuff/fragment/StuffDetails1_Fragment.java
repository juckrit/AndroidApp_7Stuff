package com.example.administrator.a7stuff.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.a7stuff.Object.Stuff;
import com.example.administrator.a7stuff.R;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class StuffDetails1_Fragment extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;
    Stuff stuff;

    public StuffDetails1_Fragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static StuffDetails1_Fragment newInstance(Stuff stuff) {
        StuffDetails1_Fragment fragment = new StuffDetails1_Fragment();
        Bundle args = new Bundle();
        args.putParcelable("Stuff", stuff);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        stuff = getArguments().getParcelable("Stuff");
        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stuff_details_1, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
        setHasOptionsMenu(true);
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here

        viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
        //showToast("ID " + String.valueOf(stuff.getId()) + "Name " + stuff.getName());

        viewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                switch (i) {
                    case 0:
                        //showToast("Tab " + i);
                        return StuffDetails1_Summary_Fragment.newInstance(stuff);
                    case 1:
                        //showToast("Tab " + i);
                        return StuffDetails1_Edit_Fragment.newInstance(stuff);

                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return getContext().getResources().getString(R.string.Summary);
                    case 1:
                        return getContext().getResources().getString(R.string.Edit);

                    default:
                        return null;
                }
            }
        });
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabLayout);
//        tabLayout.addTab(tabLayout.newTab().setText("Summary"));
//        tabLayout.addTab(tabLayout.newTab().setText("Image"));
//        tabLayout.addTab(tabLayout.newTab().setText("Details"));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
    }

    private void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }


}
