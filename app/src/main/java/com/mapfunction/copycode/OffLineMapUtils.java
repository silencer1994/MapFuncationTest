package com.mapfunction.copycode;

import android.content.Context;
import android.os.Environment;
import android.util.Log;


public class OffLineMapUtils {
	/**
	 * 获取map 缓存和读取目录
	 */
	public static String getSdCacheDir(Context context) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			java.io.File fExternalStorageDirectory = Environment
					.getExternalStorageDirectory();
			java.io.File dir = new java.io.File(
					fExternalStorageDirectory, "leadormap");
			boolean result = false;
			if (!dir.exists()) {
				result = dir.mkdir();
			}
			java.io.File leadormap = new java.io.File(dir,
					"offlineMap");
			if (!leadormap.exists()) {
				result = leadormap.mkdir();
			}
			Log.e("offline",leadormap.toString() + "/");
			return leadormap.toString() + "/";
		} else {
			return "";
		}
	}
}
