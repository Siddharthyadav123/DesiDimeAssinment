package com.example.desidimeassignment.net;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpConnection;

import com.example.desidimeassignment.R;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

/**
 * HttpRequestHandler.java The class to implement the logic for sending and
 * receiving data from server
 * 
 * @author ashishpatel
 * 
 */

public class HttpRequestHandler implements INetworkHandler {

	/**
	 * Constant for communicating with server with standard HTTP GET Method
	 */
	public static final String HTTP_GET = "GET";

	/**
	 * Constant for communicating with server with standard HTTP POST Method
	 */
	public static final String HTTP_POST = "POST";

	/**
	 * Constant for communicating with server with standard HTTP DELETE Method
	 */

	public static final String HTTP_DELETE = "DELETE";
	/**
	 * Constant for communicating with server with standard HTTP PUT Method
	 */

	public static final String HTTP_PUT = "PUT";

	/**
	 * Hashtable to save headers parameters
	 */
	protected Hashtable<Object, Object> hashtable = new Hashtable<Object, Object>();

	/**
	 * The URL to connect to the server
	 */
	protected String serviceURL = null;

	/**
	 * Method type to establish HTTP Connection Default is GET Method
	 */
	protected String methodType = HTTP_GET;

	/**
	 * Cancel Request Variable to handle connection cancellation
	 */
	protected boolean cancelRequest = false;

	/**
	 * INetworkListener Reference for managing error and data communication
	 */
	protected INetworkListener listener;

	/**
	 * Variable to set connecting message
	 */
	protected String connectingMessage = "Connecting...";

	/**
	 * Variable to set loading data message
	 */
	protected String loadingMessage = "Loading...";

	/**
	 * Variable to set processing message
	 */
	protected String processingMessage = "Please wait...";

	/**
	 * HttpConnection reference to connect to the server
	 */
	protected HttpURLConnection connection;

	/**
	 * InputStream reference to read data from server
	 */
	protected InputStream inputStream = null;

	/**
	 * Count to handle Redirection code 302
	 */
	protected int redirectionCount = 0;
	/**
	 * Possible MAX Redirection
	 */
	public static final int MAX_REDIERCTION = 2;
	/**
	 * Request Count to handle request if any error occur for connection
	 */
	protected int requestCount = 0;
	/**
	 * Max Request Count, If there is an error to communicate with server this
	 * class will retry the connection till MAX_REQUEST
	 */
	public static final int MAX_REQUEST = 5;
	/**
	 * Maximum buffer size for reading data in one chunk
	 */
	public static final int MAX_BUFFER = 1024 * 16;// in KB

	// Below added by Gaurav

	/**
	 * maximum time should be taken to connect to the server
	 */
	public final int CONNECTION_TIMEOUT = 10000; // 10 sec

	/**
	 * maximum time should be taken to read from server
	 */

	public final int READ_TIMEOUT = 10000; // 10 sec

	/**
	 * context needed to check Internet connection from here
	 */
	public Context context;

