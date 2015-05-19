package com.example.desidimeassignment.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.desidimeassignment.DealActivity;
import com.example.desidimeassignment.R;
import com.example.desidimeassignment.dataobject.DealsResultMainDo;
import com.squareup.picasso.Picasso;

public class DealListViewAdapter extends BaseAdapter {

	private Context context;
	private DealsResultMainDo dealsResultMainDo;
	private int pageType;

	public DealListViewAdapter(Context context,
			DealsResultMainDo dealsResultMainDo, int position) {
		this.context = context;
		this.dealsResultMainDo = dealsResultMainDo;
		this.pageType = position;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub

		if (pageType == DealActivity.TAB_TOP_DEALS) {
			return dealsResultMainDo.getResult().getTop().size();
		} else {
			return dealsResultMainDo.getResult().getPopular().size();
		}

	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.deals_product_list_item, null);
		}

		TextView productTitleTextView = (TextView) convertView
				.findViewById(R.id.productTitleTextView);
		TextView productUpdateTime = (TextView) convertView
				.findViewById(R.id.productUpdateTime);
		TextView productPriceTextView = (TextView) convertView
				.findViewById(R.id.productPriceTextView);
		TextView productPerOffTextView = (TextView) convertView
				.findViewById(R.id.productPerOffTextView);
		TextView productCalculatePriceOffTextView = (TextView) convertView
				.findViewById(R.id.productCalculatePriceOffTextView);
		TextView commentCounterTextView = (TextView) convertView
				.findViewById(R.id.commentCounterTextView);
		TextView shareCounterTextView = (TextView) convertView
				.findViewById(R.id.shareCounterTextView);
		TextView productProviderTextView = (TextView) convertView
				.findViewById(R.id.productProviderTextView);

		ImageView productImageView = (ImageView) convertView
				.findViewById(R.id.productImageView);

		if (pageType == DealActivity.TAB_TOP_DEALS) {
			productTitleTextView.setText(dealsResultMainDo.getResult().getTop()
					.get(position).getTitle()
					+ "");

			productUpdateTime.setText("about 1 day ago");

			productPriceTextView.setText("Rs."
					+ dealsResultMainDo.getResult().getTop().get(position)
							.getCurrent_price() + "");

			productPerOffTextView.setText("("
					+ dealsResultMainDo.getResult().getTop().get(position)
							.getOff_percent() + "% off)");

			productCalculatePriceOffTextView.setText(dealsResultMainDo
					.getResult().getTop().get(position).getOriginal_price()
					+ "");

			productCalculatePriceOffTextView
					.setPaintFlags(productCalculatePriceOffTextView
							.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

			commentCounterTextView.setText(dealsResultMainDo.getResult()
					.getTop().get(position).getComments_count()
					+ "");

			shareCounterTextView.setText(dealsResultMainDo.getResult().getTop()
					.get(position).getPopularity_count()
					+ "");

			productProviderTextView.setText("at "
					+ dealsResultMainDo.getResult().getTop().get(position)
							.getStore() + "");

			setProductImage(productImageView, dealsResultMainDo.getResult()
					.getTop().get(position).getPic_thumb());
		} else {
			productTitleTextView.setText(dealsResultMainDo.getResult()
					.getPopular().get(position).getTitle()
					+ "");

			productUpdateTime.setText("about 1 day ago");

			productPriceTextView.setText("Rs."
					+ dealsResultMainDo.getResult().getPopular().get(position)
							.getCurrent_price() + "");

			productPerOffTextView.setText("("
					+ dealsResultMainDo.getResult().getPopular().get(position)
							.getOff_percent() + "% off)");

			productCalculatePriceOffTextView.setText(dealsResultMainDo
					.getResult().getPopular().get(position).getOriginal_price()
					+ "");

			productCalculatePriceOffTextView
					.setPaintFlags(productCalculatePriceOffTextView
							.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

			commentCounterTextView.setText(dealsResultMainDo.getResult()
					.getPopular().get(position).getComments_count()
					+ "");

			shareCounterTextView.setText(dealsResultMainDo.getResult()
					.getPopular().get(position).getPopularity_count()
					+ "");

			productProviderTextView.setText("at "
					+ dealsResultMainDo.getResult().getPopular().get(position)
							.getStore() + "");

			setProductImage(productImageView, dealsResultMainDo.getResult()
					.getPopular().get(position).getPic_thumb());
		}

		return convertView;
	}

	/**
	 * This method will be responsible to set product image.. using Picasso
	 * library
	 */
	public void setProductImage(ImageView imageView, String picUrl) {

		Picasso.with(context).load(R.drawable.ic_launcher).into(imageView);

		Picasso.with(context).load(picUrl).placeholder(R.drawable.ic_launcher)
				.error(R.drawable.ic_launcher).into(imageView);

	}
}
