package com.rt7mobilereward.app;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MANIKANDAN_SETHURAJ on 2016-11-22.
 */

public class GetUserRequest extends StringRequest {

    private static final String GET_USER_REQUEST_URL = "https://devdbcenter.rt7.net:7293/api/mobile/user/getuser";
    private static final String API_KEY = "09Afi504Nz6gYU2hGBohMtLKG3HwAjRPF";
    private static final String ENTITY_API_KEY = "09Alb0A7k1dsXFZWyRnvtjoL8VzY2kuVP";
    private Map<String, String> prams;
    private static String headerFile;



    public GetUserRequest(String header,
                         Response.Listener<String> listener, Response.ErrorListener errorListener){
        super(Method.GET,GET_USER_REQUEST_URL,listener,errorListener);
       headerFile = header;


    }







    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers;
        headers = new HashMap<>();
        headers.put("apikey",API_KEY);
        headers.put("entity_api_key",ENTITY_API_KEY);
        headers.put("Authorization",headerFile);
        Log.d("Header Author",headerFile);
        return headers;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Log.d("All the Data::",prams.toString());
        return prams;

    }
    @Override
    protected void deliverResponse(String response) {
        Log.d("DeliverResponse",response.toString());
        String first = "{";
        String at = "\"at\"";
        String second = ":";
        String doublequotes = "\"";
        String cama = ",";
        String all = "\"all\"";
        String res = first+at+second+doublequotes+headerFile+doublequotes+cama+all+second.concat(response)+"}";
        super.deliverResponse(res);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        headerFile = response.headers.get("a_t");
        //  response.headers.put("App",head);
        Log.d("parseNet::",response.data.toString());
        return super.parseNetworkResponse(response);
    }
}
