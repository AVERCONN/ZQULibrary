package com.zqu.library.util;

/**
 * 网络地址帮助类
 * 
 * @author Aboo
 * 
 */
public class WebUrlHelper {
	// http://222.200.98.171:81 广东工业大学
	// http://10.0.1.102:81 肇庆学院
	public static final String HOST_IP = "10.0.1.102:81";

	public static final String HOST_URL = "http://10.0.1.102:81";
	/** 图书检索URL */
	public static final String BOOK_SEARCH_RESULT_URL = HOST_URL
			+ "/searchresult.aspx";
	/** 图书详细信息URL */
	public static final String BOOK_INFO_URL = HOST_URL + "/bookinfo.aspx";
	/** 图书分类导航URL */
	public static final String BOOKSHELF_BROWSE_URL = HOST_URL
			+ "/clsbrowse_book.aspx";

	private static String USER_LOGIN_URL = HOST_URL + "/login.aspx";
	private static String USER_INFO_RETURN_URL = "ReturnUrl=/user/userinfo.aspx";
	private static String USER_BORROW_BOOK_CONDITION_RETURN_URL = "ReturnUrl=/user/bookborrowed.aspx";
	private static String USER_BOOK_OVERDUE_NOTICE_RETURN_URL = "ReturnUrl=/user/chtsmessage.aspx";
	private static String USER_BORROW_BOOK_HISTORY_RETURN_URL = "ReturnUrl=/user/bookborrowedhistory.aspx";
	private static String mUserValue1Str = "__EVENTTARGET=";
	private static String mUserValue2Str = "__EVENTARGUMENT=";
	private static String mUserValue3Str = "__VIEWSTATE=%2FwEPDwUKLTM1MTcxODg2OA9kFgJmD2QWDGYPZBYCAgEPFgIeBGhyZWYFDWNzcy9zdHlsZS5jc3NkAgEPDxYCHghJbWFnZVVybAUbflxpbWFnZXNcaGVhZGVyb3BhYzRnaWYuZ2lmZGQCAg8PFgIeBFRleHQFJ%2BiCh%2BW6huWtpumZouWbvuS5pummhuS5puebruajgOe0ouezu%2Be7n2RkAgMPDxYCHwIFGzIwMTPlubQxMeaciDI25pelIOaYn%2Bacn%2BS6jGRkAgQPZBYEZg9kFgQCAQ8WAh4LXyFJdGVtQ291bnQCBxYQAgEPZBYCZg8VAwtzZWFyY2guYXNweAAM55uu5b2V5qOA57SiZAICD2QWAmYPFQMOY2xzYnJvd3NlLmFzcHgADOWIhuexu%2BWvvOiIqmQCAw9kFgJmDxUDDmJvb2tfcmFuay5hc3B4AAzor7vkuabmjIflvJVkAgQPZBYCZg8VAwl4c3RiLmFzcHgADOaWsOS5pumAmuaKpWQCBQ9kFgJmDxUDFHJlYWRlcnJlY29tbWVuZC5hc3B4AAzor7vogIXojZDotK1kAgYPZBYCZg8VAxNvdmVyZHVlYm9va3NfZi5hc3B4AAzmj5DphpLmnI3liqFkAgcPZBYCZg8VAxJ1c2VyL3VzZXJpbmZvLmFzcHgAD%2BaIkeeahOWbvuS5pummhmQCCA9kFgICAQ8WAh4HVmlzaWJsZWhkAgMPFgIfA2ZkAgEPZBYEAgEPZBYEZg8PZBYCHgxhdXRvY29tcGxldGUFA29mZmQCBA8PFgIfAmVkZAICD2QWBmYPEGRkFgFmZAIBDxBkZBYBZmQCAg8PZBYCHwUFA29mZmQCBQ8PFgIfAgWlAUNvcHlyaWdodCAmY29weTsyMDA4LTIwMDkuIFNVTENNSVMgT1BBQyA0LjAxIG9mIFNoZW56aGVuIFVuaXZlcnNpdHkgTGlicmFyeS4gIEFsbCByaWdodHMgcmVzZXJ2ZWQuPGJyIC8%2B54mI5p2D5omA5pyJ77ya5rex5Zyz5aSn5a2m5Zu%2B5Lmm6aaGIEUtbWFpbDpzenVsaWJAc3p1LmVkdS5jbmRkZN%2Faug8BXTkLTbfnJz4O4%2F0rLkSN";
	private static String mUserValue4Str = "__EVENTVALIDATION=%2FwEWBQLsgseFCAKOmK5RApX9wcYGAsP9wL8JAqW86pcIwL7m9gKae8o0PANgGT1emmHBmhU%3D";
	private static String mUserValue5Str = "ctl00%24ContentPlaceHolder1%24txtlogintype=0";
	private static String mUserValue6Str = "ctl00%24ContentPlaceHolder1%24txtUsername_Lib=";
	private static String mUserValue7Str = "ctl00%24ContentPlaceHolder1%24txtPas_Lib=";
	private static String mUserValue8Str = "ctl00%24ContentPlaceHolder1%24btnLogin_Lib=%E7%99%BB%E5%BD%95";

