package com.rt7mobilereward.app;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MANIKANDAN_SETHURAJ on 2016-11-10.
 */

public class GetStoreList extends StringRequest {

    private static final String STORE_REQUEST_URL = "https://devdbcenter.rt7.net:7293/api/mobile/store/list";
    private static final String API_KEY = "09Afi504Nz6gYU2hGBohMtLKG3HwAjRPF";
    private static final String ENTITY_API_KEY = "09Alb0A7k1dsXFZWyRnvtjoL8VzY2kuVP";
    private String id;
    public GetStoreList(Response.Listener listener,Response.ErrorListener errorListener){
        super(Method.GET,STORE_REQUEST_URL,listener,errorListener);

    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        return null;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers;
        headers = new HashMap<>();
        headers.put("apikey",API_KEY);
        headers.put("entity_api_key",ENTITY_API_KEY);
        String idpart = "rt7login auth=";
        String idfull = idpart.concat(id);
        headers.put("Authorization",idfull);
        return headers;
    }
}
