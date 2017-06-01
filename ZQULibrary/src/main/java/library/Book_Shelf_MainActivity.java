package library;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

import com.zqu.library.R;

public class Book_Shelf_MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.bao_main);
		
		ExpandableListView browselist=(ExpandableListView) findViewById(R.id.browse_list);
		
		final String []groupData;
		final String [][]childData;


		
		browselist.setGroupIndicator(null);
		
		groupData=getResources().getStringArray(R.array.series_parentname);
		childData=new String[][]{
				getResources().getStringArray(R.array.series_childname1),
				getResources().getStringArray(R.array.series_childname2),
				getResources().getStringArray(R.array.series_childname3),
				getResources().getStringArray(R.array.series_childname4),
				getResources().getStringArray(R.array.series_childname5),
				getResources().getStringArray(R.array.series_childname6),
				getResources().getStringArray(R.array.series_childname7_1),
				getResources().getStringArray(R.array.series_childname7_2),
				getResources().getStringArray(R.array.series_childname7_3),
				getResources().getStringArray(R.array.series_childname7_4),
				getResources().getStringArray(R.array.series_childname8),
				getResources().getStringArray(R.array.series_childname9),
				getResources().getStringArray(R.array.series_childname10),
				getResources().getStringArray(R.array.series_childname11),
				getResources().getStringArray(R.array.series_childname12),
				getResources().getStringArray(R.array.series_childname13_1),
				getResources().getStringArray(R.array.series_childname13_2),
				getResources().getStringArray(R.array.series_childname13_3),
				getResources().getStringArray(R.array.series_childname13_4),
				getResources().getStringArray(R.array.series_childnmae13_5),
				getResources().getStringArray(R.array.series_childname14),
				getResources().getStringArray(R.array.series_childname15),
				getResources().getStringArray(R.array.series_childname16),
				getResources().getStringArray(R.array.series_childname17),
				getResources().getStringArray(R.array.series_childname18_1),
				getResources().getStringArray(R.array.series_childname18_2),
				getResources().getStringArray(R.array.series_childname18_3),
				getResources().getStringArray(R.array.series_childname18_4),
				getResources().getStringArray(R.array.series_childname18_5),
				getResources().getStringArray(R.array.series_childname18_6),
				getResources().getStringArray(R.array.series_childname18_7),
				getResources().getStringArray(R.array.series_childname18_8),
				getResources().getStringArray(R.array.seies_childname18_9),
				getResources().getStringArray(R.array.series_childname18_10),
				getResources().getStringArray(R.array.series_childname18_11),
				getResources().getStringArray(R.array.series_childname18_12),
				getResources().getStringArray(R.array.series_childname18_13),
				getResources().getStringArray(R.array.series_childname18_14),
				getResources().getStringArray(R.array.series_childname18_15),
				getResources().getStringArray(R.array.series_childname18_16),
				getResources().getStringArray(R.array.series_childname19),
				getResources().getStringArray(R.array.series_childname20),
				getResources().getStringArray(R.array.series_childname21),
				getResources().getStringArray(R.array.series_childname22)
				};
		
		final MyBookExpandAdapter adapter=new MyBookExpandAdapter(this,groupData,childData);
		
		browselist.setAdapter(adapter);
		
		browselist.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
										int groupPosition, int childPosition, long id) {

				String content = adapter.getChild(groupPosition, childPosition).toString();

				Intent intent = new Intent(Book_Shelf_MainActivity.this, BrowselistviewActivity.class);

				intent.putExtra("content", content);
				startActivity(intent);
				finish();
				return true;
			}

		});
	}
}
