package org.video.speciallivewallpaper;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;

import org.video.speciallivewallpaper.bean.Video;

import java.util.ArrayList;

public class VideoUtil {
    public static ArrayList<Video> initData(Context paramContext) {
        DurationUtils localDurationUtils = new DurationUtils();
        ArrayList localArrayList = new ArrayList();
        Cursor localCursor = paramContext.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, new String[]{"_data", "_id", "title", "mime_type", "duration", "_size"}, null, null, null);
        if (localCursor.moveToFirst()) {
            do {
                long l = localCursor.getLong(localCursor.getColumnIndexOrThrow("duration"));
                if (l > 10000L) {
                    Video localVideo = new Video();
                    localVideo.setPath(localCursor.getString(localCursor.getColumnIndexOrThrow("_data")));
                    int i = localCursor.getInt(localCursor.getColumnIndexOrThrow("_id"));
                    localVideo.setDuration(localDurationUtils.stringForTime((int) l));
                    BitmapFactory.Options localOptions = new BitmapFactory.Options();
                    localOptions.inDither = false;
                    localOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    localVideo.setBitmap(MediaStore.Video.Thumbnails.getThumbnail(paramContext.getContentResolver(), i, 3, localOptions));
                    localArrayList.add(localVideo);
                }
            } while (localCursor.moveToNext());
        }
        return localArrayList;
    }
}
