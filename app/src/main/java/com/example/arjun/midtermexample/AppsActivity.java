package com.example.arjun.midtermexample;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
//import android.renderscript.Type;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
//import java.lang.reflect.Type;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Collections.sort;

public class AppsActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    AppAdapter adapter;
    List<Apps> flist = new ArrayList<Apps>();
    static final String TITLE = "title";
    static final String IMAGE="image";
    static final String APPURL="url";
    SharedPreferences preferences;
    ArrayList<Apps> preflist = new ArrayList<Apps>();
    Type type = new TypeToken<ArrayList<Apps>>(){}.getType();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps);

        int code = (int) getIntent().getExtras().get(String.valueOf(MainActivity.CODE));


        if(code == 111) {
            if (isConnectedOnline()) {
                Toast.makeText(AppsActivity.this, "Its Connected", Toast.LENGTH_SHORT).show();
                new GetAppsAsync().execute("https://itunes.apple.com/us/rss/topgrossingapplications/limit=100/xml");

            } else {
                Toast.makeText(AppsActivity.this, "Not Connected", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            if (isConnectedOnline()) {
                Toast.makeText(AppsActivity.this, "Its Connected", Toast.LENGTH_SHORT).show();
                SharedPreferences pref2 = getSharedPreferences("list", MODE_PRIVATE);
                String json = pref2.getString("FAVORITES", null);
                Gson gson = new Gson();
                ArrayList<Apps> fav = gson.fromJson(json, type);

                ListView lv = (ListView) findViewById(R.id.listView);
                adapter = new AppAdapter(AppsActivity.this, R.layout.itemlayout, fav);
                lv.setAdapter(adapter);
                adapter.setNotifyOnChange(true);

            } else {
                Toast.makeText(AppsActivity.this, "Not Connected", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_apps, menu);
        //getMenuInflater().inflate(R.menu.switchmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        //noinspection SimplifiableIfStatement
        if (id == R.id.developer) {
            Log.d("demo", "dev clicked");

            Collections.sort(flist, new Comparator<Apps>() {
                @Override
                public int compare(Apps emp1, Apps emp2) {
                    return emp1.getDevname().compareToIgnoreCase(emp2.getDevname()); // ascending..for descending..switch places of 1 and 2
                }
            });

            ListView lv = (ListView) findViewById(R.id.listView);
            adapter = new AppAdapter(AppsActivity.this, R.layout.itemlayout, flist);
            lv.setAdapter(adapter);
            adapter.setNotifyOnChange(true);

        }
        if(id == R.id.appname){
            Log.d("demo", "appname clicked");

            Collections.sort(flist, new Comparator<Apps>() {
                @Override
                public int compare(Apps emp1, Apps emp2) {
                    return emp1.getTitle().compareToIgnoreCase(emp2.getTitle());
                }
            });
            ListView lv = (ListView) findViewById(R.id.listView);
            adapter = new AppAdapter(AppsActivity.this, R.layout.itemlayout, flist);
            lv.setAdapter(adapter);
            adapter.setNotifyOnChange(true);

        }
        if(id == R.id.price){
            Log.d("demo", "price clicked");

            Collections.sort(flist, new Comparator<Apps>() {
                @Override
                public int compare(Apps emp1, Apps emp2) {
                    return emp1.getPrice().compareToIgnoreCase(emp2.getPrice());
                }
            });
            ListView lv = (ListView) findViewById(R.id.listView);
            adapter = new AppAdapter(AppsActivity.this, R.layout.itemlayout, flist);
            lv.setAdapter(adapter);
            adapter.setNotifyOnChange(true);

        }
        if(id == R.id.clearhistory){
            Log.d("demo", "history clicked");
            SharedPreferences pref2 = getSharedPreferences("list", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref2.edit();
            editor.clear();
            editor.commit();
        }
        return super.onOptionsItemSelected(item);
    }

    public class GetAppsAsync extends AsyncTask<String, Void, List<Apps>> {


        @Override
        protected List<Apps> doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                publishProgress();
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("GET");
                int statusCode = con.getResponseCode();
                if (statusCode == HttpURLConnection.HTTP_OK) {
                    InputStream in = con.getInputStream();
                    return AppsUtil.parsePodcast(in);//data obtained is sent for parsing
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }  catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(AppsActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading Results ...");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(final List<Apps> result) {
            super.onPostExecute(result);
            if (result != null) {
                Log.d("demo", result.toString());
                flist.addAll(result);
                progressDialog.dismiss();
                ListView lv = (ListView) findViewById(R.id.listView);
                adapter = new AppAdapter(AppsActivity.this, R.layout.itemlayout, result);
                lv.setAdapter(adapter);
                adapter.setNotifyOnChange(true);

//                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Log.d("demo", "item click");
//
//                        String i_d = result.get(position).getId();
//                        String title = result.get(position).getTitle();
//                        String img = result.get(position).getSimg();
//                        String appurl = result.get(position).getAppurl();
//                        String date = result.get(position).getDate();
//                        String devname = result.get(position).getDevname();
//                        String price = result.get(position).getPrice();
//                        Apps l = new Apps(i_d, appurl, title, devname, date, price, img);
//
//                        preflist.add(l);
//
//
//                        SharedPreferences.Editor editor = getSharedPreferences("list", MODE_PRIVATE).edit();
//                        Gson gson = new Gson();
//                        String jsonFavorites = gson.toJson(preflist);
//                        editor.putString("FAVORITES", jsonFavorites);
//                        editor.commit();
//
//                        Intent intent = new Intent(AppsActivity.this, PreviewActivity.class);
//                        intent.putExtra(TITLE, title);
//                        intent.putExtra(IMAGE, img);
//                        intent.putExtra(APPURL, appurl);
//                        startActivity(intent);
//                    }
//                });
                lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        ImageView image = (ImageView) view.findViewById(R.id.imageView);
                        Drawable drawable = AppsActivity.this.getResources().getDrawable(R.drawable.goldstar);
                        image.setImageDrawable(drawable);

                        SharedPreferences.Editor editor = getSharedPreferences("list", MODE_PRIVATE).edit();
                        Gson gson = new Gson();
                        String jsonFavorites = gson.toJson(preflist);
                        editor.putString("FAVORITES", jsonFavorites);
                        editor.commit();

                        return true;
                    }
                });

            }
        }
    }

    private boolean isConnectedOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ninfo = cm.getActiveNetworkInfo();
        if (ninfo != null && ninfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
