package com.zqu.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zqu.library.R;
import com.zqu.library.util.DataKeyBean;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class BookshelfActivity extends Activity implements OnClickListener {

	/** 显示图书的ListView */
	private ListView mBookshelfLv;
	private View mBack;
	private Context mContext;
	private SimpleAdapter mBookshelfListAdapter = null;
	private static final String BOOKSHELF_ITEM_KEY = "bookshelfListItemKey";
	private AlertDialog.Builder mBuilder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bookshelf);
		mContext = BookshelfActivity.this;
		mBuilder = new AlertDialog.Builder(mContext);

		mBookshelfLv = (ListView) findViewById(R.id.bookshelf_bookCategory_lv);
		mBack = findViewById(R.id.bookshelf_titlebar_title);
		mBack.setOnClickListener(this);

		mBookshelfListAdapter = new SimpleAdapter(mContext,
				getBookshelfItemList(getBookshelfListString()),
				R.layout.bookshelf_listview_item_style,
				new String[] { BOOKSHELF_ITEM_KEY },
				new int[] { R.id.bookshelfListItemStyle_content_tv });
		mBookshelfLv.setAdapter(mBookshelfListAdapter);

		mBookshelfLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				showItemDialog(arg2);
			}
		});
	}

	@Override
	public void finish() {
		super.finish();
		// 结束当前activity 的切换动画
		overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
	}

	/**
	 * 子类显示
	 * 
	 * @param index
	 */
	private void showItemDialog(final int index) {
		mBuilder.setTitle("请选择");
		mBuilder.setItems(getBookshelfItemStringArrayId(index),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						String[] items = getResources().getStringArray(
								getBookshelfItemStringArrayId(index));
						Intent itemShowIntent = new Intent(mContext,
								BookshelfItemShowActivity.class);
						itemShowIntent.putExtra(DataKeyBean.BOOKSHELF_KEY,
								items[which].trim().toString());
						startActivity(itemShowIntent);
						overridePendingTransition(R.anim.start_left_in,
								R.anim.start_right_out);
					}
				});
		mBuilder.create().show();
	}

	/**
	 * 获得图书各类别的名称
	 * 
	 * @return 名称数组
	 */
	private String[] getBookshelfListString() {
		String[] bookshelfItemsStr = {
				getResources().getString(R.string.bookshelfList_item_1),
				getResources().getString(R.string.bookshelfList_item_2),
				getResources().getString(R.string.bookshelfList_item_3),
				getResources().getString(R.string.bookshelfList_item_4),
				getResources().getString(R.string.bookshelfList_item_5),
				getResources().getString(R.string.bookshelfList_item_6),
				getResources().getString(R.string.bookshelfList_item_7),
				getResources().getString(R.string.bookshelfList_item_8),
				getResources().getString(R.string.bookshelfList_item_9),
				getResources().getString(R.string.bookshelfList_item_10),
				getResources().getString(R.string.bookshelfList_item_11),
				getResources().getString(R.string.bookshelfList_item_12),
				getResources().getString(R.string.bookshelfList_item_13),
				getResources().getString(R.string.bookshelfList_item_14),
				getResources().getString(R.string.bookshelfList_item_15),
				getResources().getString(R.string.bookshelfList_item_16),
				getResources().getString(R.string.bookshelfList_item_17),
				getResources().getString(R.string.bookshelfList_item_18),
				getResources().getString(R.string.bookshelfList_item_19),
				getResources().getString(R.string.bookshelfList_item_20),
				getResources().getString(R.string.bookshelfList_item_21),
				getResources().getString(R.string.bookshelfList_item_22),
				getResources().getString(R.string.bookshelfList_item_23),
				getResources().getString(R.string.bookshelfList_item_24),
				getResources().getString(R.string.bookshelfList_item_25),
				getResources().getString(R.string.bookshelfList_item_26),
				getResources().getString(R.string.bookshelfList_item_27),
				getResources().getString(R.string.bookshelfList_item_28),
				getResources().getString(R.string.bookshelfList_item_29),
				getResources().getString(R.string.bookshelfList_item_30),
				getResources().getString(R.string.bookshelfList_item_31),
				getResources().getString(R.string.bookshelfList_item_32),
				getResources().getString(R.string.bookshelfList_item_33),
				getResources().getString(R.string.bookshelfList_item_34),
				getResources().getString(R.string.bookshelfList_item_35),
				getResources().getString(R.string.bookshelfList_item_36),
				getResources().getString(R.string.bookshelfList_item_37),
				getResources().getString(R.string.bookshelfList_item_38),
				getResources().getString(R.string.bookshelfList_item_39),
				getResources().getString(R.string.bookshelfList_item_40),
				getResources().getString(R.string.bookshelfList_item_41),
				getResources().getString(R.string.bookshelfList_item_42),
				getResources().getString(R.string.bookshelfList_item_43),
				getResources().getString(R.string.bookshelfList_item_44) };

		return bookshelfItemsStr;
	}

	/**
	 * 将 bookshelf 中的字符串组转换成 List型数据。
	 * 
	 * @param bookshelfStrings
	 *            bookshelf中的list项字符串。
	 * @return 转换为List型的bookshelf项。
	 */
	private List<Map<String, String>> getBookshelfItemList(
			String[] bookshelfStrings) {
		List<Map<String, String>> bookshelfList1 = new ArrayList<Map<String, String>>();
		Map<String, String> bookshelfMap = new HashMap<String, String>();
		for (int i = 0; i < bookshelfStrings.length; i++) {
			bookshelfMap.put(BOOKSHELF_ITEM_KEY, bookshelfStrings[i]);
			bookshelfList1.add(bookshelfMap);
			bookshelfMap = new HashMap<String, String>();
		}
		return bookshelfList1;
	}

	/**
	 * 获得每个类别的子类别字符串组资源ID
	 * 
	 * @param index
	 *            每个item的Index
	 * @return 资源ID
	 */
	private int getBookshelfItemStringArrayId(int index) {
		int id = 0;
		switch (index) {
		case 0:
			id = R.array.bookShelf_category_1;
			break;

		case 1:
			id = R.array.bookShelf_category_2;
			break;
		case 2:
			id = R.array.bookShelf_category_3;
			break;
		case 3:
			id = R.array.bookShelf_category_4;
			break;
		case 4:
			id = R.array.bookShelf_category_5;
			break;
		case 5:
			id = R.array.bookShelf_category_6;
			break;

		case 6:
			id = R.array.bookShelf_category_7_1;
			break;
		case 7:
			id = R.array.bookShelf_category_7_2;
			break;
		case 8:
			id = R.array.bookShelf_category_7_3;
			break;
		case 9:
			id = R.array.bookShelf_category_7_4;
			break;
		case 10:
			id = R.array.bookShelf_category_8;
			break;
		case 11:
			id = R.array.bookShelf_category_9;
			break;
		case 12:
			id = R.array.bookShelf_category_10;
			break;
		case 13:
			id = R.array.bookShelf_category_11;
			break;
		case 14:
			id = R.array.bookShelf_category_12;
			break;
		case 15:
			id = R.array.bookShelf_category_13_1;
			break;
		case 16:
			id = R.array.bookShelf_category_13_2;
			break;
		case 17:
			id = R.array.bookShelf_category_13_3;
			break;
		case 18:
			id = R.array.bookShelf_category_13_4;
			break;
		case 19:
			id = R.array.bookShelf_category_13_5;
			break;
		case 20:
			id = R.array.bookShelf_category_14;
			break;
		case 21:
			id = R.array.bookShelf_category_15;
			break;

		case 22:
			id = R.array.bookShelf_category_16;
			break;
		case 23:
			id = R.array.bookShelf_category_17;
			break;
		case 24:
			id = R.array.bookShelf_category_18_1;
			break;
		case 25:
			id = R.array.bookShelf_category_18_2;
			break;
		case 26:
			id = R.array.bookShelf_category_18_3;
			break;
		case 27:
			id = R.array.bookShelf_category_18_4;
			break;
		case 28:
			id = R.array.bookShelf_category_18_5;
			break;
		case 29:
			id = R.array.bookShelf_category_18_6;
			break;
		case 30:
			id = R.array.bookShelf_category_18_7;
			break;
		case 31:
			id = R.array.bookShelf_category_18_8;
			break;
		case 32:
			id = R.array.bookShelf_category_18_9;
			break;
		case 33:
			id = R.array.bookShelf_category_18_10;
			break;
		case 34:
			id = R.array.bookShelf_category_18_11;
			break;
		case 35:
			id = R.array.bookShelf_category_18_12;
			break;
		case 36:
			id = R.array.bookShelf_category_18_13;
			break;
		case 37:
			id = R.array.bookShelf_category_18_14;
			break;
		case 38:
			id = R.array.bookShelf_category_18_15;
			break;
		case 39:
			id = R.array.bookShelf_category_18_16;
			break;
		case 40:
			id = R.array.bookShelf_category_19;
			break;
		case 41:
			id = R.array.bookShelf_category_20;
			break;
		case 42:
			id = R.array.bookShelf_category_21;
			break;
		case 43:
			id = R.array.bookShelf_category_22;
			break;
		default:
			break;
		}
		return id;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.bookshelf_titlebar_title:
			((Activity) mContext).finish();
			break;

		default:
			break;
		}
	}
}
