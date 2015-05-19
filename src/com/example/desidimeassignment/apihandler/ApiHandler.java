package com.example.desidimeassignment.apihandler;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.desidimeassignment.net.HttpRequestHandlerImpl;

public class ApiHandler extends AsyncTask<String, Integer, String> {

	private int requestType;
	private Context context;
	private UpdateViewsListener updateViewsListener;
	private String loadingProgressMsgString;
	private ProgressDialog progressBar;
	private boolean isError = false;
	private String errorString = "No Error";

	private String jsonResponseString = null;
	private String methodType;

	private boolean showPopUpProgress = false;

	private String URL = "http://www.desidime.com/api/v1/premium_deals/list/";

	public ApiHandler(Context context, UpdateViewsListener updateViewListener,
			int requestType, String loadingProgressMsgString,
			String methodType, boolean showPopUpProgress) {

		this.requestType = requestType;
		this.context = context;
		this.updateViewsListener = updateViewListener;
		this.loadingProgressMsgString = loadingProgressMsgString;
		this.methodType = methodType;
		this.showPopUpProgress = showPopUpProgress;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		// showing dialog popup
		if (showPopUpProgress) {
			progressBar = new ProgressDialog(context);
			progressBar.setMessage(loadingProgressMsgString);
			progressBar.setIndeterminate(false);
			progressBar.setCancelable(false);
			progressBar.show();
		}

	}

	@Override
	protected String doInBackground(String... params) {

		try {

			HttpRequestHandlerImpl httpRequestHandlerImpl = new HttpRequestHandlerImpl(
					URL, context);
			httpRequestHandlerImpl.setMethodType(methodType);

			if (methodType == HttpRequestHandlerImpl.HTTP_POST) {

				String postString = "";

				/**
				 * adding headers
				 */
				httpRequestHandlerImpl.setHeaders("accept", "application/json");

				httpRequestHandlerImpl.setHeaders("accept", "text/javascript");

				jsonResponseString = new String(
						httpRequestHandlerImpl.execute(postString));
			}

			System.out.println(">>sid>>response= " + jsonResponseString);

		} catch (Exception e) {

			isError = true;
			errorString = e.getMessage();

		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);

		if (showPopUpProgress) {
			if (progressBar != null && progressBar.isShowing())
				progressBar.dismiss();
		}
		checkForErrorAndUpdateView();

	}

	private void checkForErrorAndUpdateView() {

		if (jsonResponseString.toString().startsWith("{\"error\"")) {

			try {
				isError = true;
				JSONObject jsongObejct = new JSONObject(jsonResponseString);
				errorString = jsongObejct.get("error").toString();
			} catch (Exception e) {
				e.printStackTrace();
				errorString = e.getMessage();
			}

		}

		updateViewsListener.updateViews(requestType, jsonResponseString,
				isError, errorString);

	}

}