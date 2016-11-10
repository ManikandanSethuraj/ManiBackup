package com.rt7mobilereward.app;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MANIKANDAN_SETHURAJ on 2016-11-08.
 */

public class EnrollRequest extends StringRequest {

    private static final String ENROLL_REQUEST_URL = "https://devdbcenter.rt7.net:7293/api/mobile/signup/enroll";
    private static final String API_KEY = "09Afi504Nz6gYU2hGBohMtLKG3HwAjRPF";
    private static final String ENTITY_API_KEY = "09Alb0A7k1dsXFZWyRnvtjoL8VzY2kuVP";
    private Map<String, String> prams;
    private static String headerFile;



    public EnrollRequest(String firstName, String lastName,String address1, String address2,String city,
                         String state, String zipcode, String phone,String card, String email, String pass, String date,
                         Response.Listener<String> listener, Response.ErrorListener errorListener){
        super(Method.POST,ENROLL_REQUEST_URL,listener,errorListener);
        prams = new HashMap<>();
        String id = "";
        prams.put("firstname",firstName);
        prams.put("lastname",lastName);
        prams.put("address1",address1);
        prams.put("address2",address2);
        prams.put("city",city);
        prams.put("state",state);
        prams.put("zip",zipcode);
        prams.put("phone", phone);
        prams.put("customer_card_number",card);
        prams.put("email",email);
        prams.put("password",pass);
        prams.put("date_of_birth",date);
      //  prams.put("_id",id);


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



//    @Override
//    protected Response<String> parseNetworkResponse(NetworkResponse response) {
//        int mStatusCode = response.statusCode;
//        return super.parseNetworkResponse(response);
//    }
}
