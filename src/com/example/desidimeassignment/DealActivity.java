package com.example.desidimeassignment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.desidimeassignment.adapters.DealsPagerAdapter;
import com.example.desidimeassignment.apihandler.ApiHandler;
import com.example.desidimeassignment.apihandler.UpdateViewsListener;
import com.example.desidimeassignment.dataobject.DealsResultMainDo;
import com.example.desidimeassignment.net.HttpRequestHandlerImpl;
import com.google.gson.Gson;

/**
 * This activity class will be responsible for showing deals
 * 
 * @author siddharth
 * 
 */
public class DealActivity extends Activity implements OnClickListener,
		UpdateViewsListener {

	/**
	 * Tabs views
	 */
	private LinearLayout topTabLinearLayout;
	private LinearLayout popularTabLinearLayout;
	private TextView topTabTextView;
	private TextView PopularTabTextView;

	/**
	 * Deals view pager
	 */
	private ViewPager dealsViewPager;
	/**
	 * Flags to tell current selected deal
	 */
	public static final int TAB_TOP_DEALS = 0;
	public static final int TAB_POPULAR_DEALS = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deal_main_layout);
		initViews();
		registerEvents();

		setTabSelectionColor(TAB_TOP_DEALS);

		ApiHandler requestDeals = new ApiHandler(this, this, 1,
				"Loading Deals", HttpRequestHandlerImpl.HTTP_POST, true);
		requestDeals.execute();
	}

	/**
	 * method to initialize views
	 */
	private void initViews() {
		topTabLinearLayout = (LinearLayout) findViewById(R.id.topTabLinearLayout);
		popularTabLinearLayout = (LinearLayout) findViewById(R.id.popularTabLinearLayout);
		topTabTextView = (TextView) findViewById(R.id.topTabTextView);
		PopularTabTextView = (TextView) findViewById(R.id.PopularTabTextView);
		dealsViewPager = (ViewPager) findViewById(R.id.dealsViewPager);
	}

	/**
	 * method to register events
	 */
	private void registerEvents() {

		topTabLinearLayout.setOnClickListener(this);
		popularTabLinearLayout.setOnClickListener(this);
		registerSlideEventOnViewPager();

	}

	private void registerSlideEventOnViewPager() {
		dealsViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int pageNo) {
				// setting tab on page change
				setTabSelectionColor(pageNo);

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	/**
	 * setting deal view pager
	 * 
	 * @param dealsResultMainDo
	 */
	private void setUpDealViewPager(DealsResultMainDo dealsResultMainDo) {
		DealsPagerAdapter genrePageAdapter = new DealsPagerAdapter(this,
				dealsResultMainDo);
		dealsViewPager.setAdapter(genrePageAdapter);
	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.topTabLinearLayout:
			onTopDealTabClick(view);
			break;

		case R.id.popularTabLinearLayout:
			onPopularDealTabClick(view);
			break;
		}

	}

	/**
	 * method to perform action on popular button tab
	 */
	private void onPopularDealTabClick(View view) {
		setTabSelectionColor(TAB_POPULAR_DEALS);
		dealsViewPager.setCurrentItem(TAB_POPULAR_DEALS, false);
	}

	/**
	 * method to perform action on top tab click
	 * 
	 * @param view
	 */
	private void onTopDealTabClick(View view) {
		setTabSelectionColor(TAB_TOP_DEALS);
		dealsViewPager.setCurrentItem(TAB_TOP_DEALS, false);
	}

	/**
	 * setting color on tabs
	 * 
	 * @param pageNo
	 */
	private void setTabSelectionColor(int pageNo) {

		switch (pageNo) {
		case TAB_TOP_DEALS:
			topTabLinearLayout.setBackgroundColor(getResources().getColor(
					R.color.VeryLightGray));
			popularTabLinearLayout.setBackgroundColor(getResources().getColor(
					R.color.DarkGray));
			topTabTextView.setTextColor(getResources().getColor(R.color.Black));
			PopularTabTextView.setTextColor(getResources().getColor(
					R.color.White));
			break;
		case TAB_POPULAR_DEALS:
			topTabLinearLayout.setBackgroundColor(getResources().getColor(
					R.color.DarkGray));
			popularTabLinearLayout.setBackgroundColor(getResources().getColor(
					R.color.VeryLightGray));
			topTabTextView.setTextColor(getResources().getColor(R.color.White));
			PopularTabTextView.setTextColor(getResources().getColor(
					R.color.Black));
			break;
		}

	}

	@Override
	public void updateViews(int requestType, String responseJsonString,
			boolean isErrorInResponse, String errorString) {

		// Parsed response json string using Gson library which will parse the
		// json
		Gson gson = new Gson();
		DealsResultMainDo dealsResultMainDo = gson.fromJson(responseJsonString,
				DealsResultMainDo.class);
		// setting up view pager
		setUpDealViewPager(dealsResultMainDo);

	}

}
