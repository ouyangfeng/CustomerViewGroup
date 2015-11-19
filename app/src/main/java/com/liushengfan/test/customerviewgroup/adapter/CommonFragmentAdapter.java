package com.liushengfan.test.customerviewgroup.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.liushengfan.test.customerviewgroup.cinterface.FragmentAdapterProvider;


/**
 * @author liushengfan
 *         <p>
 *         2014-5-5
 *         <p>
 * @modify by hm 8-4 <li>替换 {@link FragmentStatePagerAdapter} 为
 *         {@link FragmentPagerAdapter} <li>重写
 *         {@link FragmentStatePagerAdapter #restoreState(android.os.Parcelable, ClassLoader)}
 */
public class CommonFragmentAdapter extends FragmentPagerAdapter {

	protected FragmentAdapterProvider provider = null;

	public CommonFragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	/**
	 * @param fm
	 * @param pro
	 *            不能为空
	 */
	public CommonFragmentAdapter(FragmentManager fm, FragmentAdapterProvider pro) {
		super(fm);
		if (pro == null) {
			return;
		}
		provider = pro;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return provider == null ? null : provider.getTitle(position);
	}

	@Override
	public Fragment getItem(int position) {
		return provider == null ? null : provider.getItem(position);
	}

	@Override
	public int getCount() {
		return provider == null ? 0 : provider.getCount();
	}
	
	
	
}
