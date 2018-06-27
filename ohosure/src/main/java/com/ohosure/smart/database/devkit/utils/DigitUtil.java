package com.ohosure.smart.database.devkit.utils;


import com.ohosure.smart.database.devkit.log.MLog;

/**
 * An Utility Class for Digit-related.
 * 
 * @author daxingj
 */
public final class DigitUtil
{
	private static final char digit[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
		'A', 'B', 'C', 'D', 'E', 'F', 'a', 'b', 'c', 'd', 'e', 'f' };
	public static final String TAG = DigitUtil.class.getSimpleName();

	
	/**
	 * Get hex string from a byte array. Will trim all 0 on the right.
	 * @param b - Byte array.
	 * @return The hex format string instance of the byte array
	 * @see {@link Hex#get(byte[])}
	 */
	public static String getHexString(byte[] b)
	{
		return getHexString(b, true);
	}
	/**
	 * Get hex string from a byte array. Will trim all 0 on the right.
	 * @param b - Byte array.
	 * @param trim0 - If need trim all 0 at tail, set it true. {@link #getHexString(byte[])} set trim0 to true as default.
	 * @return The hex format string instance of the byte array
	 * @see {@link Hex#get(byte[], boolean)}
	 */
	public static String getHexString(byte[] b, boolean trim0)
	{
		return Hex.get(b, trim0);
	}
	
	/**
	 * A converter for Little end to big end.
	 * 
	 * @param data
	 * @param len
	 * @param step
	 * 
	 * @author jiarongy
	 */
	public static void littleEnd2BigEnd(byte[] data, int len, boolean step)
	{
		byte temp;
		if (step)
		{
			for (int i = 0; i < len;)
			{
				temp = data[i + 3];
				data[i + 3] = data[i];
				data[i] = temp;

				temp = data[i + 2];
				data[i + 2] = data[i + 1];
				data[i + 1] = temp;
				i = i + 4;
			}
		}
		else
		{
			for (int i = 0; i < len;)
			{
				temp = data[i + 1];
				data[i + 1] = data[i];
				data[i] = temp;
				i = i + 2;
			}
		}
	}

	/**
	 * Convert BCD byte array to a String number.
	 * Every BCD byte indicates 2 digits.
	 * i.e. length of returned string is 2 multiple of length of BCD bytes. 
	 * 
	 * @param bytes - BCD original byte array.
	 * @param fromIndex - Begining index which is included.
	 * @param numOfBcdBytes - Number of BCD bytes. e.g.: BCD string "160102" has 3 bytes.
	 * @return String number. If invalid input parameters, return null.
	 */
	public static String getBCD(byte[] bytes, int fromIndex, int numOfBcdBytes)
	{
		if (null == bytes || bytes.length == 0 || numOfBcdBytes < 1)
		{
			return null;
		}

		int nextIndexOfLast = bytes.length;
		if (nextIndexOfLast > numOfBcdBytes + fromIndex)
		{
			nextIndexOfLast = numOfBcdBytes + fromIndex;
		}
		

		StringBuilder sb = new StringBuilder(numOfBcdBytes * 2);
		int high4Ander = 240;

		for (int i = fromIndex; i < nextIndexOfLast; ++i)
		{
			int high = (bytes[i] & high4Ander) >>> 4;
			int low = bytes[i] & 15;
			boolean isValid = checkRegion(high, 0, 9) && checkRegion(low, 0, 9);
			if (isValid)
			{
				sb.append(high);
				sb.append(low);
			}
			else
			{
				return null;
			}
		}

		return sb.toString();
	}
	
	/**
	 * Check if the candidate is in the region (lowIncluded to highInclude)
	 * @param candidate - The one who will be checked.
	 * @param lowIncluded - low digit which is included.
	 * @param highInclude - high digit which is included.
	 * @return If it is between low and high digits, return true; otherwise return false.
	 */
	public static boolean checkRegion(int candidate, int lowIncluded, int highIncluded)
	{
		if (lowIncluded > highIncluded)
		{
			// swap
			int temp = highIncluded;
			highIncluded = lowIncluded;
			lowIncluded = temp;
		}
		
		return candidate >= lowIncluded && candidate <= highIncluded;
	}

