package com.ntu.cst;
/**
 * 
0  头像
F  指纹信息数据包
-----以上关联采集图像信息表-----
K  公民信息—关联公民基本信息表
L  证件信息—关联证件信息表
M  制证申请—关联制证申请表及子表
N  收寄信息—关联收寄信息表


 * @author hajime
 *
 */
public class datasync_type 
{
	public final static String basicinfo ="K";
	public final static String photoinfo ="0F";
	public final static String certification_request ="M";
	public final static String certification_info ="L";
	public final static String receiveSend_info ="N";

}
