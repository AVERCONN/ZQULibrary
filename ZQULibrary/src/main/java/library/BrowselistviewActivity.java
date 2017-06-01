package library;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import library.BookListView.LoadDataListener;
import com.zqu.library.R;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class BrowselistviewActivity extends Activity implements LoadDataListener{
	
	Button booklist_back;
	TextView booklist_title;
	BookListView booklist_view;
	private String title_no;//������
	private String title;	//����
	private int pageno=1;
	static int pagetotal;
	Handler handler;
	ArrayList<Book> listData=new ArrayList<Book>();
	MyBookListAdapter listAdapter;
	

	@Override
	protected void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.bao_booklist_main);
	
	booklist_back=(Button) findViewById(R.id.booklist_back);
	booklist_title=(TextView) findViewById(R.id.booklist_title);
	booklist_view=(BookListView) findViewById(R.id.booklist_view);
	booklist_view.setLoadDataListener(this);
	
	getBookTitle(this.getIntent());
	
	booklist_title.setText(title_no);
	
	booklist_back.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			Intent intent=new Intent(BrowselistviewActivity.this,Book_Shelf_MainActivity.class);
			
			startActivity(intent);
			finish();
		}
		
	});
	
	new Thread(new BookListThread(title_no,title,pageno,true)).start();
	
	handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			if(msg.what==0x123){
				pagetotal=msg.getData().getInt("pagetotal");
				Log.d("PageTotal", String.valueOf(msg.getData().getInt("pagetotal")));
			}
			else if(msg.what==0x456){ 
				
				if(pageno+1<=pagetotal){
				pageno++;
				new Thread(new BookListThread(title_no,title,pageno,false)).start();
				}
				else{
					Toast.makeText(getActivityContext(), "�Ѿ���������...", Toast.LENGTH_SHORT).show();;
					booklist_view.isLoadCompleted();
				}
			} if(msg.what==0x789){
				booklist_view.setSelection((pageno-1)*20);
				booklist_view.isLoadCompleted();
			}else if(msg.what==0x147){
				showDialog(msg.getData().getString("title"),msg.getData().getString("content"));
			}
		}
		
	};
	
	booklist_view.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			

