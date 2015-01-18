package com.example.c.domparser;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class MainActivity extends ActionBarActivity {

    TextView textview;

    class MyTask extends AsyncTask<String, Void, Document>{

        @Override
        protected Document doInBackground(String... params) {
            URL url;
            Document doc = null;

            try {
                url = new URL(params[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                doc = db.parse(url.openStream());

            } catch (Exception e) {
                e.printStackTrace();
            }

            return doc;
        }

        @Override
        protected void onPostExecute(Document document) {
            super.onPostExecute(document);
            String str = "";
            NodeList nodeList = document.getElementsByTagName("data");

            for(int i=0; i < nodeList.getLength(); i++){
                str += "\n" + i + " : data";
                Element dataElement = (Element) nodeList.item(i);    //<data>..</data>

                str += getNodeValue("day", dataElement) + " 일 ";
                str += getNodeValue("hour", dataElement) + " 시 ";
                str += getNodeValue("temp", dataElement) + " 도";
                str += getNodeValue("wfKor", dataElement) + " ";
            }
            textview.setText(str);
        }

        private String getNodeValue(String tagName, Element dataElement) {
            NodeList dayNodeList = dataElement.getElementsByTagName(tagName);
            Element dayElement = (Element) dayNodeList.item(0); //<day>..</day>
            NodeList dayTextNodeList = dayElement.getChildNodes();  // <day> 하위 전체 노드를 가져옴
            return dayTextNodeList.item(0).getNodeValue(); //<day>0</day> -> 0을 가져옴
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textview = (TextView)findViewById(R.id.textViewResult);

        MyTask task = new MyTask();
        task.execute("http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=1153059500");
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
