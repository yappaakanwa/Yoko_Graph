package com.example.madao3.yoko_graph;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
    private Editor num_editor;
    private Editor n_editor;
    private Editor d_editor;

    private int year;
    private int month;
    private int dateofmonth;
    private int tasknum;
    private String date;
    private String name;

    private DatePicker inputDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_append);
        inputDate = (DatePicker) findViewById(R.id.inputdatePicker);
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.appendButton:
                final EditText tasknameEditText = (EditText) findViewById(R.id.taskNameEditText);

                d_sp = getSharedPreferences("d_savedata", MODE_PRIVATE);

                year = inputDate.getYear();
                month = inputDate.getMonth() + 1;
                if(month == 13){
                    year++;
                    month = 1;
                }
                dateofmonth = inputDate.getDayOfMonth();
                date = String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(dateofmonth);
                name = tasknameEditText.getText().toString();
                if(d_sp.getString(name,"already") == "already") {
                    writeTasks();
                    finish();
                }else{
                    Toast.makeText(this, "既に登録されているタスクです",Toast.LENGTH_SHORT).show();
                }
                    break;
            case R.id.cancelButton:
                finish();
                break;
        }
    }

    public void writeTasks(){
        num = getSharedPreferences("num", MODE_PRIVATE);
        num_editor = num.edit();
        n_sp = getSharedPreferences("n_savedata", MODE_PRIVATE);
        d_sp = getSharedPreferences("d_savedata", MODE_PRIVATE);
        n_editor = n_sp.edit();
        d_editor = d_sp.edit();

        tasknum = num.getInt("tasknum", -1);
        tasknum++;
        num_editor.putInt("tasknum", tasknum);
        num_editor.commit();

        n_editor.putString(String.valueOf(tasknum), name);
        d_editor.putString(name, date);
        n_editor.commit();
        d_editor.commit();
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
