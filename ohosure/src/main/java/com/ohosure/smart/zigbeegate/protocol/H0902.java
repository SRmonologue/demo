package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.core.Const;
import com.ohosure.smart.database.devkit.utils.BitWriter;

import java.io.UnsupportedEncodingException;



public class H0902 extends HSend {

    int nameLength;
    String name;
    int descriptionLength;
    String description;
    long roomAreaId;
    long sceneId;
    int sceneType;
    int overviewId;

    public H0902(long sceneId, String name, String description, long areaId, int sceneType, int overviewId)
            throws UnsupportedEncodingException {
        messageCode = 0x0902;
        this.sceneId = sceneId;
        this.name = name;
        this.description = description;
        this.roomAreaId = areaId;
        this.sceneType = sceneType;
        this.overviewId = overviewId;

        // message flag & message header
        bufferSize += 5;
        // sceneId
        bufferSize += 4;
        //sceneType
        bufferSize += 2;
        // area_id
        bufferSize += 4;
        // overview_id
        bufferSize += 4;
        // length WORD
        bufferSize += 2;
        // name
        nameLength = name.getBytes(Const.CHARSET).length;
        bufferSize += nameLength;
        // length WORD
        bufferSize += 2;
        // description
        descriptionLength = description.getBytes(Const.CHARSET).length;
        bufferSize += descriptionLength;

    }

    public byte[] getBytes() throws UnsupportedEncodingException {
        byte[] data = new byte[bufferSize];

        BitWriter bitWriter = BitWriter.newInstance(data);

        bitWriter.setBYTE(messageFlag);
        bitWriter.setWORD(messageCode);
        bitWriter.setWORD(bufferSize - sizeBefore);
        bitWriter.setDWORD(this.sceneId);
        bitWriter.setWORD(this.sceneType);
        bitWriter.setDWORD(this.roomAreaId);
        bitWriter.setDWORD(this.overviewId);
        bitWriter.setWORD(this.nameLength);
        bitWriter.setBYTEArray(this.name.getBytes(Const.CHARSET));
        bitWriter.setWORD(this.descriptionLength);
        bitWriter.setBYTEArray(this.description.getBytes(Const.CHARSET));

        return data;
    }

}
