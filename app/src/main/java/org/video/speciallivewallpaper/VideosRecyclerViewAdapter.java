package org.video.speciallivewallpaper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.video.speciallivewallpaper.bean.Video;

import java.util.ArrayList;


public class VideosRecyclerViewAdapter extends RecyclerView.Adapter<VideosViewHolder> {
    private Context context;
    private ArrayList<Video> videos;
    private MyOnClickItem myOnClickItem;

    public VideosRecyclerViewAdapter(Context context, ArrayList<Video> videos) {
        this.context = context;
        this.videos = videos;
    }


    public void myOnClickItemListener(MyOnClickItem onClickItem) {

        this.myOnClickItem = onClickItem;
    }

    @Override
    public VideosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideosViewHolder(LayoutInflater.from(context).inflate(R.layout.rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final VideosViewHolder holder, final int position) {
        holder.iv.setImageBitmap(videos.get(position).getBitmap());
        holder.tv.setText(videos.get(position).getDuration());

        if (myOnClickItem != null) {
            //图片点击事件
            holder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myOnClickItem.onClickItem(holder.getLayoutPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (videos == null) {
            return 0;
        }

        return videos.size();
    }
}

class VideosViewHolder extends RecyclerView.ViewHolder {
    public TextView tv;
    public ImageView iv;

    public VideosViewHolder(View itemView) {
        super(itemView);
        iv = itemView.findViewById(R.id.iv);
        tv = itemView.findViewById(R.id.tv);
    }

}
