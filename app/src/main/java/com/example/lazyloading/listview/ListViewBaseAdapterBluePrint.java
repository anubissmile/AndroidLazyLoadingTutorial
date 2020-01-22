package com.example.lazyloading.listview;

import android.view.View;

import org.json.JSONObject;

public interface ListViewBaseAdapterBluePrint {

    public void bindConvertView(View v);

    public View getView(View v, int pos, JSONObject data);

    public View getView(View v, int pos, Object data);
}
