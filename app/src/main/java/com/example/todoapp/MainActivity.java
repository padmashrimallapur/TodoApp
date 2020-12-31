package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.todoapp.adapter.TodoAdapter;
import com.example.todoapp.model.TodoModel;
import com.example.todoapp.utils.DataBaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {

    private RecyclerView taskRecyclerView;
    private TodoAdapter todoAdapter;
    private FloatingActionButton floatingActionButton;

    private List<TodoModel> todoList;
    private DataBaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        todoList = new ArrayList<>();

        db = new DataBaseHandler(this);
        db.openDataBase();

        floatingActionButton = findViewById(R.id.fab);
        taskRecyclerView = findViewById(R.id.taskRecyclerView);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        todoAdapter = new TodoAdapter(db,this);
        taskRecyclerView.setAdapter(todoAdapter);

        todoList = db.getAllTasks();
        Collections.reverse(todoList);
        todoAdapter.setTasks(todoList);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.createNewInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        todoList = db.getAllTasks();
        System.out.println("=====================>>>>>>>>>>TAG"+db.getAllTasks().size());
        Collections.reverse(todoList);
        todoAdapter.setTasks(todoList);
        todoAdapter.notifyDataSetChanged();
    }
}