	/**
	 * Transfer BCD String number into a partial byte array.
	 * @param number - Elements in this MUST be ALL 0-9 digits.
	 * @param dest - byte array which has been existent. We operate partial of it.
	 * @param fromDestIndex - included beginning index.
	 * @return the current useful index after converting. 
	 */
	public static int bcdString2Bytes(final String number, final byte[] dest, int fromDestIndex)
	{
		if (null == number || number.length() == 0)
		{
			return fromDestIndex;
		}
		
		final char[] src = number.toCharArray();
		int fromSrcIndex = 0;
		
		if (src.length % 2 != 0)
		{
			dest[fromDestIndex] = (byte) bcdChar2Int(src[fromSrcIndex]);
			++fromSrcIndex;
			++fromDestIndex;
		}
		
		boolean isDestSmall = dest.length * 2 < src.length - fromSrcIndex;

		int length;
		if (isDestSmall)
		{
			length = dest.length * 2 + fromSrcIndex;
			fillBytesByChars(src, fromSrcIndex, dest, fromDestIndex, length);
			return fromDestIndex + dest.length;
		}
		else
		{
			length = src.length;
			fillBytesByChars(src, fromSrcIndex, dest, fromDestIndex, length);
			return fromDestIndex + src.length / 2;
		}
	}

	/**
	 * Comnbine all byte arrays to a single byte array.
	 * 
	 * @param others - Byte arrays.
	 * @return A byte you want.
	 */
	public static byte[] combineByteArrays(byte[]... others)
	{
		if (null == others || others.length == 0)
		{
			MLog.w(TAG, "parameter invalid: should not be null or empty!");
			return null;
		}
		int len = 0;
		for (int i = 0; i < others.length; ++i)
		{
			if (others[i] != null)
			{
				len += others[i].length;
			}
		}
		if (len <= 0)
		{
			MLog.w(TAG, "parameter invalid: each item is null or empty!");
			return null;
		}
		
		byte[] result = new byte[len];
		len = 0;
		for (int i = 0; i < others.length; ++i)
		{
			int oLen = others[i].length;
			System.arraycopy(others[i], 0, result, len, oLen);
			len += oLen;
		}
		return result;
	}
	
	/**
	 * Transfer BCD byte array to a format which SimpleDataFormat could parsed.
	 * This method do not validate the parameter.
	 * @param data - The original data which contains BCD time.
	 * @param from - The BCD starting index.
	 * @param length - The total quantity you'd like to convert.
	 * @return The String format like "yyMMddhhmmss", such as "131205170801" which represents 2013-12-05 17:08:01.
	 * 		If the length exceeds length of data, result will be trimmed.
	 * 		If any error occurred, exception will be thrown.
	 */
	public static String bcd2StringTime(final byte[] data, final int from, int length)
	{
		int diff = from + length - data.length;
		if (diff >= 0)
		{
			length -= diff;
		}
		int shift = 4;
		StringBuilder sb = new StringBuilder(256);
		for (int i = from; i < from + length; ++i)
		{
			sb.append(data[i] >>> shift & 0xf);
			sb.append(data[i] & 0xf);
		}
		return sb.toString();
	}
	
	/**
	 * Transfer a whole BCD String number into a byte array.
	 * @param number - Elements in this MUST be ALL 0-9 digits.
	 * @return If number is not an all-digit String object, return null. Otherwise, return a BCD byte array. 
	 */
	public static byte[] bcdString2Bytes(String number)
	{
		if (null == number || number.length() == 0)
		{
			return null;
		}
		
		final byte[] dest;
		final char[] src = number.toCharArray();
		int fromSrcIndex = 0, fromDestIndex = 0;
		if (src.length % 2 == 0)
		{
			dest = new byte[src.length / 2];
		}
		else
		{
			dest = new byte[src.length / 2 + 1];
			dest[fromDestIndex] = (byte) bcdChar2Int(src[fromSrcIndex]);
			++fromSrcIndex;
			++fromDestIndex;
		}

		boolean isDestSmall = dest.length * 2 < src.length - fromSrcIndex;
		int length;
		if (isDestSmall)
		{
			length = dest.length * 2 + fromSrcIndex;
			return fillBytesByChars(src, fromSrcIndex, dest, fromDestIndex, length);
		}
		else
		{
			length = src.length;
			return fillBytesByChars(src, fromSrcIndex, dest, fromDestIndex, length);
		}
	}

