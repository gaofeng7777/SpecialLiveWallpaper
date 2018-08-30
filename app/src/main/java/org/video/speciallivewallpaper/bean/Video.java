package org.video.speciallivewallpaper.bean;

import android.graphics.Bitmap;

public class Video {
    private Bitmap bitmap;
    private String duration;
    private String path;

    public Video() {
    }

    public Video(String paramString1, Bitmap paramBitmap, String paramString2) {
        this.path = paramString1;
        this.bitmap = paramBitmap;
        this.duration = paramString2;
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public String getDuration() {
        return this.duration;
    }

    public String getPath() {
        return this.path;
    }

    public void setBitmap(Bitmap paramBitmap) {
        this.bitmap = paramBitmap;
    }

    public void setDuration(String paramString) {
        this.duration = paramString;
    }

    public void setPath(String paramString) {
        this.path = paramString;
    }
}
