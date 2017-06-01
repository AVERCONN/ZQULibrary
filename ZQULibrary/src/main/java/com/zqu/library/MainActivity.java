package com.zqu.library;

import com.zqu.library.util.LNewsAdapter;
import com.zqu.library.util.LibraryNews;
import com.zqu.library.util.NewsWeb;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.content.Intent;
import android.content.pm.ActivityInfo;

/**
 * 图书馆主界面显示
 * 
 * @version 13.05
 * @since 2013.05
 * @author chen
 */
public class MainActivity extends Activity implements OnClickListener {

	private Handler itemHandler;
	/** 图书检索 */
	private View mSearchBookItem;
	/** 分类浏览 */
	private View mBookShelfItem;
	/** 入库新书 */
	private View mNewBooksItem;
	/** 个人中心 */
	private View mUserItem;
	/** 退出 */
	private View mExitItem;
	/** 关于 */
	private View mAboutItem;
	private Context mContext;
	private List<LibraryNews> newsListView = new ArrayList<LibraryNews>();
	private String test;
	private ListView listView;
	//输入搜索内容
	private EditText searchContent;
	public String[] mLinks = new String[30];
	public String[] mTitle =new String[30];
	public String[] mNewsDate = new String[30];
	public String[] testt={"a","b","c"};
	private static final int DIALOG_KEY = 0;
	public Boolean lvflag=true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		setContentView(R.layout.library_main);

		mContext = this;
		// //Jpush 极光推送初始化
		// JPushInterface.setDebugMode(true);
		// JPushInterface.init(MainActivity.this);

		itemHandler = new Handler();
		mSearchBookItem = findViewById(R.id.Search_book);
		mBookShelfItem = findViewById(R.id.Book_Shelf);
		mNewBooksItem = findViewById(R.id.New_Books);
		mUserItem = findViewById(R.id.User_Information);
//		mExitItem = findViewById(R.id.main_exit_tv);
		mAboutItem = findViewById(R.id.About);
		listView = (ListView) findViewById(R.id.library_information);
		searchContent = (EditText) findViewById(R.id.Search_search);
		// 监听点击事件
		mSearchBookItem.setOnClickListener(this);
		mBookShelfItem.setOnClickListener(this);
		mNewBooksItem.setOnClickListener(this);
		mUserItem.setOnClickListener(this);
//		mExitItem.setOnClickListener(this);
		mAboutItem.setOnClickListener(this);

