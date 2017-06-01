package com.zqu.library.user;

/**
 * 学生用户
 * @author Aboo
 *
 */
public class User {

	/**
	 * 学生姓名
	 */
	public static String NAME;
	/**
	 * 借阅证账号
	 */
	public static String ACCOUNT;
	/**
	 * 借阅证密码
	 */
	public static String PW;
	/**
	 * 学生图书馆账户状态
	 */
	public static String STATUS;
	/**
	 * 学生图书馆账户余额
	 */
	public static String BALANCE;
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 获得用户的姓名
	 * @return 用户姓名
	 */
	public static String getNAME() {
		return NAME;
	}
	
	/**
	 * 获得借阅证账号
	 * @return 借阅证账号
	 */
	public static String getACCOUNT() {
		return ACCOUNT;
	}

	/**
	 * 获得借阅证密码
	 * @return 借阅证密码
	 */
	public static String getPW() {
		return PW;
	}

	/**
	 * 设置借阅证密码
	 * @param pW 借阅证密码
	 */
	public static void setPW(String pW) {
		PW = pW;
	}

	/**
	 * 获得用户的状态
	 * @return 用户状态
	 */
	public static String getSTATUS() {
		return STATUS;
	}

	/**
	 * 获得用户借阅证的余额
	 * @return 余额
	 */
	public static String getBALANCE() {
		return BALANCE;
	}

	/**
	 * 获得用户的图书借阅情况
	 */
	public static void getBorrowBookStatus(){
		
	}
	
	/**
	 * 获得用户的图书催还信息
	 */
	public static void getOverdueNotice(){
		
	}
	
	/**
	 * 获得用户的借书历史
	 */
	public static void getBorrowBookHistory(){
		
	}
	
}
