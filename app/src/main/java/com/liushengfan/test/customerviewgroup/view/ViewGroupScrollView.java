package com.liushengfan.test.customerviewgroup.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Scroller;

import com.liushengfan.test.customerviewgroup.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ViewGroupScrollView extends ViewGroup implements ViewPager.OnPageChangeListener {

    private Context mContext;

    private View mObservedView;//正在监听的 View

    private ViewPager mViewPager;//底部ViewPager

    private View mUpView;//第一部分View
    private View mDownView;//第二部分View


    private boolean viewChang = false;//第一部分与第二部分 是否已经切换  相对于初始位置

    private Scroller myScroller;

    private boolean isFling = false;

    private int boundaryY = 0;

    MyGestureListener gestureListener;

    public ViewGroupScrollView(Context context) {
        super(context);
    }

    public ViewGroupScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
        /**
         * 获得我们所定义的自定义样式属性
         */
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ViewGroupScrollView, 0, 0);
        int downViewLayoutId = a.getResourceId(R.styleable.ViewGroupScrollView_downView, 0);
        int upViewLayoutId = a.getResourceId(R.styleable.ViewGroupScrollView_upView, 0);
        mUpView = LayoutInflater.from(context).inflate(upViewLayoutId, null);
        mDownView = LayoutInflater.from(context).inflate(downViewLayoutId, null);
        a.recycle();
        if (null == mUpView || null == mDownView) {
            throw new RuntimeException("upView or downView can not be null");
        }
        addView(mUpView);
        addView(mDownView);
    }

    private void init() {
        myScroller = new Scroller(mContext);
        gestureListener = new MyGestureListener();
        detector = new GestureDetector(mContext, gestureListener);

    }

    /**
     * 设置下部分View的资源ID
     *
     * @param layoutId
     */
    public void setViewPagerLayoutId(int layoutId) {
        View v = mDownView.findViewById(layoutId);
        if (null == v || !(v instanceof ViewPager)) {
            throw new RuntimeException("can not find ViewPager in the second part of ViewGroupScrollView");
        }
        mViewPager = (ViewPager) v;
        //ViewPager 设置监听器
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        MeasureSpec.getSize(widthMeasureSpec);
        MeasureSpec.getMode(widthMeasureSpec);

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            view.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View viewUp = getChildAt(0);
        View viewDown = getChildAt(1);
        boundaryY = viewUp.getMeasuredHeight();
        viewUp.layout(l, t, r, boundaryY);
        viewDown.layout(l, boundaryY, r, b + boundaryY);
        if (changed) {
            moveToDest();
        }
    }

    private GestureDetector detector;

    public int curIndex;// 0 top 1 bottom

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            return super.dispatchTouchEvent(ev);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (!isFling) {
                    moveToDest();
                }
                isFling = false;
                break;

            default:
                break;
        }
        return true;
    }

    /**
     * 两部分View自动切换
     */
    private void moveToDest() {
        int destId = (getScrollY() / (boundaryY / 2)) >= 1 ? 1 : 0;
        moveToDest(destId);
    }

    /**
     * 两部分切换
     *
     * @param destId
     */
    public void moveToDest(int destId) {
        if (0 == destId || 1 == destId) {
            int distance = destId * boundaryY - getScrollY();
            curIndex = destId;
            myScroller.startScroll(getScrollX(), getScrollY(), 0, distance, Math.abs(distance / 2));
            invalidate();
        }
    }

    /**
     * 计算滚动位置
     */
    @Override
    public void computeScroll() {
        if (myScroller.computeScrollOffset()) {
            int curY = myScroller.getCurrY();
            scrollTo(0, curY);
            invalidate();
            if (0 == curY) {
                viewChang = false;
                mObservedView = mUpView;
            } else if (boundaryY == curY) {
                viewChang = true;
                mObservedView = getCurChild_vp(mViewPager);
            }
        }
    }

    private int startY = 0;
    private int startX = 0;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getY();
                startX = (int) ev.getX();
                break;
            case MotionEvent.ACTION_UP:
                startX = 0;
                startY = 0;
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int currentY = (int) ev.getY();
                int currentX = (int) ev.getX();
                int distanceY = currentY - startY;
                int distanceX = currentX - startX;
                if (Math.abs(distanceY) - Math.abs(distanceX) > 5) {
                    boolean interceptFromChildFeedback = isParentViewShouldInterceptEnvent(viewChang, mObservedView);
                    if (viewChang) {
                        if (interceptFromChildFeedback && distanceY > 0) {
                            intercept = true;
                        } else {
                            intercept = false;
                        }
                    } else {
                        if (interceptFromChildFeedback && distanceY < 0) {
                            intercept = true;
                        } else {
                            intercept = false;
                        }
                    }
                }
                startY = currentY;
                startX = currentX;
                break;
            default:
                break;
        }
        gestureListener.setInterrupt(intercept);
        detector.onTouchEvent(ev);
        return intercept;
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private boolean interrupt = false;

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (interrupt) {
                if (0 == getScrollY() && distanceY > 0) {
                    scrollBy(0, (int) distanceY);
                } else if (getScrollY() > 0 && getScrollY() < (boundaryY)) {
                    scrollBy(0, (int) distanceY);
                } else if ((boundaryY) == getScrollY() && distanceY < 0) {
                    scrollBy(0, (int) distanceY);
                }
            }
            // scrollBy(0, (int) distanceY);
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            isFling = true;
            if (velocityY > 0) {
                moveToDest(0);
            } else {
                moveToDest(1);
            }
            return true;
        }

        public void setInterrupt(boolean interrupt) {
            this.interrupt = interrupt;
        }
    }

    public boolean isViewChang() {
        return viewChang;
    }

    public void setViewChang(boolean viewChang) {
        this.viewChang = viewChang;
    }

    /**
     * 是否应该中断事件传输
     *
     * @param viewChang    是否上下两部分布局已经切换
     * @param observedView 被观察的可滚动的View
     * @return
     */
    private boolean isParentViewShouldInterceptEnvent(boolean viewChang, View observedView) {
        boolean intercept = true;
        if (null == observedView) {
            return intercept;
        }
        if (viewChang) {
            if (observedView instanceof ListView) {
                ListView listView = (ListView) observedView;
                if (0 != listView.getChildCount()) {
                    if (0 == listView.getFirstVisiblePosition() && 0 == listView.getChildAt(0).getTop()) {
                        intercept = true;
                    } else {
                        intercept = false;
                    }
                } else {
                    intercept = true;
                }
            } else if (observedView instanceof ScrollView) {
                ScrollView scrollView = (ScrollView) observedView;
                if (scrollView.getScrollY() == 0) {
                    intercept = true;
                } else {
                    intercept = false;
                }
            } else {
                intercept = true;
            }
        } else {
            if (observedView instanceof ListView) {
                //TODO 支持ListView 判断滑动到底部
            } else if (observedView instanceof ScrollView) {
                ScrollView scrollView = (ScrollView) observedView;
                View contentView = scrollView.getChildAt(0);
                if (contentView != null && contentView.getMeasuredHeight() <= scrollView.getScrollY() + scrollView.getHeight()) {
                    intercept = true;
                } else {
                    intercept = false;
                }
            } else {
                intercept = true;
            }
        }
        return intercept;
    }


    //ViewPager Listener begin
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (isViewChang()) {
            mObservedView = getCurChild_vp(mViewPager);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    //ViewPager Listener end

    /**
     * 获取ViewPager当前显示的View
     *
     * @param vp
     * @return
     */
    public static View getCurChild_vp(ViewPager vp) {

        int childCnt = vp.getChildCount();
        int totalCnt = vp.getAdapter().getCount();
        int curItem = vp.getCurrentItem();

        int targetIndex = 0;

        // 若"已加载child未达到应有值",则在边界 、或总数达不到limit
        if (childCnt < vp.getOffscreenPageLimit() * 2 + 1) {
            // 若-项数不足-加载所有至limit,直接返回当前
            if (childCnt == totalCnt)
                targetIndex = curItem;
            else
            // 若足
            {
                // 若在左边界(即左边child数未达到limit)
                if (curItem - vp.getOffscreenPageLimit() < 0)
                    targetIndex = curItem;
                    // 右边界
                else
                    targetIndex = vp.getOffscreenPageLimit();
            }
        }
        // childCnt完整(即总项>childCnt,且不在边界)
        else
            targetIndex = vp.getOffscreenPageLimit();

        // 取-子元素
        List<View> vs = new ArrayList<View>();
        for (int i = 0; i < childCnt; i++)
            vs.add(vp.getChildAt(i));

        // 对子元素-排序,因默认排序-不一定正确(viewpager内部机制)
        Collections.sort(vs, new Comparator<View>() {
            @Override
            public int compare(View lhs, View rhs) {
                // TODO Auto-generated method stub
                if (lhs.getLeft() > rhs.getLeft())
                    return 1;
                else if (lhs.getLeft() < rhs.getLeft())
                    return -1;
                else
                    return 0;
            }
        });

        // debug
        // for (int i = 0; i<childCnt; i++)
        // System.out.println("nimei>>vp-"+i+".x:"+vs.get(i).getLeft());
        // System.out.println("nimei>>index:"+targetIndex);

        return vs.get(targetIndex);
    }

}
