package com.ohosure.smart.zigbeegate.protocol;

import android.content.ContentResolver;
import android.content.ContentValues;

import com.ohosure.smart.core.Const;
import com.ohosure.smart.database.HSmartProvider;
import com.ohosure.smart.database.devkit.utils.BitReader;


/**
 * 产品状态上报（离线、上线）
 *
 * @author daxing
 */
public class H0230 extends HReceive {

    BitReader bitReader;
    int deviceId;
    int deviceType;
    int deviceOriginal;
    int deviceState;

    public H0230(byte[] data) {
        bitReader = BitReader.newInstance(data);

    }

    public void parse() {
        deviceId = (int) bitReader.getDWORD();
        deviceType = bitReader.getWORD();
        deviceOriginal = bitReader.getBYTE();
        deviceState = bitReader.getBYTE();
    }


    public int getDeviceId() {
        return deviceId;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public int getDeviceOriginal() {
        return deviceOriginal;
    }

    public int getDeviceState() {
        return deviceState;
    }


    public void updateDatabase(ContentResolver cr) {
        ContentValues cv = new ContentValues();
        switch (deviceOriginal) {
            case Const.ORIGINAL_PRODUCT: {
                cv.put(HSmartProvider.MetaData.Product.PRODUCT_TYPE,
                        deviceType);
                cv.put(HSmartProvider.MetaData.Product.PRODUCT_STATE,
                        deviceState);
                cr.update(HSmartProvider.MetaData.Product.CONTENT_URI, cv,
                        HSmartProvider.MetaData.Product.PRODUCT_ID + "=?",
                        new String[]{String.valueOf(deviceId)});
            }
            break;

        }
    }
}
