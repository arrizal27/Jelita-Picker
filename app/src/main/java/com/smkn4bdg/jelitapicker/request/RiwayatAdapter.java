package com.smkn4bdg.jelitapicker.request;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class RiwayatAdapter extends FragmentStatePagerAdapter {

    private int jumlahTab;

    public RiwayatAdapter(@NonNull FragmentManager fm, int jumlahTab) {
        super(fm);
        this.jumlahTab = jumlahTab;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new PendingFragment();
            case 1:
                return new DiterimaFragment();
            case 2:
                return new SelesaiFragment();
            case 3:
                return new DitolakFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return jumlahTab;
    }
}
