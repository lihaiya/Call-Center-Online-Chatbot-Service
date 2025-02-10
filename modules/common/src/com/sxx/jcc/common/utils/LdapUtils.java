package com.sxx.jcc.common.utils;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.apache.commons.lang3.StringUtils;

public class LdapUtils {
	public static boolean ldapVerify(String username, String password){
		if(StringUtils.isBlank(username)){
			return false;
		}
		
		if(username.indexOf("@corp.das-security.cn")<0){
			username = username+"@corp.das-security.cn";
		}
		return connect("10.20.120.146", "389",username,password);
	}
	
	private  static boolean connect(String host, String port, String username, String password) {
		DirContext ctx = null;
		boolean checkresult = false;
		Hashtable<String, String> HashEnv = new Hashtable<String, String>();
		HashEnv.put(Context.SECURITY_AUTHENTICATION, "simple"); // LDAP访问安全级别(none,simple,strong)
		HashEnv.put(Context.SECURITY_PRINCIPAL, username); // AD的用户名 管理员
		HashEnv.put(Context.SECURITY_CREDENTIALS, password); // AD的密码
		HashEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory"); // LDAP工厂类
		HashEnv.put("com.sun.jndi.ldap.connect.timeout", "3000");// 连接超时设置为3秒
		HashEnv.put(Context.PROVIDER_URL, "ldap://" + host + ":" + port);// 默认端口389
		try {
			ctx = new InitialDirContext(HashEnv);// 初始化上下文
			checkresult = true;
		} /**catch (AuthenticationException e) {
			System.out.println("身份验证失败!");
			e.printStackTrace();
		} catch (javax.naming.CommunicationException e) {
			System.out.println("AD域连接失败!");
			e.printStackTrace();
		}**/
		catch (Exception e) {
			checkresult = false;
		} finally {
			if (null != ctx) {
				try {
					ctx.close();
					ctx = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return checkresult;
	}
	
	public static void main(String[] args) {
		//LdapUtils.ldapVerify("richie.dai@corp.das-security.cn", "1qaz@WSX3edc$RFVv");
		//LdapUtils.connect("10.20.120.146", "389", "tel1@corp.das-security.cn", "#PK$*TiZ8V");
	}

}