	/**
	 * To set Method Type for HTTP Request
	 * 
	 * @param methodType
	 *            HttpRequestHandler.POST or HttpRequestHandler.GET
	 */
	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}

	public String getMethodType() {
		return methodType;
	}

	/**
	 * To set headers parameter before creating connection
	 * 
	 * @param key
	 *            To set key for header
	 * @param value
	 *            To set value for given key in header
	 */
	public void setHeaders(String key, String value) {
		hashtable.put(key, value);
	}

	public String getHeaders() {
		return hashtable.toString();
	}

	/**
	 * Cancel Server Communication Request
	 */
	public void cancelRequest() {
		this.cancelRequest = true;
		closeConnection();
	}

	/**
	 * Server url to request
	 * 
	 * @param url
	 */
	public void setServiceURL(String url) {
		serviceURL = url;
	}

	/**
	 * To Destroy Other data.
	 */
	public void destroy() {

		closeConnection(); // added by ashish
		if (hashtable != null) {
			hashtable.clear();
		}
		hashtable = null;
		serviceURL = null;

		listener = null;
		connectingMessage = null;
		loadingMessage = null;
		processingMessage = null;
		connection = null;
		inputStream = null;
	}

	/**
	 * Remove headers
	 */
	public void removeHeaders() {
		hashtable.clear();
	}

	/**
	 * To Open a new connection to server
	 * 
	 * @return HttpConnection reference
	 * @throws java.lang.Exception
	 * @see HttpConnection
	 */
	public HttpURLConnection openConnection() throws Exception {

		URL url = new URL(serviceURL);
		HttpURLConnection urlConnection = (HttpURLConnection) url
				.openConnection();

		try {

			// hashtable.put("User-Agent", System.getProperty("http.agent"));
			hashtable.put("User-Agent", System.getProperty("http.agent"));
			Enumeration enumeration = hashtable.keys();
			while (enumeration.hasMoreElements()) {
				String key = enumeration.nextElement().toString();
				urlConnection.setRequestProperty(key, hashtable.get(key)
						.toString());
			}
			// Tools.printLog(" >>"
			// +urlConnection.getRequestProperties().toString());

			// urlConnection.setRequestProperty("User-Agent",
			// System.getProperty("http.agent"));
			// Tools.printLog("user agent 11 "+System.getProperty("http.agent"));
			urlConnection.setRequestMethod(methodType);

		} catch (Exception e) {
			// Logger.e(
			// MainActivity.getInstance(),
			// "Exception",
			// " [HttpRequestHandler] [openConnection] : "
			// + Calendar.getInstance().getTime() + ", "
			// + e.getMessage());
		}
		return urlConnection;

	}

	/**
	 * Recieve Data from server
	 * 
	 * @return Response from server in form of ByteArray
	 * @throws java.lang.Exception
	 */
	public byte[] execute() throws Exception {

		// ByteArrayOutputStream References
		ByteArrayOutputStream baos = null;

		try {

			// checking whether network available or not
			if (!Utils.isNetworkAvailable(context)) {

				throw new WifiMobileDataException(
						context.getString(R.string.no_internet_access_tryagain));
			}

			// opening connection
			connection = openConnection();
			connection.setConnectTimeout(CONNECTION_TIMEOUT);
			connection.setReadTimeout(READ_TIMEOUT);

			if (cancelRequest) {
				throw new CancelException(
						context.getString(R.string.request_canelled));
			}
			if (listener != null) {
				listener.setLoadingMessage(connectingMessage);
			}
			connection.connect();
			// getting response code to handle error
			int responseCode = connection.getResponseCode();

			if (cancelRequest) {
				throw new CancelException(
						context.getString(R.string.request_canelled));
			}

			int byteWritten = 0;
			int length = 0;
			// responseCode = 500;
			switch (responseCode) {
			// status 406 means resource is only capable of generating content
			// not acceptable according to the Accept headers sent in the
			// request. addded by gaurav for delete mix
			case 406:
				return "{}".toString().getBytes();
			case 200:
				break;
			case 302:
				if (redirectionCount < MAX_REDIERCTION) {
					redirectionCount++;
					setServiceURL(connection.getHeaderField("Location"));
					closeConnection(connection, null, inputStream, baos);
					return execute();
				}
				break;
			default: {

				String str = connection.getHeaderField("Content-Encoding");

				// data is zipped we need to use gzipinputstream
				if (str != null && str.compareTo("gzip") != -1) {
					inputStream = new GZIPInputStream(
							connection.getErrorStream());
				} else {
					inputStream = connection.getErrorStream();
				}

				baos = new ByteArrayOutputStream();
				byte[] buff = new byte[MAX_BUFFER];

				while ((length = inputStream.read(buff)) != -1) {
					baos.write(buff, 0, length);
				}

				// checking whether network available or not
				if (!Utils.isNetworkAvailable(context)) {

					throw new WifiMobileDataException(
							context.getString(R.string.no_internet_access_tryagain));
				}

				String errorData = new String(baos.toByteArray());
				// Logger.i(MainActivity.getInstance(), "ErrorData",
				// " [HttpRequestHandler] [execute()] : "
				// + Calendar.getInstance().getTime() + ", "
				// + " errorData : " + errorData
				// + ", responseCode : " + responseCode);
				// if data is not in json format we need to throw the exception
				if (errorData.trim().charAt(0) == '{') {

					throw new ServerException(errorData);

				} else {

					throw new ServerException(
							context.getString(R.string.service_temp_unavailable));
				}

			}

			}
			if (listener != null) {
				listener.setLoadingMessage(loadingMessage);
			}
			// opening input stream
			String str = connection.getHeaderField("Content-Encoding");
			//
			// data is zipped we need to use gzipinputstream
			if (str != null && str.compareTo("gzip") != -1) {
				inputStream = new GZIPInputStream(connection.getInputStream());
				String contentLength = connection
						.getHeaderField("Content-Length");
			} else {
				inputStream = connection.getInputStream();
			}
			baos = new ByteArrayOutputStream();
			byte[] buff = new byte[MAX_BUFFER];

			while ((length = inputStream.read(buff)) != -1) {

				baos.write(buff, 0, length);
				byteWritten += length;
			}

			if (listener != null) {
				listener.setLoadingMessage(processingMessage);
			}
			// Tools.printLog("in execute >> " + cancelRequest);
			if (cancelRequest) {
				throw new CancelException(
						context.getString(R.string.request_canelled));
			}

			// checking whether network available or not
			if (!Utils.isNetworkAvailable(context)) {

				throw new WifiMobileDataException(
						context.getString(R.string.no_internet_access_tryagain));
			}
			byte data[] = baos.toByteArray();

			if (listener != null) {
				listener.onDataRecieved(data);
			}

			return baos.toByteArray();
		} catch (CancelException cancelException) {
			// Logger.e(MainActivity.getInstance(), "Exception",
			// " [HttpRequestHandler] [execute] [CancelException] : "
			// + Calendar.getInstance().getTime() + ", " + ", "
			// + cancelException.getMessage());
			throw cancelException;
		} catch (ServerException serverException) {
			// Logger.e(MainActivity.getInstance(), "Exception",
			// " [HttpRequestHandler] [execute] [ServerException]: "
			// + Calendar.getInstance().getTime() + ", "
			// + serverException.getMessage());

			if (!Utils.isNetworkAvailable(context)) {

				throw new WifiMobileDataException(
						context.getString(R.string.no_internet_access_tryagain));
			}

			throw serverException;
		} catch (WifiMobileDataException wifiMobileDataException) {
			// Logger.e(MainActivity.getInstance(), "Exception",
			// " [HttpRequestHandler] [execute] [WifiMobileDataException]: "
			// + Calendar.getInstance().getTime() + ", "
			// + wifiMobileDataException.getMessage());
			// hitting api again in the case of next song error

			throw wifiMobileDataException;
		} catch (UnknownHostException exception) {
			// Logger.e(MainActivity.getInstance(), "Exception",
			// " [HttpRequestHandler] [execute] [UnknownHostException]: "
			// + Calendar.getInstance().getTime() + ", "
			// + exception.getMessage());
			// hitting api again in the case of next song error

			if (!Utils.isNetworkAvailable(context)) {

				throw new WifiMobileDataException(
						context.getString(R.string.no_internet_access_tryagain));
			}

			// occurs when there is internet connention but no data left
			throw new UnknownHostException(
					context.getString(R.string.unable_to_fetch_data));
		} catch (ConnectException exception) {
			// Logger.e(MainActivity.getInstance(), "Exception",
			// " [HttpRequestHandler] [execute] [ConnectException]: "
			// + Calendar.getInstance().getTime() + ", "
			// + exception.getMessage());

			// checking whether network available or not
			if (!Utils.isNetworkAvailable(context)) {

				throw new WifiMobileDataException(
						context.getString(R.string.no_internet_access_tryagain));
			}
			// need to again make the object to set string as
			// "connection timeout"
			// occurs when there is internet connention but no data left
			throw new UnknownHostException(
					context.getString(R.string.unable_to_fetch_data));

		} catch (SocketTimeoutException exception) {
			// Logger.e(MainActivity.getInstance(), "Exception",
			// " [HttpRequestHandler] [execute] [SocketTimeoutException]: "
			// + Calendar.getInstance().getTime() + ", "
			// + exception.getMessage());
			// hitting api again in the case of next song error

			// checking whether network available or not
			if (!Utils.isNetworkAvailable(context)) {

				throw new WifiMobileDataException(
						context.getString(R.string.no_internet_access_tryagain));
			}
			// need to again make the object to set string as
			// "connection timeout"
			exception = new SocketTimeoutException(
					context.getString(R.string.unable_to_fetch_data));

			throw exception;
		} catch (IOException ioe) {
			// Logger.e(
			// MainActivity.getInstance(),
			// "Exception",
			// " [HttpRequestHandler] [execute] [IOException]: "
			// + Calendar.getInstance().getTime() + ", "
			// + ioe.getMessage());
			// checking whether network available or not
			if (!Utils.isNetworkAvailable(context)) {

				throw new WifiMobileDataException(
						context.getString(R.string.no_internet_access_tryagain));
			}
			// Tools.showExceptionLog("ioe; " + ioe.getMessage(),e);

			if (requestCount < MAX_REQUEST) {
				System.out.println(">>sid>>again requesting");
				requestCount++;
				closeConnection(connection, null, inputStream, baos);
				return execute();
			} else {
				requestCount = 0;
				if (cancelRequest) {
					throw new CancelException(
							context.getString(R.string.request_canelled));
				} else {
					throw ioe;
				}
			}

		} catch (Exception ex) {
			// Logger.e(
			// MainActivity.getInstance(),
			// "Exception",
			// " [HttpRequestHandler] [execute] [Exception]: "
			// + Calendar.getInstance().getTime() + ", "
			// + ex.getMessage());
			// hitting api again in the case of next song error

			if (cancelRequest) {
				throw new CancelException(
						context.getString(R.string.request_canelled));
			} else {
				throw ex;
			}
		} finally {
			closeConnection(connection, null, inputStream, baos);

		}

	}

	/**
	 * Recieve Data from server
	 * 
	 * @return Response from server in form of ByteArray
	 * @throws java.lang.Exception
	 */
	public byte[] execute(String data) throws Exception {

		ByteArrayOutputStream baos = null;

		try {

			// checking whether network available or not
			if (!Utils.isNetworkAvailable(context)) {

				throw new WifiMobileDataException(
						context.getString(R.string.no_internet_access_tryagain));
			}

			// opening connection
			connection = openConnection();
			connection.setConnectTimeout(CONNECTION_TIMEOUT);
			connection.setReadTimeout(READ_TIMEOUT);
			// set content length
			connection.setRequestProperty("Content-Length",
					"" + Integer.toString(data.getBytes().length));

			connection.setDoInput(true);
			connection.setDoOutput(true);

			DataOutputStream wr = new DataOutputStream(
					connection.getOutputStream());

			wr.write(data.toString().getBytes());

			wr.flush();
			wr.close();

			if (cancelRequest) {
				throw new CancelException(
						context.getString(R.string.request_canelled));
			}
			if (listener != null) {
				listener.setLoadingMessage(connectingMessage);
			}
			connection.connect();
			// getting response code to handle error
			int responseCode = connection.getResponseCode();

			if (cancelRequest) {
				throw new CancelException(
						context.getString(R.string.request_canelled));
			}

			int byteWritten = 0;
			int length = 0;
			// responseCode = 500;
			switch (responseCode) {
			// 201 added by gaurav as it's needed, 201 indicates successfully
			// something created..added for create mix
			case 201:
				break;
			case 200:
				break;
			case 302:
				if (redirectionCount < MAX_REDIERCTION) {
					redirectionCount++;
					setServiceURL(connection.getHeaderField("Location"));
					closeConnection(connection, null, inputStream, baos);
					return execute(data);
				}
				break;
			default: {

				String str = connection.getHeaderField("Content-Encoding");

				// data is zipped we need to use gzipinputstream
				if (str != null && str.compareTo("gzip") != -1) {

					inputStream = new GZIPInputStream(
							connection.getErrorStream());

				} else {
					inputStream = connection.getErrorStream();
				}

				baos = new ByteArrayOutputStream();
				byte[] buff = new byte[MAX_BUFFER];

				while ((length = inputStream.read(buff)) != -1) {
					baos.write(buff, 0, length);
				}

				// checking whether network available or not
				if (!Utils.isNetworkAvailable(context)) {

					throw new WifiMobileDataException(
							context.getString(R.string.no_internet_access_tryagain));
				}

				String errorData = new String(baos.toByteArray());

				// Logger.i(MainActivity.getInstance(), "ErrorData",
				// " [HttpRequestHandler] [execute(String data)] : "
				// + Calendar.getInstance().getTime() + ", "
				// + " errorData : " + errorData
				// + ", responseCode : " + responseCode);
				// if data is not in json format we need to throw the exception
				if (errorData.trim().charAt(0) == '{') {

					throw new ServerException(errorData);
				} else {
					throw new ServerException(
							context.getString(R.string.service_temp_unavailable));
				}

			}

			}
			if (listener != null) {
				listener.setLoadingMessage(loadingMessage);
			}
			// opening input stream
			String str = connection.getHeaderField("Content-Encoding");

			if (str != null && str.compareTo("gzip") != -1) {

				inputStream = new GZIPInputStream(connection.getInputStream());

			} else {
				inputStream = connection.getInputStream();

			}
			baos = new ByteArrayOutputStream();
			byte[] buff = new byte[MAX_BUFFER];

			while ((length = inputStream.read(buff)) != -1) {

				baos.write(buff, 0, length);
				byteWritten += length;
			}

			if (listener != null) {
				listener.setLoadingMessage(processingMessage);
			}
			// Tools.printLog("in execute >> " + cancelRequest);
			if (cancelRequest) {
				throw new CancelException(
						context.getString(R.string.request_canelled));
			}

			// checking whether network available or not
			if (!Utils.isNetworkAvailable(context)) {

				throw new WifiMobileDataException(
						context.getString(R.string.no_internet_access_tryagain));
			}

			byte response[] = baos.toByteArray();

			if (listener != null) {
				listener.onDataRecieved(response);
			}

			return response;
		} catch (CancelException cancelException) {
			// Logger.e(MainActivity.getInstance(), "Exception",
			// " [HttpRequestHandler] [execute(String data) ] [CancelException]: "
			// + Calendar.getInstance().getTime() + ", "
			// + cancelException.getMessage());
			throw cancelException;
		} catch (ServerException serverException) {
			// Logger.e(MainActivity.getInstance(), "Exception",
			// " [HttpRequestHandler] [execute(String data)] [ServerException]: "
			// + Calendar.getInstance().getTime() + ", "
			// + serverException.getMessage());
			// hitting api again in the case of next song error

			if (!Utils.isNetworkAvailable(context)) {

				throw new WifiMobileDataException(
						context.getString(R.string.no_internet_access_tryagain));
			}

			throw serverException;
		} catch (WifiMobileDataException wifiMobileDataException) {
			// Logger.e(MainActivity.getInstance(), "Exception",
			// " [HttpRequestHandler] [execute(String data)] [WifiMobileDataException]: "
			// + Calendar.getInstance().getTime() + ", "
			// + wifiMobileDataException.getMessage());
			// hitting api again in the case of next song error

			throw wifiMobileDataException;
		} catch (UnknownHostException exception) {
			// Logger.e(MainActivity.getInstance(), "Exception",
			// " [HttpRequestHandler] [execute(String data)] [UnknownHostException]: "
			// + Calendar.getInstance().getTime() + ", "
			// + exception.getMessage());
			// hitting api again in the case of next song error

			if (!Utils.isNetworkAvailable(context)) {

				throw new WifiMobileDataException(
						context.getString(R.string.no_internet_access_tryagain));
			}

			// occurs when there is internet connention but no data left
			throw new UnknownHostException(
					context.getString(R.string.unable_to_fetch_data));
		} catch (ConnectException exception) {
			// Logger.e(MainActivity.getInstance(), "Exception",
			// " [HttpRequestHandler] [convertToDate] [ConnectException]: "
			// + Calendar.getInstance().getTime() + ", "
			// + exception.getMessage());
			// hitting api again in the case of next song error

			// checking whether network available or not
			if (!Utils.isNetworkAvailable(context)) {

				throw new WifiMobileDataException(
						context.getString(R.string.no_internet_access_tryagain));
			}

			// need to again make the object to set string as
			// "connection timeout"
			// occurs when there is internet connention but no data left
			throw new UnknownHostException(
					context.getString(R.string.unable_to_fetch_data));

		} catch (SocketTimeoutException exception) {
			// Logger.e(MainActivity.getInstance(), "Exception",
			// " [HttpRequestHandler] [execute(String data)] [SocketTimeoutException]: "
			// + Calendar.getInstance().getTime() + ", "
			// + exception.getMessage());
			// hitting api again in the case of next song error

			// checking whether network available or not
			if (!Utils.isNetworkAvailable(context)) {

				throw new WifiMobileDataException(
						context.getString(R.string.no_internet_access_tryagain));
			}
			// need to again make the object to set string as
			// "connection timeout"
			exception = new SocketTimeoutException(
					context.getString(R.string.unable_to_fetch_data));

			throw exception;
		} catch (IOException ioe) {
			// Logger.e(
			// MainActivity.getInstance(),
			// "Exception",
			// " [HttpRequestHandler] [execute(String data)] [IOException]: "
			// + Calendar.getInstance().getTime() + ", "
			// + ioe.getMessage());

			// checking whether network available or not
			if (!Utils.isNetworkAvailable(context)) {

				throw new WifiMobileDataException(
						context.getString(R.string.no_internet_access_tryagain));
			}
			if (requestCount < MAX_REQUEST) {

				System.out.println(">>sid>>again requesting");
				requestCount++;
				closeConnection(connection, null, inputStream, baos);
				return execute(data);
			} else {
				requestCount = 0;
				if (cancelRequest) {
					throw new CancelException(
							context.getString(R.string.request_canelled));
				} else {
					throw ioe;
				}
			}

		} catch (Exception ex) {
			// Logger.e(
			// MainActivity.getInstance(),
			// "Exception",
			// " [HttpRequestHandler] [execute(String data)] [Exception]: "
			// + Calendar.getInstance().getTime() + ", "
			// + ex.getMessage());

			if (cancelRequest) {
				throw new CancelException(
						context.getString(R.string.request_canelled));
			} else {
				throw ex;
			}
		} finally {
			closeConnection(connection, null, inputStream, baos);
		}

	}

	/**
	 * Close All Open Connection
	 * 
	 * @param connection
	 *            HttpConnection Reference
	 * @param outputStream
	 *            OutputStream Reference
	 * @param inputStream
	 *            InputStream Reference
	 * @param baos
	 *            ByteArrayOutputStream Reference
	 */
	protected void closeConnection(HttpURLConnection connection,
			OutputStream outputStream, InputStream inputStream,
			ByteArrayOutputStream baos) {
		if (outputStream != null) {
			try {
				outputStream.close();
			} catch (IOException ioe) {
				// Logger.e(
				// MainActivity.getInstance(),
				// "Exception",
				// " [HttpRequestHandler] [CloseConnection1] : "
				// + Calendar.getInstance().getTime() + ", "
				// + ioe.getMessage());
			} // Ignored
		}

		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException ioe) {
				// Logger.e(
				// MainActivity.getInstance(),
				// "Exception",
				// " [HttpRequestHandler] [CloseConnection2] : "
				// + Calendar.getInstance().getTime() + ", "
				// + ioe.getMessage());
			} // Ignored
		}

		if (connection != null) {

			connection.disconnect();
			// Ignored
		}
		if (baos != null) {
			try {
				baos.close();
			} catch (IOException ioe) {
				// Logger.e(
				// MainActivity.getInstance(),
				// "Exception",
				// " [HttpRequestHandler] [CloseConnection3] : "
				// + Calendar.getInstance().getTime() + ", "
				// + ioe.getMessage());
			} // Ignored
		}

	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeConnection() {
		// TODO Auto-generated method stub
		if (connection != null) {

			connection.disconnect();
			// Ignored
		}
	}

}
