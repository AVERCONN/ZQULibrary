package library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zqu.library.R;

import java.util.List;

public class MyBookListAdapter extends BaseAdapter {

	private List<Book> book_list;
	LayoutInflater inflater;
	ViewHolder holder;
	
	public MyBookListAdapter(Context context,List<Book> book_list) {
		this.inflater=LayoutInflater.from(context);
		this.book_list=book_list;
	}

	@Override
	public int getCount() {
		return book_list.size();
	}

	@Override
	public Book getItem(int arg0) {
		return book_list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
			
			if(arg1==null){
			holder=new ViewHolder();
			arg1=inflater.inflate(R.layout.bao_booklist_item, null);
			holder.title=(TextView) arg1.findViewById(R.id.booklist_title);
			holder.publish=(TextView) arg1.findViewById(R.id.booklist_publish);
			holder.date=(TextView) arg1.findViewById(R.id.booklist_date);
			holder.searchno=(TextView) arg1.findViewById(R.id.booklist_searchno);
			arg1.setTag(holder);
			}else
				holder=(ViewHolder) arg1.getTag();
			
			holder.title.setText(getItem(arg0).getTitle());
			holder.publish.setText(getItem(arg0).getPublish());
			holder.date.setText(getItem(arg0).getPublish_date());
			holder.searchno.setText(getItem(arg0).getSearch_no());
		
		
		return arg1;
	}
	
	public void onChangeData(List<Book> book_list){
		this.book_list=book_list;
		
		this.notifyDataSetChanged();
	}

	//��ʾ����Ϣ
	class ViewHolder {
		TextView title;
		TextView publish;
		TextView date;
		TextView searchno;
	}
}

