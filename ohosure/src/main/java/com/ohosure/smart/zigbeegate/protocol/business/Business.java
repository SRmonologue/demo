package com.ohosure.smart.zigbeegate.protocol.business;


import com.ohosure.smart.zigbeegate.protocol.HReceive;
import com.ohosure.smart.zigbeegate.protocol.model.HDevice;

import org.json.JSONObject;


public class Business implements BusinessInterface {

    @Override
    public void on0100Response(HReceive receive) {

    }

    @Override
    public void on0101Response(HReceive receive) {

    }

    @Override
    public void on0201Response(HReceive receive) {
        // TODO Auto-generated method stub

    }

    @Override
    public void on0202Response(HReceive receive) {
        // TODO Auto-generated method stub

    }

    @Override
    public void on0203Response(HReceive receive) {
        // TODO Auto-generated method stub

    }

    @Override
    public void on0207Response(HReceive receive) {
        // TODO Auto-generated method stub

    }

    @Override
    public void on0208Response(HReceive receive) {
        // TODO Auto-generated method stub

    }

    @Override
    public void on0241Response(HReceive receive) {
        // TODO Auto-generated method stub

    }

    @Override
    public void on0242Response(HReceive receive) {
        // TODO Auto-generated method stub

    }

    @Override
    public void on0243Response(HReceive receive) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onResponseAuth(int rescode) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onResponseCancel() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onResponseUpdate(int updateMode) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onResponseConfig(int rescode) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onResponseConfigJson(String res) {

    }

    @Override
    public void on0265Response(int rescode) {

    }

    @Override
    public void onRequestDeviceStautsChange() {

    }

    @Override
    public void onRequestDeviceOnlineChange() {

    }

    @Override
    public void on0211Response(HReceive receive) {
        // TODO Auto-generated method stub

    }

    @Override
    public void on0261Response(HReceive receive) {
        // TODO Auto-generated method stub

    }

    @Override
    public void on0221Response(HReceive receive) {
        // TODO Auto-generated method stub

    }

    @Override
    public void on0222Response(HReceive receive) {

    }

    @Override
    public void on0223Response(HReceive receive) {

    }

    @Override
    public void on0262Response(HReceive receive) {
        // TODO Auto-generated method stub

    }

    @Override
    public void on0212Response(HReceive receive) {
        // TODO Auto-generated method stub

    }

    @Override
    public void on0213Response(HReceive receive) {
        // TODO Auto-generated method stub

    }

    @Override
    public void on0214Response(HReceive receive) {
        // TODO Auto-generated method stub

    }

    @Override
    public void on0215Response(HReceive receive) {

    }

    @Override
    public void on0216Response(HReceive receive) {

    }

    @Override
    public void on0217Response(HReceive receive) {

    }

    @Override
    public void on0280Response(HReceive receive) {
        // TODO Auto-generated method stub

    }

    @Override
    public void on0281Response(HReceive receive) {
        // TODO Auto-generated method stub

    }

    @Override
    public void on0282Response(HReceive receive) {
        // TODO Auto-generated method stub

    }
    @Override
    public void on0283Response(HReceive receive) {
        // TODO Auto-generated method stub

    }
    @Override
    public void on0284Response(HReceive receive) {
        // TODO Auto-generated method stub

    }
    @Override
    public void on0285Response(HReceive receive) {
        // TODO Auto-generated method stub

    }
    @Override
    public void on0204Response(HReceive receive) {
        // TODO Auto-generated method stub

    }

    @Override
    public void on0244Response(HReceive receive) {
        // TODO Auto-generated method stub

    }

    @Override
    public void on0205Response(HReceive receive) {
        // TODO Auto-generated method stub

    }

    @Override
    public void on0206Response(HReceive receive) {

    }

    @Override
    public void on0209Response(HReceive receive) {

    }

    @Override
    public void on020aResponse(HReceive receive) {

    }

    @Override
    public void on020bResponse(HReceive receive) {

    }

    @Override
    public void onRequestValueChange() {
        // TODO Auto-generated method stub

    }


    /**
     * 房间信息修改
     *
     * @param receive
     */
    @Override
    public void on0264Response(HReceive receive) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onRequestEnergy(JSONObject json) {
        // TODO Auto-generated method stub

    }

    /**
     * 网关下设备列表
     */
    @Override
    public void onRequestDeviceList(JSONObject json) {
        // TODO Auto-generated method stub

    }

    /**
     * 网关下设备添加
     */
    @Override
    public void onRequestAddDevice(JSONObject json) {
        // TODO Auto-generated method stub

    }

    /**
     * 网关下设备列表删除
     */
    @Override
    public void onRequestDelDevice(JSONObject json) {
        // TODO Auto-generated method stub

    }

    /**
     * 主机用户列表
     */
    @Override
    public void onRequestUserList(JSONObject json) {
        // TODO Auto-generated method stub

    }

    /**
     * 创建普通用户
     */
    @Override
    public void onRequestAddUser(JSONObject json) {
        // TODO Auto-generated method stub

    }

    /**
     * 删除用户
     */
    @Override
    public void onRequestDelUser(JSONObject json) {
        // TODO Auto-generated method stub

    }
    /**
     * 删除用户
     */
    @Override
    public void onRequestVerifyUser(JSONObject json) {
        // TODO Auto-generated method stub

    }
    /**
     * 解绑用户
     */
    @Override
    public void onRequestUnbindUser(JSONObject json) {
        // TODO Auto-generated method stub

    }

    /**
     * 发送验证码
     */
    @Override
    public void onRequestSendSmsCode(JSONObject json) {
        // TODO Auto-generated method stub

    }

    /**
     * 设置管理员
     */
    @Override
    public void onRequestPutAdmin(JSONObject json) {
        // TODO Auto-generated method stub

    }

    /**
     * 重命名网关
     */
    @Override
    public void onRequestRenameAlias(JSONObject json) {
        // TODO Auto-generated method stub

    }
    /**
     * 返回最新的网关版本信息
     */
    @Override
    public void onRequestUpdateDescription(JSONObject json) {
        // TODO Auto-generated method stub

    }
    @Override
    public void onRequestLocation(HDevice hDevice) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onRequestTableControls(String data) {

    }

    @Override
    public void onRequestTableAlarms(String data) {

    }

    @Override
    public void onRequestAlarm(JSONObject json) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onRequestPushIRData(JSONObject json) {

    }

    @Override
    public void onRequestTable(String res) {

    }

    /**
     * 设备离线提示
     *
     * @param receive
     */
    @Override
    public void on0260Response(HReceive receive) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onRemoteIssure(int rescode) {
        // TODO Auto-generated method stub

    }

    @Override
    public void on02a1Response(HReceive receive) {
        // TODO Auto-generated method stub

    }

    @Override
    public void on02a2Response(HReceive receive) {
        // TODO Auto-generated method stub

    }

    @Override
    public void on02a3Response(HReceive receive) {
        // TODO Auto-generated method stub

    }

    @Override
    public void on02b0Response(HReceive receive) {

    }

    @Override
    public void on02b1Response(HReceive receive) {

    }

    @Override
    public void on0245Response(HReceive receive) {
        // TODO Auto-generated method stub

    }

    @Override
    public void on0246Response(HReceive receive) {

    }


}
