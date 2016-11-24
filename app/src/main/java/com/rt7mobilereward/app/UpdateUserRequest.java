package com.rt7mobilereward.app;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MANIKANDAN_SETHURAJ on 2016-11-22.
 */

public class UpdateUserRequest extends StringRequest {

    private static final String UPDATE_USER_REQUEST_URL = "https://devdbcenter.rt7.net:7293/api/mobile/user/updateuser";
    private static final String API_KEY = "09Afi504Nz6gYU2hGBohMtLKG3HwAjRPF";
    private static final String ENTITY_API_KEY = "09Alb0A7k1dsXFZWyRnvtjoL8VzY2kuVP";
    private Map<String, String> prams;
    private static String  headerFile;



    public UpdateUserRequest(String tokenValue, JSONObject objectValue,
                             Response.Listener<String> listener, Response.ErrorListener errorListener){
        super(Method.PUT,UPDATE_USER_REQUEST_URL,listener,errorListener);
        headerFile = tokenValue;
        prams = new HashMap<>();
        prams.put("user",objectValue.toString());
        Log.d("Sending Value:",objectValue.toString());
       // prams.put(objectValue.toString());

    }

//    public UpdateUserRequest(String tokenValue,String firstnameuur,String lastnameuur,String carduur,
//            String emailuur,String address1uur,String address2uur,String cityuur,String stateuur,
//                             String zipuur,String phoneuur,String dobuur,
//                             Response.Listener<String> listener, Response.ErrorListener errorListener){
//        super(String.valueOf(Method.PUT),UPDATE_USER_REQUEST_URL,listener,errorListener);
//        headerFile = tokenValue;
//        prams = new HashMap<>();
//        prams.put("customer_card_number",carduur);
//        prams.put("firstname",firstnameuur);
//        prams.put("lastname",lastnameuur);
//        prams.put("email",emailuur);
//        prams.put("address1",address1uur);
//        prams.put("address2",address2uur);
//        prams.put("city",cityuur);
//        prams.put("state",stateuur);
//        prams.put("zip",zipuur);
//        prams.put("phone",phoneuur);
//        prams.put("date_of_birth",dobuur);
//    }





    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers;
        headers = new HashMap<>();
        headers.put("apikey",API_KEY);
        headers.put("entity_api_key",ENTITY_API_KEY);
        headers.put("Authorization",headerFile);

        return headers;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Log.d("params",prams.toString());
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
