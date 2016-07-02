package cn.w.utils;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import cn.w.i.IService;

public class Common {

	private static final Logger LOG = Logger.getLogger(Common.class);
	public static final String MD5 = "MD5";
	public static char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static int tokenToID(String username, String token) {
		int id = -1;
		return id;
	}

	public static String errorResult(int messageCode) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put(IService.CODE, IService.ERROR);
		jsonObj.put(IService.MESSAGE, messageCode);
		return jsonObj.toString();
	}

	public static String md5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance(MD5);
			md.update(str.getBytes("utf-8"));

			byte[] mdbyte = md.digest();
			StringBuilder sb = new StringBuilder(mdbyte.length * 2);
			for (int i = 0; i < mdbyte.length; i++) {
				sb.append(hexChar[(mdbyte[i] & 0xf0) >>> 4]);
				sb.append(hexChar[mdbyte[i] & 0x0f]);
			}
			str = sb.toString();
		} catch (Exception e) {
			LOG.error("Md5 Exception", e);
		}
		return str;
	}

	public static String token() {
		String retOrdNo = "";
		long time = new Date().getTime();
		int num = (int) (time % 10000000);
		char baseChar = 'A';
		char basechar = 'a';
		for (int i = 0; i < 3; i++) {
			Random random = new Random();
			int rint = random.nextInt(26);
			if (i < 2) {
				retOrdNo += (char) (baseChar + rint);
			} else {
				retOrdNo += Integer.toString(num) + (char) (basechar + rint);
			}
		}
		return retOrdNo;
	}

	public static String formatDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return format.format(date);
	}

	public static void filterDate(Map<String, String> map) {
		String time = map.get(IService.TIME);
		time = time.substring(0, time.length() - 2);
		map.put(IService.TIME, time);
	}

}
