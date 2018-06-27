package com.ohosure.smart.database.devkit.utils;

import com.ohosure.smart.database.devkit.constant.Const;
import com.ohosure.smart.database.devkit.constant.NoConst;
import com.ohosure.smart.database.devkit.log.MLog;

import java.io.UnsupportedEncodingException;




/**
 * Utility for bit operations (read only).
 * In this class, there is a byte array passed in when constructing and an index to the current index of the array. <br/>
 * You could set the the index as {@code fromIndex} in constructor. <br/>
 * After constructed, the index will be shifted following the byte array. <br/>
 * For example: if you invoke {@code getDWORD()}, the method return a 4-byte digit to you as a long type.
 * And the inner index will be shifted for 4 and you could continue to invoke other getting method 
 * to get the other bytes in other position of the byte array.
 * 
 * @author daxingj
 */
public final class BitReader
{
	/**
	 * The byte array which acts as the bit-shift destination. 
	 */
	private final byte[] originalBytes;
	
	/**
	 * The index of current available byte array {@code originalBytes}. It will be shifted automatically.
	 */
	private int currIndex;
	
	/**
	 * The flag indicates if it is bigEndian mode.
	 * True is big endian; false is little endian.
	 */
	private boolean bigEndian;

	/**
	 * Bit Reader. This equals to invoke <code>BitOp(originalBytes, 0)</code>.
	 * @param bigEndian - True is big endian; false is little endian.
	 * @param originalBytes - the original byte array
	 */
	private BitReader(boolean bigEndian, byte[] originalBytes)
	{
		this(bigEndian, originalBytes, 0);
	}

	/**
	 * Bit Operator.
	 * @param bigEndian - True is big endian; false is little endian.
	 * @param originalBytes - the original byte array.
	 * @param fromIndex - set the index of the (included) beginning of the byte array <b>originalBytes</b>.
	 */
	private BitReader(boolean bigEndian, byte[] originalBytes, int fromIndex)
	{
		this.bigEndian = bigEndian;
		this.originalBytes = originalBytes;
		this.currIndex = fromIndex;
	}

	/**
	 * Make a new instance of BitOp associated with {@link #originalBytes}. You will get null if any parameter is illegal.
	 * This takes big endian as defaults.
	 * @param originalBytes - Original byte array which will be operated.
	 * @return The instance you want. If any parameter is illegal, null instance will be got.
	 */
	public static BitReader newInstance(byte[] originalBytes)
	{
		return newInstance(true, originalBytes);
	}

	/**
	 * Make a new instance of BitOp associated with {@link #originalBytes}. You will get null if any parameter is illegal.
	 * @param bigEndian - True is big endian; false is little endian.
	 * @param originalBytes - Original byte array which will be operated.
	 * @return The instance you want. If any parameter is illegal, null instance will be got.
	 */
	public static BitReader newInstance(boolean bigEndian, byte[] originalBytes)
	{
		if (null == originalBytes || originalBytes.length == 0)
		{
			return null;
		}
		
		return new BitReader(bigEndian, originalBytes);
	}

	/**
	 * Make a new instance of BitOp associated with {@link #originalBytes}. You will get null if any parameter is illegal.
	 * @param bigEndian - True is big endian; false is little endian.
	 * @param originalBytes - Original byte array which will be operated.
	 * @param fromIndex - set the index of the (included) beginning of the byte array <b>originalBytes</b>.
	 * @return The instance you want. If any parameter is illegal, null instance will be got.
	 */
	public static BitReader newInstance(boolean bigEndian, byte[] originalBytes, int fromIndex)
	{
		if (null == originalBytes || originalBytes.length == 0)
		{
			return null;
		}
		
		if (fromIndex < 0 || fromIndex > originalBytes.length)
		{
			return null;
		}
		
		return new BitReader(bigEndian, originalBytes, fromIndex);
	}
	
	/**
	 * If you want to skip some index, you could use this method.
	 */
	public void setCurrentIndex(int currentIndex)
	{
		this.currIndex = currentIndex;
	}
	
	/**
	 * Retrieve a DWORD type from a byte array. And the index increments 4 automatically.
	 * @return long value
	 */
	public final long getDWORD()
	{
		if (currIndex + 4 > originalBytes.length)
		{
			MLog.w(BitReader.class.getSimpleName(), "len(4) is greater than the remaining length from currIndex(" + currIndex + ") in getDWORD()");
			return 0;
		}
		long x;
		if (this.bigEndian)
		{
			x = (originalBytes[currIndex] << 24 & NoConst.L32) + (originalBytes[currIndex + 1] << 16 & NoConst.I24) + (originalBytes[currIndex + 2] << 8 & NoConst.I16) + (originalBytes[currIndex + 3] & NoConst.I8);
		}
		else
		{
			x = (originalBytes[currIndex + 3] << 24 & NoConst.L32) + (originalBytes[currIndex + 2] << 16 & NoConst.I24) + (originalBytes[currIndex + 1] << 8 & NoConst.I16) + (originalBytes[currIndex] & NoConst.I8);
		}
		this.currIndex += 4;
		return x;
	}

	/**
	 * Retrieve a DWORD array from a byte array. And the index increments <b>4 * len</b> automatically if parameter is valid.
	 * @param len - length of destination array. This must be positive.
	 * @return long value
	 */
	public final long[] getDWORDArray(int len)
	{
		if (len <= 0 || originalBytes.length < this.currIndex + len * 4)
		{
			return null;
		}
		
		final long[] arr = new long[len];
		for (int i = 0; i < len; ++i)
		{
			arr[i] = getDWORD();
		}
		return arr;
	}
	
