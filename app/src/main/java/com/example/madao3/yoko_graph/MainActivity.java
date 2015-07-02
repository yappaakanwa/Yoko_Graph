package com.example.madao3.yoko_graph;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.ImageView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.InterruptedIOException;
import java.lang.ref.SoftReference;
import java.text.DateFormat;
import java.util.Date;
import java.text.ParseException;
import java.util.zip.DataFormatException;


public class MainActivity extends ActionBarActivity {

    private TextView tasknumTextView;
    private TextView textView;

    private boolean p[] = new boolean[9];

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
    private SharedPreferences deadline_sp;
    private SharedPreferences checkDefault;
    private Editor num_editor;
    private Editor n_editor;
    private Editor d_editor;
    private Editor dl_editor;
    private Editor cd_ediror;

    private ScrollView taskScroll;
    private LinearLayout taskLayout;
    private LinearLayout ll;
    private LinearLayout.LayoutParams lp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        defineAll();

        try {
            setDeadline();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        visiblePointer();
        setScrollView();
    }

    //宣言文
    private void defineAll(){
        pointer1 = (ImageView) findViewById(R.id.pointerImageView1);
        pointer2 = (ImageView) findViewById(R.id.pointerImageView2);
        pointer3 = (ImageView) findViewById(R.id.pointerImageView3);
        pointer4 = (ImageView) findViewById(R.id.pointerImageView4);
        pointer5 = (ImageView) findViewById(R.id.pointerImageView5);
        pointer6 = (ImageView) findViewById(R.id.pointerImageView6);
        pointer7 = (ImageView) findViewById(R.id.pointerImageView7);
        pointer8 = (ImageView) findViewById(R.id.pointerImageView8);

        tasknumTextView = (TextView) findViewById(R.id.taskNumTextView);
        textView = (TextView) findViewById(R.id.taskTextView);


        num = getSharedPreferences("num", MODE_PRIVATE);
        n_sp = getSharedPreferences("n_savedata", MODE_PRIVATE);
        d_sp = getSharedPreferences("d_savedata", MODE_PRIVATE);
        deadline_sp = getSharedPreferences("deadline", MODE_PRIVATE);
        checkDefault = getSharedPreferences("checkdefault", MODE_PRIVATE);
        num_editor = num.edit();
        n_editor = n_sp.edit();
        d_editor = d_sp.edit();
        dl_editor = deadline_sp.edit();
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
            num_editor.putInt("clear", 0);
            num_editor.commit();
            cd_ediror.putBoolean("check", true);
            cd_ediror.commit();
        }

        tasknum = num.getInt("tasknum", -1);
        tasknumTextView.setText(String.valueOf(tasknum));

