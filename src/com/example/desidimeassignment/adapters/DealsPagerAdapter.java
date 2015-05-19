package com.example.desidimeassignment.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.example.desidimeassignment.R;
import com.example.desidimeassignment.dataobject.DealsResultMainDo;

public class DealsPagerAdapter extends PagerAdapter {

	private Context context;
	private DealsResultMainDo dealsResultMainDo;

	public DealsPagerAdapter(Context context,
			DealsResultMainDo dealsResultMainDo) {
		this.context = context;
		this.dealsResultMainDo = dealsResultMainDo;
	}

	@Override
	public int getCount() {
		return 2;
	}

	@Override
	public Object instantiateItem(View collection, int position) {

		View view = LayoutInflater.from(context).inflate(
				R.layout.deals_pager_layout, null);

		ListView productListView = (ListView) view
				.findViewById(R.id.productItemListView);

		DealListViewAdapter dealsPagerAdapter = new DealListViewAdapter(
				context, dealsResultMainDo, position);
		productListView.setAdapter(dealsPagerAdapter);

		((ViewPager) collection).addView(view, 0);
		return view;

	}

	@Override
	public void destroyItem(View collection, int position, Object view) {
		((ViewPager) collection).removeView((View) view);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((View) object);
	}

}
