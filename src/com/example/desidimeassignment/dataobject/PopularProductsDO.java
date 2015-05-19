package com.example.desidimeassignment.dataobject;

/**
 * Data Object class to keep populars products parsed info
 * 
 * @author siddharth
 * 
 */

// Response example:

/*
 * "id": 52529, "title": "Milton Econa Blue Tiffin Box Set (3 Pcs) ",
 * "off_percent": "", "store": "Paytm", "current_price": 573, "original_price":
 * 0, "shipping_charge": 0, "posted_user_id": 275346, "store_url":
 * "http://links.desidime.com?ref=mobile_deals&url=https://paytm.com/shop/p/milton-econa-3-blue-SWBSSFECOSA3BLUEMTSABL"
 * , "shareurl":
 * "http://www.desidime.com/premium_deals/milton-econa-blue-tiffin-box-set-3-pcs"
 * , "posted_username": "sinha.vipul", "popularity_count": 345,
 * "comments_count": 4, "deal_detail": "
 * 
 * "posted_user_current_dimes": 5149, "posted_user_image":
 * "http://cdn0.desidime.com/avatars/275346/medium/3D_Cool_Nature_Wallpapers_For_Desktop_(4).jpg?1424666776"
 * , "posted_user_rank": "Deal Captain", "deal_category_name":
 * "Kitchen and Home Appliances", "deal_category_id": 26, "created_at":
 * "2015-05-16T18:27:51+05:30", "store_id": 315, "pic_thumb":
 * "http://cdn0.desidime.com/photos/52529/small/0.jpg?1431781064"
 */

public class PopularProductsDO {

	private int id;
	private String title;
	private String off_percent;
	private String store;
	private int current_price;
	private int original_price;
	private int shipping_charge;
	private int posted_user_id;
	private String store_url;
	private String shareurl;
	private String posted_username;
	private int popularity_count;
	private int comments_count;
	private String deal_detail;
	private int posted_user_current_dimes;
	private String posted_user_image;
	private String posted_user_rank;
	private String deal_category_name;
	private int deal_category_id;
	private String created_at;
	private int store_id;
	private String pic_thumb;

	/**
	 * setter getters
	 * 
	 * @return
	 */

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOff_percent() {
		return off_percent;
	}

	public void setOff_percent(String off_percent) {
		this.off_percent = off_percent;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public int getCurrent_price() {
		return current_price;
	}

	public void setCurrent_price(int current_price) {
		this.current_price = current_price;
	}

	public int getOriginal_price() {
		return original_price;
	}

	public void setOriginal_price(int original_price) {
		this.original_price = original_price;
	}

	public int getShipping_charge() {
		return shipping_charge;
	}

	public void setShipping_charge(int shipping_charge) {
		this.shipping_charge = shipping_charge;
	}

	public int getPosted_user_id() {
		return posted_user_id;
	}

	public void setPosted_user_id(int posted_user_id) {
		this.posted_user_id = posted_user_id;
	}

	public String getStore_url() {
		return store_url;
	}

	public void setStore_url(String store_url) {
		this.store_url = store_url;
	}

	public String getShareurl() {
		return shareurl;
	}

	public void setShareurl(String shareurl) {
		this.shareurl = shareurl;
	}

	public String getPosted_username() {
		return posted_username;
	}

	public void setPosted_username(String posted_username) {
		this.posted_username = posted_username;
	}

	public int getPopularity_count() {
		return popularity_count;
	}

	public void setPopularity_count(int popularity_count) {
		this.popularity_count = popularity_count;
	}

	public int getComments_count() {
		return comments_count;
	}

	public void setComments_count(int comments_count) {
		this.comments_count = comments_count;
	}

	public String getDeal_detail() {
		return deal_detail;
	}

	public void setDeal_detail(String deal_detail) {
		this.deal_detail = deal_detail;
	}

	public int getPosted_user_current_dimes() {
		return posted_user_current_dimes;
	}

	public void setPosted_user_current_dimes(int posted_user_current_dimes) {
		this.posted_user_current_dimes = posted_user_current_dimes;
	}

	public String getPosted_user_image() {
		return posted_user_image;
	}

	public void setPosted_user_image(String posted_user_image) {
		this.posted_user_image = posted_user_image;
	}

	public String getPosted_user_rank() {
		return posted_user_rank;
	}

	public void setPosted_user_rank(String posted_user_rank) {
		this.posted_user_rank = posted_user_rank;
	}

	public String getDeal_category_name() {
		return deal_category_name;
	}

	public void setDeal_category_name(String deal_category_name) {
		this.deal_category_name = deal_category_name;
	}

	public int getDeal_category_id() {
		return deal_category_id;
	}

	public void setDeal_category_id(int deal_category_id) {
		this.deal_category_id = deal_category_id;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public int getStore_id() {
		return store_id;
	}

	public void setStore_id(int store_id) {
		this.store_id = store_id;
	}

	public String getPic_thumb() {
		return pic_thumb;
	}

	public void setPic_thumb(String pic_thumb) {
		this.pic_thumb = pic_thumb;
	}

}
