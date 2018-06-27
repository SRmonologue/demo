package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.database.devkit.utils.BitWriter;

public class H09b0 extends HSend {


    long productId;
    int remoteParamLength;
    int keyParamLength;
    byte[] remoteParam, keyParam;

    public H09b0(long productId, byte[] remoteParam, byte[] keyParam) {
        messageCode = 0x09b0;
        this.productId = productId;
        this.remoteParam = remoteParam;
        this.keyParam = keyParam;
        this.remoteParamLength = remoteParam.length;
        this.keyParamLength = keyParam.length;
        // message flag & message header
        bufferSize += 5;
        // productId DWORD
        bufferSize += 4;
        //length WORD
        bufferSize += 2;
        //remoteParamLength
        bufferSize += remoteParamLength;
        // length WORD
        bufferSize += 2;
        // keyParamLength
        bufferSize += keyParamLength;

    }

    public byte[] getBytes() {
        byte[] data = new byte[bufferSize];

        BitWriter bitWriter = BitWriter.newInstance(data);
        bitWriter.setBYTE(messageFlag);
        bitWriter.setWORD(messageCode);
        bitWriter.setWORD(bufferSize - sizeBefore);
        bitWriter.setDWORD(this.productId);
        bitWriter.setWORD(this.remoteParamLength);
        bitWriter.setBYTEArray(this.remoteParam);
        bitWriter.setWORD(this.keyParamLength);
        bitWriter.setBYTEArray(this.keyParam);
        return data;
    }
}
