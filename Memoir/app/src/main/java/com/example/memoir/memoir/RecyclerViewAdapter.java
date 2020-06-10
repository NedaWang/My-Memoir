package com.example.memoir.memoir;

import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memoir.R;
import com.example.memoir.entity.Memoir;
import com.example.memoir.network.SearchMovieAPI;
import com.example.memoir.util.JsonConvert;
import com.squareup.picasso.Picasso;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter <RecyclerViewAdapter.ViewHolder>{

    private List<Memoir> memoirs;
    String url;

    // Pass in the contact array into the constructor
    public RecyclerViewAdapter(List<Memoir> memoirs) {
        this.memoirs = memoirs;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // ViewHolder should contain variables for all the views in each row of the list
        public TextView name;
        public TextView release_date;
        public TextView watch_date;
        public TextView suburb;
        public TextView comment;
        public TextView score;
        public ImageView delete;
        public androidx.appcompat.widget.AppCompatImageView poster;
        // a constructor that accepts the entire View (itemView)
        // provides a reference and access to all the views in each row
        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            release_date = itemView.findViewById(R.id.release_date);
            watch_date=itemView.findViewById(R.id.watch_date);
            suburb = itemView.findViewById(R.id.suburb);
            comment=itemView.findViewById(R.id.comment);
            score=itemView.findViewById(R.id.score);
            delete = itemView.findViewById(R.id.delete);
            poster = itemView.findViewById(R.id.poster);
        }
    }

    //This method creates a new view holder that is constructed with a new View, inflate from a layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the view from an XML layout file
        View memoirView = inflater.inflate(R.layout.card_memoir, parent, false); // construct the viewholder with the new view
        ViewHolder viewHolder = new ViewHolder(memoirView);
        return viewHolder;
    }

    // this method binds the view holder created with data that will be displayed
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Memoir memoir = memoirs.get(position);
        // viewholder binding with its data at the specified position
        TextView tvName = holder.name;
        TextView tvReleaseDate = holder.release_date;
        TextView tvWatchDate = holder.watch_date;
        TextView tvSuburb = holder.suburb;
        TextView tvComment = holder.comment;
        TextView tvScore = holder.score;
        ImageView ivDelete = holder.delete;
        androidx.appcompat.widget.AppCompatImageView imPoster = holder.poster;

        tvName.setText(memoir.getName());
        tvName.setTypeface(Typeface.DEFAULT_BOLD);
        tvReleaseDate.setText(memoir.getReleaseDate().substring(0,10));
        tvWatchDate.setText(memoir.getWatchDate().substring(0,10));
        tvSuburb.setText(memoir.getCinema().getPostcode());
        tvComment.setText(memoir.getComment());
        tvScore.setText(memoir.getScore());

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memoirs.remove(memoir);
                notifyDataSetChanged();
            }
        });

        SearchPosterTask searchPosterTask = new SearchPosterTask(imPoster);
        searchPosterTask.execute(memoir.getName(), memoir.getReleaseDate());

        //final String url = "https://image.tmdb.org/t/p/w500" + movieImage;
        //String url = "https://image.tmdb.org/t/p/w500/" + movieImage;
        //Picasso.get().load(url).into(imPoster);
    }

    @Override
    public int getItemCount() {
        return memoirs.size();
    }


    private class SearchPosterTask extends AsyncTask<String, Void, String>{
        androidx.appcompat.widget.AppCompatImageView imPoster;
        public SearchPosterTask(androidx.appcompat.widget.AppCompatImageView imPoster){
            this.imPoster = imPoster;
        }

        @Override
        protected String doInBackground(String... strings) {
            String info = SearchMovieAPI.searchMovieByNameAndYear(strings[0], strings[1].substring(0,4)); // param is name and year
            String result = JsonConvert.extractPosterBasedOnNameAndYear(info, strings[1].substring(0,10));
            Log.i("posterPath", result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            //final String url = "https://image.tmdb.org/t/p/w500" + movieImage;
            url = "https://image.tmdb.org/t/p/w500/" + s;
            Picasso.get().load(url).into(imPoster);
        }
    }

}
