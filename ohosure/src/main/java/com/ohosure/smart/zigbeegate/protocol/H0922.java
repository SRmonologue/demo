package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.database.devkit.utils.BitWriter;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class H0922 extends HSend {

    int actionId;
    int actionType;
    int actionNumber;

    ArrayList<HBehavior> mActionList;

    public H0922(int actionId, int actionType, ArrayList<HBehavior> list) {
        messageCode = 0x0922;
        this.actionId = actionId;
        this.actionType = actionType;
        this.actionNumber = list.size();
        this.mActionList = list;
        // message flag & message header
        bufferSize += 5;
        // actionId DWORD
        bufferSize += 4;
        //actionType
        bufferSize += 1;
        // actionNumber WORD
        bufferSize += 2;
        // action[N]
        for (HBehavior action : list) {
            bufferSize += 13 + action.getFeatureValue().length;
        }

    }

    public byte[] getBytes() throws UnsupportedEncodingException {
        byte[] data = new byte[bufferSize];

        BitWriter bitWriter = BitWriter.newInstance(data);

        bitWriter.setBYTE(messageFlag);
        bitWriter.setWORD(messageCode);
        bitWriter.setWORD(bufferSize - sizeBefore);
        bitWriter.setDWORD(this.actionId);
        bitWriter.setBYTE(this.actionType);
        bitWriter.setWORD(this.actionNumber);
        for (HBehavior action : this.mActionList) {
            bitWriter.setDWORD(action.getDeviceId());
            bitWriter.setWORD(action.getDeviceType());
            bitWriter.setBYTE(action.getDeviceOriginal());
            bitWriter.setWORD(action.getFeatureType());
            bitWriter.setWORD(action.getDelayTime());
            bitWriter.setWORD(action.getFeatureValue().length);
            bitWriter.setBYTEArray(action.getFeatureValue());
        }

        return data;
    }
}
