package com.example.desidimeassignment.dataobject;

import java.util.ArrayList;

/**
 * class to keep deals.. i.e. tops and popular based on json response
 * 
 * @author sid
 * 
 */
public class DealsResultMainDo {

	private String errorcode;
	private String errorstr;
	private Result result;

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	public String getErrorstr() {
		return errorstr;
	}

	public void setErrorstr(String errorstr) {
		this.errorstr = errorstr;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	/**
	 * class to hold tops and products both
	 * 
	 * @author sid
	 * 
	 */
	public class Result {

		// arraylist to keep top products
		private ArrayList<TopProductsDO> top;

		// arraylist to keep popular products
		private ArrayList<PopularProductsDO> popular;

		public ArrayList<TopProductsDO> getTop() {
			return top;
		}

		public void setTop(ArrayList<TopProductsDO> top) {
			this.top = top;
		}

		public ArrayList<PopularProductsDO> getPopular() {
			return popular;
		}

		public void setPopular(ArrayList<PopularProductsDO> popular) {
			this.popular = popular;
		}

	}
}
