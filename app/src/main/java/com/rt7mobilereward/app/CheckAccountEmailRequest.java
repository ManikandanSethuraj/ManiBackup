package com.rt7mobilereward.app;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MANIKANDAN_SETHURAJ on 2016-11-04.
 */
public class CheckAccountEmailRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "https://devdbcenter.rt7.net:7293/api/mobile/signup/checkaccount";
    private static final String API_KEY = "09Afi504Nz6gYU2hGBohMtLKG3HwAjRPF";
    private static final String ENTITY_API_KEY = "09Alb0A7k1dsXFZWyRnvtjoL8VzY2kuVP";
    private Map<String, String> prams;



    public CheckAccountEmailRequest(String email_short_signup, Response.Listener<String> listener,Response.ErrorListener errorListener){
        super(Method.POST,LOGIN_REQUEST_URL,listener,null);
        prams = new HashMap<>();
        prams.put("email",email_short_signup);

    }







    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers;
        headers = new HashMap<>();
        headers.put("apikey",API_KEY);
        headers.put("entity_api_key",ENTITY_API_KEY);
        return headers;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return prams;
    }
}
