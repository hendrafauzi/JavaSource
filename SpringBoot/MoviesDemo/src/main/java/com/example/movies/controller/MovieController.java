package com.example.movies.controller;

import com.example.movies.exception.BadRequestException;
import com.example.movies.model.Request;
import com.example.movies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/demo/v1")
public class MovieController
{
    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService)
    {
        this.movieService = movieService;
    }

    @GetMapping(value = "/demo/v1/movies/{title}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getMovies(@PathVariable("title")String title)
    {
        if (null != title)
            return movieService.getMoviesData(title);
        else
            throw new BadRequestException();
    }

    @PostMapping(value = "/demo/v2/movies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getMoviesByTitle(@RequestBody Request request)
    {
        if (null != request.getTitle())
            return movieService.getMoviesByTitle(request.getTitle());
        else
            throw new BadRequestException();
    }
}
