package library;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable{
	
	private String title;  		//����
	private String publish;		//������
	private String publish_date;	//��������
	private String search_no;	//������
	private String place;		//�عݵص�
	private String login_no;	//��¼��
	private String book_style;	//�鱾����
	private String ctrlno;		//������


	public Book(){//�޲οչ��캯��
		
	}
	
	public String getCtrlno() {
		return ctrlno;
	}

	public void setCtrlno(String ctrlno) {
		this.ctrlno = ctrlno;
	}

	public Book(String title) {
		this.title=title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublish() {
		return publish;
	}

	public void setPublish(String publish) {
		this.publish = publish;
	}

	public String getPublish_date() {
		return publish_date;
	}

	public void setPublish_date(String publish_date) {
		this.publish_date = publish_date;
	}

	public String getSearch_no() {
		return search_no;
	}

	public void setSearch_no(String search_no) {
		this.search_no = search_no;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getLogin_no() {
		return login_no;
	}

	public void setLogin_no(String login_no) {
		this.login_no = login_no;
	}

	public String getBook_style() {
		return book_style;
	}

	public void setBook_style(String book_style) {
		this.book_style = book_style;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
	}
	
	

}
