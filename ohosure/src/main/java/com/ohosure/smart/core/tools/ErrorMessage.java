package com.ohosure.smart.core.tools;


import com.ohosure.smart.core.Const;
import com.ohosure.smart.zigbeegate.protocol.RequestAuth;
import com.ohosure.smart.zigbeegate.protocol.RequestBind;
import com.ohosure.smart.zigbeegate.protocol.RequestCancel;
import com.ohosure.smart.zigbeegate.protocol.RequestConfig;
import com.ohosure.smart.zigbeegate.protocol.RequestEnergy;
import com.ohosure.smart.zigbeegate.protocol.RequestHeartbeat;
import com.ohosure.smart.zigbeegate.protocol.RequestPassword;
import com.ohosure.smart.zigbeegate.protocol.RequestRegister;
import com.ohosure.smart.zigbeegate.protocol.RequestSend;

public class ErrorMessage {

    public static String getErrorTip(String requestType, int errorCode) {
        String resChar = "";

        if (requestType.equalsIgnoreCase(RequestAuth.class.getSimpleName())) {

            switch (errorCode) {
                case 101:
                    resChar = "用户名不存在";
                    break;
                case 102:
                    resChar = "密码错误";
                    break;
                case 103:
                    resChar = "未绑定网关";
                    break;
                case 104:
                    resChar = "服务器连接失败";
                    break;
                case 105:
                    resChar = "保持登录失败，请重新登录";
                    break;
                default:
                    resChar = "未知错误";
                    break;
            }
        } else if (requestType.equalsIgnoreCase(RequestBind.class.getSimpleName())) {

            switch (errorCode) {
                case 10:
                    resChar = "网关读写失败";
                    break;
                case 201:
                    resChar = "不支持的设备类型";
                    break;

                default:
                    resChar = "未知错误";
                    break;
            }
        } else if (requestType.equalsIgnoreCase(RequestCancel.class.getSimpleName())) {

            switch (errorCode) {
                case 401:
                    resChar = "session不存在";
                    break;
                case 402:
                    resChar = "session不正确";
                    break;
                default:
                    resChar = "未知错误";
                    break;
            }
        } else if (requestType.equalsIgnoreCase(RequestConfig.class.getSimpleName())) {
            switch (errorCode) {
                case 301:
                    resChar = "消息解析失败";
                    break;
                case 302:
                    resChar = "数据错误";
                    break;
                case 303:
                    resChar = "家庭网关不在线";
                    break;
                case 404:
                    resChar = "数据同步失败，家庭智能主机与手机应用版本不同步，请下载最新应用";
                    break;
            }

        } else if (requestType.equalsIgnoreCase(RequestEnergy.class.getSimpleName())) {

        } else if (requestType
                .equalsIgnoreCase(RequestHeartbeat.class.getSimpleName())) {

            switch (errorCode) {
                case 501:
                    resChar = "会话未认证" + RequestHeartbeat.class.getSimpleName();
                    break;
                case 502:
                    resChar = "会话超时";
                    break;
                case 503:
                    resChar = "会话不存在";
                    break;

                default:
                    break;
            }
        } else if (requestType.equalsIgnoreCase(RequestRegister.class.getSimpleName())) {

            switch (errorCode) {
                case 601:
                    resChar = "用户名不合法";
                    break;
                case 602:
                    resChar = "用户名已存在";
                    break;
                case 603:
                    resChar = "密码不合法";
                    break;
                default:
                    resChar = "Register未知错误:" + errorCode;
                    break;
            }
        } else if (requestType.equalsIgnoreCase(RequestSend.class.getSimpleName())) {

            switch (Const.ResultCode.getEnumResultCode(errorCode)) {

                case RESULT_CODE_CHANNEL_INVALID:
                    resChar = "通道号不正确";
                    break;
                case RESULT_CODE_CTRLDEV_INVALID:
                    resChar = "控制类型不正确";
                    break;
                case RESULT_CODE_FAILED:
                    resChar = "错误";
                    break;
                case RESULT_CODE_KEY_INVALID:
                    resChar = "按键号不正确";
                    break;
                case RESULT_CODE_NAME_EXIST:
                    resChar = "该区域下已有同名设备存在";
                    break;
                case RESULT_CODE_PRODUCT_INVALID:
                    resChar = "产品ID不正确";
                    break;
                case RESULT_CODE_ROOM_INVALID:
                    resChar = "区域不正确或已被删除";
                    break;
                case RESULT_CODE_SCENE_INVALID:
                    resChar = "场景号不正确";
                    break;
                case RESULT_CODE_ZIGBEE_TIMEOUT:
                    resChar = "配置超时";
                    break;
                case RESULT_CODE_EXCEED_MAXSCENE:
                    resChar = "超过最大支持场景数";
                    break;
                default:
                    resChar = "Send未知错误:" + errorCode;
                    break;
            }
        } else if (requestType.equalsIgnoreCase(RequestSend.class.getSimpleName()
                + "prev")) {
            switch (errorCode) {
                case 301:
                    resChar = "消息解析失败";
                    break;
                case 302:
                    resChar = "数据错误";
                    break;
                case 303:
                    resChar = "家庭网关不在线";
                    break;
                case 444:
                    resChar = "保存红外数据异常，请检查智能主机版本";
                    break;
                case 501:
                    resChar = "会话未认证";
                    break;
                case 502:
                    resChar = "会话超时";
                    break;
                case 503:
                    resChar = "会话不存在";
                    break;
                default:
                    resChar = "PrevSend未知错误:" + errorCode;
                    break;
            }

        } else if (requestType.equalsIgnoreCase(RequestPassword.class.getSimpleName())) {

            switch (errorCode) {
                case 604:
                    resChar = "用户未注册";
                    break;
                default:
                    resChar = "未知错误";
                    break;
            }
        }

        return resChar;
    }
}
