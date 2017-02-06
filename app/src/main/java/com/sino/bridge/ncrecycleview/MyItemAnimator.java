package com.sino.bridge.ncrecycleview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.support.v4.animation.AnimatorCompatHelper;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/12/28.
 */
public class MyItemAnimator extends SimpleItemAnimator {
    ArrayList<RecyclerView.ViewHolder> mRemoveAnimations = new ArrayList<>();
    private ArrayList<RecyclerView.ViewHolder> mPendingRemovals = new ArrayList<>();

    @Override
    public boolean animateRemove(RecyclerView.ViewHolder holder) {
        resetAnimation(holder);
        mPendingRemovals.add(holder);
        return true;
    }

    private void resetAnimation(RecyclerView.ViewHolder holder) {
        AnimatorCompatHelper.clearInterpolator(holder.itemView);
        endAnimation(holder);
    }

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder holder) {
        return false;
    }

    @Override
    public boolean animateMove(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        return false;
    }

    @Override
    public boolean animateChange(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder, int fromLeft, int fromTop, int toLeft, int toTop) {
        return false;
    }

    @Override
    public void runPendingAnimations() {
        // First, remove stuff
        for (RecyclerView.ViewHolder holder : mPendingRemovals) {
            animateRemoveImpl(holder);
        }
        mPendingRemovals.clear();
    }

    @Override
    public void endAnimation(RecyclerView.ViewHolder item) {
        if (mPendingRemovals.remove(item)) {
            dispatchRemoveFinished(item);
        }
    }

    @Override
    public void endAnimations() {
        int count = mPendingRemovals.size();
        for (int i = count - 1; i >= 0; i--) {
            RecyclerView.ViewHolder item = mPendingRemovals.get(i);
            dispatchRemoveFinished(item);
            mPendingRemovals.remove(i);
        }
        cancelAll(mRemoveAnimations);
        dispatchAnimationsFinished();
    }
    void cancelAll(List<RecyclerView.ViewHolder> viewHolders) {
        for (int i = viewHolders.size() - 1; i >= 0; i--) {
            ViewCompat.animate(viewHolders.get(i).itemView).cancel();
        }
    }
    private void animateRemoveImpl(final RecyclerView.ViewHolder holder) {
        final View view = holder.itemView;
        final ObjectAnimator visToInvis = ObjectAnimator.ofFloat(view, "rotationY", 0f, 180f);
        visToInvis.setDuration(getRemoveDuration());
        visToInvis.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                dispatchRemoveStarting(holder);
            }

            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                visToInvis.removeListener(this);
                ObjectAnimator.ofFloat(view, "rotationY", 180f, 0f).setDuration(0).start();
                dispatchRemoveFinished(holder);
                mRemoveAnimations.remove(holder);
                dispatchFinishedWhenDone();
            }
        });
        visToInvis.start();
        mRemoveAnimations.add(holder);
    }

    /**
     * Check the state of currently pending and running animations. If there are none
     * pending/running, call {@link #dispatchAnimationsFinished()} to notify any
     * listeners.
     */
    void dispatchFinishedWhenDone() {
        if (!isRunning()) {
            dispatchAnimationsFinished();
        }
    }

    @Override
    public boolean isRunning() {
        return !mRemoveAnimations.isEmpty() || !mPendingRemovals.isEmpty();
    }
}
