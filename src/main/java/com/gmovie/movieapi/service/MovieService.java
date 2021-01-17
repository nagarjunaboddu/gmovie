package com.gmovie.movieapi.service;

import com.gmovie.movieapi.model.Movie;
import com.gmovie.movieapi.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> getMovieByTitle(String movieTitle) {
        return movieRepository.findById(movieTitle);
    }

    public Movie updateMovie(Movie movie) {
        Optional<Movie> movie1 = movieRepository.findById(movie.getTitle());
        if (movie1.get()==null  ||  movie1.get().getRating().isEmpty()) {
            movie1.get().setRating(movie.getRating());
        } else {
            movie1.get().setRating(movie1.get().getRating() + "," + movie.getRating());
        }
        Movie savedMovie =  movieRepository.save(movie1.get());
        savedMovie.setAvgrating(this.getAvgrating(savedMovie));
        return savedMovie;
    }

    public String getAvgrating(Movie movie) {
        String[] ratings = movie.getRating().split(",");
        double avgVal = 0;
        for (String rating1 : ratings) {
            int val = Integer.parseInt(rating1);
            avgVal += val;
        }
        avgVal = avgVal / ratings.length;
        return String.valueOf(avgVal);
    }

}
