/*
 * BruceHurrican
 * Copyright (c) 2016.
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 *    This document is Bruce's individual learning the android demo, wherein the use of the code from the Internet, only to use as a learning exchanges.
 *    And where any person can download and use, but not for commercial purposes.
 *    Author does not assume the resulting corresponding disputes.
 *    If you have good suggestions for the code, you can contact BurrceHurrican@foxmail.com
 *    本文件为Bruce's个人学习android的作品, 其中所用到的代码来源于互联网，仅作为学习交流使用。
 *    任和何人可以下载并使用, 但是不能用于商业用途。
 *    作者不承担由此带来的相应纠纷。
 *    如果对本代码有好的建议，可以联系BurrceHurrican@foxmail.com
 */

package bruce.kk.brucetodos;

import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bruceutils.utils.logdetails.LogDetails;

import java.text.SimpleDateFormat;
import java.util.List;

import bruce.kk.brucetodos.database.bean.UnFinishItem;

/**
 * Created by BruceHurrican on 16/11/26.
 */

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    private List<UnFinishItem> dataList;
    private CardViewItemClickListener itemClickListener;
    private CardViewItemLongClickListener itemLongClickListener;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public TodoAdapter() {
    }

    public void setDataList(List<UnFinishItem> dataList) {
        LogDetails.i("dataList->" + dataList);
        this.dataList = dataList;
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_todo_item, parent, false);
        return new TodoViewHolder(view);
    }

    public void setItemClickListener(CardViewItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public void setItemLongClickListener(CardViewItemLongClickListener listener) {
        this.itemLongClickListener = listener;
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, final int position) {
        holder.tvContent.setText(dataList.get(position).content);
        holder.tvCreateDate.setText("创建日期\n" + format.format(dataList.get(position).createDay.getTime()));
        holder.tvModifyDate.setText("修改日期\n" + format.format(dataList.get(position).modifyDay.getTime()));
        if (null != dataList.get(position).finishDay) {
            holder.tvFinishDate.setText("完成日期\n" + format.format(dataList.get(position).finishDay.getTime()));
            holder.tvContent.setPaintFlags(holder.tvContent.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//            holder.tvContent.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.tvFinishDate.setText("完成日期\n待定");
            holder.tvContent.setPaintFlags(holder.tvContent.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.itemClick(v, position);
            }
        });
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                itemLongClickListener.itemLongClick(v, position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public interface CardViewItemClickListener {
        void itemClick(View view, int position);
    }

    public interface CardViewItemLongClickListener {
        void itemLongClick(View view, int position);
    }

    public static class TodoViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public TextView tvCreateDate, tvContent, tvModifyDate, tvFinishDate;

        public TodoViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cv);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            tvCreateDate = (TextView) itemView.findViewById(R.id.tv_create_date);
            tvModifyDate = (TextView) itemView.findViewById(R.id.tv_modify_date);
            tvFinishDate = (TextView) itemView.findViewById(R.id.tv_finish_date);
        }
    }
}
