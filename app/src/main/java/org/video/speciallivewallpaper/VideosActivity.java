package org.video.speciallivewallpaper;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.video.speciallivewallpaper.bean.Video;

import java.util.ArrayList;

public class VideosActivity extends BaseActivity {

    private RecyclerView rv;
    private ArrayList<Video> videos;
    private int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0x13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        checkPermission();

    }

    private void initView() {

        initData();

        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new GridLayoutManager(this, 3));
        rv.setItemAnimator(new DefaultItemAnimator());
        VideosRecyclerViewAdapter videosRecyclerViewAdapter = new VideosRecyclerViewAdapter(this, this.videos);
        rv.setAdapter(videosRecyclerViewAdapter);

        videosRecyclerViewAdapter.myOnClickItemListener(new MyOnClickItem() {
            @Override
            public void onClickItem(int position) {

                if (save(VideosActivity.this.videos.get(position).getPath())) {
                    if (check()) {
                        NormalWallpaperService.setToWallPaper(VideosActivity.this);
                        currentService = "org.video.speciallivewallpaper.NormalWallpaperService";
                    } else {
                        NormalWallpaperService2.setToWallPaper(VideosActivity.this);
                        currentService = "org.video.speciallivewallpaper.NormalWallpaperService2";
                    }
                }

            }
        });


    }


    private void initData() {
        this.videos = new ArrayList();
        this.videos.addAll(VideoUtil.initData(this));
        Log.i("TAG", "----videos.size()--- " + videos.size());
    }

    private void checkPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);

        } else {

            initView();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 0x13: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    initView();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    finish();
                }
                return;
            }
            default:
                break;
        }


    }
}
