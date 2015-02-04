package com.romainpiel.bitcointracker.fragment;

import android.app.Fragment;

import com.romainpiel.bitcointracker.activity.BaseActivity;

public class BaseFragment extends Fragment {

    public void inject(Object object) {
        ((BaseActivity) getActivity()).inject(object);
    }
}
