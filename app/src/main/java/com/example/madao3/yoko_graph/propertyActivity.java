package com.example.madao3.yoko_graph;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class propertyActivity extends ActionBarActivity {

    private int taskcount;
    private int tasknumber;

    private String name;
    private String date;

    private SharedPreferences num_sp;
    private SharedPreferences name_sp;
    private SharedPreferences date_sp;
    private Editor num_editor;
    private Editor name_editor;
    private Editor date_editor;

    private EditText nameEditText;
    private EditText yearEditText;
    private EditText monthEditText;
    private EditText dateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property);

        Intent intent = getIntent();
        tasknumber = intent.getIntExtra("tasknumber", -1);

        num_sp = getSharedPreferences("num", MODE_PRIVATE);
        name_sp = getSharedPreferences("n_savedata", MODE_PRIVATE);
        date_sp = getSharedPreferences("d_savedata", MODE_PRIVATE);
        num_editor = num_sp.edit();
        name_editor = name_sp.edit();
        date_editor = date_sp.edit();

        taskcount = num_sp.getInt("tasknum", -1);
        name = name_sp.getString(String.valueOf(tasknumber), "error");
        date = date_sp.getString(name, "error");

        nameEditText = (EditText) findViewById(R.id.taskNameEdit);
        yearEditText = (EditText) findViewById(R.id.yearEditText);
        monthEditText = (EditText) findViewById(R.id.monthEditText);
        dateEditText = (EditText) findViewById(R.id.dateEditText);
        setTaskINf();

    }

    private void setTaskINf(){
        int year, month, day;
        String[] dates = date.split("/",0);
        nameEditText.setText(name);
        year = Integer.parseInt(dates[0]) - 2000;
        month = Integer.parseInt(dates[1]);
        day = Integer.parseInt(dates[2]);
        yearEditText.setText(String.valueOf(year));
        monthEditText.setText(String.valueOf(month));
        dateEditText.setText(String.valueOf(day));
    }

    public void back(View v){
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_property, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
