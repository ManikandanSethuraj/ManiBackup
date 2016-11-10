package com.rt7mobilereward.app;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MANIKANDAN_SETHURAJ on 2016-11-02.
 */
public class LoginRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "https://devdbcenter.rt7.net:7293/api/mobile/login";
    private static final String API_KEY = "09Afi504Nz6gYU2hGBohMtLKG3HwAjRPF";
    private static final String ENTITY_API_KEY = "09Alb0A7k1dsXFZWyRnvtjoL8VzY2kuVP";
    private Map<String, String> prams;
    public String headerFile;



    public LoginRequest(String userName, String passWord, Response.Listener<String> listener, Response.ErrorListener errorListener){
        super(Method.POST,LOGIN_REQUEST_URL,listener,errorListener);
        prams = new HashMap<>();
        prams.put("username",userName);
        prams.put("pass",passWord);
    }

    public String getHeaderFile() {
        return headerFile;
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
        Log.d("params",prams.toString());
        return prams;
    }

    @Override
    protected void deliverResponse(String response) {
        super.deliverResponse(response);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        //System.out.println(Responseresponse.headers.get("a_t"));
        Log.d("Header File::",response.headers.get("a_t"));
        headerFile =  response.headers.get("a_t");
        return super.parseNetworkResponse(response);
    }


}
