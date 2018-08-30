package org.video.speciallivewallpaper;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;

public class NormalWallpaperService2 extends WallpaperService {
    public static final String INTENT_FLTER = "org.video.speciallivewallpaper";
    public static final String KEY_ACTION = "action";
    public static final int NO_SOUND = 100;
    public static final int YES_SOUND = 101;
    private static final String TAG = "TAG";
    private MyWallpaperEngine myWallpaperEngine;
    private AssetFileDescriptor assetFileDescriptor;

    public static void setToWallPaper(Activity paramActivity) {
        Intent localIntent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        localIntent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, new ComponentName(paramActivity, NormalWallpaperService2.class));
        paramActivity.startActivityForResult(localIntent, 0x12);
    }

    @Override
    public Engine onCreateEngine() {
        Log.i(TAG, "onCreateEngine: ----");
        myWallpaperEngine = new MyWallpaperEngine();
        return myWallpaperEngine;
    }

    public static void noSound(Context context) {
        Intent intent = new Intent(INTENT_FLTER);
        intent.putExtra(KEY_ACTION, NO_SOUND);
        context.sendBroadcast(intent);
    }

    public static void yesSound(Context context) {
        Intent intent = new Intent(INTENT_FLTER);
        intent.putExtra(KEY_ACTION, YES_SOUND);
        context.sendBroadcast(intent);
    }

    public void item(Context context, int item) {
        Intent intent = new Intent(INTENT_FLTER);
        intent.putExtra(KEY_ACTION, item);
        context.sendBroadcast(intent);
    }

    private class MyWallpaperEngine extends Engine {
        private MediaPlayer mediaPlayer;
        private BroadcastReceiver broadcastReceiver;

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            Log.i(TAG, "onSurfaceCreated: ----");
            super.onSurfaceCreated(holder);

            mediaPlayer = new MediaPlayer();

            mediaPlayer.setSurface(holder.getSurface());

            try {
                AssetManager assets = getApplicationContext().getAssets();

                SharedPreferences mp4_data = getSharedPreferences("mp4_data", 0);
                String data = mp4_data.getString("path", "a.mp4");
                int length = data.length();

                if (length != 0 && length > 5) {
                    mediaPlayer.setDataSource(data);
                } else {
                    assetFileDescriptor = assets.openFd(data);
                    mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
                }

                mediaPlayer.setLooping(true);

                int anInt = getSharedPreferences("soundType", 0).getInt("type", 100);
                if (anInt == 100) {
                    mediaPlayer.setVolume(0, 0);
                } else if (anInt == 101) {
                    mediaPlayer.setVolume(1.0f, 1.0f);
                }
                mediaPlayer.prepare();
                mediaPlayer.start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            if (visible) {
                mediaPlayer.start();
            } else {
                mediaPlayer.pause();
            }
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            Log.i(TAG, "onSurfaceDestroyed: ----");
            super.onSurfaceDestroyed(holder);

            mediaPlayer.release();
            mediaPlayer = null;
        }


        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            Log.i(TAG, "onCreate: ----");
            super.onCreate(surfaceHolder);

            IntentFilter intentFilter = new IntentFilter(INTENT_FLTER);

            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    int intExtra = intent.getIntExtra(KEY_ACTION, NO_SOUND);

                    getSharedPreferences("soundType", 0).edit().putInt("type", intExtra).commit();

                    switch (intExtra) {
                        case NO_SOUND:
                            mediaPlayer.setVolume(0, 0);
                            break;
                        case YES_SOUND:
                            mediaPlayer.setVolume(1.0f, 1.0f);
                            break;

                        default:
                            break;
                    }
                }
            };

            registerReceiver(broadcastReceiver, intentFilter);
        }

        @Override
        public void onDestroy() {
            Log.i(TAG, "onDestroy: ----");
            if (broadcastReceiver != null) {
                unregisterReceiver(broadcastReceiver);
            }
            super.onDestroy();
        }
    }
}
