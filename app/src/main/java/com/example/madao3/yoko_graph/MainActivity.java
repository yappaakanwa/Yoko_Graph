package com.example.madao3.yoko_graph;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InterruptedIOException;
import java.lang.ref.SoftReference;
import java.text.DateFormat;
import java.util.Date;
import java.text.ParseException;
import java.util.zip.DataFormatException;


public class MainActivity extends ActionBarActivity {

    private TextView tasknumTextView;

    private ImageView pointer1;
    private ImageView pointer2;
    private ImageView pointer3;
    private ImageView pointer4;
    private ImageView pointer5;
    private ImageView pointer6;
    private ImageView pointer7;
    private ImageView pointer8;

    private int tasknum;

    private SharedPreferences num;
    private SharedPreferences n_sp;
    private SharedPreferences d_sp;
    private SharedPreferences m_sp;
    private SharedPreferences deadline_sp;
    private SharedPreferences checkDefault;
    private Editor num_editor;
    private Editor n_editor;
    private Editor d_editor;
    private Editor m_editor;
    private Editor dl_editor;
    private Editor cd_ediror;

    TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pointer1 = (ImageView) findViewById(R.id.pointerImageView1);
        pointer2 = (ImageView) findViewById(R.id.pointerImageView2);
        pointer3 = (ImageView) findViewById(R.id.pointerImageView3);
        pointer4 = (ImageView) findViewById(R.id.pointerImageView4);
        pointer5 = (ImageView) findViewById(R.id.pointerImageView5);
        pointer6 = (ImageView) findViewById(R.id.pointerImageView6);
        pointer7 = (ImageView) findViewById(R.id.pointerImageView7);
        pointer8 = (ImageView) findViewById(R.id.pointerImageView8);

        tasknumTextView = (TextView) findViewById(R.id.taskNumTextView);
        test = (TextView) findViewById(R.id.Test);

        num = getSharedPreferences("num", MODE_PRIVATE);
        num_editor = num.edit();
        n_sp = getSharedPreferences("n_savedata", MODE_PRIVATE);
        d_sp = getSharedPreferences("d_savedata", MODE_PRIVATE);
        m_sp = getSharedPreferences("m_savedata", MODE_PRIVATE);
        deadline_sp = getSharedPreferences("deadline", MODE_PRIVATE);
        n_editor = n_sp.edit();
        d_editor = d_sp.edit();
        m_editor = m_sp.edit();
        dl_editor = deadline_sp.edit();

        checkDefault = getSharedPreferences("checkdefault", MODE_PRIVATE);
        cd_ediror = checkDefault.edit();

        if (checkDefault.getBoolean("check", false) == false) {
            pointer2.setVisibility(View.INVISIBLE);
            pointer3.setVisibility(View.INVISIBLE);
            pointer4.setVisibility(View.INVISIBLE);
            pointer5.setVisibility(View.INVISIBLE);
            pointer6.setVisibility(View.INVISIBLE);
            pointer7.setVisibility(View.INVISIBLE);
            pointer8.setVisibility(View.INVISIBLE);

            num_editor.putInt("tasknum", 0);
            num_editor.commit();
            Toast.makeText(this, " aa ", Toast.LENGTH_SHORT).show();
            cd_ediror.putBoolean("check", true);
            cd_ediror.commit();
        }

        tasknum = num.getInt("tasknum", -1);
        tasknumTextView.setText(String.valueOf(tasknum));
        try {
            setDeadline();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        visiblePointer();
    }

    public void onClick(View v) {
        Intent intent2 = new Intent(this, infActivity.class);
        switch (v.getId()) {
            case R.id.appendButton:
                Intent intent = new Intent(this, appendActivity.class);
                startActivity(intent);
                break;
            case R.id.pointerImageView2:
                test.setText("pointer2 clicked");
                intent2.putExtra("pointerNum", 2);
                startActivity(intent2);
                break;
            case R.id.pointerImageView3:
                test.setText("pointer3 clicked");
                intent2.putExtra("pointerNum", 3);
                startActivity(intent2);
                break;
            case R.id.pointerImageView4:
                test.setText("pointer4 clicked");
                intent2.putExtra("pointerNum", 4);
                startActivity(intent2);
                break;
            case R.id.pointerImageView5:
                test.setText("pointer5 clicked");
                intent2.putExtra("pointerNum", 5);
                startActivity(intent2);
                break;
            case R.id.pointerImageView6:
                test.setText("pointer6 clicked");
                intent2.putExtra("pointerNum", 6);
                startActivity(intent2);
                break;
            case R.id.pointerImageView7:
                test.setText("pointer7 clicked");
                intent2.putExtra("pointerNum", 7);
                startActivity(intent2);
                break;
            case R.id.pointerImageView8:
                test.setText("pointer8 clicked");
                intent2.putExtra("pointerNum", 8);
                startActivity(intent2);
                break;
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
            name = n_sp.getString(String.valueOf(i),"error");
            try {
                datetime = DateFormat.getDateInstance().parse(d_sp.getString(name,"0/0/0"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            date_today = (datetime.getTime() - today.getTime()) / oneday;
            if(name != "error") test.setText(String.valueOf(date_today));
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

    public void visiblePointer() {
        int IVnumber;
        if(tasknum == 0) return;
        boolean p2 = false
                ,p3 = false
                ,p4 = false
                ,p5 = false
                ,p6 = false
                ,p7 = false
                ,p8 = false;
        for(int i = 1;i <= tasknum;i++){
            IVnumber = deadline_sp.getInt(String.valueOf(i), -1);
            test.setText(String.valueOf(IVnumber));
            switch (IVnumber){
                case -1:
                    break;
                case 2:
                    p2 = true;
                    break;
                case 3:
                    p3 = true;
                    break;
                case 4:
                    p4 = true;
                    break;
                case 5:
                    p5 = true;
                    break;
                case 6:
                    p6 = true;
                    break;
                case 7:
                    p7 = true;
                    break;
                case 8:
                    p8 = true;
                    break;
            }
        }
        if(p2) pointer2.setVisibility(View.VISIBLE); else pointer2.setVisibility(View.INVISIBLE);
        if(p3) pointer3.setVisibility(View.VISIBLE); else pointer3.setVisibility(View.INVISIBLE);
        if(p4) pointer4.setVisibility(View.VISIBLE); else pointer4.setVisibility(View.INVISIBLE);
        if(p5) pointer5.setVisibility(View.VISIBLE); else pointer5.setVisibility(View.INVISIBLE);
        if(p6) pointer6.setVisibility(View.VISIBLE); else pointer6.setVisibility(View.INVISIBLE);
        if(p7) pointer7.setVisibility(View.VISIBLE); else pointer7.setVisibility(View.INVISIBLE);
        if(p8) pointer8.setVisibility(View.VISIBLE); else pointer8.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        tasknum = num.getInt("tasknum", -1);
        tasknumTextView.setText(String.valueOf(tasknum));
        test.setText(n_sp.getString(String.valueOf(tasknum), "No Data"));
        try {
            setDeadline();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        visiblePointer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
