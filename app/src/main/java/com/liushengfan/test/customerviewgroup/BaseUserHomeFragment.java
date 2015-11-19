package com.liushengfan.test.customerviewgroup;

import android.support.v4.app.Fragment;
import android.widget.ListView;


public abstract class BaseUserHomeFragment extends Fragment {
    protected final long CACHE_TIME = 1000 * 60 * 5;
    protected ListView listview;


    /**
     * ListView scroll to top
     */
    public void scrollToTop() {
        listview.post(new Runnable() {
            @Override
            public void run() {
                listview.smoothScrollToPosition(0);
            }
        });
    }


}
