package com.zqu.library;

import com.zqu.library.R;
import com.zqu.library.util.DataKeyBean;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * 用以根据输入的关键词进行图书检索
 * 
 * @see #search(Button) 检索处理函数
 * @see #clear(Button) 清空处理函数
 * @author Aboo
 * 
 */
public class SearchBookActivity extends Activity implements OnClickListener {

	private Context mContext;
	private View mBack;
	private Spinner mBookLocationSpinner;
	private EditText mKeyWordEdt;
	private Button mSearchBtn;
	private String mBookLocationStr = "ALL";
	private String mKeyWordStr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_book);
		mContext = SearchBookActivity.this;

		mBack = findViewById(R.id.searchBook_titlebar_title);
		mBookLocationSpinner = (Spinner) findViewById(R.id.searchBook_bookLocationSpinner);
		mKeyWordEdt = (EditText) findViewById(R.id.searchBook_keyWord_edt);
		mSearchBtn = (Button) findViewById(R.id.searchBook_search_btn);

		mBack.setOnClickListener(this);
		mSearchBtn.setOnClickListener(this);

		mBookLocationSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						mBookLocationStr = getLocationKey(arg2);
						// Log.e("LOCATION-INDEX", arg2 + "");
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
					}
				});
	}

	@Override
	public void finish() {
		super.finish();
		// 结束当前activity 的切换动画
		overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.searchBook_titlebar_title:
			((Activity) mContext).finish();
			break;

		case R.id.searchBook_search_btn:

			mKeyWordStr = getSearchKeyWord();

			if (!mKeyWordStr.trim().toString().equals("")) {

				Intent searchResultIntent = new Intent(mContext,
						SearchBookResultActivity.class);
				searchResultIntent.putExtra(
						DataKeyBean.SEARCH_BOOK_LOCATION_KEY, mBookLocationStr);
				searchResultIntent.putExtra(
						DataKeyBean.SEARCH_BOOK_KEYWORD_KEY, mKeyWordStr);
				startActivity(searchResultIntent);
			} else {
				Toast.makeText(mContext, "请输入关键词", Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 获得搜索关键词
	 * 
	 * @return 搜索关键词
	 */
	private String getSearchKeyWord() {
		return mKeyWordEdt.getText().toString();
	}

	/**
	 * 获得藏书店key
	 * 
	 * @param index
	 * @return
	 */
	private String getLocationKey(int index) {
		String temp = "";
		switch (index) {
		case 0:
			temp = "ALL";
			break;

		case 1:
			temp = "3";
			break;

		case 2:
			temp = "4";
			break;

		case 3:
			temp = "6";
			break;

		case 4:
			temp = "7";
			break;
		case 5:
			temp = "8";
			break;
		case 6:
			temp = "9";
			break;
		case 7:
			temp = "10";
			break;
		case 8:
			temp = "12";
			break;
		case 9:
			temp = "13";
			break;
		case 10:
			temp = "15";
			break;
		case 11:
			temp = "16";
			break;

		case 12:
			temp = "17";
			break;

		case 13:
			temp = "18";
			break;

		case 14:
			temp = "19";
			break;

		case 15:
			temp = "20";
			break;
		case 16:
			temp = "21";
			break;
		case 17:
			temp = "22";
			break;
		case 18:
			temp = "23";
			break;
		case 19:
			temp = "24";
			break;
		case 20:
			temp = "25";
			break;
		case 21:
			temp = "26";
			break;
		case 22:
			temp = "27";
			break;

		case 23:
			temp = "28";
			break;

		case 24:
			temp = "29";
			break;

		case 25:
			temp = "30";
			break;

		case 26:
			temp = "31";
			break;
		case 27:
			temp = "39";
			break;
		case 28:
			temp = "169";
			break;
		case 29:
			temp = "171";
			break;
		case 30:
			temp = "172";
			break;
		case 31:
			temp = "204";
			break;
		case 32:
			temp = "224";
			break;
		case 33:
			temp = "225";
			break;

		case 34:
			temp = "226";
			break;

		case 35:
			temp = "227";
			break;

		case 36:
			temp = "228";
			break;

		case 37:
			temp = "229";
			break;
		case 38:
			temp = "230";
			break;
		case 39:
			temp = "231";
			break;
		case 40:
			temp = "232";
			break;
		case 41:
			temp = "233";
			break;
		case 42:
			temp = "234";
			break;
		case 43:
			temp = "235";
			break;
		case 44:
			temp = "235";
			break;
		case 45:
			temp = "236";
			break;
		case 46:
			temp = "237";
			break;
		case 47:
			temp = "238";
			break;
		case 48:
			temp = "239";
			break;
		default:
			break;
		}

		return temp;
	}
}
