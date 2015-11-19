package com.liushengfan.test.customerviewgroup;

import android.text.SpannableString;


/**
 * 个人主页.导航适配接口
 * @author lsf
 *
 */
public interface  IuserHomeNav  {
	 /**
	  * 返回当前页卡Pager Title
	  * @param position
	  * @return
	  */
	 SpannableString getTitle(int position);
}