	/**
	 * Transfer char to integer.
	 * 
	 * @param c1 - According to ASCII table
	 * @return Only return 0,1,2,3,4,5,6,7,8,9.<br/>
	 *         If error, return -1.
	 */
	public static int bcdChar2Int(char c1)
	{
		int charKey_0 = 48;

		if (charKey_0 <= c1 && c1 <= charKey_0 + 9)
		{
			return c1 - charKey_0;
		}

		return -1;
	}

	/**
	 * Transfer char to integer.
	 * 
	 * @param c1
	 *            - According to ASCII table
	 * @return Only return 0,1,2,3,4,5,6,7,8,9,A,B,C,D,E,F,a,b,c,d,e,f.<br/>
	 *         If error, return -1.
	 */
	public static int char2Int(char c1)
	{
		int charKey_0 = 48;
		int charKey_A = 65;
		int charKey_a = 97;

		if (charKey_0 <= c1 && c1 <= charKey_0 + 9)
		{
			return c1 - charKey_0;
		}
		else if (charKey_A <= c1 && c1 <= charKey_A + 5)
		{
			return c1 - charKey_A + 10;
		}
		else if (charKey_a <= c1 && c1 <= charKey_a + 5)
		{
			return c1 - charKey_a + 10;
		}

		return -1;
	}
	
	/**
	 * Do split work without validation.
	 * @param packet - String data packet.
	 * @return The byte array data.
	 */
	private static byte[] basicSplit(String packet)
	{
		packet = packet.trim();
		int len = packet.length();
		byte[] b = new byte[(len + 1) / 2];
		char[] c = packet.toCharArray();
		int left = -1;
		for (int i = 0; i < c.length; ++i)
		{
			int found = -1;
			for (int j = 0; j < digit.length; ++j)
			{
				if (c[i] == digit[j])
				{
					found = j;
					break;
				}
			}

			if (-1 < found && found < digit.length)
			{
				// found
				int result = char2Int(digit[found]);
				if (-1 < result && result < 16)
				{
					if (left < 0)
					{
						left = result;
					}
					else
					{
						int temp = i / 2;
						if (temp < len / 2)
							b[temp] = (byte) ((left << 4) + result);
						left = -1;
					}
				}
				else
				{
					String msg = "ERROR: result=" + result;
					MLog.d(TAG, msg);
					return null;
				}
			}
			else
			{
				String msg = "ERROR: found=" + found;
				MLog.d(TAG, msg);
				return null;
			}
		}

		return b;
	}

	/**
	 * 1. trim packet. 2. if any character in packet is not included in
	 * <em>digit</em>, return null.
	 * 
	 * @param packet - Only contains hex number characters.
	 * @see char2Int(char c1)
	 * @return if no issue, return the byte array which represents the string packet.
	 */
	public static byte[] split(String packet)
	{
		if (null == packet || packet.length() == 0)
		{
			return null;
		}
		
		return basicSplit(packet);
	}

	/**
	 * A variety of {@link #split(String)}. This version split by space firstly.
	 * For example, "E1 10" will be split to 2 String instances "E1" and "10" firstly and then do the {@link #split(String)} work.
	 *  
	 * 1. trim packet. 2. if any character in packet is not included in
	 * <em>digit</em>, return null.
	 * 
	 * @param packet - Only contains hex number characters.
	 * @see char2Int(char c1)
	 * @return if no issue, return the byte array which represents the string packet.
	 */
	public static byte[] splitBySpace(String packet)
	{
		if (null == packet || packet.length() == 0)
		{
			return null;
		}
		
		String[] ss = packet.split("\\s+");
		byte[] result = new byte[ss.length];
		for (int i = 0; i < ss.length; ++i)
		{
			byte[] b = basicSplit(ss[i]);
			if (null == b)
			{
				String msg = "packet[" + packet + "] is not valid so result of basicSplit() is null, s[" + i + "]=" + ss[i];
				MLog.w(TAG, msg);
				return null;
			}
			if (b.length < 1)
			{
				String msg = "packet[" + packet + "] is not valid so length of result of basicSplit() is " + b.length + ", s[" + i + "]=" + ss[i];
				MLog.w(TAG, msg);
				continue;
			}

			result[i] = b[0];
		}
		
		return result;
	}

