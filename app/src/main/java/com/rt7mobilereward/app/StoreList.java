package com.rt7mobilereward.app;

/**
 * Created by MANIKANDAN_SETHURAJ on 2016-11-10.
 */

public class StoreList {
    private String store_name;
    private String store_address;
    private String store_id;

    public StoreList(String  store_name, String store_address, String store_id) {
        super();
        this. store_name= store_name;
        this.store_address = store_address;
        this.store_id = store_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public String getStore_address() {
        return store_address;
    }

    public String getStore_id() {
        return store_id;
    }
}
