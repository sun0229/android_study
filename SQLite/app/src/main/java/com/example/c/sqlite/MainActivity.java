package com.example.c.sqlite;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textViewResult = (TextView) findViewById(R.id.textViewResult);

        MySQLiteHandler handler = new MySQLiteHandler(this);
        handler.insert("sunny1",20,"seoul1");
        handler.insert("sunny2",30,"seoul2");
        handler.insert("sunny3",40,"seoul3");
        handler.insert("sunny4",50,"seoul4");
        handler.insert("sunny5",60,"seoul5");
        handler.insert("sunny6",70,"seoul6");

        //textViewResult.setText(handler.getAllData());

        handler.updateAge("sunny1",25);
        //textViewResult.setText(handler.getAllData());

        handler.delete("sunny5");
        textViewResult.setText(handler.getAllData());
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
