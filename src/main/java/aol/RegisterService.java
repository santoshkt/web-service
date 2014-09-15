package aol;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class RegisterService {

	private final static String pageURL = "http://myopenissues.com/magento/index.php/customer/account/create/";
	private final static String actionURL = "http://myopenissues.com/magento/index.php/customer/account/createpost/";
	private final static String createdURL = "http://myopenissues.com/magento/index.php/customer/account/index/";

	public static String processRegistration(HttpServletRequest request) {

		if (request == null) {
			return null;
		}

		BasicCookieStore cookieStore = new BasicCookieStore();
		HttpClient httpclient = HttpClients.custom()
				.setDefaultCookieStore(cookieStore).build();
		
		try {
			

			// To establish a session, get a cookie by sending a GET request.
			HttpResponse httpResponse = sendGETRequest(httpclient, pageURL);
			if (httpResponse != null) {
				consumeResponse(httpResponse);
			} else {
				// Session was not established.
				return null;
			}
			// Session established.

			// Send Register request using this client.
			httpResponse = sendRegisterRequest(httpclient, request, actionURL);
			if (httpResponse == null) {
				return null;
			} else {
				String location = getLocation(httpResponse);
				if (location == null) {
					return null;
				} else if (location.equals(createdURL)) {
					return "success";
				} else if (location.equals(pageURL)) {
					// Must be a 302. If so, send request to actionURL
					// again and it will generate the response. Get the error
					// message from the response.
					HttpResponse errorPageResponse = sendGETRequest(httpclient,
							location);
					
					String errorMsg = getErrorMessage(errorPageResponse);
					
					if (errorPageResponse != null) {
						consumeResponse(errorPageResponse);
					}
					
					return errorMsg;

				} else{
					// Unhandled page case. We do not know what to do!
					return null;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			// Release any resources. No need to close client.
		}

		return null;
	}

	private static HttpResponse sendRegisterRequest(HttpClient httpclient,
			HttpServletRequest request, String URL)
			throws ClientProtocolException, IOException {

		if (httpclient == null || request == null || URL == null) {
			return null;
		}

		// Even though there are some null values here, the final webservice
		// will take care of them and will return appropriate results to us.
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String email = request.getParameter("email");
		String pwd = request.getParameter("pwd");
		String cpwd = request.getParameter("cpwd");

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("firstname", firstname));
		nameValuePairs.add(new BasicNameValuePair("lastname", lastname));
		nameValuePairs.add(new BasicNameValuePair("email", email));
		nameValuePairs.add(new BasicNameValuePair("password", pwd));
		nameValuePairs.add(new BasicNameValuePair("confirmation", cpwd));

		HttpPost post = new HttpPost(URL);
		try {
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

		return httpclient.execute(post);
	}

	private static String getLocation(HttpResponse response) {
		if(response == null){
			return null;
		}
		System.out.println("Response Headers Start");
		for (Header header : response.getAllHeaders()) {
			System.out.println(header.toString());
			if (header.getName().equals("Location")) {
				System.out.println("Location found.");
				return (String) header.getValue();
			}
		}
		System.out.println("Response Headers End");
		return null;
	}

	public static String getSuccessMessage() {
		return "Thank you for submitting form. Your registration is successful. Verify your account <a href=\"http://myopenissues.com/magento/\">here</a>";
	}

	private static String getErrorMessage(HttpResponse errorPageResponse)
			throws HttpResponseException, IOException {
		if( errorPageResponse == null){
			return null;
		}
		System.out.println("Finding error message.");

		String errorMsg = null;
		String responseString = new BasicResponseHandler()
				.handleResponse(errorPageResponse);
		// System.out.println("Response: " + responseString);

		// We can do this by using a HTML Parser and get the element with this
		// error-msg class. Lets not complicate as we do this only one in this
		// entire project

		String errorSearchMsg = "<ul class=\"messages\"><li class=\"error-msg\">";
		String errorEndMsg = "<form";

		if (responseString.contains(errorSearchMsg)) {
			System.out
					.println("Error message is present. Lets extract the message.");
			int errorSearchIndex = responseString.indexOf(errorSearchMsg);
			int errorIndex = errorSearchIndex + errorSearchMsg.length();
			int errorEndIndex = responseString.indexOf(errorEndMsg, errorIndex);
			errorMsg = responseString.substring(errorIndex, errorEndIndex);
			System.out.println("Error Message: " + errorMsg);
		} else {
			System.out.println("Error occured, but no message found.");
		}

		return errorMsg;
	}

	private static HttpResponse sendGETRequest(HttpClient httpclient, String URL) {
		
		if(httpclient == null || URL == null){
			return null;
		}
		
		HttpGet httpget = new HttpGet(URL);
		try {
			return httpclient.execute(httpget);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static void consumeResponse(HttpResponse response) {

		if (response == null) {
			return;
		}

		HttpEntity entity = response.getEntity();

		if (entity != null) {
			try {
				EntityUtils.consume(entity);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return;
	}
}