	/**
	 * First byte XOR with next until the length - 1.
	 * 
	 * @param bytes - source byte array.
	 * @param length - length of {@code bytes}.
	 * @return the check code.
	 */
	public static byte generateCheckCode(byte[] bytes, int length)
	{
		byte xor = bytes[0];
		for (int i = 1; i < length; ++i)
		{
			xor ^= bytes[i];
		}
		return xor;
	}

	/**
	 * Copy integer array into byte array. 
	 * If each integer element is greater than <code>Byte.MAX_VALUE</code>, ignore the higher bits and only reserve 8 bits.
	 * @param src - source int array
	 * @param fromSrcIndex - beginning index of source array
	 * @param dest - destination byte array
	 * @param fromDestIndex - beginning index of destination array
	 * @return the current pointing index
	 */
	public static int ints2Bytes(final int[] src, int fromSrcIndex, final byte[] dest, int fromDestIndex)
	{
		if (null == src || src.length == 0)
		{
			return fromDestIndex;
		}
		fillBytesByInts(src, fromSrcIndex, dest, fromDestIndex);
		return fromDestIndex + src.length - fromSrcIndex;
	}

	/**
	 * Copy integer array into byte array. 
	 * If each integer element is greater than <code>Byte.MAX_VALUE</code>, ignore the higher bits and only reserve 8 bits.
	 * @param src - source int array
	 * @param dest - destination byte array
	 * @param fromDestIndex - beginning index of destination array
	 * @return the current pointing index
	 */
	public static int ints2Bytes(final int[] src, final byte[] dest, int fromDestIndex)
	{
		if (null == src || null == dest || src.length == 0 || dest.length == 0)
		{
			return fromDestIndex;
		}
		fillBytesByInts(src, 0, dest, fromDestIndex);
		return fromDestIndex + src.length;
	}

	/**
	 * Copy ALL integer array into byte array. 
	 * If each integer element is greater than <code>Byte.MAX_VALUE</code>, ignore the higher bits and only reserve 8 bits.
	 * @param src - source integer array
	 * @return destination byte array
	 */
	public static byte[] ints2Bytes(final int[] src)
	{
		if (null == src)
		{
			return null;
		}
		byte[] dest = new byte[src.length];
		fillBytesByInts(src, 0, dest, 0);
		return dest;
	}
	
	/**
	 * Copy byte array into integer array. 
	 * @param src - source byte array
	 * @param fromSrcIndex - beginning index of source array
	 * @param dest - destination integer array
	 * @param fromDestIndex - beginning index of destination array
	 * @return the current pointing index
	 */
	public static int bytes2Ints(final byte[] src, int fromSrcIndex, final int[] dest, int fromDestIndex, int length)
	{
		if (null == src || src.length == 0)
		{
			return fromDestIndex;
		}
		int actualLength = fillIntsByBytes(src, fromSrcIndex, dest, fromDestIndex, length);
		return fromDestIndex + actualLength - fromSrcIndex;
	}
	
	/**
	 * Copy byte array into integer array. 
	 * @param src - source byte array
	 * @param fromSrcIndex - beginning index of source array
	 * @param dest - destination integer array
	 * @param fromDestIndex - beginning index of destination array
	 * @return the current pointing index
	 */
	public static int bytes2Ints(final byte[] src, int fromSrcIndex, final int[] dest, int fromDestIndex)
	{
		if (null == src || src.length == 0)
		{
			return fromDestIndex;
		}
		fillIntsByBytes(src, fromSrcIndex, dest, fromDestIndex);
		return fromDestIndex + src.length - fromSrcIndex;
	}

	/**
	 * Copy byte array into integer array. 
	 * @param src - source byte array
	 * @param dest - destination integer array
	 * @param fromDestIndex - beginning index of destination array
	 * @return the current pointing index
	 */
	public static int bytes2Ints(final byte[] src, final int[] dest, int fromDestIndex)
	{
		if (null == src || null == dest || src.length == 0 || dest.length == 0)
		{
			return fromDestIndex;
		}
		fillIntsByBytes(src, 0, dest, fromDestIndex);
		return fromDestIndex + src.length;
	}