		searchContent.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (KeyEvent.KEYCODE_ENTER == keyCode && event.getAction() == KeyEvent.ACTION_DOWN) {
					String data = searchContent.getText().toString();
					Intent intent = new Intent(MainActivity.this, SearchBookResultActivity.class);
					intent.putExtra("maindata", data);
					startActivity(intent);
					return true;
				}
				return false;

			}
		});



		try {
			ProgressAsyncTask asyncTask = new ProgressAsyncTask(listView);
			asyncTask.execute(10000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(lvflag==false)
		listView.setVisibility(View.GONE);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

											@Override
											public void onItemClick(AdapterView<?> parent, View view, int position,
																	long id) {
												LibraryNews ln = newsListView.get(position);
												for (int i = 0; i < 19; i++)
													if (ln.getTitle().equals(mTitle[i])) {
														Intent intent = new Intent(MainActivity.this, NewsWeb.class);
														intent.putExtra("link", mLinks[i]);
                                                        Log.d("fag55", mLinks[i]);
                                                        startActivity(intent);
													}

											}
										}
		);
	}


			// 弹出"查看"对话框
			@Override
			protected Dialog onCreateDialog(int id) {
				switch (id) {
					case DIALOG_KEY: {
						ProgressDialog dialog = new ProgressDialog(this);
						dialog.setMessage("获取数据中  请稍候...");
						dialog.setIndeterminate(true);
						dialog.setCancelable(true);
						return dialog;
					}
				}
				return null;
			}

			class ProgressAsyncTask extends AsyncTask<Integer, Integer, Boolean> {

				//		private ListView webView;
				public ProgressAsyncTask(ListView webView) {
					super();
//			this.webView=webView;

				}

				/**
				 * 这里的Integer参数对应AsyncTask中的第一个参数 这里的String返回值对应AsyncTask的第三个参数
				 * 该方法并不运行在UI线程当中，主要用于异步操作，所有在该方法中不能对UI当中的空间进行设置和修改
				 * 但是可以调用publish Progress方法触发onProgressUpdate对UI进行操作
				 */
				@Override
				protected Boolean doInBackground(Integer... params) {
					String str = null;
					Document doc = null;
					Document date=null;
					int titlecount = 0, linkscount = 0;
					Log.d("db2","fsfs");
					try {
//                String url ="http://www.cnblogs.com/zyw-205520/p/3355681.html";
//
//                doc= Jsoup.parse(new URL(url).openStream(),"utf-8", url);
//                //doc = Jsoup.parse(readHtml(url));
//                //doc=Jsoup.connect(url).get();
//                str=doc.body().toString();
//                http://www.cnblogs.com/zyw-205520/archive/2012/12/20/2826402.html
						doc = Jsoup.connect("http://www.zqu.edu.cn/").get();
						date = Jsoup.connect("http://www.zqu.edu.cn/").get();
						Log.d("adb3","fsccfs");
                        Elements ListDiv = doc.getElementsByAttributeValue("class", "table table-condensed").select("a");
                        Elements ListDate= date.getElementsByAttributeValue("class", "my-panel-item-date");
						Log.d("dad45", ListDiv.toString());

						for (Element element : ListDate) {
							Log.d("date2", element.html().toString());
                            mNewsDate[titlecount++] = element.html().toString();

						}
						titlecount=0;
						for (Element element : ListDiv) {
							str = element.html();
							System.out.println(element.html());
                            String href=element.select("a").attr("href");
							String linkHref = "http://www.zqu.edu.cn/" + href;
							String title=element.select("a").attr("title");
							Log.d("fad1", element.html().toString());
							Log.d("fad2", linkHref);
                            Log.d("fad3", href);
//                            mLinks[titlecount++] = "http://www.zqu.edu.cn/" + element.select("a").attr("href");
						if(href.equals("http://news.zqu.edu.cn")||href.equals("http://cul.zqu.edu.cn")){
                            mLinks[titlecount] = href;
                            Log.d("fad4",  mLinks[titlecount]);
                            mTitle[titlecount++] = title;

                        }
                            else{
								mLinks[titlecount] = linkHref;
                            Log.d("fad4",  mLinks[titlecount]);
							    mTitle[titlecount++] = title;
                        }
						}
						Log.d("doInBackground", str.toString());
						System.out.println(str);
						Boolean lvflag=true;
						//你可以试试GBK或UTF-8
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Log.d("fad245", e.toString());
						 lvflag=false;
					}
//			return str.toString() ;
					//return test();
					return true;
				}

				/**
				 * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）
				 * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置
				 */
				@Override
				protected void onPostExecute(Boolean result) {
//			ListView.loadData(result, "text/html;charset=utf-8", null);


					initLNews();
					LNewsAdapter adapter = new LNewsAdapter(MainActivity.this, R.layout.library_news_item, newsListView);
					listView.setAdapter(adapter);


					removeDialog(DIALOG_KEY);
				}

				// 该方法运行在UI线程当中,并且运行在UI线程当中 可以对UI空间进行设置
				@Override
				protected void onPreExecute() {
					showDialog(DIALOG_KEY);
				}

				/**
				 * 这里的Intege参数对应AsyncTask中的第二个参数
				 * 在doInBackground方法当中，，每次调用publishProgress方法都会触发onProgressUpdate执行
				 * onProgressUpdate是在UI线程中执行，所有可以对UI空间进行操作
				 */
				@Override
				protected void onProgressUpdate(Integer... values) {
					Log.d("fad555553", test);

				}
			}

			private void initLNews() {
				LibraryNews news1 = new LibraryNews(mTitle[0], R.drawable.lbook,mNewsDate[0]);
				newsListView.add(news1);
				LibraryNews news2 = new LibraryNews(mTitle[1], R.drawable.lbook,mNewsDate[1]);
				newsListView.add(news2);
				LibraryNews news3 = new LibraryNews(mTitle[2], R.drawable.lbook,mNewsDate[2]);
				newsListView.add(news3);
				LibraryNews news4 = new LibraryNews(mTitle[3], R.drawable.lbook,mNewsDate[3]);
				newsListView.add(news4);
				LibraryNews news5 = new LibraryNews(mTitle[4], R.drawable.lbook,mNewsDate[4]);
				newsListView.add(news5);
				LibraryNews news6 = new LibraryNews(mTitle[5], R.drawable.lbook,mNewsDate[5]);
				newsListView.add(news6);
				LibraryNews news7 = new LibraryNews(mTitle[6], R.drawable.lbook,mNewsDate[6]);
				newsListView.add(news7);
				LibraryNews news8 = new LibraryNews(mTitle[7], R.drawable.lbook,mNewsDate[7]);
				newsListView.add(news8);
				LibraryNews news9 = new LibraryNews(mTitle[8], R.drawable.lbook,mNewsDate[8]);
				newsListView.add(news9);
				LibraryNews news10 = new LibraryNews(mTitle[9], R.drawable.lbook,mNewsDate[9]);
				newsListView.add(news10);
				LibraryNews news11 = new LibraryNews(mTitle[10], R.drawable.lbook,mNewsDate[10]);
				newsListView.add(news11);
				LibraryNews news12 = new LibraryNews(mTitle[11], R.drawable.lbook,mNewsDate[11]);
				newsListView.add(news12);
				LibraryNews news13 = new LibraryNews(mTitle[12], R.drawable.lbook,mNewsDate[12]);
				newsListView.add(news13);
				LibraryNews news14 = new LibraryNews(mTitle[13], R.drawable.lbook,mNewsDate[13]);
				newsListView.add(news14);
				LibraryNews news15 = new LibraryNews(mTitle[14], R.drawable.lbook,mNewsDate[14]);
				newsListView.add(news15);
				LibraryNews news16 = new LibraryNews(mTitle[15], R.drawable.lbook,mNewsDate[15]);
				newsListView.add(news16);
				LibraryNews news17 = new LibraryNews(mTitle[16], R.drawable.lbook,mNewsDate[16]);
				newsListView.add(news17);
				LibraryNews news18 = new LibraryNews(mTitle[17], R.drawable.lbook,mNewsDate[17]);
				newsListView.add(news18);
				LibraryNews news19 = new LibraryNews(mTitle[18], R.drawable.lbook,mNewsDate[18]);
				newsListView.add(news19);
//		for (int i = 0; i < 7; i++) {
//			Log.d("fad123", mTitle[0]);
//			Log.d("fad123", mTitle[1]);
//			Log.d("fad123", mTitle[2]);
//			Log.d("fad123", mTitle[3]);
//			Log.d("fad123", mTitle[4]);
//			Log.d("fad123", mTitle[5]);
//			Log.d("fad123", mTitle[6]);
//			lntitle[i].setNews(mTitle[i], R.drawable.lbook);
//			newsListView.add(lntitle[i]);
//		}
			}