//			Toast.makeText(getActivityContext(), listAdapter.getItem(arg2).getCtrlno(), Toast.LENGTH_SHORT).show();
			
		new Thread(new BookIntroduceThread(listAdapter.getItem(arg2).getTitle(),listAdapter.getItem(arg2).getCtrlno())).start();
		
		}
	});
			
	}
	
	
	/*������б��߳���
	 *****************************************************************************************/
	class BookListThread implements Runnable{
		
		Document doc;
		
		private int pageno;		//ҳ��
		private int pagetotal;	//ҳ��
		private String title_no;//������
		private String title;	//����
		boolean isFirst;
		Message msg=new Message();
		Bundle bundle =new Bundle();
		
		BookListThread(String title_no,String title,int pageno,boolean isFirst){
			this.title_no=title_no;
			this.title=title;
			this.pageno=pageno;
			this.isFirst=isFirst;
		}
		
		@Override
		public void run() {
			doc=getConnection(getURL(title_no,title,pageno));
			
			handler.post(new Runnable(){
			
				@Override
				public void run() {
				
					//ͳ��ҳ��
					if(isFirst){
						
						bundle.putInt("pagetotal",getPageTotal(doc));
						
						msg.what=0x123;
						msg.setData(bundle);
						handler.sendMessage(msg);
						}
						
							listData=AnalayDocument(doc,listData);
							
					if(listData.size()>0){
						if(isFirst){
							listAdapter=new MyBookListAdapter(getActivityContext(),listData);
							booklist_view.setAdapter(listAdapter);
							booklist_view.setTag(listAdapter);
						}else{
							listAdapter=(MyBookListAdapter) booklist_view.getTag();
							listAdapter.onChangeData(listData);
							handler.sendEmptyMessage(0x789);
							}
					}else
						Log.d("CHECK", "����ԴΪ��");
					
				}
				
			});
		}}
	 /********************************************************************/
	
	/*���ͼ�����߳���
	 ********************************************************************/
	class BookIntroduceThread implements Runnable{

		String ctrlno;
		String title;
		Document doc;
		String content;
		
		public BookIntroduceThread(String title,String ctrlno){
			this.title=title;
			this.ctrlno=ctrlno;
		}
		
		@Override
		public void run() {
			doc=getConnection(getResources().getString(R.string.baseurl)+ctrlno);
			content=AnalayIntroduce(doc);
			
			if(content!=null)
				Log.d("CHECK", content);
			else
				Log.d("CHECK", "��ȡʧ��");
			
			Message msg=new Message();
			Bundle bundle=new Bundle();
			
			msg.what=0x147;
			bundle.putString("title", title);
			bundle.putString("content", content);
			
			msg.setData(bundle);
			handler.sendMessage(msg);
		}
		
	}
	
	//ƴ��URL
	public String getURL(String clcode,String clname,int page){
		return getResources().getString(R.string.searchbook)+"?clcode="+clcode+"&clname="+clname+"&page="+page;
	}
	
	//��������
	public Document getConnection(String url){
		
		Connection con=Jsoup.connect(url);
		con.header("Accept",
				"image/jpeg, application/x-ms-application, image/gif, application/xaml+xml, image/pjpeg, application/x-ms-xbap, */*");
		con.header("Accept-Encoding", "gzip, deflate");
		con.header("Accept-Language", "zh-CN");
		con.header("Connection", "keep-alive");
		
		Document doc=null;
		try {
			doc=con.timeout(10000).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	/*
	 * �����õ����б�
	 */
	public ArrayList<Book> AnalayDocument(Document doc,ArrayList<Book> listData){
		
		Elements table=doc.select("table").select("tr");
		
		for(int i=1;i<table.size();i++){
			Elements td=table.get(i).select("td");
			String url=td.select("span.title a").attr("href");
			Book book=new Book();
			
			book.setCtrlno(url);
			Log.d(getPackageName(), url);
			
			book.setCtrlno(url);
			book.setTitle(td.get(1).text().replaceAll(":", ":"));
			book.setPublish(td.get(2).text());
			book.setPublish_date(td.get(3).text());
			book.setSearch_no(td.get(4).text());
			
			listData.add(book);
		}
		
		return listData;
	}
	
	/*
	 * ����Docment����ü��
	 */
	public String AnalayIntroduce(Document doc){
		String s=null;
		s=doc.select("span#ctl00_ContentPlaceHolder1_bookcardinfolbl").text();
		return s;
	}
	
	/*
	 * ��ʾͼ����
	 */
	public void showDialog(String title,String content){
		View introduce_layout=LayoutInflater.from(getActivityContext()).inflate(R.layout.bao_introduce_layout, null);
		TextView introduce_text=(TextView) introduce_layout.findViewById(R.id.introduce_text);
		
		Builder dialog_builder=new Builder(getActivityContext());

		dialog_builder.setTitle(title);
		dialog_builder.setNegativeButton("����", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
//				Toast.makeText(getActivityContext(), "�������", Toast.LENGTH_SHORT).show();
				arg0.dismiss();
			}
			
		});
		
		dialog_builder.setView(introduce_layout);
		introduce_text.setText(content);
		dialog_builder.show();
	}
	
	public void getBookTitle(Intent intent){
		
		String []sb=intent.getStringExtra("content").split(" ");
		title_no=sb[0];
		title=sb[1];
	}

	public Context getActivityContext(){
		return this;
	}
	
	public int getPageTotal(Document doc){
		
		Element pagetotal=doc.select("span#Pagination2_gplbl2").first();
		
		if(pagetotal==null) return 0;
		else
		return Integer.valueOf(pagetotal.text());
		
	}

	@Override
	public void onLoad() {
		Message ms=new Message();
		ms.what=0x456;
		handler.sendMessage(ms);
	}
	
	
}
