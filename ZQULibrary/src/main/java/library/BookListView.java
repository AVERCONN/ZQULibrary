package library;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.zqu.library.R;

public class BookListView extends ListView {

	View footview;//�ײ����
	int totalItemCount;
	int lastItem;
	boolean isLoading;
	LoadDataListener loadListener;
	
	public BookListView(Context context) {
		super(context);
		initView(context);
	}

	public BookListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}




	public BookListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView(context);
		
	}
	
	public int getTotalItemCount() {
		return totalItemCount;
	}

	public void setTotalItemCount(int totalItemCount) {
		this.totalItemCount = totalItemCount;
	}

	public int getLastItem() {
		return lastItem;
	}

	public void setLastItem(int lastItem) {
		this.lastItem = lastItem;
	}
	
	public void initView(Context context){
		footview=LayoutInflater.from(context).inflate(R.layout.bao_booklist_footlayout, null);
		this.addFooterView(footview);
		footview.findViewById(R.id.booklistlayout).setVisibility(View.GONE);
		this.setOnScrollListener(new OnScrollListener(){

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				setLastItem(firstVisibleItem+visibleItemCount);
				setTotalItemCount(totalItemCount);
			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if(getLastItem()==getTotalItemCount()&&scrollState==SCROLL_STATE_IDLE&&!isLoading){
					isLoading=true;
					footview.findViewById(R.id.booklistlayout).setVisibility(View.VISIBLE);
					Log.d("CHECK", "listview�Ѿ���������");
					loadListener.onLoad();
				}
			}
			
		});
	}
	public void setLoadDataListener(LoadDataListener loadListener){
		this.loadListener=loadListener;
	}
	
	public void isLoadCompleted(){
		isLoading=false;
		footview.findViewById(R.id.booklistlayout).setVisibility(View.GONE);
	}
	
	/*
	 * �Զ���ӿ�LoadDataInterface������ʵ�ּ�������
	 */
	
	public interface LoadDataListener{
		public void onLoad();
		
	}

}
