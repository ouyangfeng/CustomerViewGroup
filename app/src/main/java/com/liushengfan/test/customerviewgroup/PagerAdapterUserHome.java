package com.liushengfan.test.customerviewgroup;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.text.SpannableString;


public class PagerAdapterUserHome extends CommonFragmentAdapter implements IuserHomeNav {

    private static final String TAG = "PagerAdapterUserHome";

    public PagerAdapterUserHome(FragmentManager fm, FragmentAdapterProvider pro) {
        super(fm, pro);
        // TODO Auto-generated constructor stub
    }

    public PagerAdapterUserHome(FragmentManager fm) {
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

    // /**
    // * child fragment {@link #getItem(int)} 重载
    // *
    // * @param position
    // * @return
    // */
    // public Object instantiateItem(View container, int position) {
    // Object object = super.instantiateItem(container, position);
    // try{
    // ((FragmentUserHomeAdapterProvider)
    // provider).setDynamicArgument((Fragment) object);
    // }catch(Exception e){
    // LogUtil.e(TAG, "setDynamicArgument ERROR");
    // e.printStackTrace();
    // }
    // return object;
    // }
}
