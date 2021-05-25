package com.DataFlair.dataflairtodolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

        //TaskList = (ListView) findViewById(R.id.list_todo);

        //updateUI();
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
        // setup the alert builder
        final DatabaseAcess databaseAcess = DatabaseAcess.getInstance(getApplicationContext(), "tasks");
        databaseAcess.open();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText taskEdit = new EditText(getApplicationContext());
        builder.setTitle("Add a new task");
        builder.setMessage("What do you want to do next?");
        // add a button
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Task task = new Task();
                task.task = String.valueOf(taskEdit.getText());
                database = new DatabaseAcess(getApplicationContext(), "tasks");
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
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    void setAdapter() {
        //setOnClickListener();
        database = new DatabaseAcess(this, "tasks");
        tasksAdded = database.returnAllTask();
        SpeciesRecycler = findViewById(R.id.speciesRecycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        SpeciesRecycler.setLayoutManager(gridLayoutManager);
        SpeciesRecycler.setHasFixedSize(true);
        adapter = new SpeciesViewAdapter(this, tasksAdded, listener);
        SpeciesRecycler.setAdapter(adapter);
    }

//    private void setOnClickListener() {
//        listener = (v, position) -> {
//            Intent intent = new Intent(getApplicationContext(), InfAdicionais.class);
//            intent.putExtra("selected_specie", speciesAdded.get(position));
//            Log.d("Name", speciesAdded.get(position).identificacao);
//            startActivity(intent);
//        };
//    }

//    public void deleteTask(View view) {
//        View parent = (View) view.getParent();
//        TextView taskTextView = (TextView) parent.findViewById(R.id.title_task);
//        String task = String.valueOf(taskTextView.getText());
//        SQLiteDatabase db = taskHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task);
//        db.delete(TaskContract.TaskEntry.TABLE, TaskContract.TaskEntry.COL_TASK_TITLE + " = ?", new String[]{task});
//        db.close();
//        setAdapter();
//        //updateUI();
//    }

//    private void updateUI() {
//        ArrayList<String> taskList = new ArrayList<>();
//        SQLiteDatabase db = taskHelper.getReadableDatabase();
//        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
//                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE},
//                null, null, null, null, null);
//        while (cursor.moveToNext()) {
//            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
//            taskList.add(cursor.getString(idx));
//        }
//
//        if (arrAdapter == null) {
//            arrAdapter = new ArrayAdapter<>(this, R.layout.todo_task, R.id.title_task, taskList);
//            TaskList.setAdapter(arrAdapter);
//        } else {
//            arrAdapter.clear();
//            arrAdapter.addAll(taskList);
//            arrAdapter.notifyDataSetChanged();
//        }
//
//        cursor.close();
//        db.close();
//    }
}
