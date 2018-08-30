package org.video.speciallivewallpaper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boot);

        findViewById(R.id.mp4_7).setOnClickListener(this);
        findViewById(R.id.mp4_8).setOnClickListener(this);
        findViewById(R.id.mp4_9).setOnClickListener(this);
        findViewById(R.id.videos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,VideosActivity.class));
            }
        });

   }

    @Override
    public void onClick(View view) {
        String path;
        switch (view.getId()) {
            case R.id.mp4_7:
                path = "a.mp4";
                break;
            case R.id.mp4_8:
                path = "b.mp4";
                break;
            case R.id.mp4_9:
                path = "c.mp4";
                break;
            default:
                path = "a.mp4";
                break;
        }

        if ((view.getId() != 0) && (save(path))) {
            if (check()) {
                NormalWallpaperService.setToWallPaper(this);
                currentService = "org.video.speciallivewallpaper.NormalWallpaperService";
            } else {
                NormalWallpaperService2.setToWallPaper(this);
                currentService = "org.video.speciallivewallpaper.NormalWallpaperService2";
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
