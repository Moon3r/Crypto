package com.example.crypto.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.crypto.R;
import com.example.crypto.bean.MenuBean;

import java.util.LinkedList;
import java.util.List;


public class SecondLevelMenuAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<MenuBean> mMenuBeanList = new LinkedList<>();

    public SecondLevelMenuAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<MenuBean> dataList) {
        mMenuBeanList = dataList;
    }

    public List<MenuBean> getData() {
        return mMenuBeanList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SecondLevelMenuViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_second_level_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        ((SecondLevelMenuViewHolder) holder).rl.setTag(mMenuBeanList.get(position).getKey());
        ((SecondLevelMenuViewHolder) holder).tv.setText(mMenuBeanList.get(position).getName());
        ((SecondLevelMenuViewHolder) holder).rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onClick(holder.getAdapterPosition());
                }
            }
        });
//        if (position == 0 && ((SecondLevelMenuViewHolder) holder).rl.getTag().equals(mMenuBeanList.get(position).getKey())) {
//            ((SecondLevelMenuViewHolder) holder).rl.setBackgroundResource(R.drawable.bg_ffffff_10_topcorners_4px_stroke_0px);
//        } else if (position == mMenuBeanList.size() - 1 && ((SecondLevelMenuViewHolder) holder).rl.getTag().equals(mMenuBeanList.get(position).getKey())) {
//            ((SecondLevelMenuViewHolder) holder).rl.setBackgroundResource(R.drawable.bg_ffffff_10_bottomcorners_4px_stroke_0px);
//        } else {
//            ((SecondLevelMenuViewHolder) holder).rl.setBackgroundResource(R.drawable.bg_ffffff_10_allcorners_0px_stroke_0px);
//        }
    }

    @Override
    public int getItemCount() {
        return mMenuBeanList.size();
    }

    class SecondLevelMenuViewHolder extends RecyclerView.ViewHolder {


        TextView tv;
        RelativeLayout rl;

        private SecondLevelMenuViewHolder(View itemView) {
            super(itemView);

            tv = itemView.findViewById(R.id.tv);
            rl = itemView.findViewById(R.id.rl);
        }
    }

    public OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
