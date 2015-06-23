package com.example.madao3.yoko_graph;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.DatePicker;
import android.widget.Toast;


public class appendActivity extends ActionBarActivity {

    private SharedPreferences num;
    private SharedPreferences n_sp;
    private SharedPreferences d_sp;
    private SharedPreferences m_sp;
    private SharedPreferences num_editor;
    private SharedPreferences.Editor n_editor;
    private SharedPreferences.Editor d_editor;
    private SharedPreferences.Editor m_editor;

    private EditText tasknameEditText;
    private EditText memoEditText;

    private int year;
    private int month;
    private int dateofmonth;
    private String date;
    private String name;
    private String memo;

    private DatePicker inputDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_append);
        inputDate = (DatePicker) findViewById(R.id.inputdatePicker);
        EditText tasknameEditText = (EditText) findViewById(R.id.taskNameEditText);
        EditText memoEditText = (EditText) findViewById(R.id.memoEditText);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.appendButton:
                year = inputDate.getYear();
                month = inputDate.getMonth() + 1;
                if(month == 13){
                    year++;
                    month = 1;
                }
                dateofmonth = inputDate.getDayOfMonth();
                date = String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(dateofmonth);
                name = tasknameEditText.getText().toString();
                memo = memoEditText.getText().toString();
                break;
        }
    }

    public void writeTasks(){
        n_sp = getSharedPreferences("n_savedata", MODE_PRIVATE);
        d_sp = getSharedPreferences("d_savedate", MODE_PRIVATE);
        m_sp = getSharedPreferences("m_savedate", MODE_PRIVATE);
        n_editor = n_sp.edit();
        d_editor = d_sp.edit();
        m_editor = m_sp.edit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_append, menu);
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
