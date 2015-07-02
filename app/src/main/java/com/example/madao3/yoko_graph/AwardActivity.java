package com.example.madao3.yoko_graph;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;


public class AwardActivity extends ActionBarActivity {

    private SharedPreferences num_sp;
    private SharedPreferences name_sp;
    private SharedPreferences date_sp;
    private SharedPreferences deadline_sp;
    private SharedPreferences checkDefault;
    private Editor num_editor;
    private Editor date_editor;
    private Editor name_editor;
    private Editor dl_editor;
    private Editor cd_editor;

    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_award);

        num_sp = getSharedPreferences("num", MODE_PRIVATE);
        int clearNum = num_sp.getInt("clear", 0);
        text = (TextView) findViewById(R.id.clearTextView);
        text.setText(String.valueOf(clearNum));
        name_sp = getSharedPreferences("n_savedata", MODE_PRIVATE);
        date_sp = getSharedPreferences("d_savedata", MODE_PRIVATE);
        deadline_sp = getSharedPreferences("deadline", MODE_PRIVATE);
        checkDefault = getSharedPreferences("checkdefault", MODE_PRIVATE);
        num_editor = num_sp.edit();
        name_editor = name_sp.edit();
        date_editor = date_sp.edit();
        dl_editor = deadline_sp.edit();
        cd_editor = checkDefault.edit();
    }

    //戻るボタン
    public void back(View v){
        finish();
    }

    //全削除ボタン
    public void bomb(View v){
        AlertDialog.Builder notice = new AlertDialog.Builder(this)
                .setTitle("確認！")
                .setMessage("すべてのデータを消去します")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        num_editor.clear();
                        name_editor.clear();
                        date_editor.clear();
                        dl_editor.clear();
                        cd_editor.clear();
                        num_editor.commit();
                        name_editor.commit();
                        date_editor.commit();
                        dl_editor.commit();
                        cd_editor.commit();
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
        getMenuInflater().inflate(R.menu.menu_award, menu);
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
