package com.zqu.library.util;

import com.zqu.library.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * 自定义 ListView 样式
 * @author Aboo
 *
 */
public class MyListView extends ListView {

	public MyListView(Context context) {
		super(context);
	}
    public MyListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
        case MotionEvent.ACTION_DOWN:
                int x = (int) ev.getX();
                int y = (int) ev.getY();
                int itemnum = pointToPosition(x, y);

                if (itemnum == AdapterView.INVALID_POSITION)
                        break;                 
                else{  
                    if(itemnum==0){
                        if(itemnum==(getAdapter().getCount()-1)){                                    
                            setSelector(R.drawable.app_list_corner_round);
                        }else{
                            setSelector(R.drawable.app_list_corner_round_top);
                        }
                    }else {
                        if(itemnum==(getAdapter().getCount()-1)){
                        	setSelector(R.drawable.app_list_corner_round_bottom);
                        }else{                            
                        	setSelector(R.drawable.app_list_corner_shape);
                        }
                    }
                }
                break;
                
        case MotionEvent.ACTION_UP:
                break;
        }
        
		return super.onInterceptTouchEvent(ev);
	}

}
