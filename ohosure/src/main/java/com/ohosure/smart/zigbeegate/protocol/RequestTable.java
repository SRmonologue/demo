package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.core.Const;
import com.ohosure.smart.core.communication.HttpOutMessage;
import com.ohosure.smart.database.devkit.log.MLog;
import com.ohosure.smart.zigbeegate.protocol.model.HAction;
import com.ohosure.smart.zigbeegate.protocol.model.HDevice;

import org.json.JSONObject;



public class RequestTable extends HttpOutMessage {

    //获取设备属性配置项
    public static final String INFO_CONFIGS = "configs";
    //获取转换网关下的设备列表（如红外转发器、433转发器、315转发器、485转发器等等）
    public static final String INFO_CONTROLS = "controls";
    //获取安防报警记录
    public static final String INFO_ALARMS = "alarms";
    //获取门锁用户列表
    public static final String INFO_DOORUSERS = "doorUsers";
    //获取门锁操作记录
    public static final String INFO_DOORRECORDS = "doorRecords";
    //获取面板所有按键配置
    public static final String INFO_KEYALL = "keyAll";
    //获取所有场景配置
    public static final String INFO_SCENEALL = "sceneAll";
    //获取所有定时任务配置
    public static final String INFO_TASKALL = "taskAll";
    //获取今日定时任务配置
    public static final String INFO_TASK_TODAY = "taskToday";
    //获取所有联动配置
    public static final String INFO_LINKAGEALL = "linkageAll";
    //获取指定id按键动作行为
    public static final String INFO_KEYCONFIG = "keyConfig";
    //获取指定id场景动作行为
    public static final String INFO_SCENECONFIG = "sceneConfig";
    //获取指定id定时任务动作行为
    public static final String INFO_TASKCONFIG = "taskConfig";
    //获取指定id联动动作行为
    public static final String INFO_LINKAGECONFIG = "linkageConfig";
    //获取指定id场景动作的设备
    public static final String INFO_SCENEDEVICES = "sceneDevices";
    //获取指定id联动触发条件
    public static final String INFO_LINKAGEIN = "linkageIn";
    //获取RS485/IP转发器配置信息
    public static final String INFO_SERIALGATECONFIG = "serialGateConfig";
    //获取RS485对接设备配置信息
    public static final String INFO_SERIALPANELCONFIG = "serialPanelConfig";
    //获取RS485设备运转信息
    public static final String INFO_SERIALPANELINFO = "serialPanelInfo";
    /**
     * 以下保留
     **/
    public static final String INFO_ACTIONS = "actions";
    public static final String INFO_KEYFEATURES = "keyFeatures";
    public static final String INFO_SCENEFEATURES = "sceneFeatures";
    public static final String INFO_TASKFEATURES = "taskFeatures";
    public static final String INFO_LINKAGEFEATURES = "linkageFeatures";


    public static String httpBody(String session, String info) {
        return httpBody(session, info, null, null, 0, 0);
    }

    public static String httpBody(String session, String info, HDevice device) {
        return httpBody(session, info, device, null, 0, 0);
    }

    public static String httpBody(String session, String info, HAction action) {
        return httpBody(session, info, null, action, 0, 0);
    }

    public static String httpBody(String session, String info, HDevice device, int id, int size) {
        return httpBody(session, info, device, null, id, size);
    }

    public static String httpBody(String session, String info, HDevice device, HAction action, int id, int size) {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("sessionID", session);
            jsonObject.put("queryInfo", info);

            if (action != null) {
                JSONObject actionJson = new JSONObject();
                actionJson.put("id", String.valueOf(action.getId()));
                actionJson.put("type", String.valueOf(action.getType()));
                jsonObject.put("action", actionJson);

                JSONObject deviceJson = new JSONObject();
                deviceJson.put("id", "");
                deviceJson.put("type", "");
                deviceJson.put("original", "");
                jsonObject.put("device", deviceJson);
            }
            if (device != null) {
                JSONObject actionJson = new JSONObject();
                actionJson.put("id", "");
                actionJson.put("type", "");
                jsonObject.put("action", actionJson);

                JSONObject deviceJson = new JSONObject();
                deviceJson.put("id", String.valueOf(device.getId()));
                deviceJson.put("type", String.valueOf(device.getType()));
                deviceJson.put("original", String.valueOf(device.getOriginal()));
                jsonObject.put("device", deviceJson);
            }
            JSONObject page = new JSONObject();
            page.put("id", String.valueOf(id));
            page.put("size", String.valueOf(size));
            jsonObject.put("page", page);

            JSONObject systemJson = new JSONObject();
            systemJson.put("type", Const.HOST_IDENTITY);
            jsonObject.put("system", systemJson);
        } catch (Exception e) {
            MLog.e("RequestTable", "!!!-------failed------!!!");
            e.printStackTrace();
        }

        return jsonObject.toString();
    }

    public RequestTable(String value) {

        sb.append("POST /requestTable HTTP/1.1\r\n")
                .append("Accept: */*\r\n")
                .append("Connection: keep-alive\r\n")
                .append("Content-Length: " + value.length() + "\r\n")
                .append("\r\n").append(value);
    }

}
