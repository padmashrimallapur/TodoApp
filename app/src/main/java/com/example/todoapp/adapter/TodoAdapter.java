package com.example.todoapp.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.AddNewTask;
import com.example.todoapp.MainActivity;
import com.example.todoapp.R;
import com.example.todoapp.model.TodoModel;
import com.example.todoapp.utils.DataBaseHandler;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private List<TodoModel>  todoList;
    private MainActivity mainActivity;
    private DataBaseHandler db;

    public TodoAdapter(DataBaseHandler dataBaseHandler, MainActivity activity){
        this.db = dataBaseHandler;
        this.mainActivity = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent,  int viewType ){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder viewHolder, int position){
        db.openDataBase();
        final TodoModel item = todoList.get(position);
        System.out.println("+++++++++++++++++>>>>>>>>>TAG"+ item.getTask());
        viewHolder.task.setText(item.getTask());
        viewHolder.task.setChecked(toBoolean(item.getStatus()));
        viewHolder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 if(isChecked){
                     db.updateStatus(item.getId(), 1) ;
                 }else{
                     db.updateStatus(item.getId(), 0);
                 }
            }
        });
    }

    private boolean toBoolean(int status){
        return status != 0;
    }

    public int getItemCount(){
        return todoList.size();
    }

    public void setTasks(List<TodoModel> toDoList){
        this.todoList = toDoList;
        notifyDataSetChanged();
    }

    public void editItem(int position){
        TodoModel task = todoList.get(position);
        Bundle bundle  = new Bundle();
        bundle.putInt("id", task.getId());
        bundle.putString("task", task.getTask());
        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(mainActivity.getSupportFragmentManager(), AddNewTask.TAG);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;

        ViewHolder(View view){
            super(view);
            task = view.findViewById(R.id.checkbox);
        }
    }


}
