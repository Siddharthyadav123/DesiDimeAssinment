package com.example.desidimeassignment.dataobject;

/**
 * Class to keep Top products parsed info
 * 
 * @author siddharth
 * 
 */

// Respose Example:

/*
 * "id": 52537, "title": "Raymond & Park Avenue at 50%+30%", "off_percent":
 * "60", "store": "Jabong", "current_price": 300, "original_price": 650,
 * "shipping_charge": 0, "posted_user_id": 102440, "store_url":
 * "http://links.desidime.com?ref=mobile_deals&url=http://www.jabong.com/men/clothing/raymond/%3Fdir=asc%26forder=q--brand%26q=raymond%2520men%2520clothing%26qc=raymond%2520men%2520clothing%26sort=price"
 * , "shareurl":
 * "http://www.desidime.com/premium_deals/raymond-park-avenue-at-50-30",
 * "posted_username": "Lega©y", "popularity_count": 1046, "comments_count": 14,
 * "deal_detail": "
 * 
 * "posted_user_current_dimes": 27, "posted_user_image":
 * "http://cdn0.desidime.com/avatars/102440/medium/LEGACY-__stackedcentre_RGB-hi_res.jpg?1420895370"
 * , "posted_user_rank": "Deal Major", "deal_category_name":
 * "Fashion & Apparels", "deal_category_id": 1, "created_at":
 * "2015-05-17T20:30:01+05:30", "store_id": 384, "pic_thumb":
 * "http://cdn0.desidime.com/photos/52537/small/Raymond-Blue-Slim-Fit-V-Neck-Sweater-8604-565967-1-catalog.jpg?1431874797"
 */

public class TopProductsDO {

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
