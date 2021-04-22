package com.smkn4bdg.jelitapicker.request;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.smkn4bdg.jelitapicker.R;

public class RiwayatActivity extends AppCompatActivity {

    TabLayout tabLayout;
    TabItem tabPending, tabDiterima, tabSelesai, tabDitolak;
    ViewPager viewPager;
    MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        findView();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        RiwayatAdapter riwayatAdapter = new RiwayatAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(riwayatAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void findView(){
        tabLayout = findViewById(R.id.tab_layout);
        tabPending = findViewById(R.id.status_pending);
        tabDiterima = findViewById(R.id.status_diterima);
        tabSelesai = findViewById(R.id.status_selesai);
        tabDitolak = findViewById(R.id.status_ditolak);
        viewPager = findViewById(R.id.view_pager);
        toolbar = findViewById(R.id.toolbar);
    }
}