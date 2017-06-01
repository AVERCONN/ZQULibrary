package com.zqu.library;

/**
 * "书"类，包含书本基本属性
 * @author Aboo
 *
 */
public class Book {
	/**
	 * 书名
	 */
	private String NAME;
	/**
	 * 作者
	 */
	private String WRITER;
	/**
	 * 出版社
	 */
	private String PRESS;
	/**
	 * 出版日期
	 */
	private String PUBLICATION_DATA;
	/**
	 * 馆藏地点
	 */
	private String LOCATION;
	/**
	 * 索引号
	 */
	private String INDEX_NO;
	/**
	 * 状态
	 */
	private String STATUS;
	/**
	 * 借阅类型
	 */
	private String LANGUAGE;
	/**
	 * 内容简介
	 */
	private String CONTENT;
	/** 类别 */
	private String CATEGORY;
	
	/**
	 * Book 类构造方法
	 */
	public Book() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 获得 “书名”
	 * @return 书名
	 */
	public String getNAME() {
		return NAME;
	}
	/**
	 * 设置 “书名”
	 * @param nAME 书名
	 */
	public void setNAME(String nAME) {
		NAME = nAME;
	}

	/**
	 * 获得 “作者”
	 * @return 作者
	 */
	public String getWRITER() {
		return WRITER;
	}
	/**
	 * 设置 “作者”
	 * @param wRITER 作者 
	 */
	public void setWRITER(String wRITER) {
		WRITER = wRITER;
	}
	
	/**
	 * 获得 “出版社”
	 * @return 出版社
	 */
	public String getPRESS() {
		return PRESS;
	}
	/**
	 * 设置 “出版社”
	 * @param pRESS 出版社
	 */
	public void setPRESS(String pRESS) {
		PRESS = pRESS;
	}
	
	/**
	 * 获得 “出版日期”
	 * @return 出版日期
	 */
	public String getPUBLICATION_DATA() {
		return PUBLICATION_DATA;
	}
	/**
	 * 设置 “出版日期”
	 * @param pUBLICATION_DATA 出版日期
	 */
	public void setPUBLICATION_DATA(String pUBLICATION_DATA) {
		PUBLICATION_DATA = pUBLICATION_DATA;
	}
	
	/**
	 * 获得 “馆藏地点”
	 * @return 馆藏地点
	 */
	public String getLOCATION() {
		return LOCATION;
	}
	/**
	 * 设置 “馆藏地点”
	 * @param lOCATION 馆藏地点
	 */
	public void setLOCATION(String lOCATION) {
		LOCATION = lOCATION;
	}
	
	/**
	 * 获得 “索引号”
	 * @return 索引号
	 */
	public String getINDEX_NO() {
		return INDEX_NO;
	}
	/**
	 * 设置“索引号”
	 * @param iNDEX_NO 索引号
	 */
	public void setINDEX_NO(String iNDEX_NO) {
		INDEX_NO = iNDEX_NO;
	}
	
	/**
	 * 获得 “状态”
	 * @return 状态
	 */
	public String getSTATUS() {
		return STATUS;
	}
	/**
	 * 设置 “状态”
	 * @param sTATUS 借阅状态
	 */
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	
	/**
	 * 获得 “借阅类型”
	 * @return 借阅类型
	 */
	public String getLANGUAGE() {
		return LANGUAGE;
	}
	/**
	 * 设置 “借阅类型”
	 * @param lANGUAGE 借阅类型
	 */
	public void setLANGUAGE(String lANGUAGE) {
		LANGUAGE = lANGUAGE;
	}
	
	/**
	 * 获得“内容简介”
	 * @return 内容简介
	 */
	public String getCONTENT() {
		return CONTENT;
	}
	/**
	 * 设置“内容简介”
	 * @param cONTENT 内容简介
	 */
	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}
	/**
	 * 获得“类别”
	 * @return 类别
	 */
	public String getCATEGORY() {
		return CATEGORY;
	}
	/**
	 * 设置 “类别”
	 * @param cATEGORY 类别
	 */
	public void setCATEGORY(String cATEGORY) {
		CATEGORY = cATEGORY;
	}
	

}
