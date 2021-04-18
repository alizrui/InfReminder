package com.example.infreminder.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.infreminder.view.ReminderCalendarView;
import com.example.infreminder.view.ReminderListView;

public class ViewPagerFragmentStateAdapter extends FragmentStateAdapter {

    public ViewPagerFragmentStateAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        Fragment result = null;

        switch (position) {
            case 0: result = new ReminderListView(); break;
            case 1: result = new ReminderCalendarView(); break;
        }

        return result;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
