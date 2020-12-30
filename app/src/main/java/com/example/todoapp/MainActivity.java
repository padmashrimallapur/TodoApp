package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.todoapp.adapter.TodoAdapter;
import com.example.todoapp.model.TodoModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView taskRecyclerView;
    private TodoAdapter todoAdapter;

    private List<TodoModel> todoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        todoList = new ArrayList<>();

        taskRecyclerView = findViewById(R.id.taskRecyclerView);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        todoAdapter = new TodoAdapter(this);
        taskRecyclerView.setAdapter(todoAdapter);

        TodoModel todoModel = new TodoModel();
        todoModel.setTask("Test Task");
        todoModel.setStatus(0);
        todoModel.setId(1);

        todoList.add(todoModel);
        todoList.add(todoModel);
        todoList.add(todoModel);
        todoList.add(todoModel);


        todoAdapter.setTasks(todoList);
    }
}