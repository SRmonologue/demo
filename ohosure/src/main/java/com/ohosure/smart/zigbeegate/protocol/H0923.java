package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.database.devkit.utils.BitWriter;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class H0923 extends HSend {

    int linkageId;
    int actionNumber;
    ArrayList<HLinkageIn> mActionList;

    public H0923(int linkageId, ArrayList<HLinkageIn> list) {
        messageCode = 0x0923;
        this.linkageId = linkageId;
        this.actionNumber = list.size();
        this.mActionList = list;
        // message flag & message header
        bufferSize += 5;
        // linkageId DWORD
        bufferSize += 4;

        // actionNumber WORD
        bufferSize += 2;
        // action[N]
        for (HLinkageIn action : list) {
            bufferSize += 21+ action.getFeatureValue().length;
        }

    }

    public byte[] getBytes() throws UnsupportedEncodingException {
        byte[] data = new byte[bufferSize];

        BitWriter bitWriter = BitWriter.newInstance(data);

        bitWriter.setBYTE(messageFlag);
        bitWriter.setWORD(messageCode);
        bitWriter.setWORD(bufferSize - sizeBefore);
        bitWriter.setDWORD(this.linkageId);
        bitWriter.setWORD(this.actionNumber);
        for (HLinkageIn action : this.mActionList) {

            bitWriter.setDWORD(action.getDeviceId());
            bitWriter.setWORD(action.getDeviceType());
            bitWriter.setBYTE(action.getDeviceOriginal());
            bitWriter.setWORD(action.getFeatureType());
            bitWriter.setBYTE(action.getCondition1());
            bitWriter.setBYTE(action.getCondition2());
            bitWriter.setDWORD(action.getCondition3());
            bitWriter.setDWORD(action.getCondition4());
            bitWriter.setWORD(action.getFeatureValue().length);
            bitWriter.setBYTEArray(action.getFeatureValue());

        }

        return data;
    }
}
