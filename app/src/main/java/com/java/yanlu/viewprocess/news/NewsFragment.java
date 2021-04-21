package com.java.yanlu.viewprocess.news;

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
import com.java.yanlu.viewprocess.news.pages.NewsAll;
import com.java.yanlu.viewprocess.news.pages.NewsEvents;
import com.java.yanlu.viewprocess.news.pages.NewsNews;
import com.java.yanlu.viewprocess.news.pages.NewsPapers;
import com.java.yanlu.viewprocess.news.pages.NewsPoints;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {
    List<Fragment> fragments;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_news, container, false);

        final TabLayout tab_layout_news = root.findViewById(R.id.tab_layout_news);

        final ViewPager viewPager = root.findViewById(R.id.viewPager_news);

      //  viewPager.setOffscreenPageLimit(5);//

        fragments = new ArrayList<>();
        init();
        final FragAdapter fragAdapter = new FragAdapter(getFragmentManager(),fragments);
        viewPager.setAdapter(fragAdapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_layout_news));
        tab_layout_news.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fragAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return root;
    }
    public class FragAdapter extends FragmentStatePagerAdapter {
        public FragAdapter(FragmentManager fragmentManager, List<Fragment> list){
            super(fragmentManager);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {

            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    public void init(){
        fragments.clear();
        fragments.add(new NewsAll());
        fragments.add(new NewsNews());
        fragments.add(new NewsPapers());
        fragments.add(new NewsEvents());
        fragments.add(new NewsPoints());
    }

}