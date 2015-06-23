package com.example.madao3.yoko_graph;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.ImageView;


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

    private int taskNum = 0;

    private SharedPreferences num;
    private SharedPreferences n_sp;
    private SharedPreferences d_sp;
    private SharedPreferences m_sp;
    private Editor num_editor;
    private Editor n_editor;
    private Editor d_editor;
    private Editor m_editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setPointer();
        tasknumTextView = (TextView) findViewById(R.id.taskNumTextView);
        tasknumTextView.setText(String.valueOf(taskNum));
        visiblePointer();
        num = getSharedPreferences("num", MODE_PRIVATE);
        num_editor = num.edit();
        n_sp = getSharedPreferences("n_savedata", MODE_PRIVATE);
        d_sp = getSharedPreferences("d_savedate", MODE_PRIVATE);
        m_sp = getSharedPreferences("m_savedate", MODE_PRIVATE);
        n_editor = n_sp.edit();
        d_editor = d_sp.edit();
        m_editor = m_sp.edit();
        if(taskNum == 0){
            num_editor.putInt("tasknum", 0);
        }
    }

    private void setPointer(){
        pointer1 = (ImageView) findViewById(R.id.pointerImageView1);
        pointer2 = (ImageView) findViewById(R.id.pointerImageView2);
        pointer3 = (ImageView) findViewById(R.id.pointerImageView3);
        pointer4 = (ImageView) findViewById(R.id.pointerImageView4);
        pointer5 = (ImageView) findViewById(R.id.pointerImageView5);
        pointer6 = (ImageView) findViewById(R.id.pointerImageView6);
        pointer7 = (ImageView) findViewById(R.id.pointerImageView7);
        pointer8 = (ImageView) findViewById(R.id.pointerImageView8);

        pointer1.setVisibility(View.INVISIBLE);
        pointer2.setVisibility(View.INVISIBLE);
        pointer3.setVisibility(View.INVISIBLE);
        pointer4.setVisibility(View.INVISIBLE);
        pointer5.setVisibility(View.INVISIBLE);
        pointer6.setVisibility(View.INVISIBLE);
        pointer7.setVisibility(View.INVISIBLE);
        pointer8.setVisibility(View.INVISIBLE);
    }

    public void appendTask(View v){
        Intent intent = new Intent(this, appendActivity.class);
        startActivity(intent);
    }

    public void visiblePointer(){
        pointer2.setVisibility(View.VISIBLE);
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
