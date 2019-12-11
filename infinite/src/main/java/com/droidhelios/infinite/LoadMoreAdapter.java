package com.droidhelios.infinite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.droidhelios.infinite.listener.OnLoadMoreItems;
import com.droidhelios.infinite.util.LoadMore;

import java.util.List;

public abstract class LoadMoreAdapter extends Adapter<ViewHolder> {
    private final int layoutResource;
    private OnLoadMoreItems onLoadMoreListener;
    private boolean isLoading;
    private List mMoreList;

    private int minimumItemCount = LoadMore.DEFAULT_MINIMUM_COUNT;

    public void setMinimumItemCount(int minimumItemCount) {
        this.minimumItemCount = minimumItemCount;
    }


    public LoadMoreAdapter(List list) {
        this.layoutResource = R.layout.item_loading;
        this.mMoreList = list;
    }

    public LoadMoreAdapter(List list, int layoutResource) {
        this.mMoreList = list;
        this.layoutResource = layoutResource;
    }

    /**
     * Call this method when no longer to use.
     */
    public void stop() {
        if(isLoading){
            updateList(false);
        }
    }

    /**
     * Call this method when data fetch successfully from server.
     */
    public void finish() {
        updateList(false);
        isLoading = false;
    }


    public void setOnLoadMoreListener(OnLoadMoreItems mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return mMoreList.get(position) == null ? LoadMore.VIEW_TYPE_LOADING : LoadMore.VIEW_TYPE_ITEM;
    }

    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == LoadMore.VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(layoutResource, parent, false);
            return new LoadingViewHolder(view);
        } else {
            return this.onAbstractCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        } else {
            this.onAbstractBindViewHolder(holder, position);
        }

        if (onLoadMoreListener != null && getItemCount() >= minimumItemCount
                && !isLoading && position == mMoreList.size() - 1) {
            holder.itemView.post(new Runnable() {
                @Override
                public void run() {
                    updateList(true);
                    onLoadMoreListener.onLoadMore();
                    isLoading = true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mMoreList == null ? 0 : mMoreList.size();
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        LoadingViewHolder(View view) {
            super(view);
            progressBar = view.findViewById(R.id.progressBar1);
        }
    }

    private void updateList(boolean isAdd) {
        if(isAdd){
            mMoreList.add(null);
            notifyItemInserted(mMoreList.size() - 1);
        }else {
            mMoreList.remove(mMoreList.size() - 1);
            notifyItemRemoved(mMoreList.size());
        }
    }


    protected abstract ViewHolder onAbstractCreateViewHolder(ViewGroup parent, int viewType);

    protected abstract void onAbstractBindViewHolder(ViewHolder holder, int position);


}

//        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (linearLayoutManager != null) {
//                    totalItemCount = linearLayoutManager.getItemCount();
//                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
//                    if (!isLoading && totalItemCount >= minimumItemCount && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
//                        if (onLoadMoreListener != null && mMoreList!=null) {
//                            mMoreList.add(null);
//                            notifyItemInserted(mMoreList.size() - 1);
//                            onLoadMoreListener.onLoadMore();
//                        }
//                        isLoading = true;
//                    }
//                }
//            }
//        });