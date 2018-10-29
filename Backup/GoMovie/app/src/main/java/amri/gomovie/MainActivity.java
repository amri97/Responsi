package amri.gomovie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.MenuItem;

import amri.gomovie.NowPlayingAdapter.MovieNowPlayingAdapter;
import amri.gomovie.Attribute.MovieAttribute;
import amri.gomovie.Attribute.MovieResponse;
import amri.gomovie.APIconnect.Server;
import amri.gomovie.APIconnect.Service;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private final static String API_KEY = "7d14bd98ef36478c34a4f3953c14431b";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movie_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Service apiService =
                Server.getClient().create(Service.class);

        Call<MovieResponse> call = apiService.getNowPlayingMovie(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                final List<MovieAttribute> movieAttributes = response.body().getResults();
                recyclerView.setAdapter(new MovieNowPlayingAdapter(movieAttributes, R.layout.list_item_movie, getApplicationContext()));

                recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                    GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {

                        public boolean onSingleTapUp(MotionEvent e) {
                            return true;
                        }
                    });

                    @Override
                    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                        View child = rv.findChildViewUnder(e.getX(), e.getY());
                        if (child != null && gestureDetector.onTouchEvent(e)) {
                            int position = rv.getChildAdapterPosition(child);

                            Intent i = new Intent(MainActivity.this, DetailMovieActivity.class);
                            i.putExtra("title", movieAttributes.get(position).getTitle());
                            i.putExtra("date", movieAttributes.get(position).getReleaseDate());
                            i.putExtra("vote", movieAttributes.get(position).getVoteAverage().toString());
                            i.putExtra("overview", movieAttributes.get(position).getOverview());
                            i.putExtra("bg", movieAttributes.get(position).getPosterPath());
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
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionmenu, menu);
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.setting) {
            startActivity(new Intent(this, MenuActivity.class));

        }
        return super.onOptionsItemSelected(item);
    }
}