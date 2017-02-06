package com.sino.bridge.ncrecycleview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by eve on 2016/7/6 0006.
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    public static final int NONE = 0;
    public static final int VERTICAL = 1;
    public static final int HORIZONTAL = 2;
    private int space;
    private int type;

    public SpaceItemDecoration(int space, int type) {
        this.space = space;
        this.type = type;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //不是第一个的格子都设一个左边和底部的间距
        if (type == NONE) {
            outRect.left = space;
            outRect.bottom = space;
            outRect.top = space;
            outRect.right = space;
        } else if (type == VERTICAL) {
            outRect.bottom = space;
        } else if (type == HORIZONTAL) {
            outRect.left = space;
        }

    }

}
