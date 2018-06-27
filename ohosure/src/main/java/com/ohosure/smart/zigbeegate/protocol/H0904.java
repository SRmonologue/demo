package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.core.Const;
import com.ohosure.smart.database.devkit.utils.BitWriter;

import java.io.UnsupportedEncodingException;

public class H0904 extends HSend {

    int taskId;
    int repeatTime;
    int executeTime;
    int enableStatus;
    int lengthOMark;
    String mark;


    public H0904(int taskId, int repeatTime,
                 int executeTime, int enableStatus, String mark) throws UnsupportedEncodingException {
        messageCode = 0x0904;
        this.taskId = taskId;
        this.repeatTime = repeatTime;
        this.executeTime = executeTime;
        this.enableStatus = enableStatus;
        this.lengthOMark = mark.getBytes(Const.CHARSET).length;
        this.mark = mark;
        // message flag & message header
        bufferSize += 5;
        // taskId
        bufferSize += 4;
        // repeatTime
        bufferSize += 1;
        // executeTime
        bufferSize += 2;
        // enableStatus
        bufferSize += 1;
        // length of mark
        bufferSize += 2;
        bufferSize += lengthOMark;

    }

    public byte[] getBytes() throws UnsupportedEncodingException {
        byte[] data = new byte[bufferSize];
        BitWriter bitWriter = BitWriter.newInstance(data);
        bitWriter.setBYTE(messageFlag);
        bitWriter.setWORD(messageCode);
        bitWriter.setWORD(bufferSize - sizeBefore);
        bitWriter.setDWORD(this.taskId);
        bitWriter.setBYTE(this.repeatTime);
        bitWriter.setWORD(this.executeTime);
        bitWriter.setBYTE(this.enableStatus);
        bitWriter.setWORD(this.lengthOMark);
        bitWriter.setBYTEArray(this.mark.getBytes(Const.CHARSET));
        return data;
    }
}
