package com.gmovie.movieapi.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmovie.movieapi.model.Movie;
import com.gmovie.movieapi.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class MovieControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MovieRepository movieRepository;

    String moviePath = "src/test/java/com/gmovie/movieapi/data/movie.json";

    @Autowired
    ObjectMapper mapper;

    List<Movie> movieList;

    @BeforeEach
    void setUp() throws IOException {
        File moviePath1 = new File(moviePath);
        movieList = mapper.readValue(moviePath1, new TypeReference<ArrayList<Movie>>() {
        });
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
    public void getAllMovies_returnsListOfMovies() throws Exception {

        when(movieRepository.findAll()).thenReturn(movieList);

        mockMvc.perform(get("/api/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$.[0].title").value("The Avengers"));

    }

    @Test
    public void getMovieByTitle_ReturnsMovieDetails() throws Exception {

        Optional<Movie> expectedMovie = Optional.of(getMovieByTitle("Superman Returns"));

        when(movieRepository.findById("Superman Returns")).thenReturn(expectedMovie);

        mockMvc.perform(get("/api/movies/Superman Returns"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Superman Returns"))
                .andExpect(jsonPath("$.director").value("Bryan Singer"));
    }

    @Test
    public void getMovieByTitle_ReturnsNotFound() throws Exception {

        mockMvc.perform(get("/api/movies/bad hero"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("The movie you're looking for was not found"));
    }

    @Test
    public void updateRatingforMovie_ReturnsMovie() throws Exception {

        Optional<Movie> expectedMovie = Optional.of(getMovieByTitle("Superman Returns"));
        expectedMovie.get().setRating("4");

        Movie movie = new Movie();
        movie.setTitle("Superman Returns");
        movie.setRating("2");

        when(movieRepository.findById("Superman Returns")).thenReturn(expectedMovie);

        String jsonMovie = mapper.writeValueAsString(movie);
        when(movieRepository.save(Mockito.any(Movie.class))).thenReturn(expectedMovie.get());

        mockMvc.perform(put("/api/movies")
                .content(jsonMovie)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.title").value("Superman Returns"))
                .andExpect(jsonPath("$.avgrating").value("3.0"));

    }

    @Test
    public void updateRatingforMovie_multipleratings() throws Exception {

        Optional<Movie> expectedMovie = Optional.of(getMovieByTitle("Superman Returns"));
        expectedMovie.get().setRating("5");

        Movie movie = new Movie();
        movie.setTitle("Superman Returns");
        movie.setRating("3");

        String jsonMovie = mapper.writeValueAsString(movie);

        when(movieRepository.findById("Superman Returns")).thenReturn(expectedMovie);

        when(movieRepository.save(Mockito.any(Movie.class))).thenReturn(expectedMovie.get());

        mockMvc.perform(put("/api/movies")
                .content(jsonMovie)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Superman Returns"))
                .andExpect(jsonPath("$.avgrating").value("4.0"));

    }
}
