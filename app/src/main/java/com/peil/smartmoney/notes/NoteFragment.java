package com.peil.smartmoney.notes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peil.smartmoney.R;
import com.peil.smartmoney.base.BaseFragment;
import com.peil.smartmoney.cost.CostFragment;


public class NoteFragment extends BaseFragment {

    public static NoteFragment newInstance() {

        Bundle args = new Bundle();

        NoteFragment fragment = new NoteFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_notes, container, false);
        return view;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

    }

}
