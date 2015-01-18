package com.example.c.listview01;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

class MyData {
    int mImgIcon;
    String mTitle, mSubTitle;

    MyData(int imgIcon, String title, String subTitle) {
        mImgIcon = imgIcon;
        mTitle = title;
        mSubTitle = subTitle;
    }
}

public class MainActivity extends ActionBarActivity {
    //String[] list = {"Hello", "world","Oracle","java","asp","Hello", "world","Oracle","java","asp","Hello", "world","Oracle","java","asp"};

    MyCustomAdapter adapter;
    ArrayList<MyData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<MyData>();
        list.add(new MyData(R.drawable.abc_ic_ab_back_mtrl_am_alpha,"title1","subtitle1"));
        list.add(new MyData(R.drawable.abc_ic_ab_back_mtrl_am_alpha,"title2","subtitle2"));
        list.add(new MyData(R.drawable.abc_ic_ab_back_mtrl_am_alpha,"title3","subtitle3"));
        list.add(new MyData(R.drawable.abc_ic_ab_back_mtrl_am_alpha,"title4","subtitle4"));
        list.add(new MyData(R.drawable.abc_ic_ab_back_mtrl_am_alpha,"title5","subtitle5"));
        list.add(new MyData(R.drawable.abc_ic_ab_back_mtrl_am_alpha,"title6","subtitle6"));
        list.add(new MyData(R.drawable.abc_ic_ab_back_mtrl_am_alpha,"title7","subtitle7"));
        list.add(new MyData(R.drawable.abc_ic_ab_back_mtrl_am_alpha,"title8","subtitle8"));
        list.add(new MyData(R.drawable.abc_ic_ab_back_mtrl_am_alpha,"title9","subtitle9"));
        list.add(new MyData(R.drawable.abc_ic_ab_back_mtrl_am_alpha,"title10","subtitle10"));

        ListView listview = (ListView) findViewById(R.id.listView);



        adapter = new MyCustomAdapter(this, R.layout.list_row, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, list.get(position).mTitle, Toast.LENGTH_SHORT).show();
            }
        });

        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int count = totalItemCount - visibleItemCount;

                if(firstVisibleItem >= count){  // listitem이 화면에 다 보이고 있을 경우
                    list.add(new MyData(R.drawable.abc_ic_ab_back_mtrl_am_alpha,"newtitle1","subtitle1"));
                    list.add(new MyData(R.drawable.abc_ic_ab_back_mtrl_am_alpha,"newtitle2","subtitle2"));
                    list.add(new MyData(R.drawable.abc_ic_ab_back_mtrl_am_alpha,"newtitle3","subtitle3"));
                    list.add(new MyData(R.drawable.abc_ic_ab_back_mtrl_am_alpha,"newtitle4","subtitle4"));
                    list.add(new MyData(R.drawable.abc_ic_ab_back_mtrl_am_alpha,"newtitle5","subtitle5"));
                    list.add(new MyData(R.drawable.abc_ic_ab_back_mtrl_am_alpha,"newtitle6","subtitle6"));

                    adapter.notifyDataSetChanged();
                }

            }
        });

        /*
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list); // activity, 리스트 레이아웃 형태, 데이터
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //((TextView)view).getText().toString();
                Toast.makeText(MainActivity.this, list[position], Toast.LENGTH_SHORT).show();
            }
        });
        */
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
