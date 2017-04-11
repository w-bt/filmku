package id.widhianbramantya.android.filmku;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

public class FilmKu extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private CustomAdapter adapter;
    private List<MyData> data_list;
    private int page = 1;
    private String q = "a";
    private int flagInit = 0;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_ku);
        textView = (TextView) findViewById(R.id.editText);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        data_list = new ArrayList<>();

        if(flagInit== 0){
            load_data_from_server(page, "a");
        }

        textView.addTextChangedListener(new TextWatcher() {
            private Timer timer;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (timer != null) {
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(final Editable s) {
                // user typed: start the timer
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        flagInit = 1;
                        if(s.toString().equals("")){
                            q = "a";
                            data_list.clear();
                            load_data_from_server(page, "a");
                        } else {
                            q = s.toString();
                            data_list.clear();
                            load_data_from_server(page, s.toString());
                        }
                    }
                }, 600);
            }
        });

        gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new CustomAdapter(this,data_list);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(gridLayoutManager.findLastCompletelyVisibleItemPosition() == data_list.size()-1){
                    page++;
                    load_data_from_server(page, q);
                }
            }
        });
    }

    private void load_data_from_server(final int no, final String s){
        AsyncTask<Integer,Void,Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integer) {
                try {
                    JSONObject json = readJsonFromUrl("http://www.omdbapi.com/?s=" + s + "&type=movie&page=" + no);
                    JSONArray arr = json.getJSONArray("Search");
                    for(int i = 0; i < arr.length(); i++){
                        JSONObject object = arr.getJSONObject(i);
                        JSONObject jsonDetail = readJsonFromUrl("http://www.omdbapi.com/?i=" + object.getString("imdbID"));

                        MyData data = new MyData(object.getString("imdbID"),object.getString("Title") + " (" + object.getString("Year") + ") ",object.getString("Poster"), "\"" + jsonDetail.getString("Plot") + "\"", "Rating : " + jsonDetail.getString("imdbRating") + "/10");

                        data_list.add(data);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid){
                adapter.notifyDataSetChanged();;
            }
        };

        task.execute(no);
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }
}
