package com.lau.nicetechnewsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView view;
    ArrayList<String> titles;
    ArrayList<String> urls;
    SQLiteDatabase db;
    public static final String EXTRA_MESSAGE = "Link";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            db=this.openOrCreateDatabase("techappdb",MODE_PRIVATE,null);
            //db.execSQL("CREATE TABLE IF NOT EXISTS articles (article_title VARCHAR,article_id VARCHAR,article_url VARCHAR)");
            Cursor c = db.rawQuery("Select * from articles",null);
            titles = new ArrayList<String>();
            urls = new ArrayList<String>();
            view = (ListView) findViewById(R.id.listView);
            int titleIndex = c.getColumnIndex("article_title");
            int urlIndex = c.getColumnIndex("article_url");
            c.moveToFirst();

            for(int i=0;i<c.getCount();i++){
                titles.add(c.getString(titleIndex));
                urls.add(c.getString(urlIndex));
                c.moveToNext();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, titles);
            view.setAdapter(adapter);
            view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                    intent.putExtra(EXTRA_MESSAGE, urls.get(i));
                    startActivity(intent);
                }
            });

        }catch(Exception e){
            e.printStackTrace();
        }

        /*  titles = new ArrayList<String>();
        urls = new ArrayList<String>();
        view = (ListView) findViewById(R.id.listView);
        JsonGetter jsonGetter = new JsonGetter();
        jsonGetter.execute("https://hacker-news.firebaseio.com/v0/topstories.json");
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                intent.putExtra(EXTRA_MESSAGE, urls.get(i));
                startActivity(intent);
            }
        });*/

    }

  /* public class JsonGetter extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray arr = new JSONArray(s);
                for (int i = 35; i < 55; i++) {
                    String articleId = arr.getString(i);

                    Log.i("Id:" + i, articleId);
                    ArticleFetcher articleFetcher = new ArticleFetcher();
                    articleFetcher.execute("https://hacker-news.firebaseio.com/v0/item/" + articleId + ".json?print=pretty");
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }*/

   /* public class ArticleFetcher extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return result;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject json = new JSONObject(s);
                String title = json.getString("title");
                String url = json.getString("url");
                String id= json.getString("id");
                String complete="'"+title+"','"+id+"','"+url+"'";
                titles.add(title);
                urls.add(url);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, titles);
                view.setAdapter(adapter);
              db.execSQL("INSERT INTO articles (article_title,article_id,article_url) VALUES("+complete+")");
                Cursor c = db.rawQuery("Select * from articles",null);
                int titleIndex = c.getColumnIndex("article_title");
                int urlIndex = c.getColumnIndex("article_url");
                c.moveToFirst();

                while(c!=null){
                    Log.i("Title:",c.getString(titleIndex));
                    Log.i("Url:",c.getString(urlIndex));
                    c.moveToNext();
                }
                //Log.i("Title:", title);
                //Log.i("URL:", url);




            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }*/
}