	/**
	 * Retrieve a WORD type from a byte array. And the index increments 2 automatically.
	 * @return int value
	 */
	public final int getWORD()
	{
		if (currIndex + 2 > originalBytes.length)
		{
			MLog.w(BitReader.class.getSimpleName(), "len(2) is greater than the remaining length from currIndex(" + currIndex + ") in getWORD()");
			return 0;
		}
		int x;
		if (this.bigEndian)
		{
			x = (originalBytes[currIndex] << 8 & NoConst.I16) + (originalBytes[currIndex + 1] & NoConst.I8);
		}
		else
		{
			x = (originalBytes[currIndex + 1] << 8 & NoConst.I16) + (originalBytes[currIndex] & NoConst.I8);
		}
		this.currIndex += 2;
		return x;
	}
	
	/**
	 * Retrieve a WORD array from a byte array. And the index increments <b>2 * len</b> automatically if parameter is valid.
	 * @param len - length of destination array. This must be positive. If the <em>len</em> is greater than the leftover length of the array {@link #originalBytes}, return the 
	 * @return integer array value
	 */
	public final int[] getWORDArray(int len)
	{
		if (len <= 0 || originalBytes.length < this.currIndex + len * 2)
		{
			MLog.d(BitReader.class.getSimpleName(), "getWORDArray() return null: len=" + len + ", originalBytes.length=" + originalBytes.length + ", currIndex=" + currIndex);
			return null;
		}
		
		int leftoverLen = originalBytes.length - currIndex;
		if (leftoverLen < len)
		{
			len = leftoverLen;
		}
		
		final int[] arr = new int[len];
		for (int i = 0; i < len; ++i)
		{
			arr[i] = getWORD();
		}
		return arr;
	}
	
	/**
	 * Retrieve a BYTE type from a byte array. And the index increments 1 automatically.
	 * @return int value
	 */
	public final int getBYTE()
	{
		if (currIndex + 1 > originalBytes.length)
		{
			MLog.w(BitReader.class.getSimpleName(), "len(1) is greater than the remaining length from currIndex(" + currIndex + ") in getBYTE()");
			return 0;
		}
		return originalBytes[this.currIndex++] & NoConst.I8;
	}

	/**
	 * Retrieve a BYTE array from a byte array. And the index increments <b>len</b> automatically if parameter is valid.
	 * @param len - length of destination array. This must be positive.
	 * @return integer array value
	 */
	public final int[] getBYTEArray(int len)
	{
		if (len <= 0 || originalBytes.length < currIndex + len)
		{
			return null;
		}
		
		final int[] arr = new int[len];
		for (int i = 0; i < len; ++i, ++this.currIndex)
		{
			arr[i] = (originalBytes[currIndex + 1] & NoConst.I8);
		}
		return arr;
	}

	/**
	 * Convert BCD bytes to String. 
	 * @return String BCD format code.
	 */
	public final String getBytesBCD(int len)
	{
		if (currIndex + len > originalBytes.length)
		{
			throw new IllegalArgumentException("parameter len(" + len + ") is too long, max is " + (originalBytes.length - currIndex));
		}
		String s = DigitUtil.getBCD(originalBytes, currIndex, len);
		this.currIndex += len;
		return s;
	}
	
	/**
	 * Get String type by the specified <b>len</b>
	 * @param len - length of String.
	 * @return String type object.
	 */
	public final String getString(int len, String charsetName)
	{
		if (currIndex + len > originalBytes.length)
		{
			len = originalBytes.length - currIndex;
			MLog.w(BitReader.class.getSimpleName(), "len(" + len + ") is greater than the remaining length from currIndex(" + currIndex + ") in getString()");
		}
		if (len == 0)
		{
			return Const.EMPTY;
		}
		byte[] temp = new byte[len];
		for (int i = 0; i < len; ++i, ++this.currIndex)
		{
			temp[i] = this.originalBytes[currIndex];
		}
		if (null == charsetName)
		{
			return new String(temp);
		}
		
		String result;
		try
		{
			result = new String(temp, charsetName);
		}
		catch (UnsupportedEncodingException e)
		{
			return Const.ERR;
		}
		return result;
	}

	/**
	 * Get String type by the specified <b>len</b>
	 * @param len - length of String.
	 * @return String type object.
	 */
	public final String getString(int len)
	{
		return getString(len, null);
	}
	
	/**
	 * Get current available index.
	 * This is the same as the length of used bytes.
	 * @return the current index (it also means the length of used bytes).
	 */
	public final int getCurrentIndex()
	{
		return this.currIndex;
	}
	
	/**
	 * Get a sub byte array object from originalBytes. 
	 * This equals <code>getByteArray(originalBytes.length - ix)</code> which means retrieve sub array till the end.
	 * @return byte[] object. If invalid, return null.
	 */
	public final byte[] getByteArray()
	{
		return getByteArray(originalBytes.length - currIndex);
	}

	/**
	 * Get a sub byte array object from originalBytes.
	 * @param len - specified length of the sub array to be returned.
	 * @return byte[] object. If invalid, return null.
	 */
	public final byte[] getByteArray(final int len)
	{
		if (len > originalBytes.length - this.currIndex)
		{
			return null;
		}
		byte[] result = new byte[len];
		System.arraycopy(originalBytes, currIndex, result, 0, len);
		return result;
	}
}
