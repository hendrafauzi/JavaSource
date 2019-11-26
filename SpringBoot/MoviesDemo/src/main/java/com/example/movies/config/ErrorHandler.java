package com.example.movies.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Scanner;

@Component
public class ErrorHandler implements ResponseErrorHandler
{
    private static final Logger logger = LogManager.getLogger();

    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse)
    {
        return false;
    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException
    {
        //conversion logic for decoding conversion
        ByteArrayInputStream arrayInputStream = (ByteArrayInputStream) clientHttpResponse.getBody();
        Scanner scanner = new Scanner(arrayInputStream, "UTF-8");
        scanner.useDelimiter("\\Z");
        String data = "";

        if (scanner.hasNext())
            data = scanner.next();

        logger.debug("handleError: {}", data);
    }
}