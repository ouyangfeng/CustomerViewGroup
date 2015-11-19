package com.liushengfan.test.customerviewgroup.cinterface;

import android.support.v4.app.Fragment;

/**
 * @author liushengfan
 *         <p>
 *         2014-5-5
 *         <p>
 *         定义一个接口类，实现Adapter中的方法
 *         <p>
 *         {@link #getCount()} 返回ViewPager中child数量
 *         <p>
 *         {@link #getItem(int)} 返回每个child fragment
 *         <p>
 *         {@link #getTitle(int)} 返回每个child 对应的title
 */
public interface FragmentAdapterProvider {

	/**
	 * 返回每个child 对应的title
	 * 
	 * @param position
	 * @return
	 */
	public String getTitle(int position);

	/**
	 * 返回每个child fragment
	 * 
	 * @param position
	 * @return
	 */
	public Fragment getItem(int position);

	/**
	 * 返回ViewPager中child数量
	 * 
	 * @return
	 */
	public int getCount();

}
