package com.amri.gomovie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Page {
    @GET("movie/now_playing")
    Call<ViewMovie> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<ViewMovie> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);
}