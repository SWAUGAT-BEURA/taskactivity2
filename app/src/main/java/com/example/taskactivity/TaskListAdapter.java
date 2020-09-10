package com.example.taskactivity;

import android.content.Context;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskListHolder>{

    private Context context;
    private ArrayList<Task> tasks;
    private TaskitemclickListner listener;

    public TaskListAdapter(Context context, ArrayList<Task> tasks){
        this.context=context;
        this.tasks=tasks;
    }

    public void setListener(TaskitemclickListner listener){

        this.listener=listener;
    }

    @NonNull
    @Override
    public TaskListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskListHolder(LayoutInflater.from(context).inflate(R.layout.cell_task,parent,false ));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListHolder holder, int position) {
        final Task currenttask=tasks.get(position);
        holder.mtvtasktitles.setText(currenttask.taskTitle);
        final ArrayList<Items> taskitems=Task.convertStringstoArrayList(currenttask.taskItemsStrings);

        holder.mllTaskitems.removeAllViews();
        for (final Items item:taskitems){
            View view =LayoutInflater.from(context).inflate(R.layout.cell_view_items,null);
            CheckBox mchitem= view.findViewById(R.id.ch_view_item);
            TextView mtvItem=view.findViewById(R.id.tv_view_item);


            mtvItem.setText(item.itemName);
            mchitem.setChecked(item.isItemChecked);

            if (item.isItemChecked){
                mtvItem.setPaintFlags(TextPaint.STRIKE_THRU_TEXT_FLAG);
            }

            holder.mllTaskitems.addView(view);

            mchitem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (listener!=null){
                        listener.onTaskItemClicked(item,b,currenttask);
                    }
                }
            });

            holder.mrldelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        listener.ondeleteclicked(item,currenttask);
                    }
                }
            });
            holder.mrledit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        listener.onupdateclicked(item,currenttask);
                    }

                }
            });



        }

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class TaskListHolder extends RecyclerView.ViewHolder{

        private TextView mtvtasktitles;
        private LinearLayout mllTaskitems;
        private RelativeLayout mrledit;
        private RelativeLayout mrldelete;

        public TaskListHolder(@NonNull View itemView) {
            super(itemView);

            mtvtasktitles=itemView.findViewById(R.id.tv_task_title);
            mllTaskitems=itemView.findViewById(R.id.ll_view_items);
            mrledit=itemView.findViewById(R.id.rl_edit);
            mrldelete=itemView.findViewById(R.id.rl_delete);
        }
    }

    public interface TaskitemclickListner{
        void onTaskItemClicked(Items item,boolean checkedValue,Task task);
        void onupdateclicked(Items item,Task task);
        void ondeleteclicked(Items item,Task task);
    }
}