//	View.OnKeyListener onKey=new View.OnKeyListener() {
//		@Override
//		public boolean onKey(View v, int keyCode, KeyEvent event) {
//			// TODO Auto-generated method stub
//			if (keyCode == KeyEvent.KEYCODE_ENTER) {
//				//这里写发送信息的方法
//				String data = searchContent.getText().toString();
//				Intent intent = new Intent(MainActivity.this, SearchBookResultActivity.class);
//			}
//		}
//	}




			/**
			 * 主界面中每个Item的点击事件监听
			 */
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
					case R.id.Search_book:

						Intent searchBookIntent = new Intent(MainActivity.this,
								SearchBookActivity.class);
						startActivity(searchBookIntent);
						// 进入动画
						overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);

						break;
					case R.id.Book_Shelf:
						Intent bookshelfIntent = new Intent(MainActivity.this,
								library.Book_Shelf_MainActivity.class);
						startActivity(bookshelfIntent);
						// 进入动画
						overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);

						break;
					case R.id.New_Books:
						// Intent newBookIntent = new Intent(MainActivity.this,
						// NewBookActivity.class);
						// startActivity(newBookIntent);
						// //进入动画
						// overridePendingTransition(R.anim.slide_in_up,
						// R.anim.slide_out_down);

						break;
					case R.id.User_Information:
						Intent userIntent = new Intent(MainActivity.this,
								LoginActivity.class);
						startActivity(userIntent);
						// 进入动画
						overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);

						break;


//		case R.id.main_exit_tv:
//			// 退出 Dialog 显示
//			createExitDialog();
//
//			break;
					case R.id.About:
						Intent aboutIntent = new Intent(MainActivity.this,
								AboutActivity.class);
						startActivity(aboutIntent);
						// 进入动画
						overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);

						break;

					default:
						break;
				}
			}

			@Override
			public void finish() {
				// TODO Auto-generated method stub
				super.finish();
				overridePendingTransition(R.anim.end_left_in, R.anim.end_right_out);
			}





			/**
			 * “返回” 物理键事件监听
			 */
			@Override
			public boolean onKeyDown(int keyCode, KeyEvent event) {
				if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {
					createExitDialog();
					return true;
				}
				return super.onKeyDown(keyCode, event);
			}

			/**
			 * 创建退出程序时的dialog提示
			 */
			private void createExitDialog() {
				// 退出 Dialog 创建并显示
				AlertDialog.Builder exitBuilder = new AlertDialog.Builder(
						MainActivity.this);

				final AlertDialog exitAlertDialog = exitBuilder.show();
				exitAlertDialog.setContentView(R.layout.dialog_exit);

				View confrimBtn = exitAlertDialog
						.findViewById(R.id.dialogExit_confirm_btn);
				View cancleBtn = exitAlertDialog
						.findViewById(R.id.dialogExit_cancle_btn);

				confrimBtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						exitAlertDialog.dismiss();
						((Activity) mContext).finish();
					}
				});
				cancleBtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						exitAlertDialog.dismiss();
					}
				});
			}
		}