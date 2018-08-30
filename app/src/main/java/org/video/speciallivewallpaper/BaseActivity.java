package org.video.speciallivewallpaper;

import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {
    public SharedPreferences sp;
    public SharedPreferences.Editor edit;
    public String currentService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sp = getSharedPreferences("mp4_data", 0);
        edit = sp.edit();
    }


    public boolean check() {
        int i = this.sp.getInt("num", 0);
        boolean bool = true;
        if (i != 1) {
            bool = false;
        }
        return bool;
    }

    public void updateNum(int paramInt) {
        this.edit.remove("num");
        this.edit.putInt("num", paramInt);
        this.edit.commit();
    }

    public boolean save(String paramString) {
        edit.remove("path");
        edit.putString("path", paramString);
        return edit.commit();
    }

    @Override
    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
        super.onActivityResult(paramInt1, paramInt2, paramIntent);

        if ((paramInt1 == 0x12) && (isLiveWallpaperRunning())) {
            if (check()) {
                updateNum(2);
            } else {
                updateNum(1);
            }

            Toast.makeText(this, "Set successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isLiveWallpaperRunning() {
        Object localObject = WallpaperManager.getInstance(this).getWallpaperInfo();
        if (localObject != null) {
            String str = ((WallpaperInfo) localObject).getPackageName();
            localObject = ((WallpaperInfo) localObject).getServiceName();
            if ((str.equals(getPackageName())) && (this.currentService.equals(localObject))) {
                return true;
            }
        }
        return false;
    }
}
