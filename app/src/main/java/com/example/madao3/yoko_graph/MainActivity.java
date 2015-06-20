package com.example.madao3.yoko_graph;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {

    ImageView pointer1;
    ImageView pointer2;
    ImageView pointer3;
    ImageView pointer4;
    ImageView pointer5;
    ImageView pointer6;
    ImageView pointer7;
    ImageView pointer8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setPointer();
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
