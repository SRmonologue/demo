package com.ohosure.smart.database.devkit.file;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import com.ohosure.smart.database.devkit.log.MLog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;



public class FileOperator {

	private static final String TAG = FileOperator.class.getName();

	/**
	 * Read StringBuild From Assets for Android open mode
	 * (AssetManager.ACCESS_STREAMING)
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static StringBuilder readStringFromAssets(Context context,
                                                     String fileName) {
		StringBuilder sb = new StringBuilder();
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(context.getAssets()
					.open(fileName)));
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (Exception e) {
			MLog.e(TAG, "readStringBuildFromAssets read error!");
			MLog.e(TAG, e.toString());
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					MLog.e(TAG, "readStringBuildFromAssets close error!");
					MLog.e(TAG, e.toString());
				}
		}
		return sb;
	}

	/**
	 * Copy file data from one to another
	 * 
	 * @param from
	 * @param to
	 * @throws IOException
	 */
	public static void fileCopy(String from, String to) throws IOException {

		FileInputStream in = new FileInputStream(from);
		File file = new File(to);
		if (!file.exists())
			file.createNewFile();
		FileOutputStream out = new FileOutputStream(file);
		int c;
		byte buffer[] = new byte[1024];
		while ((c = in.read(buffer)) != -1) {
			out.write(buffer, 0, c);
		}
		in.close();
		out.close();

	}

	/**
	 * Copy file data from one to another
	 * 
	 * @param from
	 * @param to
	 * @throws IOException
	 */
	public static void fileCopy(InputStream from, String to) throws IOException {

		InputStream in = from;
		File file = new File(to);
		if (!file.exists())
			file.createNewFile();
		FileOutputStream out = new FileOutputStream(file);
		int c;
		byte buffer[] = new byte[1024];
		while ((c = in.read(buffer)) != -1) {
			out.write(buffer, 0, c);
		}
		in.close();
		out.close();

	}

	/**
	 * Copy file data from one to another(using Channel)
	 * 
	 * @param from
	 * @param to
	 * @throws Exception
	 */
	public static void fileCopyOnChannel(String from, String to)
			throws Exception {
		File f1 = new File(from);
		File f2 = new File(to);
		int length = 1048576;// 1MB
		FileInputStream in = new FileInputStream(f1);
		FileOutputStream out = new FileOutputStream(f2);
		FileChannel inC = in.getChannel();
		FileChannel outC = out.getChannel();

		while (inC.position() != inC.size()) {

			if ((inC.size() - inC.position()) < 1048576)
				length = (int) (inC.size() - inC.position());
			else
				length = 1048576;
			inC.transferTo(inC.position(), length, outC);
			inC.position(inC.position() + length);

		}
		inC.close();
		outC.close();
	}
	
	public float getAvailableInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		System.out.println("getAvailableInternalMemorySize  " + path.toString()
				+ " " + availableBlocks * blockSize / 1024.0 / 1024.0 / 1024.0
				+ "GB");

		return availableBlocks * blockSize / 1024 / 1024 / 1024;
	}

	public float getTotalInternalMemorySize() {
		File path = Environment.getDataDirectory();

		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		System.out.println("getTotalInternalMemorySize  " + path.toString()
				+ " " + totalBlocks * blockSize / 1024.0 / 1024.0 / 1024.0
				+ "GB");

		return totalBlocks * blockSize / 1024 / 1024 / 1024;
	}

	public boolean externalMemoryAvailable() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	public float getTotalExternalMemorySize() {
		if (externalMemoryAvailable()) {
			File path = Environment.getExternalStorageDirectory();

			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long totalBlocks = stat.getBlockCount();
			System.out.println("getTotalExternalMemorySize  " + path.toString()
					+ " " + totalBlocks * blockSize / 1024.0 / 1024.0 / 1024.0
					+ " " + "GB");

			return totalBlocks * blockSize / 1024 / 1024 / 1024;
		} else {
			return -1;
		}
	}

}
