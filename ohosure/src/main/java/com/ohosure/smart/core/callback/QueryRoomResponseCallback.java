package com.ohosure.smart.core.callback;

import com.ohosure.smart.zigbeegate.protocol.model.DBRoomArea;

import java.util.List;

/**
 * 描述：
 * Created by 9527 on 2018/6/28.
 */
public interface QueryRoomResponseCallback {
    void getRoomListResponse(List<DBRoomArea> list);
}
