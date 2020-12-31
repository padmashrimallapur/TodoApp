package com.example.todoapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.todoapp.model.TodoModel;
import com.example.todoapp.utils.DataBaseHandler;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;

public class AddNewTask extends BottomSheetDialogFragment {

    public static final String TAG = "ActionButtonDialog";

    private EditText newTaskText;
    private Button  newTaskSaveButton;
    public DataBaseHandler db;

    public static AddNewTask createNewInstance(){
        return new AddNewTask();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_task, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newTaskText = Objects.requireNonNull(getView()).findViewById(R.id.newTask);
        newTaskSaveButton = view.findViewById(R.id.newTaskButton);

        db = new DataBaseHandler(getActivity());
        db.openDataBase();

        boolean isUpdate = false;
        final Bundle bundle = getArguments();
        if(bundle != null) {
            isUpdate = true;
            String task = bundle.getString("task");
            newTaskText.setText(task);
            if (task.length() > 0) {
                newTaskSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
            }
        }

            newTaskText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    //No implementation
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(s.toString().equals("")){
                            newTaskSaveButton.setEnabled(false);
                            newTaskSaveButton.setTextColor(Color.GRAY);
                        }else{
                            newTaskSaveButton.setEnabled(true);
                            newTaskSaveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark));
                        }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    //No Implementation
                }
            });

            final boolean finalIsUpdate = isUpdate;
            newTaskSaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String taskName = newTaskText.getText().toString();
                    if (finalIsUpdate) {
                        db.updateTask(bundle.getInt("id"), taskName);
                    } else {
                        TodoModel task = new TodoModel();
                        System.out.println("+++++++++++++TAG++++++++"+taskName);
                        task.setTask(taskName);
                        task.setStatus(0);
                        db.insertTask(task);
                    }

                    dismiss();
                }
            });
        }


    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        Activity activity = getActivity();
        if(activity instanceof  DialogCloseListener){
            ((DialogCloseListener)activity).handleDialogClose(dialog);
        }
    }
}
