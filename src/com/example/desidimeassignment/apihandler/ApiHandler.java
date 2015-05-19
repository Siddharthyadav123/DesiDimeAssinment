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

	// private void downloadImages(GallaryResponseDO gallaryResponseDO,
	// LocalDataBase dataBase) {
	//
	// // deleting existing pics from database
	// dataBase.deleteAllGalaryPics();
	// System.out.println(">>deleted galary pic");
	//
	// for (int i = 0; i < gallaryResponseDO.getSlider().size(); i++) {
	//
	// try {
	// MenuActivity.galaryBitmap.add(BitmapFactory
	// .decodeStream((InputStream) new URL(
	// AppConstants.URL_GET_GALARY_PICS_BASE_URL
	// + gallaryResponseDO.getSlider().get(i)
	// .getImageName()).getContent()));
	//
	// System.out.println(">>sid>>image= " + i);
	//
	// Bitmap bitmap = MenuActivity.galaryBitmap.get(i);
	// ByteArrayOutputStream bos = new ByteArrayOutputStream();
	// bitmap.compress(CompressFormat.PNG, 0 /* ignored for PNG */, bos);
	// byte[] bitmapdata = bos.toByteArray();
	//
	// // storing image name and images
	// dataBase.addGalaryImageInLocalDataBase(gallaryResponseDO
	// .getSlider().get(i).getImageName(), gallaryResponseDO
	// .getSlider().get(i).getModified(), bitmapdata);
	//
	// } catch (Exception e) {
	// isError = true;
	// errorString = e.getMessage();
	// break;
	// }
	// }
	//
	// }

	// private String getURL(String[] params) {
	//
	// String URL = null;
	// switch (requestType) {
	// case AppConstants.API_REQUEST_SIGN_IN:
	// URL = AppConstants.URL_SIGN_IN + "?userName=" + params[0]
	// + "&password=" + params[1];
	// break;
	// case AppConstants.API_REQUEST_SIGN_UP:
	//
	// /**
	// * http://admin.capellosalons.com/json/signup.php? firstName
	// * =shital&lastName=kalbande&email=shitalkalbande123
	// *
	// * @gmail. com&userName=shital123&password=admin123&address
	// * =Nagpur&contact=9021000000
	// */
	//
	// URL = AppConstants.URL_SIGN_UP + "?firstName=" + params[0]
	// + "&lastName=" + params[1] + "&email=" + params[2]
	// + "&userName=" + params[3] + "&password=" + params[4]
	// + "&address=" + params[5] + "&contact=" + params[6];
	//
	// break;
	// case AppConstants.API_REQUEST_REGISTRATION:
	// break;
	// case AppConstants.API_REQUEST_GALARY_PICS:
	//
	// URL = AppConstants.URL_GET_GALARY_PICS;
	//
	// break;
	// case AppConstants.API_REQUEST_FIX_APPOINMENTS:
	//
	// // http://admin.capellosalons.com
	// //
	// /json/fixAppointment.php?userID=9&appointmentDate=2015-1-21&appointmentTime=10:00
	// // 20am&name=sid&category=CASA&service=hair cut
	//
	// // url won't take '&' so need to replace that
	// // params[5] = params[5].replaceAll("&", "and");
	//
	// URL = AppConstants.URL_FIX_APPOINTMENT;
	//
	// break;
	// case AppConstants.API_REQUEST_GET_CATALOGUE:
	// break;
	// case AppConstants.API_REQUEST_GET_STORE_DATA:
	// URL = AppConstants.URL_GET_STORE_INFORMATION;
	// break;
	// case AppConstants.API_REQUEST_POST_FEEDBACK:
	//
	// /**
	// * http://admin.capellosaloonandspa.com/Json/userFeedback.php?
	// * userID=4& ratings=3&serviceType=Hair Cut&message=nice services
	// */
	//
	// // url won't take '&' so need to replace that
	// params[2] = params[2].replaceAll("&", "and");
	// params[3] = params[3].replaceAll("&", "and");
	//
	// URL = AppConstants.URL_SEND_FEEDBACK + "?userID=" + params[0]
	// + "&ratings=" + params[1] + "&serviceType=" + params[2]
	// + "&message=" + params[3];
	//
	// break;
	//
	// case AppConstants.API_REQUEST_GET_OFFERS:
	// URL = AppConstants.URL_GET_OFFERS;
	// break;
	//
	// case AppConstants.API_REQUEST_GET_PACKAGES:
	// URL = AppConstants.URL_GET_PACKAGES;
	// break;
	//
	// }
	// return URL;
	// }
	//
	// private void performSomeActionIfNeed() {
	//
	// switch (requestType) {
	// case AppConstants.API_REQUEST_SIGN_IN:
	// break;
	// case AppConstants.API_REQUEST_SIGN_UP:
	// break;
	// case AppConstants.API_REQUEST_REGISTRATION:
	// break;
	// case AppConstants.API_REQUEST_GALARY_PICS:
	//
	// Gson gson = new Gson();
	// GallaryResponseDO gallaryResponseDO = gson.fromJson(
	// jsonResponseString, GallaryResponseDO.class);
	//
	// getBitmap(gallaryResponseDO);
	// break;
	// case AppConstants.API_REQUEST_FIX_APPOINMENTS:
	// break;
	// case AppConstants.API_REQUEST_GET_CATALOGUE:
	// break;
	// case AppConstants.API_REQUEST_GET_STORE_DATA:
	// break;
	// case AppConstants.API_REQUEST_POST_FEEDBACK:
	// break;
	// case AppConstants.API_REQUEST_GET_OFFERS:
	// break;
	// }
	// }

}