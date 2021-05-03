package com.geekhive.foodeydeliveryboy.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.geekhive.foodeydeliveryboy.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainOrderFragment extends Fragment {

    TabLayout tabs;
    ViewPager viewPager;

    public MainOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_order_fragment, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        // Set Tabs inside Toolbar
        tabs = (TabLayout) view.findViewById(R.id.result_tabs);
        setupViewPager(viewPager);
        tabs.setupWithViewPager(viewPager);
        return view;

    }

    private void setupViewPager(ViewPager viewPager) {
        AdapterPager adapter = new AdapterPager(getChildFragmentManager());
        adapter.addFragment(new OrderFragment(), "Food");
        adapter.addFragment(new GroceryOrderFragment(), "Grocery");
        adapter.addFragment(new CakeOrderFragment(), "Cake");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(0);
    }

    // pager adapter
    static class AdapterPager extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public AdapterPager(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
