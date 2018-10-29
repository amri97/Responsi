package amri.gomovie.NowPlayingAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import amri.gomovie.Attribute.MovieAttribute;
import amri.gomovie.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieNowPlayingAdapter extends RecyclerView.Adapter<MovieNowPlayingAdapter.MovieViewHolder> {

    private Context context;
    private List<MovieAttribute> movieAttributes;
    private int rowLayout;


    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        LinearLayout moviesLayout;
        TextView movieTitle;
        TextView data;
        TextView movieDescription;
        ImageView backbg;


        public MovieViewHolder(View v) {
            super(v);
            moviesLayout = (LinearLayout) v.findViewById(R.id.movies_layout);
            movieTitle = (TextView) v.findViewById(R.id.title);
            data = (TextView) v.findViewById(R.id.subtitle);
            movieDescription = (TextView) v.findViewById(R.id.description);
            backbg = (ImageView) v.findViewById(R.id.backbg);
        }
    }

    public MovieNowPlayingAdapter(List<MovieAttribute> movieAttributes, int rowLayout, Context context) {
        this.movieAttributes = movieAttributes;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public MovieNowPlayingAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent,
                                                                     int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        holder.movieTitle.setText(movieAttributes.get(position).getTitle());
        holder.data.setText(movieAttributes.get(position).getReleaseDate());
        holder.movieDescription.setText(movieAttributes.get(position).getOverview());
        Picasso.with(context).load("https://image.tmdb.org/t/p/w300_and_h450_bestv2" + movieAttributes.get(position).getBackdropPath()).resize(200, 250).into(holder.backbg);
    }

    @Override
    public int getItemCount() {
        return movieAttributes.size();
    }
}
