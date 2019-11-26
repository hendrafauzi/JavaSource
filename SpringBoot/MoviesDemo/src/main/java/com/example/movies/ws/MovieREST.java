package com.example.movies.ws;

import com.example.movies.model.Transaction;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MovieREST
{
    private static Logger logger = LogManager.getLogger();

    private RestTemplate restTemplate;

    @Autowired
    public MovieREST(RestTemplate restTemplate)
    {
        this.restTemplate = restTemplate;
    }

    public Transaction getMovies(String title)
    {
        Transaction transaction = new Transaction();

        try
        {
            String url = "http://www.omdbapi.com/?i=tt3896198&apikey=41465c29&s=" + title;

            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "*/*");

            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            if (null != responseEntity && null != responseEntity.getBody())
            {
                transaction = new Gson().fromJson(responseEntity.getBody(), Transaction.class);
            }
        }
        catch (Exception e)
        {
            logger.error("Failed when getMovie: ", e);
        }

        return transaction;
    }
}
