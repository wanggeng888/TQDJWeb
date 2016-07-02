package cn.w.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.sun.org.apache.xml.internal.security.utils.Base64;

public class Transfer {

	public static final Logger LOG = Logger.getLogger(Transfer.class);

	public static String urlDecode(String str) {
		String result = null;
		try {
			result = URLDecoder.decode(str, "utf-8");
			if (result.endsWith("=")) {
				result = result.substring(0, result.length() - 1);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static JSONObject dataToJSONObject(String data) {
		if (data != null) {
			try {
				String obj = new String(Base64.decode(Base64.decode(data
						.getBytes("utf-8"))));
				obj = URLDecoder.decode(obj, "utf-8");
				System.out.println("---->> obj: " + obj);
				return JSONObject.fromObject(obj);
			} catch (Exception e) {
				LOG.error("data to JSON Exception", e);
			}
		}
		return null;
	}

	public static JSONArray dataToJSONOArray(String data) {
		if (data != null) {
			try {
				String obj = new String(Base64.decode(Base64.decode(data
						.getBytes("utf-8"))));
				obj = URLDecoder.decode(obj, "utf-8");
				return JSONArray.fromObject(obj);
			} catch (Exception e) {
				LOG.error("data to JSON Exception", e);
			}
		}
		return null;
	}

	public static String getTimeString() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return format.format(new Date());
	}

	public static String inputStreamToString(InputStream inputStream) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputStream));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			LOG.error(e, e);
		}
		return sb.toString();
	}
}
