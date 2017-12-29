package com.example.jszx.myapplication.Note;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jszx.myapplication.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王志杰 on 2017/12/10.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private List<Content> contents;
    public NoteAdapter(List<Content> contentList)
    {
        this.contents=contentList;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public ViewHolder(View view)
        {
            super(view);
            textView=(TextView) view.findViewById(R.id.note_item);
        }
    }
    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Content content=contents.get(position);
        holder.textView.setText(content.getTitleText());
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false);
        final ViewHolder viewHolder=new ViewHolder(view);

        //编辑笔记
        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=viewHolder.getAdapterPosition();
                Intent intent=new Intent(parent.getContext(),MainActivity.class);
                intent.putExtra("actual_position",position);
                intent.putExtra("note_position",contents.get(position).getPosition());
                intent.putExtra("state",1);
                parent.getContext().startActivity(intent);
            }
        });

        //长按选择删除
        viewHolder.textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(parent.getContext());
                builder.setTitle("删除该笔记");
                builder.setMessage("确定删除吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int position=viewHolder.getAdapterPosition();
                        DataSupport.delete(Content.class,contents.get(position).getId());
                        contents.remove(position);
                        notifyItemRemoved(position);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.create();
                builder.show();
                return true;
            }
        });
        return viewHolder;
    }
    public void add(Content content,int position)
    {
        if (contents==null)
        {
            contents=new ArrayList<>();
        }
        contents.add(content);
        notifyItemInserted(position);
    }

}