	/**
	 * 获得用户登录URL
	 * 
	 * @param account
	 * @param pw
	 * @return
	 */
	public static String getUserLoginUrl(String account, String pw) {
		return USER_LOGIN_URL + "?" + USER_INFO_RETURN_URL + "&"
				+ mUserValue1Str + "&" + mUserValue2Str + "&"
				+ mUserValue3Str + "&" + mUserValue4Str + "&"
				+ mUserValue5Str + "&" + mUserValue6Str
				+ account + "&" + mUserValue7Str + pw + "&"
				+ mUserValue8Str;
	}

	/**
	 * 获得用户信息URL
	 * 
	 * @param account
	 * @param pw
	 * @return
	 */
	public static String getUserInfoUrl(String account, String pw) {
		return USER_LOGIN_URL + "?" + USER_INFO_RETURN_URL + "&"
				+ mUserValue1Str + "&" + mUserValue2Str + "&"
				+ mUserValue3Str + "&" + mUserValue4Str + "&"
				+ mUserValue5Str + "&" + mUserValue6Str
				+ account + "&" + mUserValue7Str + pw + "&"
				+ mUserValue8Str;
	}

	/**
	 * 获得当前借书情况URL
	 * 
	 * @param account
	 * @param pw
	 * @return
	 */
	public static String getUserBorrowBookConditionUrl(String account,
			String pw) {
		return USER_LOGIN_URL + "?"
				+ USER_BORROW_BOOK_CONDITION_RETURN_URL + "&"
				+ mUserValue1Str + "&" + mUserValue2Str + "&"
				+ mUserValue3Str + "&" + mUserValue4Str + "&"
				+ mUserValue5Str + "&" + mUserValue6Str
				+ account + "&" + mUserValue7Str + pw + "&"
				+ mUserValue8Str;
	}

	/**
	 * 获得借书历史URL
	 * 
	 * @param account
	 * @param pw
	 * @return
	 */
	public static String getUserBorrowBookHistoryUrl(int page,
			String account, String pw) {
		return USER_LOGIN_URL + "?"
				+ USER_BORROW_BOOK_HISTORY_RETURN_URL
				+ "?page=" + page + "&" + mUserValue1Str + "&"
				+ mUserValue2Str + "&" + mUserValue3Str + "&"
				+ mUserValue4Str + "&" + mUserValue5Str + "&"
				+ mUserValue6Str + account + "&"
				+ mUserValue7Str + pw + "&" + mUserValue8Str;
	}

	/**
	 * 获得催还图书URL
	 * 
	 * @param account
	 * @param pw
	 * @return
	 */
	public static String getUserBookOverdueNoticeUrl(String account,
			String pw) {
		return USER_LOGIN_URL + "?"
				+ USER_BOOK_OVERDUE_NOTICE_RETURN_URL + "&"
				+ mUserValue1Str + "&" + mUserValue2Str + "&"
				+ mUserValue3Str + "&" + mUserValue4Str + "&"
				+ mUserValue5Str + "&" + mUserValue6Str
				+ account + "&" + mUserValue7Str + pw + "&"
				+ mUserValue8Str;
	}
}
