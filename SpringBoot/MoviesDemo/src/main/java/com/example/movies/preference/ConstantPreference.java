package com.example.movies.preference;


public class ConstantPreference
{
    public ConstantPreference()
    {
        //Do Nothing
    }

    public static final String RESPONSE_CODE_NO_CONTENT = "204";
    public static final String RESPONSE_MESSAGE_NO_CONTENT = "We accepted your request but there is nothing to return (e.g. response is empty)";

    public static final String RESPONSE_CODE_BAD_REQUEST = "400";
    public static final String RESPONSE_MESSAGE_BAD_REQUEST = "Syntax error, e.g. request is missing required parameters/attributes or parameter values are of incorrect type";

    public static final String RESPONSE_CODE_SERVICE_UNAVAILABLE = "503";
    public static final String RESPONSE_MESSAGE_SERVICE_UNAVAILABLE = "There is planned service outage. We should specify response headers or error object with " +
            "more details on service outage";

    public static final String RESPONSE_CODE_INTERNAL_SERVER_ERROR = "500";
    public static final String RESPONSE_MESSAGE_INTERNAL_SERVER_ERROR = "Internal Server Error";
}