        taskScroll = (ScrollView) findViewById(R.id.taskScrollView);
        taskLayout = new LinearLayout(this);
        taskLayout.setOrientation(LinearLayout.VERTICAL);
        taskLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        taskScroll.addView(taskLayout);
        ll = new LinearLayout(this);
        lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 5, 0, 5);
    }

    //クリックリスナー
    public void onClick(View v) {
        Intent intent2 = new Intent(this, infActivity.class);
        switch (v.getId()) {
            case R.id.appendButton:
                Intent intent = new Intent(this, appendActivity.class);
                startActivity(intent);
                break;
            case R.id.pointerImageView2:
                intent2.putExtra("pointerNum", 2);
                startActivity(intent2);
                break;
            case R.id.pointerImageView3:
                intent2.putExtra("pointerNum", 3);
                startActivity(intent2);
                break;
            case R.id.pointerImageView4:
                intent2.putExtra("pointerNum", 4);
                startActivity(intent2);
                break;
            case R.id.pointerImageView5:
                intent2.putExtra("pointerNum", 5);
                startActivity(intent2);
                break;
            case R.id.pointerImageView6:
                intent2.putExtra("pointerNum", 6);
                startActivity(intent2);
                break;
            case R.id.pointerImageView7:
                intent2.putExtra("pointerNum", 7);
                startActivity(intent2);
                break;
            case R.id.pointerImageView8:
                intent2.putExtra("pointerNum", 8);
                startActivity(intent2);
                break;
            case R.id.awardButton:
                Intent intent4 = new Intent(this, AwardActivity.class);
                startActivity(intent4);
                break;
        }
    }

    //期日までの日数によりカテゴライズ
    private void setDeadline() throws ParseException {
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
            if(date_today >= 0 && date_today <= 24) dl_editor.putInt(String.valueOf(i), 2);
            else if(date_today > 24 && date_today <= 72) dl_editor.putInt(String.valueOf(i), 3);
            else if(date_today > 72 && date_today <= (24*7)) dl_editor.putInt(String.valueOf(i), 4);
            else if(date_today > (24*7) && date_today <= (24*10)) dl_editor.putInt(String.valueOf(i), 5);
            else if(date_today > (24*10) && date_today <= (24*14)) dl_editor.putInt(String.valueOf(i), 6);
            else if(date_today > (24*14) && date_today <= (24*21)) dl_editor.putInt(String.valueOf(i), 7);
            else if(date_today > (24*21)) dl_editor.putInt(String.valueOf(i), 8);
            else if(date_today < 0) dl_editor.putInt(String.valueOf(i), -1);
            dl_editor.commit();

        }
    }

    //近傍の予定があるかどうかでポインターを表示するか決めている。
    private void visiblePointer() {
        checkPointer();
        if(p[2]) pointer2.setVisibility(View.VISIBLE); else pointer2.setVisibility(View.INVISIBLE);
        if(p[3]) pointer3.setVisibility(View.VISIBLE); else pointer3.setVisibility(View.INVISIBLE);
        if(p[4]) pointer4.setVisibility(View.VISIBLE); else pointer4.setVisibility(View.INVISIBLE);
        if(p[5]) pointer5.setVisibility(View.VISIBLE); else pointer5.setVisibility(View.INVISIBLE);
        if(p[6]) pointer6.setVisibility(View.VISIBLE); else pointer6.setVisibility(View.INVISIBLE);
        if(p[7]) pointer7.setVisibility(View.VISIBLE); else pointer7.setVisibility(View.INVISIBLE);
        if(p[8]) pointer8.setVisibility(View.VISIBLE); else pointer8.setVisibility(View.INVISIBLE);
    }

    //期日を過ぎたタスクを削除
    public void delete(int n) {
        d_editor.remove(n_sp.getString(String.valueOf(n), "error"));
        d_editor.commit();
        if (tasknum == 1) {
            n_editor.remove(String.valueOf(tasknum));
            dl_editor.clear();
            dl_editor.commit();
            n_editor.commit();
        } else {
            for (int i = 1; i <= tasknum; i++) {
                if (i > n) {
                    String value = n_sp.getString(String.valueOf(i), "error");
                    n_editor.putString(String.valueOf(i - 1), value);
                    if (i == tasknum) {
                        n_editor.remove(String.valueOf(i));
                    }
                    n_editor.commit();
                }
            }
        }
        tasknum--;
        num_editor.putInt("tasknum", tasknum);
        num_editor.commit();
    }

    //デッドラインからポインターを表示する位置を指定
    private void checkPointer(){
        int IVnumber;
        for(int i = 0;i < 9;i++) {
            p[i] = false;
        }
        if(tasknum == 0) return;
        for(int i = 1;i <= tasknum;i++){
            IVnumber = deadline_sp.getInt(String.valueOf(i), -1);
            switch (IVnumber){
                case -1:
                    delete(i);
                    break;
                case 2:
                    p[2] = true;
                    break;
                case 3:
                    p[3] = true;
                    break;
                case 4:
                    p[4] = true;
                    break;
                case 5:
                    p[5] = true;
                    break;
                case 6:
                    p[6] = true;
                    break;
                case 7:
                    p[7] = true;
                    break;
                case 8:
                    p[8] = true;
                    break;
            }
        }
    }

    //真ん中のスクロールビューを表示
    private void setScrollView(){
        int pointerNum = 0;
        checkPointer();
        for(int i = 2;i < 9;i++){
            if(p[i]){
                pointerNum = i;
                break;
            }
        }
        if(pointerNum == 0) return;
        TextView[] tasks = new TextView[tasknum+1];
        for(int i = 1;i<tasknum+1;i++) {
            if(deadline_sp.getInt(String.valueOf(i), -1) == pointerNum) {
                String name = n_sp.getString(String.valueOf(i), "error");
                String date = d_sp.getString(name, "error");
                tasks[i] = new TextView(this);
                tasks[i].setText("タスク名：" + name + "\n" + "期限：" + date);
                tasks[i].setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                tasks[i].setGravity(Gravity.CENTER);
                tasks[i].setPadding(0, 10, 0, 10);
                tasks[i].setBackgroundColor(Color.WHITE);
                tasks[i].setLayoutParams(lp);
                tasks[i].setClickable(true);
                tasks[i].setTag(new Integer(i));
                tasks[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toProperty((Integer) v.getTag());
                    }
                });
                taskLayout.addView(tasks[i]);
            }
        }
    }

    //プロパティ画面への遷移
    private void toProperty(int num){
        Intent intent3 = new Intent(this, propertyActivity.class);
        intent3.putExtra("tasknumber", num);
        startActivity(intent3);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (checkDefault.getBoolean("check", false) == false) {
            pointer2.setVisibility(View.INVISIBLE);
            pointer3.setVisibility(View.INVISIBLE);
            pointer4.setVisibility(View.INVISIBLE);
            pointer5.setVisibility(View.INVISIBLE);
            pointer6.setVisibility(View.INVISIBLE);
            pointer7.setVisibility(View.INVISIBLE);
            pointer8.setVisibility(View.INVISIBLE);

            num_editor.putInt("tasknum", 0);
            num_editor.putInt("clear", 0);
            num_editor.commit();
            cd_ediror.putBoolean("check", true);
            cd_ediror.commit();
        }
        else {
            tasknum = num.getInt("tasknum", -1);
            tasknumTextView.setText(String.valueOf(tasknum));
            try {
                setDeadline();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            visiblePointer();
            taskLayout.removeAllViews();
            setScrollView();
        }
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
