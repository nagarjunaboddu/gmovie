package com.gmovie.movieapi.unit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmovie.movieapi.model.Movie;
import com.gmovie.movieapi.repository.MovieRepository;
import com.gmovie.movieapi.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@JdbcTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class MovieServiceTest {

    @MockBean
    MovieRepository movieRepository;

    ObjectMapper mapper;

    String moviePath = "src/test/java/com/gmovie/movieapi/data/movie.json";
    List<Movie> movieList;

    @BeforeEach
    void setUp() throws IOException {
        mapper = new ObjectMapper();

        File moviePath1 = new File(moviePath);
        movieList = mapper.readValue(moviePath1, new TypeReference<ArrayList<Movie>>() {
        });

    }

    @Test
    public void serviceShouldReturnAllMovies() {
        when(movieRepository.findAll()).thenReturn(movieList);

        MovieService service = new MovieService(movieRepository);

        List<Movie> allMovies = service.getAllMovies();

        assertEquals(movieList, allMovies);
    }

    private Movie getMovieByTitle(String title) {
        for (Movie movie : movieList) {
            if (movie.getTitle().equals(title)) {
                return movie;
            }
        }
        return new Movie();
    }

    @Test
    public void serviceShouldReturnMovieDetails() {
        Optional<Movie> expectedMovie = Optional.of(getMovieByTitle("Superman Returns"));

        when(movieRepository.findById("Superman Returns")).thenReturn(expectedMovie);

        MovieService service = new MovieService(movieRepository);

        Optional<Movie> movie = service.getMovieByTitle("Superman Returns");

        assertEquals(expectedMovie, movie);
    }

    @Test
    public void serviceShouldReturnUpdatedMovie() {

        Optional<Movie> expectedMovie = Optional.of(getMovieByTitle("Superman Returns"));
        expectedMovie.get().setRating("3");

        when(movieRepository.findById("Superman Returns")).thenReturn(expectedMovie);

        when(movieRepository.save(expectedMovie.get())).thenReturn(expectedMovie.get());

        MovieService service = new MovieService(movieRepository);
        Movie movie = new Movie();
        movie.setTitle("Superman Returns");
        movie.setRating("3");
        Movie returnedMovie = service.updateMovie(movie);

        assertEquals(expectedMovie.get().getAvgrating(), returnedMovie.getAvgrating());

    }

    @Test
    public void serviceShouldReturnUpdatedMovie_multipleRatings() {

        Optional<Movie> expectedMovie = Optional.of(getMovieByTitle("Superman Returns"));
        expectedMovie.get().setRating("5");

        when(movieRepository.findById("Superman Returns")).thenReturn(expectedMovie);

        when(movieRepository.save(expectedMovie.get())).thenReturn(expectedMovie.get());

        MovieService service = new MovieService(movieRepository);
        Movie movie = new Movie();
        movie.setTitle("Superman Returns");
        movie.setRating("3");

        Movie returnedMovie = service.updateMovie(movie);

        assertEquals("4.0", returnedMovie.getAvgrating());

    }


}
