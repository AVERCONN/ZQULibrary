package com.zqu.library;

import java.util.ArrayList;
import java.util.List;

import com.zqu.library.R;
import com.zqu.library.util.DataKeyBean;
import com.zqu.library.util.MyPagerAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 用于显示书本相关信息
 * 
 * @author Aboo
 * 
 */
public class BookInfoActivity extends Activity implements OnClickListener {

	/** 内容显示ViewPaer */
	private ViewPager mContentViewPager;
	/** 内容显示ViewPaer的数据源适配器 */
	private MyPagerAdapter mPagerAdapter;
	/** 内容页集合 */
	private List<View> mPagerlList;
	/** “基本信息”页 */
	private View mBookBaseInfoPager;
	/** “内容简介”页 */
	private View mBookIntroPager;
	/** 内容页切换按钮 */
	private TextView mTab1Tv, mTab2Tv;

	/** "基本信息"页 “书名”项 */
	private TextView mBookNameTv;
	/** "基本信息"的 “作者”项 */
	private TextView mBookWriterTv;
	/** "基本信息"的 “出版社”项 */
	private TextView mBookPressTv;
	/** "基本信息"的 “出版日期”项 */
	private TextView mBookPublicationDateTv;
	/** "基本信息"的 “馆藏地点”项 */
	private TextView mBookLocationTv;
	/** "基本信息"的 “索取号”项 */
	private TextView mBookIndexNoTv;
	/** "基本信息"的 “状态”项 */
	private TextView mBookStatusTv;
	/** "基本信息"的 “借阅类型”项 */
	private TextView mBookLanguageTv;

	/** “内容简介”页简介 */
	private TextView mBookIntroTv;

