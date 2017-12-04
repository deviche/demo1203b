package com.example.sf.demo1203b;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sf on 2017/12/3 0003.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    public static final int VIEW_TYPE_NORMAL = 0;
    public static final int VIEW_TYPE_MORE = 1;
    private Context context;
    private ArrayList<String> datas;
    public static View more;

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MyAdapter(Context context, ArrayList<String> datas) {
        this.context = context;
        this.datas = datas;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_NORMAL) {
            View view = View.inflate(context, R.layout.item, null);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 80);
            view.setLayoutParams(layoutParams);
            final MyViewHolder viewHolder = new MyViewHolder(view);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //？
                    int pos = viewHolder.getAdapterPosition();
                    if (onItemClickListener != null){
                        onItemClickListener.onClick(pos);
                    }
                }
            });
            return viewHolder;
        } else if (viewType == VIEW_TYPE_MORE) {
            View view = View.inflate(context, R.layout.more, null);
            more= view;
            MoreViewHolder viewHolder = new MoreViewHolder(view);
            return viewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < datas.size()) {
            ((MyViewHolder) holder).textView.setText(datas.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        /*if (position<datas.size()-1)
        return VIEW_TYPE_NORMAL;
        else
            return VIEW_TYPE_MORE;*/
        int type = VIEW_TYPE_NORMAL;
        if (position == datas.size()) {
            type = VIEW_TYPE_MORE;
        }
        return type;
    }

    @Override
    public int getItemCount() {
        return datas.size() + 1;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv);
        }
    }

    class MoreViewHolder extends RecyclerView.ViewHolder {
        public MoreViewHolder(View itemView) {
            super(itemView);
        }
    }

    void addDatas(ArrayList<String> addDatas) {
        if (datas!=null){
            datas.addAll(addDatas);
        }

    }
}
