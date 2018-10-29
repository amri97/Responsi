package com.amri.gomovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    // TODO - insert your themoviedb.org API KEY here
    private final static String API_KEY = "7d14bd98ef36478c34a4f3953c14431b";

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId()==R.id.setting){
            startActivity(new Intent(this, BahasaAct.class));

        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY first from themoviedb.org", Toast.LENGTH_LONG).show();
            return;
        }

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movie_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Page apiService =
                ConnectAPI.getClient().create(Page.class);

        Call<ViewMovie> call = apiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<ViewMovie>() {
            @Override
            public void onResponse(Call<ViewMovie>call, Response<ViewMovie> response) {
                final List<ItemsMovie> itemsMovies = response.body().getResults();
                Log.d(TAG, "Number of itemsMovies received: " + itemsMovies.size());
                Toast.makeText(MainActivity.this, "Number of itemsMovies received: " + itemsMovies.size(), Toast.LENGTH_LONG).show();
                recyclerView.setAdapter(new AttributeMovie(itemsMovies, R.layout.view_movie, getApplicationContext()));
                /*perintah klik recyclerview*/
                recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                    GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {

                        public boolean onSingleTapUp(MotionEvent e){
                            return true;
                        }
                    });

                    @Override
                    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                        View child = rv.findChildViewUnder(e.getX(), e.getY());
                        if (child != null && gestureDetector.onTouchEvent(e)){
                            int position = rv.getChildAdapterPosition(child);
                            /*Intent i = new Intent(getApplicationContext(), MovieActivity.class);
                            i.putExtra("id", itemsMovies.get(position).getId());
                            getApplicationContext().startActivity(i);*/
                            Toast.makeText(getApplicationContext(), "Id : " + itemsMovies.get(position).getId() + " selected", Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(MainActivity.this, MovieActivity.class);
                            i.putExtra("title", itemsMovies.get(position).getTitle());
                            i.putExtra("date", itemsMovies.get(position).getReleaseDate());
                            i.putExtra("vote", itemsMovies.get(position).getVoteAverage().toString());
                            i.putExtra("overview", itemsMovies.get(position).getOverview());
                            i.putExtra("bg", itemsMovies.get(position).getPosterPath());
                            MainActivity.this.startActivity(i);


                        }
                        return false;
                    }

                    @Override
                    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

                    }

                    @Override
                    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

                    }
                });
            }

            @Override
            public void onFailure(Call<ViewMovie>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }
}