	/**
	 * Copy ALL byte array into integer array. 
	 * @param src - source byte array
	 * @return destination byte array
	 */
	public static int[] bytes2Ints(final byte[] src)
	{
		if (null == src)
		{
			return null;
		}
		int[] dest = new int[src.length];
		fillIntsByBytes(src, 0, dest, 0);
		return dest;
	}
	
	/**
	 * Copy ALL integer array beginning from a specified index <code>fromSrcIndex</code> into byte array.
	 * Suggest to use the return byte array instead of direct <code>dest</code> because it will return null when error.
	 * 
	 * @param src - source integer array
	 * @param fromSrcIndex - beginning index of integer array
	 * @param dest - destination byte array
	 * @param fromDestIndex - beginning index of destination array
	 */
	private static final void fillBytesByInts(final int[] src, int fromSrcIndex, final byte[] dest, int fromDestIndex)
	{
		for (; fromSrcIndex < src.length; ++fromSrcIndex, ++fromDestIndex)
		{
			dest[fromDestIndex] = (byte) src[fromSrcIndex];
		}
	}

	
	/**
	 * Copy ALL byte array beginning from a specified index <code>fromSrcIndex</code> into integer array.
	 * Suggest to use the return byte array instead of direct <code>dest</code> because it will return null when error.
	 * 
	 * @param src - source byte array
	 * @param fromSrcIndex - beginning index of byte array
	 * @param dest - destination integer array
	 * @param fromDestIndex - beginning index of destination array
	 * @param length - total length to be converted
	 * @return actual length of converted
	 */
	private static final int fillIntsByBytes(final byte[] src, int fromSrcIndex, final int[] dest, int fromDestIndex, int length)
	{
		if (length > src.length || length < 1)
		{
			length = src.length;
		}
		for (; fromSrcIndex < length; ++fromSrcIndex, ++fromDestIndex)
		{
			dest[fromDestIndex] = src[fromSrcIndex];
		}
		return length;
	}
	
	/**
	 * Copy ALL byte array beginning from a specified index <code>fromSrcIndex</code> into integer array.
	 * Suggest to use the return byte array instead of direct <code>dest</code> because it will return null when error.
	 * 
	 * @param src - source byte array
	 * @param fromSrcIndex - beginning index of byte array
	 * @param dest - destination integer array
	 * @param fromDestIndex - beginning index of destination array
	 */
	private static final void fillIntsByBytes(final byte[] src, int fromSrcIndex, final int[] dest, int fromDestIndex)
	{
		fillIntsByBytes(src, fromSrcIndex, dest, fromDestIndex, src.length);
	}

	/**
	 * Copy ALL char array beginning from a specified index <code>fromSrcIndex</code> into byte array.
	 * Suggest to use the return byte array instead of direct <code>dest</code> because it will return null when error.
	 * NOTE: This method does NOT CHECK any paramters!
	 * 
	 * @param src - source char array
	 * @param fromSrcIndex - beginning index of char array
	 * @param dest - destination byte array
	 * @param fromDestIndex - beginning index of byte array
	 * @param length - the specified length to be filled at dest.
	 * 
	 * @return The byte array. If error, return null.
	 */
	private static final byte[] fillBytesByChars(char[] src, int fromSrcIndex, byte[] dest, int fromDestIndex, int length)
	{
		for (; fromSrcIndex < length; fromSrcIndex += 2, ++fromDestIndex)
		{
			int iChar0 = bcdChar2Int(src[fromSrcIndex]);
			int iChar1 = bcdChar2Int(src[fromSrcIndex + 1]);
			if (iChar0 < 0 || iChar1 < 0)
			{
				return null;
			}
			dest[fromDestIndex] = (byte) ((iChar0 << 4) + iChar1);
		}
		return dest;
	}

