package com.ohosure.smart.database.devkit.utils;


/**
 * Utility for bit operations (write only).
 * In this class, there is a byte array passed in when constructing and an index to the current index of the array. <br/>
 * You could set the the index as {@code fromIndex} in constructor. <br/>
 * After constructed, the index will be shifted following the byte array. <br/>
 * For example: if you invoke {@code getDWORD()}, the method return a 4-byte digit to you as a long type.
 * And the inner index will be shifted for 4 and you could continue to invoke other getting method 
 * to get the other bytes in other position of the byte array.
 * 
 * @author daxingj
 */
public final class BitWriter
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
	private BitWriter(boolean bigEndian, byte[] originalBytes)
	{
		this(bigEndian, originalBytes, 0);
	}

	/**
	 * Bit Operator.
	 * @param bigEndian - True is big endian; false is little endian.
	 * @param originalBytes - the original byte array.
	 * @param fromIndex - set the index of the (included) beginning of the byte array <b>originalBytes</b>.
	 */
	private BitWriter(boolean bigEndian, byte[] originalBytes, int fromIndex)
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
	public static BitWriter newInstance(byte[] originalBytes)
	{
		return newInstance(true, originalBytes);
	}

	/**
	 * Make a new instance of BitOp associated with {@link #originalBytes}. You will get null if any parameter is illegal.
	 * @param bigEndian - True is big endian; false is little endian.
	 * @param originalBytes - Original byte array which will be operated.
	 * @return The instance you want. If any parameter is illegal, null instance will be got.
	 */
	public static BitWriter newInstance(boolean bigEndian, byte[] originalBytes)
	{
		if (null == originalBytes || originalBytes.length == 0)
		{
			return null;
		}
		
		return new BitWriter(bigEndian, originalBytes);
	}

	/**
	 * Make a new instance of BitOp associated with {@link #originalBytes}. You will get null if any parameter is illegal.
	 * @param bigEndian - True is big endian; false is little endian.
	 * @param originalBytes - Original byte array which will be operated.
	 * @param fromIndex - set the index of the (included) beginning of the byte array <b>originalBytes</b>.
	 * @return The instance you want. If any parameter is illegal, null instance will be got.
	 */
	public static BitWriter newInstance(boolean bigEndian, byte[] originalBytes, int fromIndex)
	{
		if (null == originalBytes || originalBytes.length == 0)
		{
			return null;
		}
		
		if (fromIndex < 0 || fromIndex > originalBytes.length)
		{
			return null;
		}
		
		return new BitWriter(bigEndian, originalBytes, fromIndex);
	}
	
	/**
	 * If you want to skip some index, you could use this method.
	 */
	public void setCurrentIndex(int currentIndex)
	{
		this.currIndex = currentIndex;
	}
	
	/**
	 * Set a DWORD type from a byte array. And the index increments 4 automatically.
	 * @param dword - DWORD
	 */
	public final void setDWORD(long dword)
	{
		if (this.bigEndian)
		{
			originalBytes[currIndex++] = (byte)(dword >>> 24);
			originalBytes[currIndex++] = (byte)(dword >>> 16);
			originalBytes[currIndex++] = (byte)(dword >>> 8);
			originalBytes[currIndex++] = (byte) dword;
		}
		else
		{
			originalBytes[currIndex++] = (byte) dword;
			originalBytes[currIndex++] = (byte)(dword >>> 8);
			originalBytes[currIndex++] = (byte)(dword >>> 16);
			originalBytes[currIndex++] = (byte)(dword >>> 24);
		}
	}

	/**
	 * Set a DWORD array from a byte array. And the index increments <b>4 * len</b> automatically if parameter is valid.
	 * @param dwords - DWORD array.
	 */
	public final void setDWORDArray(long[] dwords)
	{
		int len = dwords.length;
		for (int i = 0; i < len; ++i)
		{
			setDWORD(dwords[i]);
		}
	}
	
	/**
	 * Set a WORD type from a byte array. And the index increments 2 automatically.
	 * @param word - WORD
	 */
	public final void setWORD(int word)
	{
		if (this.bigEndian)
		{
			originalBytes[currIndex++] = (byte)(word >>> 8);
			originalBytes[currIndex++] = (byte) word;
		}
		else
		{
			originalBytes[currIndex++] = (byte) word;
			originalBytes[currIndex++] = (byte)(word >>> 8);
		}
	}
	
	/**
	 * Set a WORD array from a byte array. And the index increments <b>2 * len</b> automatically if parameter is valid.
	 * @param len - length of destination array. This must be positive. If the <em>len</em> is greater than the leftover length of the array {@link #originalBytes}, return the 
	 * @param words - WORD array
	 */
	public final void setWORDArray(int[] words)
	{
		int len = words.length;
		for (int i = 0; i < len; ++i)
		{
			setWORD(words[i]);
		}
	}

	/**
	 * Set a BYTE type from a byte array. And the index increments 1 automatically.
	 * @param b - BYTE
	 */
	public final void setBYTE(byte b)
	{
		originalBytes[currIndex++] = b;
	}

	/**
	 * Set a BYTE type from a byte array. And the index increments 1 automatically.
	 * @param b - BYTE
	 */
	public final void setBYTE(int b)
	{
		originalBytes[currIndex++] = (byte) b;
	}

	/**
	 * Set a BYTE array from a byte array.
	 * @param bArray - BYTE array
	 */
	public final void setBYTEArray(byte[] bArray)
	{
		int len = bArray.length;
		for (int i = 0; i < len; ++i)
		{
			setBYTE(bArray[i]);
		}
	}

	/**
	 * Set a BYTE array from a byte array.
	 * @param bArray - BYTE array
	 */
	public final void setBYTEArray(int[] bArray)
	{
		int len = bArray.length;
		for (int i = 0; i < len; ++i)
		{
			setBYTE(bArray[i]);
		}
	}

	/**
	 * Put BCD bytes into byte array. 
	 * @param bcd - String BCD format code.
	 */
	public final void setBytesBCD(String bcd)
	{
		this.currIndex = DigitUtil.bcdString2Bytes(bcd, originalBytes, currIndex);
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
	
	public final byte[] getOrignalByteArray()
	{
		return this.originalBytes;
	}
}
