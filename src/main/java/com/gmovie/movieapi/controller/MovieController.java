package com.gmovie.movieapi.controller;

import com.gmovie.movieapi.model.Movie;
import com.gmovie.movieapi.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MovieController {

    @Autowired
    MovieService movieService;

    @GetMapping("/api/movies")
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/api/movies/{title}")
    public ResponseEntity getMovieByTitle(@PathVariable String title) {
        Optional<Movie> movie = movieService.getMovieByTitle(title);
        if (movie.isPresent()) {
            return new ResponseEntity(movie.get(), HttpStatus.OK);
        }
        return new ResponseEntity("The movie you're looking for was not found", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/api/movies")
    @ResponseStatus(HttpStatus.OK)
    public Movie updateMovieRating(@RequestBody Movie movie) {
        return movieService.updateMovie(movie);
    }
}