	/**
	 * Copy long array into byte array. 
	 * If each long element is greater than <code>Byte.MAX_VALUE</code>, ignore the higher bits and only reserve 8 bits.
	 * @param src - source long array
	 * @param fromSrcIndex - beginning index of source long array
	 * @param dest - destination byte array
	 * @param fromDestIndex - beginning index of destination array
	 * @return the current pointing index
	 */
	public static int longs2Bytes(final long[] src, int fromSrcIndex, final byte[] dest, int fromDestIndex)
	{
		if (null == src || null == dest || src.length == 0 || dest.length == 0)
		{
			return fromDestIndex;
		}
		fillBytesByLongs(src, fromSrcIndex, dest, fromDestIndex);
		return fromDestIndex + src.length - fromSrcIndex;
	}

	/**
	 * Copy long array into byte array. 
	 * If each long element is greater than <code>Byte.MAX_VALUE</code>, ignore the higher bits and only reserve 8 bits.
	 * @param src - source long array
	 * @param dest - destination byte array
	 * @param fromDestIndex - beginning index of destination array
	 * @return the current pointing index
	 */
	public static int longs2Bytes(final long[] src, final byte[] dest, int fromDestIndex)
	{
		if (null == src || null == dest || src.length == 0 || dest.length == 0)
		{
			return fromDestIndex;
		}
		fillBytesByLongs(src, 0, dest, fromDestIndex);
		return fromDestIndex + src.length;
	}

	/**
	 * Copy ALL long array into byte array. 
	 * If each long element is greater than <code>Byte.MAX_VALUE</code>, ignore the higher bits and only reserve 8 bits.
	 * @param src - source long array
	 * @return destination byte array
	 */
	public static byte[] longs2Bytes(final long[] src)
	{
		if (null == src)
		{
			return null;
		}
		byte[] dest = new byte[src.length];
		fillBytesByLongs(src, 0, dest, 0);
		return dest;
	}
	
	/**
	 * Copy ALL long array beginning from a specified index <code>fromSrcIndex</code> into byte array.
	 * Suggest to use the return byte array instead of direct <code>dest</code> because it will return null when error.
	 * 
	 * @param src - source long array
	 * @param fromSrcIndex - beginning index of long array
	 * @param dest - destination byte array
	 * @param fromDestIndex - beginning index of destination array
	 */
	private static final void fillBytesByLongs(long[] src, int fromSrcIndex, byte[] dest, int fromDestIndex)
	{
		for (; fromSrcIndex < src.length; ++fromSrcIndex, ++fromDestIndex)
		{
			dest[fromSrcIndex] = (byte) src[fromSrcIndex];
		}
	}

	/**
	 * Convert integer array to a String for printing.
	 * @param sb - StringBuilder instance which the invoker using.
	 * @param printed - integer array.
	 * @return String object indicates the array.
	 */
	public static void toString(StringBuilder sb, int[] printed)
	{
		if (null == printed)
		{
			sb.append("null");
			return;
		}
		
		for (int i = 0; i < printed.length - 1; ++i)
		{
			sb.append(printed[i]);
			sb.append(',');
		}
		sb.append(printed[printed.length - 1]);
	}

	/**
	 * Convert byte array to a String for printing.
	 * @param sb - StringBuilder instance which the invoker using.
	 * @param printed - byte array.
	 * @return String object indicates the array.
	 */
	public static void toString(StringBuilder sb, byte[] printed)
	{
		if (null == printed)
		{
			sb.append("null");
			return;
		}
		
		for (int i = 0; i < printed.length - 1; ++i)
		{
			sb.append(printed[i]);
			sb.append(',');
		}
		sb.append(printed[printed.length - 1]);
	}
	
	/**
	 * Sum all the numbers from a specified number to the end. You could specify the from number (included), sum count and sum step.
	 * This step is 1 by default.
	 * @param from - Summing begins from this number (included).
	 * @param count - The quantity of addends. It should be at least 1, otherwise result equals to <em>from number</em>.
	 * @return the summation result. If the sum result exceed the boundary value of Integer, return that value. 
	 * 			If any other invalid or error, return value of <em>from number</em>.
	 */
	public static int sum(int from, int count)
	{
		return sum(from, count, 1);
	}
	
