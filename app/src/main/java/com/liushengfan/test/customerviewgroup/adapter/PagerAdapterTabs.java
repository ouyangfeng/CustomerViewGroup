package com.liushengfan.test.customerviewgroup.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.text.SpannableString;

import com.liushengfan.test.customerviewgroup.cinterface.IuserHomeNav;
import com.liushengfan.test.customerviewgroup.cinterface.FragmentAdapterProvider;

/**
 * @author liushengfan
 */
public class PagerAdapterTabs extends CommonFragmentAdapter implements IuserHomeNav {

    private static final String TAG = "PagerAdapterUserHome";

    public PagerAdapterTabs(FragmentManager fm, FragmentAdapterProvider pro) {
        super(fm, pro);
        // TODO Auto-generated constructor stub
    }

    public PagerAdapterTabs(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }

    @Override
    public SpannableString getTitle(int position) {
        // TODO Auto-generated method stub
        SpannableString spannableString = new SpannableString("1");
        return spannableString;
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;// notifyDataChanged 有效
    }

}
