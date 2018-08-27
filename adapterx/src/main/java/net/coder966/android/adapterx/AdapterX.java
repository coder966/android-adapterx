package net.coder966.android.adapterx;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * The is a RecyclerView adapter. It introduces "Load More" feature.
 * @param <T> The type of individual items in your list
 * @param <VH> The ViewHolder class
 */
public abstract class AdapterX<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter{
    public interface OnLoadMoreListener<T>{
        void onLoadMore(T lastItem);
    }

    private static class LoadingVH extends RecyclerView.ViewHolder{
        LoadingVH(View itemView){
            super(itemView);
        }
    }

    private final int VIEW_TYPE_LOADING = -1;
    private List<T> list;
    private int prefetchThreshold = 5;
    private boolean finished = false;
    private boolean isLoading = false;
    private int loadingItemIndex = -1;
    private OnLoadMoreListener<T> loadingListener;
    @LayoutRes
    private int loadingLayout;
    private View loadingView;

    public abstract VH onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, int viewType);

    public abstract void onBindViewHolder(@NonNull VH holder, @NonNull T item);

    public int getItemViewType(T item){
        return 0;
    }

    public AdapterX(List<T> list){
        this.list = list;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener<T> listener){
        this.loadingListener = listener;
    }

    public void setLoadingView(@LayoutRes int layout) {
        loadingLayout = layout;
    }

    public void setPrefetchThreshold(int threshold){
        prefetchThreshold = threshold;
    }

    /**
     * To tell that your data has been completely loaded to the adapter (meaning there is no more data), call this with NULL argument or empty list.
     * @param newItems The list of new items to be added to the RecyclerView
     */
    public void load(List<T> newItems){
        // remove loading view
        list.remove(list.size()-1);
        notifyItemRemoved(list.size());
        isLoading = false;
        loadingItemIndex = -1;

        // add the new items
        if(newItems == null || newItems.isEmpty()){
            finished = true;
        }else{
            int oldSize = list.size();
            list.addAll(newItems);
            notifyItemRangeInserted(oldSize, newItems.size());
        }
    }


    @Override
    final public void onAttachedToRecyclerView(@NonNull RecyclerView rv) {
        super.onAttachedToRecyclerView(rv);

        // check the requirements for the "load more" feature to work
        if(loadingListener == null || loadingLayout == 0 || !(rv.getLayoutManager() instanceof LinearLayoutManager)){
            return;
        }

        loadingView = LayoutInflater.from(rv.getContext()).inflate(loadingLayout, rv, false);
        final LinearLayoutManager man = (LinearLayoutManager) rv.getLayoutManager();
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView rv, int dx, int dy) {
                super.onScrolled(rv, dx, dy);
                // (dy > 0) means scroll down
                if(dy > 0 && !finished && !isLoading && (man.findLastVisibleItemPosition() + prefetchThreshold) >= man.getItemCount()){
                    isLoading = true;
                    loadingListener.onLoadMore(list.get(list.size()-1));
                    list.add(null);
                    loadingItemIndex = list.size()-1;
                    rv.post(() -> notifyItemInserted(loadingItemIndex));
                }
            }
        });
    }

    @Override
    final public int getItemCount() {
        return list.size();
    }

    @Override
    final public int getItemViewType(int position) {
        if(position == loadingItemIndex){
            return VIEW_TYPE_LOADING;
        }else{
            return getItemViewType(list.get(position));
        }
    }

    @NonNull
    @Override
    final public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_LOADING){
            return new LoadingVH(loadingView);
        }else{
            return onCreateViewHolder(LayoutInflater.from(parent.getContext()), parent, viewType);
        }
    }

    @Override
    final public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(!(holder instanceof LoadingVH)){
            onBindViewHolder((VH) holder, list.get(position));
        }
    }

}