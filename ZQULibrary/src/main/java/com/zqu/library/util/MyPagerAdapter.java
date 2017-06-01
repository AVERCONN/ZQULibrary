package com.zqu.library.util;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Viewpager 数据适配器
 * 
 * @author Aboo
 * 
 */
public class MyPagerAdapter extends PagerAdapter {

	private List<View> pagerList;

	public MyPagerAdapter(List<View> pagerList) {
		super();
		this.pagerList = pagerList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pagerList.size();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView(pagerList.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		container.addView(pagerList.get(position));
		return pagerList.get(position);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

}
