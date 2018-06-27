package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.database.devkit.utils.BitWriter;

public class H0915 extends HSend {


    int actionId;
    int actionType;
    int sceneId;

    public H0915(int actionId, int actionType, int sceneId) {
        messageCode = 0x0915;

        this.actionId = actionId;
        this.actionType = actionType;
        this.sceneId = sceneId;
        // message flag & message header
        bufferSize += 5;


        bufferSize += 4;
        bufferSize += 1;
        // senceId DWORD
        bufferSize += 4;

    }

    public byte[] getBytes() {
        byte[] data = new byte[bufferSize];

        BitWriter bitWriter = BitWriter.newInstance(data);

        bitWriter.setBYTE(messageFlag);
        bitWriter.setWORD(messageCode);
        bitWriter.setWORD(bufferSize - sizeBefore);

        bitWriter.setDWORD(this.actionId);
        bitWriter.setBYTE(this.actionType);
        bitWriter.setDWORD(this.sceneId);
        return data;
    }
}
