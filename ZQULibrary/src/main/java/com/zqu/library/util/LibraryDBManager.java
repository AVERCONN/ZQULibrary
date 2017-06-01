package com.zqu.library.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.zqu.library.R;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

/**
 * 数据库管理类
 * 主要是进行数据文件读取
 * @see #getDatabase() 获得数据库
 * @see #openDatabase() 打开目标数据库
 * @see #openDatabaseFile(String) 打开传入的目标数据库文件
 * @author Aboo
 *
 */
public class LibraryDBManager {

	/**
	 * 缓存目标数据库的byte型数组大小
	 */
	private final int BUFFER_SIZE = 40000;
	 /**
	  * 目标数据库文件名
	  */
	public String DB_NAME;
	/**
	 * 程序的包名
	 */
	public static final String PACKAGE_NAME = "com.zqu.library";
	/**
	 * 用以存放数据库的位置
	 */
	public static final String DB_PATH = "/data"+ Environment.getDataDirectory().getAbsolutePath() + "/"+ PACKAGE_NAME;
	 

	/**
	 * 目标数据库
	 */
	private SQLiteDatabase database;
	/**
	 * 上下文
	 */
	private Context context; 
	
	/**
	 * 构造方法
	 * @param context 传入的上下文
	 * @param DB_NAME 传入的数据库文件名
	 */
	public LibraryDBManager(Context context, String DB_NAME) {
		this.context = context;
		this.DB_NAME = DB_NAME;
	}
	
	/**
	 * 获得目标数据库
	 * @return 目标数据库
	 */
	public SQLiteDatabase getDatabase(){
		openDatabase();
		return database;
	}
	
	/**
	 * 打开目标数据库
	 */
	public void openDatabase() {
		this.database = this.openDatabaseFile(DB_PATH + "/" + DB_NAME);
	}
	
	/**
	 * 用以打开传入来的目标数据库文件
	 * @param DBfile 传入来的目标数据库文件
	 * @return 打开的目标数据库
	 */
	private SQLiteDatabase openDatabaseFile(String DBfile) {
	 
		 /**
		  * 数据库打开输入流
		  */
		InputStream DB_is;
		 
		try {
			//判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
			if (!(new File(DBfile).exists())) {
				//欲导入的数据库
				if(DB_NAME.equals("books.db")){
					DB_is = this.context.getResources().openRawResource(R.raw.books);
				}else {
					DB_is = this.context.getResources().openRawResource(R.raw.users);
				}
				
				FileOutputStream DB_fos = new FileOutputStream(DBfile);
				byte[] buffer = new byte[BUFFER_SIZE];
				int count = 0;
		 
				while ((count = DB_is.read(buffer)) > 0) {
					DB_fos.write(buffer, 0, count); 
				}
				DB_fos.close();
				DB_is.close();
				} 
			
			SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DBfile,null);
			return db; 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} 
			return null; 
	}

}
