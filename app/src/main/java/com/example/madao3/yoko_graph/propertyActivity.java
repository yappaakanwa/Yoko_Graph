package com.example.madao3.yoko_graph;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class propertyActivity extends ActionBarActivity {

    private int taskcount;
    private int tasknumber;

    private String name;
    private String date;

    private SharedPreferences num_sp;
    private SharedPreferences name_sp;
    private SharedPreferences date_sp;
    private SharedPreferences deadline_sp;
    private Editor num_editor;
    private Editor name_editor;
    private Editor date_editor;
    private Editor dl_editor;

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
        deadline_sp = getSharedPreferences("deadline", MODE_PRIVATE);
        num_editor = num_sp.edit();
        name_editor = name_sp.edit();
        date_editor = date_sp.edit();
        dl_editor = deadline_sp.edit();

        taskcount = num_sp.getInt("tasknum", -1);
        name = name_sp.getString(String.valueOf(tasknumber), "error");
        date = date_sp.getString(name, "error");

        nameEditText = (EditText) findViewById(R.id.taskNameEdit);
        yearEditText = (EditText) findViewById(R.id.yearEditText);
        monthEditText = (EditText) findViewById(R.id.monthEditText);
        dateEditText = (EditText) findViewById(R.id.dateEditText);
        setTaskInf();

    }

    //タスクの情報を表示
    private void setTaskInf(){
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

    //日時が不正でないかチェック
    private boolean checkDate() throws ParseException {
        Date datetime = new Date();
        Date today = new Date();

        datetime = DateFormat.getDateInstance().parse(date);
        if((datetime.getTime() - today.getTime()) < 0){
            return false;
        }else{
            return true;
        }
    }

    //戻るボタン
    public void back(View v){
        finish();
    }

    //保存ボタン
    public void save(View v){
        AlertDialog.Builder notice = new AlertDialog.Builder(this)
                .setTitle("確認！")
                .setMessage("この設定を保存しますか？")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String year, month, day;
                        date_editor.remove(name);
                        name = nameEditText.getText().toString();
                        year = yearEditText.getText().toString();
                        month = monthEditText.getText().toString();
                        day = dateEditText.getText().toString();
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            String value = "20" + year + "-0" + month + "-" + day;
                            format.setLenient(false);
                            format.parse(value);
                        } catch (ParseException e) {
                            showErrorToast(1);
                            return;
                        }
                        date = "20" + year + "/" + month + "/" + day;
                        try {
                            if (checkDate()) {
                                name_editor.putString(String.valueOf(tasknumber), name);
                                date_editor.putString(name, date);
                                name_editor.commit();
                                date_editor.commit();
                            } else {
                                showErrorToast(2);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("キャンセル", null);
        AlertDialog alertDialog = notice.create();
        alertDialog.show();
    }

    //エラートーストの表示
    private void showErrorToast(int n){
        if(n == 1) Toast.makeText(this, "日にちの値が不正です",Toast.LENGTH_SHORT).show();
        if(n == 2) Toast.makeText(this, "過去の日時です", Toast.LENGTH_SHORT).show();
    }

    //タスクの削除
    public void delete(View v){
        AlertDialog.Builder notice = new AlertDialog.Builder(this)
                .setTitle("確認！")
                .setMessage("このタスクを削除しますか？")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        date_editor.remove(name);
                        date_editor.commit();
                        if (taskcount == 1) {
                            name_editor.remove(String.valueOf(taskcount));
                            dl_editor.clear();
                            dl_editor.commit();
                            name_editor.commit();
                        } else {
                            for (int i = 1; i <= taskcount; i++) {
                                if (i > tasknumber) {
                                    String value = name_sp.getString(String.valueOf(i), "error");
                                    name_editor.putString(String.valueOf(i - 1), value);
                                    if (i == taskcount) {
                                        name_editor.remove(String.valueOf(i));
                                    }
                                    name_editor.commit();
                                }
                            }
                        }
                        taskcount--;
                        num_editor.putInt("tasknum", taskcount);
                        num_editor.commit();
                        finish();
                    }
                })
                .setNegativeButton("キャンセル", null);
        AlertDialog alertDialog = notice.create();
        alertDialog.show();
    }

    //タスク成功
    public void clear(View v){
        AlertDialog.Builder notice = new AlertDialog.Builder(this)
                .setTitle("確認！")
                .setMessage("このタスクを完了しますか？")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        date_editor.remove(name);
                        date_editor.commit();
                        if (taskcount == 1) {
                            name_editor.remove(String.valueOf(taskcount));
                            dl_editor.clear();
                            dl_editor.commit();
                            name_editor.commit();
                        } else {
                            for (int i = 1; i <= taskcount; i++) {
                                if (i > tasknumber) {
                                    String value = name_sp.getString(String.valueOf(i), "error");
                                    name_editor.putString(String.valueOf(i - 1), value);
                                    if (i == taskcount) {
                                        name_editor.remove(String.valueOf(i));
                                    }
                                    name_editor.commit();
                                }
                            }
                        }
                        taskcount--;
                        int clearNum = num_sp.getInt("clear", -1);
                        clearNum++;
                        num_editor.putInt("clear", clearNum);
                        num_editor.putInt("tasknum", taskcount);
                        num_editor.commit();
                        finish();
                    }
                })
                .setNegativeButton("キャンセル", null);
        AlertDialog alertDialog = notice.create();
        alertDialog.show();
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
