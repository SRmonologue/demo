package com.ohosure.smart.database.devkit.utils;

public class Hex
{
	public static final String get(byte[] b)
	{
		return get(b, true);
	}
	
	public static final String get(byte[] b, boolean trim0)
	{
		if (null == b || b.length == 0)
		{
			return "";
		}

		int count0 = 0;
		StringBuilder result = new StringBuilder(b.length << 1);
		if (!trim0)
		{
			for (int i = 0; i < b.length; i++)
			{
				int iValue = b[i] & 0xff;
				getHexBase(result, iValue);
			}
			return result.toString();
		}
		
		for (int i = 0; i < b.length; i++)
		{
			int iValue = b[i] & 0xff;
			if (b[i] == 0)
			{
				count0++;
			}
			else
			{
				if (count0 > 0)
				{
					for (int k = 0; k < count0; ++k)
					{
						result.append("00");
					}
					getHexBase(result, iValue);
					count0 = 0;
				}
				else
				{
					getHexBase(result, iValue);
				}
			}
		}
		return result.toString();
	}

	private static void getHexBase(StringBuilder result, int iValue)
	{
		String str = Integer.toString(iValue, 16);
		if (str.length() == 1)
		{
			result.append('0');
		}
		result.append(str);		
	}

}
