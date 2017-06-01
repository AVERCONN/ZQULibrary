package library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.zqu.library.R;

public class MyBookExpandAdapter extends BaseExpandableListAdapter {

	private String [] groupData;
	private String [][]childData;
	LayoutInflater inflater;
	public MyBookExpandAdapter(Context context,String []groupData,String[][]childData) {
		this.inflater=LayoutInflater.from(context);
		this.groupData=groupData;
		this.childData=childData;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return childData[groupPosition][childPosition];
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		convertView=inflater.inflate(R.layout.bao_browse_child, null);
		TextView tvChild=(TextView) convertView.findViewById(R.id.browse_childtext);
		tvChild.setText(getChild(groupPosition,childPosition).toString());
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return childData[groupPosition].length;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groupData[groupPosition];
	}

	@Override
	public int getGroupCount() {
		return groupData.length;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		isExpanded=false;
		convertView=inflater.inflate(R.layout.bao_browse_group, null);
		TextView tvGroup=(TextView) convertView.findViewById(R.id.browse_grouptext);
		tvGroup.setText(getGroup(groupPosition).toString());
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
