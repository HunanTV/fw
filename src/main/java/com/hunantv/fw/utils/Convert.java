package com.hunantv.fw.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Blob;
import java.sql.Clob;

public class Convert
{
	/**
	 * convert a string to a byte
	 * @param s
	 * @return byte
	 */
	public static byte str2Byte(String s)
	{
		if (s == null || s.trim().length() == 0)
			return 0;
		return Byte.parseByte(s);
	}

	/**
	 * convert a string to a short
	 * @param s
	 * @return short
	 */
	public static short str2Short(String s)
	{
		if (s == null || s.trim().length() == 0)
			return 0;
		return Short.parseShort(s);
	}

	/**
	 * convert a string to a int
	 * @param s
	 * @return
	 */
	public static int str2Int(String s)
	{
		if (s == null || s.trim().length() == 0)
			return 0;
		return Integer.parseInt(s);
	}

	/**
	 * convert a string to a long
	 * @param s
	 * @return
	 */
	public static long str2Long(String s)
	{
		if (s == null || s.trim().length() == 0)
			return 0;
		return Long.parseLong(s);
	}

	/**
	 * convert a string a float
	 * @param s
	 * @return
	 */
	public static float str2Float(String s)
	{
		if (s == null || s.trim().length() == 0)
			return 0.0F;
		return Float.parseFloat(s);
	}

	/**
	 * convert a string to a double
	 * @param s
	 * @return
	 */
	public static double str2Double(String s)
	{
		if (s == null || s.trim().length() == 0)
			return 0;
		return Double.parseDouble(s);
	}

	/**
	 * convert a string to a boolean
	 * @param s
	 * @return
	 */
	public static boolean str2Bool(String s)
	{
		if (s == null || s.trim().length() == 0)
			return false;
		else if (s.trim().equalsIgnoreCase("true"))
			return true;
		else if (s.trim().equals("1"))
			return true;
		else
			return false;
	}

	/**
	 * convert a string to a byte array
	 * @param str
	 * @param charset
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static byte[] str2Bytes(String str, String charset) throws IOException
	{
		if (null == charset)
			return str.getBytes();
		return str.getBytes(charset);
	}

	/**
	 * join two byte arrays to one byte array
	 * @param fir
	 * @param sec
	 * @return
	 */
	public static byte[] joinBytes(byte[] fir, byte[] sec)
	{
		int firLen = fir.length;
		int secLen = sec.length;
		byte[] third = new byte[firLen + secLen];

		for (int i = 0; i < firLen; i++)
			third[i] = fir[i];
		for (int i = 0; i < secLen; i++)
			third[firLen + i] = sec[i];

		return third;
	}

	/**
	 * convert a long value to bytes array
	 * @param num
	 * @return
	 */
	public static byte[] long2Bytes(long num)
	{
		byte[] b = new byte[8];
		for (int i = 0; i < b.length; i++)
			b[i] = (byte) ((num >> (i * 8)) & 0xff);
		return b;
	}

	/**
	 * convert a long value to a boolean value
	 * @param num
	 * @return
	 */
	public static boolean long2Bool(long num)
	{
		if (num == 0)
			return false;
		return true;
	}

	/**
	 * convert a byte array to a long
	 * @param b
	 * @return
	 */
	public static long bytes2Long(byte[] b)
	{
		return bytes2Long(b, 0);
	}

	/**
	 * convert a byte array to a long
	 * @param b
	 * @param start
	 * @return
	 */
	public static long bytes2Long(byte[] b, int start)
	{
		long result = 0;
		for (int i = 0; i < 8; i++)
			result += (long) (((long) (b[start + i]) & 0xff) << (i * 8));
		return result;
	}

	/**
	 * convert a int to a byte array
	 * @param num
	 * @return
	 */
	public static byte[] int2Bytes(int num)
	{
		byte[] b = new byte[4];
		for (int i = 0; i < b.length; i++)
			b[i] = (byte) ((num >> (i * 8)) & 0xff);
		return b;
	}

	/**
	 * convert a int to a boolean value
	 * @param num
	 * @return
	 */
	public static boolean int2Bool(int num)
	{
		if (num == 0)
			return false;
		return true;
	}

	/**
	 * convert a byte array to a int
	 * @param b
	 * @return
	 */
	public static int bytes2Int(byte[] b)
	{
		return bytes2Int(b, 0);
	}

	/**
	 * convert a byte array to a int
	 * @param b
	 * @param start
	 * @return
	 */
	public static int bytes2Int(byte[] b, int start)
	{
		int result = 0;
		for (int i = 0; i < 4; i++)
			result += (long) (((int) (b[start + i]) & 0xff) << (i * 8));
		return result;
	}

	/**
	 * convert a blob data to a string
	 * @param blob
	 * @return
	 * @throws Exception
	 */
	public static String blob2Str(Blob blob) throws Exception
	{
		if (blob == null)
			return "";
		StringBuffer buffer = new StringBuffer(1024);
		BufferedReader in = null;
		try
		{
			String str = null;
			in = new BufferedReader(new InputStreamReader(blob.getBinaryStream()));
			while ((str = in.readLine()) != null)
				buffer.append(str).append("\r\n");
		} catch (Exception e)
		{
			throw e;
		} finally
		{
			try
			{
				in.close();
			} catch (Exception e)
			{
				if (null != in)
					in = null;
			}
		}

		return buffer.toString();
	}

	/**
	 * convert a clob data to a string
	 * @param clob
	 * @return
	 * @throws Exception
	 */
	public static String clob2Str(Clob clob) throws Exception
	{
		if (clob == null)
			return "";
		StringBuffer buffer = new StringBuffer(1024);
		BufferedReader in = null;
		try
		{
			String str = null;
			in = new BufferedReader(clob.getCharacterStream());
			while ((str = in.readLine()) != null)
				buffer.append(str).append("\r\n");

		} catch (Exception ex)
		{
			throw ex;
		} finally
		{
			try
			{
				in.close();
			} catch (Exception e)
			{
				in = null;
			}
		}
		return buffer.toString();
	}

	public static boolean byte2Bool(byte v)
	{
		if (v == (byte) 0)
			return false;
		else
			return true;
	}

	public static byte bool2Byte(boolean b)
	{
		if (b == false)
			return (byte) 0;
		else
			return (byte) 1;
	}

	public static String str2Filepath(String str)
	{
		if (str == null || str.trim().length() == 0)
			return str;
		String separator = System.getProperty("file.separator");
		if (!str.endsWith("\\") && !str.endsWith("/"))
			str += separator;
		return str;
	}

	public static String str2Webpath(String str)
	{
		if (str == null || str.trim().length() == 0)
			return str;
		if (!str.endsWith("/"))
			str += "/";
		return str;
	}

	// public static void main(String[] args)
	// {
	// String str = "aaaaaaa";
	// str = Convert.str2Filepath(str);
	// System.out.println(str);
	// }
}
