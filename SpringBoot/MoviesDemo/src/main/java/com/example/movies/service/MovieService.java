package com.example.movies.service;

import com.example.movies.exception.NoContentException;
import com.example.movies.model.Response;
import com.example.movies.model.ResponseSearch;
import com.example.movies.model.Search;
import com.example.movies.model.Transaction;
import com.example.movies.ws.MovieREST;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService
{
    private static final Logger logger = LogManager.getLogger();
    private MovieREST movieREST;

    @Autowired
    public MovieService(MovieREST movieREST)
    {
        this.movieREST = movieREST;
    }

    /*
    getMoviesData
    Using Primitive Strategy (With Gson & Without Stream Example)
    @params: title
    Author: Hendra
     */
    public ResponseEntity<String> getMoviesData(String title)
    {
        logger.debug("title: {}", title);

        Response response = new Response();
        response.setStatus("200");
        response.setMessage("Request Was Successful");

        Transaction getMovies = movieREST.getMovies(title);

        if(null != getMovies && !getMovies.getSearch().isEmpty())
        {
            List<ResponseSearch> responseSearches = new ArrayList<>();
            List<Search> search = getMovies.getSearch();

            for(Search movieSearch : search )
            {
                ResponseSearch responseSearch = new ResponseSearch();
                responseSearch.setTitle(movieSearch.getTitle());
                responseSearch.setType(movieSearch.getType());
                responseSearch.setYear(movieSearch.getYear());
                responseSearch.setImageUrl(movieSearch.getPoster());

                responseSearches.add(responseSearch);

                response.setMovies(responseSearches);
            }
        }
        else
        {
            logger.debug("Failed when getMovies");

            throw new NoContentException();
        }

        return new ResponseEntity<>(new Gson().toJson(response), HttpStatus.OK);
    }

    /*
    getMoviesByTitle
    Using Different Response Strategy (Without Gson) & Using stream Also
    @params: title
    Author: Hendra
     */
    public ResponseEntity getMoviesByTitle(String title)
    {
        logger.debug("title: {}", title);

        Response response = new Response();
        response.setStatus("200");
        response.setMessage("Request Was Successful");

        Transaction getMovies = movieREST.getMovies(title);

        if(null != getMovies && !getMovies.getSearch().isEmpty())
        {
            List<ResponseSearch> responseSearches = new ArrayList<>();
            response.setMovies(generateMovieLists(getMovies.getSearch(), responseSearches));
        }
        else
        {
            logger.debug("Failed when getMovies");

            throw new NoContentException();
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private List<ResponseSearch> generateMovieLists(List<Search> search, List<ResponseSearch> responseSearches)
    {
        ResponseSearch responseSearch = new ResponseSearch();

        search.stream()
                .peek(peekObj -> {
                    responseSearch.setTitle(peekObj.getTitle());
                    responseSearch.setType(peekObj.getType());
                    responseSearch.setYear(peekObj.getYear());
                    responseSearch.setImageUrl(peekObj.getPoster());
                })
                .forEach(obj -> responseSearches.add(responseSearch));

        return responseSearches;
    }
}