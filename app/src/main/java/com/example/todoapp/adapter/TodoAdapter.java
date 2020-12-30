package com.example.todoapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.MainActivity;
import com.example.todoapp.R;
import com.example.todoapp.model.TodoModel;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private List<TodoModel>  todoList;
    private MainActivity mainActivity;

    public TodoAdapter(MainActivity activity){
        this.mainActivity = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent,  int viewType ){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder viewHolder, int position){
        TodoModel item = todoList.get(position);
        viewHolder.task.setText(item.getTask());
        viewHolder.task.setChecked(toBoolean(item.getStatus()));
    }

    private boolean toBoolean(int status){
        return status != 0;
    }

    public int getItemCount(){
        return todoList.size();
    }

    public void setTasks(List<TodoModel> toDoList){
        this.todoList = toDoList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;

        ViewHolder(View view){
            super(view);
            task = view.findViewById(R.id.checkbox);
        }
    }
}
