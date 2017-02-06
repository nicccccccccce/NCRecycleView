package com.sino.bridge.ncrecycleview;

import android.app.Activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private RecyclerView recyclerView;

    List mDatas = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.video_unit_recycle);
      final  VideoUnitAdapter videoUnitAdapter = new VideoUnitAdapter(this, initData());
        videoUnitAdapter.setIsLoadData(true);
        videoUnitAdapter.setOnLoadMoreListener(new VideoUnitAdapter.OnLoadMoreListener() {
            @Override
            public void OnLoadMore() {
                addData();
            }
        });

//        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,3);
//        manager.setOrientation(GridLayoutManager.HORIZONTAL);
//        manager.setSmoothScrollbarEnabled(true);
//        recyclerView.setLayoutManager(manager);

        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);

//        FullyStaggeredGridLayoutManager exManager=new FullyStaggeredGridLayoutManager(2, FullyStaggeredGridLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(exManager);
        recyclerView.addItemDecoration(new SpaceItemDecoration(5, SpaceItemDecoration.VERTICAL));//new SpaceItemDecoration(5,5)
        recyclerView.setAdapter(videoUnitAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());//DefaultItemAnimator
        recyclerView.setNestedScrollingEnabled(false);

    }

    private List<String> initData() {

        for (int i = 0; i < 15; i++) {
            mDatas.add("" + i);
        }
        return mDatas;
    }

    private List<String> addData() {

        for (int i = 15; i >= 0; i--) {
            mDatas.add("" + i);
        }
        return mDatas;
    }

    /**
     * Bitmap转化为drawable
     *
     * @param bitmap
     * @return
     */
    public static Drawable bitmap2Drawable(Bitmap bitmap) {
        return new BitmapDrawable(bitmap);
    }

    /**
     * Drawable 转 bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                    : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }

    public static Canvas drawable2Canvas(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return new Canvas(((BitmapDrawable) drawable).getBitmap());
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                    : Bitmap.Config.RGB_565);
            return new Canvas(bitmap);


        } else {
            return null;
        }
    }
}
