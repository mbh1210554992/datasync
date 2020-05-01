package com.ntu.common.cst;

/**
 * 消息类型
 * @author Jimmy
 *
 */
public class msg_type 
{
	/**
	 * 公民基本信息 K
	 */
	public final static int basicinfo =1;
	
	/**
	 * 人像、指纹信息 0F
	 */
	public final static int photoinfo =2;
	
	/**
	 * 制证申请 M
	 */
	public final static int certification_request =3;
	
	/**
	 * 证件信息 F
	 */
	public final static int certification_info =5;
	
	/**
	 * 收寄信息 N
	 */
	public final static int receiveSend_info =4;
	
	/**
	 * 同步响应
	 */
	public final static int synchro_ack =11;
}
