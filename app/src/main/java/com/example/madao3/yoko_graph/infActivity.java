package com.example.madao3.yoko_graph;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.TextView;
import android.view.Gravity;
import android.graphics.Color;
import android.widget.Toast;

import junit.framework.Test;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;


public class infActivity extends ActionBarActivity {

    private int pointerNum;
    private int tasknum;

    private ScrollView taskScroll;
    private LinearLayout taskLayout;
    private LinearLayout ll;
    LinearLayout.LayoutParams lp;

    private SharedPreferences num_sp;
    private SharedPreferences name_sp;
    private SharedPreferences date_sp;
    private SharedPreferences deadline_sp;
    private Editor dl_editor;

    Intent intent;
    TextView[] tasks;

    TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf);

        intent = getIntent();
        pointerNum = intent.getIntExtra("pointerNum",0);

        taskScroll = (ScrollView) findViewById(R.id.taskScrollView);
        taskLayout = new LinearLayout(this);
        taskLayout.setOrientation(LinearLayout.VERTICAL);
        taskLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        taskScroll.addView(taskLayout);
        ll = new LinearLayout(this);
        lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 5, 0, 5);

        num_sp = getSharedPreferences("num", MODE_PRIVATE);
        name_sp = getSharedPreferences("n_savedata", MODE_PRIVATE);
        date_sp = getSharedPreferences("d_savedata", MODE_PRIVATE);
        deadline_sp = getSharedPreferences("deadline", MODE_PRIVATE);
        dl_editor = deadline_sp.edit();

        tasknum = num_sp.getInt("tasknum", -1);

        setScrollView();
        test = (TextView) findViewById(R.id.textView1);
    }

    public void setScrollView(){
        int count = 0;
        tasks = new TextView[tasknum+1];
        for(int i = 1;i<tasknum+1;i++) {
            if(deadline_sp.getInt(String.valueOf(i), -1) == pointerNum) {
                String name = name_sp.getString(String.valueOf(i), "error");
                String date = date_sp.getString(name, "error");
                tasks[i] = new TextView(this);
                tasks[i].setText("タスク名：" + name + "\n" + "期限：" + date);
                tasks[i].setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                tasks[i].setGravity(Gravity.CENTER);
                tasks[i].setPadding(0, 10, 0, 10);
                tasks[i].setBackgroundColor(Color.RED);
                tasks[i].setLayoutParams(lp);
                tasks[i].setClickable(true);
                tasks[i].setTag(new Integer(i));
                tasks[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toProperty((Integer)v.getTag());
                    }
                });
                taskLayout.addView(tasks[i]);
                count++;
            }
        }
        if(count == 0){
            finish();
        }
    }

    public void toProperty(int num){
        if(num != -1) {
            Intent intent3 = new Intent(this, propertyActivity.class);
            intent3.putExtra("tasknumber", num);
            startActivity(intent3);
        }else{
            Toast.makeText(this,"error",Toast.LENGTH_SHORT).show();
        }
    }

    public void setDeadline() throws ParseException {
        if(tasknum == 0) return;
        String name;
        Date datetime = new Date();
        Date today = new Date();
        long date_today;
        long oneday = 1000 * 60 * 60;
        for(int i = 1;i <= tasknum;i++){
            name = name_sp.getString(String.valueOf(i),"error");
            try {
                datetime = DateFormat.getDateInstance().parse(date_sp.getString(name,"0/0/0"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            date_today = (datetime.getTime() - today.getTime()) / oneday;
            if(date_today <= 24) dl_editor.putInt(String.valueOf(i), 2);
            else if(date_today > 24 && date_today <= 72) dl_editor.putInt(String.valueOf(i), 3);
            else if(date_today > 72 && date_today <= (24*7)) dl_editor.putInt(String.valueOf(i), 4);
            else if(date_today > (24*7) && date_today <= (24*10)) dl_editor.putInt(String.valueOf(i), 5);
            else if(date_today > (24*10) && date_today <= (24*14)) dl_editor.putInt(String.valueOf(i), 6);
            else if(date_today > (24*14) && date_today <= (24*21)) dl_editor.putInt(String.valueOf(i), 7);
            else if(date_today > (24*21)) dl_editor.putInt(String.valueOf(i), 8);

            dl_editor.commit();

        }
    }

    public void back(View v){
                finish();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        taskLayout.removeAllViews();
        try {
            setDeadline();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        setScrollView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inf, menu);
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
