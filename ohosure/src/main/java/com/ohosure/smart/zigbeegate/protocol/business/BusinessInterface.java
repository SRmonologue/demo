package com.ohosure.smart.zigbeegate.protocol.business;


import com.ohosure.smart.zigbeegate.protocol.HReceive;
import com.ohosure.smart.zigbeegate.protocol.model.HDevice;

import org.json.JSONObject;

public interface BusinessInterface {
	/**
	 * 一般控制响应
	 * @param receive
	 */

	void on0100Response(HReceive receive);

	/**
	 * 设备状态反馈
	 * @param receive
	 */

	void on0101Response(HReceive receive);
	/**
	 * 区域增加响应
	 * @param receive
	 */
	
	void on0201Response(HReceive receive);

	/**
	 * 场景增加响应
	 * @param receive
	 */
	void on0202Response(HReceive receive);

	/**
	 * 家电增加响应
	 * @param receive
	 */
	void on0203Response(HReceive receive);
	/**
	 * 定时任务增加响应
	 * @param receive
	 */
	void on0204Response(HReceive receive);
	
	/**
	 * 定时任务使能响应
	 * @param receive
	 */
	void on0205Response(HReceive receive);

	/**
	 * 红外受控设备定义响应
	 * @param receive
     */
	void on0206Response(HReceive receive);


	/**
	 * 配置485网关响应
	 * @param receive
	 */
	void on0207Response(HReceive receive);

	/**
	 * 配置485网关下挂载设备响应
	 * @param receive
	 */
	void on0208Response(HReceive receive);

	/**
	 * 产品类型修改响应
	 * @param receive
     */
	void on0209Response(HReceive receive);
	/**
	 * 联动基本配置响应
	 * @param receive
	 */
	void on020aResponse(HReceive receive);
	/**
	 * 门锁用户命名
	 * @param receive
	 */
	void on020bResponse(HReceive receive);
	/**
	 * 区域删除响应
	 * @param receive
	 */
	void on0241Response(HReceive receive);
	
	/**
	 * 场景删除
	 * @param receive
	 */
	void on0242Response(HReceive receive);
	
	/**
	 * 家电删除
	 * @param receive
	 */
	void on0243Response(HReceive receive);
	
	/**
	 * 定时任务删除
	 * @param receive
	 */
	void on0244Response(HReceive receive);
	/**
	 * 设备踢网
	 * @param receive
	 */
	void on0245Response(HReceive receive);
	/**
	 * 联动任务删除
	 * @param receive
	 */
	void on0246Response(HReceive receive);
	/**
	 * 单通道控制反馈
	 * @param receive
	 */
	void on0261Response(HReceive receive);
	/**
	 * 场景控制反馈
	 * @param receive
	 */
	void on0262Response(HReceive receive);
	/**
	 * 多通道状态查询
	 * @param receive
	 */
	void on0264Response(HReceive receive);
	/**
	 * 产品状态查询反馈
	 * @param receive
	 */
	void on0260Response(HReceive receive);
	/**
	 * 产品名称/区域配置
	 * @param receive
	 */
	void on0211Response(HReceive receive);
	/**
	 * channel与家电设备关联
	 * @param receive
	 */
	void on0212Response(HReceive receive);
	/**
	 * key和单个通道关联
	 * @param receive
	 */
	void on0213Response(HReceive receive);
	/**
	 * key和场景关联
	 * @param receive
	 */
	void on0214Response(HReceive receive);

	/**
	 * 按键、定时、联动 关联场景应答
	 * @param receive
     */
	void on0215Response(HReceive receive);

	/**
	 * 擦除按键应答
	 * @param receive
     */
	void on0216Response(HReceive receive);

	/**
	 * 联动使能反馈
	 * @param receive
     */
	void on0217Response(HReceive receive);
	/**
	 * 场景通道配置应答
	 * @param receive
	 */
	void on0221Response(HReceive receive);

	/**
	 * 通用行为配置应答
	 * @param receive
     */
	void on0222Response(HReceive receive);

	/**
	 * 联动输入配置应答
	 * @param receive
     */
	void on0223Response(HReceive receive);
	/**
	 * 网关功能反馈
	 * @param receive
	 */
	void on0280Response(HReceive receive);

    /**
     * 可视对讲场景与网关场景匹配反馈
     *
     * @param receive
     */
    void on0281Response(HReceive receive);

    /**
     * 删除可视对讲场景与网关场景匹配反馈
     *
     * @param receive
     */
	void on0282Response(HReceive receive);
	/**
	 * 校准网关时间
	 *
	 * @param receive
	 */
	void on0283Response(HReceive receive);

	/**
	 * 获取网关时间
	 *
	 * @param receive
	 */
	void on0284Response(HReceive receive);

	/**
	 * 网关在线升级反馈
	 *
	 * @param receive
	 */
	void on0285Response(HReceive receive);
    /**
     * 保存萤石账号反馈
     *
     * @param receive
     */
    void on02a1Response(HReceive receive);

    /**
     * 获取萤石账号反馈
     *
     * @param receive
     */
    void on02a2Response(HReceive receive);

    /**
     * 清空萤石账号反馈
     *
     * @param receive
     */
    void on02a3Response(HReceive receive);

    /**
     * 红外按键测试（单键控制）
     *
     * @param receive
     */
    void on02b0Response(HReceive receive);

    /**
     * 获取已匹配红外遥控器反馈
     *
     * @param receive
     */
    void on02b1Response(HReceive receive);

    void onResponseAuth(int rescode);

    void onResponseCancel();

    void onResponseUpdate(int rescode);

    void onResponseConfig(int rescode);

	/**
	 * 返回设置配置信息
	 * @param res
	 */
	void onResponseConfigJson(String res);

    /**
     * 部分简单数据类型设备当前实时值
     *
     * @param rescode
     */
    void on0265Response(int rescode);

    /**
     * 设备被操作值发生变化0101
     */
    void onRequestDeviceStautsChange();

    /**
     * 产品上线离线变化0230
     */
    void onRequestDeviceOnlineChange();

	void onRequestDeviceList(JSONObject json);

	void onRequestAddDevice(JSONObject json);

	void onRequestDelDevice(JSONObject json);

    void onRequestUserList(JSONObject json);

    void onRequestAddUser(JSONObject json);

    void onRequestDelUser(JSONObject json);

    void onRequestVerifyUser(JSONObject json);

    void onRequestUnbindUser(JSONObject json);

    void onRequestPutAdmin(JSONObject json);

    void onRequestRenameAlias(JSONObject json);

	void onRequestSendSmsCode(JSONObject json);


	/**
	 * 返回最新的网关版本信息
	 */
    void onRequestUpdateDescription(JSONObject json);


	void onRequestValueChange();
	void onRequestEnergy(JSONObject json);
	void onRequestAlarm(JSONObject json);
	void onRequestPushIRData(JSONObject json);
	void onRequestTable(String res);
	/**
	 * 家庭网关远程控制异常情况
	 */
	void onRemoteIssure(int rescode);
	/**
	 * 辅助定位
	 */
	void onRequestLocation(HDevice hDevice);

    /**
     * 通用数据返回controls
     *
     * @param data
     */
    void onRequestTableControls(String data);

    /**
     * 通用数据返回alarms
     *
     * @param data
     */
    void onRequestTableAlarms(String data);
}
