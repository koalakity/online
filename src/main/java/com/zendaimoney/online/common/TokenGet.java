package com.zendaimoney.online.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.zendaimoney.online.oii.id5.common.Des2;

public class TokenGet {
	public static String getToken() {
		Random rand = new Random();
		int i = (int) (Math.random() * 1000);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String tokenId = String.valueOf(i) + ";" + sdf.format(new Date());
		try {
			tokenId = Des2.encode("LIE33LEI343ZDIKFJ", tokenId.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tokenId;
	}
}
