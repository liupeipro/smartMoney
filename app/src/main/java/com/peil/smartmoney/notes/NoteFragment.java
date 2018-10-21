package com.peil.smartmoney.notes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.peil.smartmoney.R;
import com.peil.smartmoney.base.BaseFragment;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

public class NoteFragment extends BaseFragment {
    private QMUITopBarLayout bar_top;

    private void initView(View view) {
        bar_top = view.findViewById(R.id.bar_top);
        bar_top.setTitle("备忘录");
        bar_top.addRightImageButton(R.mipmap.plus, R.id.topbar_right_change_button)
               .setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {

                           //              startActivity(new Intent(_mActivity.getApplicationContext(), CostAddActivity.class));
                                       }
                                   });
    }

    public static NoteFragment newInstance() {
        Bundle       args     = new Bundle();
        NoteFragment fragment = new NoteFragment();

        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_notes, container, false);

        initView(view);

        return view;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
