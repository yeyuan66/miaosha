package com.imooc.miaosha.util;

import org.apache.commons.codec.digest.DigestUtils;

/*自定义密码工具类
* */
public class MD5Util {

	//md5是借用别人的。
	public static String md5(String src) {
		return DigestUtils.md5Hex(src);
	}


	//自定义盐
	private static final String salt = "1a2b3c4d";

	//将用户输入转化为数据库存储，两次md5
	public static String inputPassToFormPass(String inputPass) {
		String str = ""+salt.charAt(0)+salt.charAt(2) + inputPass +salt.charAt(5) + salt.charAt(4);
		System.out.println(str);
		return md5(str);
	}

	//第二个盐是从数据库中取
	public static String formPassToDBPass(String formPass, String salt) {
		String str = ""+salt.charAt(0)+salt.charAt(2) + formPass +salt.charAt(5) + salt.charAt(4);
		return md5(str);
	}
	
	public static String inputPassToDbPass(String inputPass, String saltDB) {
		String formPass = inputPassToFormPass(inputPass);
		String dbPass = formPassToDBPass(formPass, saltDB);
		return dbPass;
	}
	
	public static void main(String[] args) {
		System.out.println(inputPassToFormPass("123456"));//d3b1294a61a07da9b49b6e22b2cbd7f9
//		System.out.println(formPassToDBPass(inputPassToFormPass("123456"), "1a2b3c4d"));
//		System.out.println(inputPassToDbPass("123456", "1a2b3c4d"));//b7797cce01b4b131b433b6acf4add449
	}
	//d3b1294a61a07da9b49b6e22b2cbd7f9
	
}
