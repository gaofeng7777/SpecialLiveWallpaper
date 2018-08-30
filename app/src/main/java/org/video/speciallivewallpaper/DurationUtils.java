package org.video.speciallivewallpaper;

import java.util.Formatter;
import java.util.Locale;

public class DurationUtils {
    private StringBuilder mFormatBuilder = new StringBuilder();
    private Formatter mFormatter = new Formatter(this.mFormatBuilder, Locale.getDefault());

    public String stringForTime(int paramInt) {
        int i = paramInt / 1000;
        int j = i % 60;
        paramInt = i / 60 % 60;
        i /= 3600;
        this.mFormatBuilder.setLength(0);
        if (i > 0) {
            return this.mFormatter.format("%d:%02d:%02d", new Object[]{Integer.valueOf(i), Integer.valueOf(paramInt), Integer.valueOf(j)}).toString();
        }
        return this.mFormatter.format("%02d:%02d", new Object[]{Integer.valueOf(paramInt), Integer.valueOf(j)}).toString();
    }
}
