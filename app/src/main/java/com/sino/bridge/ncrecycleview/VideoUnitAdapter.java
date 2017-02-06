package com.sino.bridge.ncrecycleview;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

/**
 * Created by user on 2016/11/1.
 */
public class VideoUnitAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    private int currentIndex = -1;
    private int maxSize = 15;
    private boolean isLoadData = false;
    private OnLoadMoreListener mOnLoadMoreListener;

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public void setIsLoadData(boolean isLoadData) {
        this.isLoadData = isLoadData;
    }

    public VideoUnitAdapter(Context context, List<String> datas) {
        this.mContext = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemCount() {
        if (isLoadData)
            if (mDatas.size() >= maxSize)
                return mDatas.size() + 1;
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
//        if(getItemCount()==position-1)
        return position;
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isLoadData) {
            if (mDatas.size() >= maxSize)
                if (viewType == getItemCount() - 1) {
                    return new MyBottomViewHolder(new MyBottomView(parent.getContext()));
                }
        }
        View view = inflater.inflate(R.layout.adapter_xisha_video_unit_item, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.v.setMinimumHeight(100 + (position % 3) * 30);
            if (currentIndex == position) {
                myViewHolder.ivPlaying.setVisibility(View.VISIBLE);
                myViewHolder.tvNormal.setVisibility(View.GONE);
            } else {
                myViewHolder.ivPlaying.setVisibility(View.GONE);
                myViewHolder.tvNormal.setVisibility(View.VISIBLE);
                myViewHolder.tvNormal.setText(mDatas.get(position));
            }
        } else if (holder instanceof MyBottomViewHolder) {
            if (mOnLoadMoreListener != null)
                mOnLoadMoreListener.OnLoadMore();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    VideoUnitAdapter.this.notifyItemRemoved(position);
                }
            }, 3000);

        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvNormal;
        ImageView ivPlaying;
        View v;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            tvNormal = (TextView) view.findViewById(R.id.normal_tv);
            ivPlaying = (ImageView) view.findViewById(R.id.playing_iv);
            v = view.findViewById(R.id.v);
        }

        @Override
        public void onClick(View v) {
            currentIndex = getAdapterPosition();
//            notifyDataSetChanged();
            if (mDatas.size() > 0 && currentIndex != getItemCount() - 1 && currentIndex != -1) {
                mDatas.remove(currentIndex);
                notifyItemRemoved(currentIndex);
            }

        }
    }

    class MyBottomViewHolder extends RecyclerView.ViewHolder {


        public MyBottomViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnLoadMoreListener {
        void OnLoadMore();
    }
}
