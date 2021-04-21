package com.java.yanlu.viewprocess.data;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.java.yanlu.R;
import com.java.yanlu.dataprocess.ChartData;
import com.java.yanlu.viewprocess.data.pages.DataChina;
import com.java.yanlu.viewprocess.data.pages.DataWorld;

import java.util.ArrayList;
import java.util.List;

public class DataFragment extends Fragment {
    List<Fragment> fragments;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_data, container, false);
        final TabLayout tab_layout_data = root.findViewById(R.id.tab_layout_data);
        final ViewPager viewPager = root.findViewById(R.id.viewPager_data);
        ChartData chartData = new ChartData();//初始化
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_layout_data));
        tab_layout_data.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        fragments = new ArrayList<>();
        init();
        DataFragment.FragAdapter fragAdapter = new DataFragment.FragAdapter(getFragmentManager(),fragments);
        viewPager.setAdapter(fragAdapter);
        viewPager.setCurrentItem(0);
        return root;
    }
    public class FragAdapter extends FragmentStatePagerAdapter {
        private List<Fragment>list;
        public FragAdapter(FragmentManager fragmentManager, List<Fragment> list){
            super(fragmentManager);
            this.list = list;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }
    public void init(){
        fragments.add(new DataChina());
        fragments.add(new DataWorld());
    }
}