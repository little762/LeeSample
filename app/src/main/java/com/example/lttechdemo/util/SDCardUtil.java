package com.example.lttechdemo.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by litong on 2017/12/1.
 */

public class SDCardUtil
{
    public static String SDCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;

    /**
     * 获取app缓存路径
     *
     */
    public static String getCachePath(Context context)
    {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable())
        {
            //外部存储可用
            cachePath = context.getExternalCacheDir().getPath();
        } else
        {
            //外部存储不可用
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }

    /**
     * 检查是否存在SDCard
     *
     * @return
     */
    public static boolean hasSdcard()
    {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获得文章图片保存路径
     *
     * @return
     */
    public static String getPictureDir(Context context)
    {
//		String imageCacheUrl = SDCardRoot + "XRichText" + File.separator;
        String imageCacheUrl = getCachePath(context);
        File file = new File(imageCacheUrl);
        if (!file.exists())
            file.mkdir();  //如果不存在则创建
        return imageCacheUrl;
    }

    /**
     * 图片保存到SD卡
     *
     * @param bitmap
     * @return
     */
    public static String saveToSdCard(Context context, Bitmap bitmap)
    {
        String imageUrl = getPictureDir(context) + File.separator + System.currentTimeMillis() + "-";
        File file = new File(imageUrl);
        try
        {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out))
            {
                out.flush();
                out.close();
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    //图片字节数组保存为本地图片
    public static String saveToSdCard(Context context, byte[] bytes)
    {
        String imageUrl = getPictureDir(context) + File.separator + System.currentTimeMillis() + ".jpg";
        File file = new File(imageUrl);
        try
        {
            FileOutputStream out = new FileOutputStream(file);
            out.write(bytes);
            out.flush();
            out.close();
        }catch (IOException e)
        {
            e.printStackTrace();
            return "";
        }
        return file.getAbsolutePath();
    }

    /**
     * 保存到指定路径，笔记中插入图片
     *
     * @param bitmap
     * @param path
     * @return
     */
    public static String saveToSdCard(Bitmap bitmap, String path)
    {
        File file = new File(path);
        try
        {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out))
            {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        //System.out.println("文件保存路径："+ file.getAbsolutePath());
        return file.getAbsolutePath();
    }

    /**
     * 根据Uri获取图片文件的绝对路径
     */
    public static String getFilePathByUri(Context context, final Uri uri)
    {
        if (null == uri)
        {
            return null;
        }
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
        {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme))
        {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme))
        {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor)
            {
                if (cursor.moveToFirst())
                {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1)
                    {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 删除文件
     **/
    public static void deleteFile(String filePath)
    {
        File file = new File(filePath);
        if (file.isFile() && file.exists())
            file.delete(); // 删除文件
    }
}
