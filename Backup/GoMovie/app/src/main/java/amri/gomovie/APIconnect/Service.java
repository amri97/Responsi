package amri.gomovie.APIconnect;

import amri.gomovie.Attribute.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {

    @GET("3/movie/now_playing")
    Call<MovieResponse> getNowPlayingMovie(@Query("api_key") String apiKey);

}
