package com.example.taskactivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class TaskViewActivity extends AppCompatActivity implements TaskListAdapter.TaskitemclickListner {

    private RecyclerView mRcTasks;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        mRcTasks=findViewById(R.id.rc_task);
        mRcTasks.setLayoutManager(new GridLayoutManager(TaskViewActivity.this,2));

        dbHelper=new DBHelper(TaskViewActivity.this);

        getDataFromDatabase();
    }

    public void onaddnewtaskclicked(View view){
        startActivityForResult(new Intent(TaskViewActivity.this,AddTaskActivity.class),1000);

    }
    private void getDataFromDatabase(){
        ArrayList<Task> tasks = dbHelper.getTaskfromdb(dbHelper.getReadableDatabase());
        TaskListAdapter adapter=new TaskListAdapter(TaskViewActivity.this,tasks);
        adapter.setListener(this);
        mRcTasks.setAdapter(adapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1000 && resultCode== Activity.RESULT_OK){
            getDataFromDatabase();
        }
    }

    @Override
    public void onTaskItemClicked(Items item, boolean checkedValue, Task task) {
        item.isItemChecked=checkedValue;

        ArrayList<Items>  taskItems=Task.convertStringstoArrayList(task.taskItemsStrings);
        for (Items existingitem:taskItems){
            if (existingitem.itemID==item.itemID){
                existingitem.isItemChecked=checkedValue;

            }
        }
        String editedtaskitems=Task.convertArrayListToJSONArrayString(taskItems);

        Task editedTask=new Task();
        editedTask.taskID=task.taskID;
        editedTask.taskTitle=task.taskTitle;
        editedTask.taskItemsStrings=editedtaskitems;

        dbHelper.updateDataToDatabase(dbHelper.getWritableDatabase(),editedTask);
        getDataFromDatabase();
    }

    @Override
    public void onupdateclicked(Items item, Task task) {
        Intent updateintent=new Intent(TaskViewActivity.this,AddTaskActivity.class);
        updateintent.putExtra("ITEM",task);
        startActivityForResult(updateintent,1000);
    }






    @Override
    public void ondeleteclicked(Items item, Task task) {
        dbHelper.deletedatafromdatabase(dbHelper.getWritableDatabase(),task);
        getDataFromDatabase();

    }


}
