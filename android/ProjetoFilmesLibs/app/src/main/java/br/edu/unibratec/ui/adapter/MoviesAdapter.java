package br.edu.unibratec.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import br.edu.unibratec.R;
import br.edu.unibratec.databinding.ItemMovieBinding;
import br.edu.unibratec.model.Movie;

public class MoviesAdapter extends ArrayAdapter<Movie> {

    public MoviesAdapter(Context context, List<Movie> movies) {
        super(context, 0, movies);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);

        ItemMovieBinding binding;
        if (convertView == null){
             binding = DataBindingUtil.inflate(
                     LayoutInflater.from(getContext()),
                        R.layout.item_movie, parent, false);
        } else {
            binding = DataBindingUtil.bind(convertView);
        }
        binding.setMovie(movie);
        return binding.getRoot();
    }
}