	/**
	 * Sum all the numbers from a specified number to the end. You could specify the from number (included), sum count and sum step.
	 * @param from - Summing begins from this number (included).
	 * @param count - The quantity of addends. It should be at least 1, otherwise result equals to <em>from number</em>.
	 * @param step - 1 is default.
	 * @return the summation result. If the sum result exceed the boundary value of Integer, return that value. 
	 * 			If any other invalid or error, return value of <em>from number</em>.
	 */
	public static int sum(int from, int count, int step)
	{
		if (step == 0 || count < 1)
		{
			return from;
		}
		
		int sum = 0;
		if (step > 0)
		{
			for (int i = from; i <= count; i += step)
			{
				if (sum > 0 && Integer.MAX_VALUE - sum < i)
				{
					// exceed
					return Integer.MAX_VALUE;
				}
				else
				{
					sum += i;
				}
			}
		}
		else
		{
			for (int i = from; i <= count; i += step)
			{
				if (sum < 0 && Integer.MIN_VALUE - sum > i)
				{
					// exceed
					return Integer.MIN_VALUE;
				}
				else
				{
					sum += i;
				}
			}
		}
		return sum;
	}

	/**
	 * Transfer a byte digit to a Hex String instance.
	 * @param b - byte digit.
	 * @return 2 bit Hex String object.
	 */
	public static final String byte2Hex(byte b)
	{
		int high = (b & 0xF0) >>> 4;
		int low = (b & 0x0F);
		return Integer.toHexString(high) + Integer.toHexString(low);
	}

	/**
	 * Transfer a short digit to a Hex String instance.
	 * @param i - byte digit.
	 * @return 4 bit Hex String object. Low + High.
	 */
	public static final String short2Hex(int i)
	{
		if (i > 255)
		{
			int highest = (i & 0xF000) >>> 12;
			int higher = (i & 0xF00) >>> 8;
			int high = (i & 0xF0) >>> 4;
			int low = (i & 0xF);
			return Integer.toHexString(high) + Integer.toHexString(low) + Integer.toHexString(highest) + Integer.toHexString(higher);
		}
		else
		{
			int high = (i & 0xF0) >>> 4;
			int low = (i & 0x0F);
			return Integer.toHexString(high) + Integer.toHexString(low) + " 00";
		}
	}

	/**
	 * Transfer a byte digit to a Hex String instance.
	 * @param i - this will be cast to byte.
	 * @return 2 bit Hex String object.
	 */
	public static final String byte2Hex(int i)
	{
		byte b = (byte) i;
		int high = (b & 0xF0) >>> 4;
		int low = (b & 0x0F);
		return Integer.toHexString(high) + Integer.toHexString(low);
	}

	/**
	 * Transfer BCD formatted digit to an integer.
	 * 
	 * @param bcdNo - BCD digit.
	 * @return Related integer value.
	 */
	public static final int bcd2Int(byte bcdNo)
	{
		return (bcdNo >> 4 & 0xf) * 10 + (bcdNo & 0xf);
	}

	/**
	 * Transfer BCD formatted digit to a double.
	 * 
	 * @param bcdNo - BCD digit.
	 * @return Related integer value.
	 */
	public static final double bcd2Double(byte bcdNo)
	{
		return (double) bcd2Int(bcdNo);
	}
	
	/**
	 * Transfer integer number to BCD format.
	 * @param i - The integer number to be changed.
	 * @return BCD formatted byte number.
	 */
	public static final byte int2bcd(int i)
	{
		i %= 100;
		return (byte) ((i / 10 << 4) + (i % 10));
	}
	
	/**
	 * Transfer integer number to BCD format.
	 * @param i - The integer number to be changed.
	 * @return BCD formatted byte number.
	 */
	public static final int int2double(int iVal)
	{
	    StringBuffer sb = new StringBuffer(8);
	    boolean started = false;
	    for (int i = 24; i >= 0; i -= 8) {
	    	byte b = (byte)(iVal >> i);
	    	int val = b >> 4;
	    	if (val > 0 || started) {
	    	started = true;
	    	sb.append(digit[b >> 4]);
	    	}
	    	val = b & 15;
	    	if (val > 0 || started) {
	    	started = true;
	    	sb.append(digit[b & 15]);
	    	}
	    }
	    return Integer.parseInt(sb.toString());
	}
}