	// 图书基本信息
	String mBookNameStr = "";
	String mBookWriterStr = "";
	String mBookPressStr = "";
	String mBookPublicationDateStr = "";
	String mBookLocationStr = "";
	String mBookIndexNoStr = "";
	String mBookStatusStr = "";
	String mBookLanguageStr = "";
	// 图书内容简介
	String mBookIntroStr = "";

	Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_info);
		mContext = BookInfoActivity.this;

		Bundle bookInfoBundle = getIntent().getExtras();
		getBookInfo(bookInfoBundle);

		setBookBaseInfo();
		setBookIntro();

		initInterface();

	}

	@Override
	public void finish() {
		super.finish();
		// 结束当前activity 的切换动画
		overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
	}

	/**
	 * 从Bundle中读取传递数据
	 * 
	 * @param bookBundle
	 *            存放图书信息Bundle
	 */
	private void getBookInfo(Bundle bookBundle) {
		mBookNameStr = bookBundle.getString(DataKeyBean.BOOK_NAME_KEY);
		mBookWriterStr = bookBundle.getString(DataKeyBean.BOOK_WRITER_KEY);
		mBookPressStr = bookBundle.getString(DataKeyBean.BOOK_PRESS_KEY);
		mBookPublicationDateStr = bookBundle
				.getString(DataKeyBean.BOOK_PUBLICATION_DATE_KEY);
		mBookLocationStr = bookBundle.getString(DataKeyBean.BOOK_LOCATION_KEY);
		mBookIndexNoStr = bookBundle.getString(DataKeyBean.BOOK_INDEX_NO_KEY);
		mBookStatusStr = bookBundle.getString(DataKeyBean.BOOK_STATUS_KEY);
		mBookLanguageStr = bookBundle.getString(DataKeyBean.BOOK_LANGUAGE_KEY);
		mBookIntroStr = "    "
				+ bookBundle.getString(DataKeyBean.BOOK_INTRO_KEY);
	}

	/**
	 * 初始化界面
	 */
	private void initInterface() {

		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		mBookBaseInfoPager = inflater.inflate(R.layout.book_info_page_base,
				null);
		mBookIntroPager = inflater.inflate(R.layout.book_info_page_intro, null);
		mPagerlList = new ArrayList<View>();
		mPagerlList.add(mBookBaseInfoPager);
		mPagerlList.add(mBookIntroPager);

		mPagerAdapter = new MyPagerAdapter(mPagerlList);
		mContentViewPager = (ViewPager) findViewById(R.id.bookInfo_viewpager);
		mContentViewPager.setAdapter(mPagerAdapter);

		mTab1Tv = (TextView) findViewById(R.id.bookInfo_baseInfo_tab_tv);
		mTab2Tv = (TextView) findViewById(R.id.bookInfo_intro_tab_tv);
		mTab1Tv.setOnClickListener(this);
		mTab2Tv.setOnClickListener(this);

		mContentViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				switch (arg0) {
				case 0:
					mTab1Tv.setBackgroundResource(R.drawable.tab_bg_1);
					mTab2Tv.setBackgroundResource(R.drawable.tab_bg_0);
					break;

				case 1:
					mTab1Tv.setBackgroundResource(R.drawable.tab_bg_0);
					mTab2Tv.setBackgroundResource(R.drawable.tab_bg_1);
					break;
				default:
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});

	}

	/**
	 * tab1、tab2点击事件
	 */
	public void onClick(View v) {

		switch (v.getId()) {
		// 基本信息页
		case R.id.bookInfo_baseInfo_tab_tv:
			mTab1Tv.setBackgroundResource(R.drawable.tab_bg_1);
			mTab2Tv.setBackgroundResource(R.drawable.tab_bg_0);
			mContentViewPager.setCurrentItem(0);
			break;

		// 内容简介页
		case R.id.bookInfo_intro_tab_tv:
			mTab1Tv.setBackgroundResource(R.drawable.tab_bg_0);
			mTab2Tv.setBackgroundResource(R.drawable.tab_bg_1);
			mContentViewPager.setCurrentItem(1);
			break;

		default:
			break;
		}
	}

	/**
	 * 用于设置 “基本信息”里面的项里面的内容
	 */
	private void setBookBaseInfo() {

		mBookNameTv = (TextView) mBookBaseInfoPager
				.findViewById(R.id.bookInfoPageBase_bookName_tv);
		mBookWriterTv = (TextView) mBookBaseInfoPager
				.findViewById(R.id.bookInfoPageBase_bookWriter_tv);
		mBookPressTv = (TextView) mBookBaseInfoPager
				.findViewById(R.id.bookInfoPageBase_bookPress_tv);
		mBookPublicationDateTv = (TextView) mBookBaseInfoPager
				.findViewById(R.id.bookInfoPageBase_bookPulicationData_tv);
		mBookLocationTv = (TextView) mBookBaseInfoPager
				.findViewById(R.id.bookInfoPageBase_bookLocation_tv);
		mBookIndexNoTv = (TextView) mBookBaseInfoPager
				.findViewById(R.id.bookInfoPageBase_bookIndexNo_tv);
		mBookStatusTv = (TextView) mBookBaseInfoPager
				.findViewById(R.id.bookInfoPageBase_bookStatus_tv);
		mBookLanguageTv = (TextView) mBookBaseInfoPager
				.findViewById(R.id.bookInfoPageBase_bookLanguage_tv);
		mBookNameTv.setText(mBookNameStr);
		mBookWriterTv.setText(mBookWriterStr);
		mBookPressTv.setText(mBookPressStr);
		mBookPublicationDateTv.setText(mBookPublicationDateStr);
		mBookLocationTv.setText(mBookLocationStr);
		mBookIndexNoTv.setText(mBookIndexNoStr);
		mBookStatusTv.setText(mBookStatusStr);
		mBookLanguageTv.setText(mBookLanguageStr);
	}

	/**
	 * 用于设置 “内容简介”里面的内容
	 */
	private void setBookIntro() {
		mBookIntroTv = (TextView) mBookIntroPager
				.findViewById(R.id.bookInfoPageIntro_bookContent_tv);
		mBookIntroTv.setText(mBookIntroStr);
	}

}
