package com.DataFlair.dataflairtodolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.DataFlair.dataflairtodolist.task_database.DatabaseAcess;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ListView TaskList;
    private ArrayAdapter<String> arrAdapter;
    private ArrayAdapter<String> arrayAdapterIds;
    private TextView textId, textTask;

    private List<Task> arrayTask;

    DatabaseAcess database;
    Button buttonAdd;

    public static ImageView defaultIcon, back;
    private RecyclerView SpeciesRecycler;
    private List<Task> tasksAdded;
    private SpeciesViewAdapter adapter;
    private String task;
    private SpeciesViewAdapter.ClickListenerFeature listener;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new DatabaseAcess(this, "tasks");

        setAdapter();
        buttonAdd = (Button) findViewById(R.id.addTask);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogButtonClicked(view);
            }
        });
    }

    public void showAlertDialogButtonClicked(View view) {
        database.open();
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText taskEdit = new EditText(getApplicationContext());
        builder.setTitle("Add a new task");
        builder.setMessage("What do you want to do next?");
        // add a button
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String task;
                task = String.valueOf(taskEdit.getText());
                database.insertTable(task);
                setAdapter();
            }
        });
        builder.setView(taskEdit);

        builder.setNegativeButton("Cancel", null);
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    void setAdapter() {
        setOnClickListener();
        database = new DatabaseAcess(this, "tasks");
        tasksAdded = database.returnAllTask();
        SpeciesRecycler = findViewById(R.id.speciesRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        SpeciesRecycler.setLayoutManager(layoutManager);
        SpeciesRecycler.setHasFixedSize(true);
        adapter = new SpeciesViewAdapter(this, tasksAdded, listener);
        SpeciesRecycler.setAdapter(adapter);
    }

    private void setOnClickListener() {
        listener = new SpeciesViewAdapter.ClickListenerFeature() {
            @Override
            public void onClick(View v, int position) {
                database.open();
                int positionTask;
                positionTask = tasksAdded.get(position)._id;
                database.deleteTask(positionTask);
                setAdapter();
            }
        };
    }
}
