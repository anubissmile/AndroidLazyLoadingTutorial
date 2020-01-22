package com.example.lazyloading.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.json.JSONArray;
import org.json.JSONException;

public class ListViewBaseAdapterJSONArray extends BaseAdapter {

    private Context fContext;
    private int fLayout;

    private JSONArray fDataArr;

    private String fKeyName;

    private ListViewBaseAdapterBluePrint fBluePrint;
    private ListViewBaseAdapterAutoBindingBluePrint fBluePrintAutoBind;

    private boolean isAutoBinding;

    public ListViewBaseAdapterJSONArray(Context cContext, int cLayout, JSONArray cDataArr, String cKeyName, ListViewBaseAdapterBluePrint cBluePrint){
        fContext = cContext;
        fLayout = cLayout;
        fDataArr = cDataArr;
        fKeyName = cKeyName;
        fBluePrint = cBluePrint;
        isAutoBinding = false;
    }

    public ListViewBaseAdapterJSONArray(JSONArray cDataArr, String cKeyName, ListViewBaseAdapterAutoBindingBluePrint cBluePrint){
        fDataArr = cDataArr;
        fKeyName = cKeyName;
        fBluePrintAutoBind = cBluePrint;
        isAutoBinding = true;
    }

    @Override
    public int getCount() {
        return fDataArr.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return fDataArr.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        try {
            return fDataArr.getJSONObject(position).getInt(fKeyName);
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            if(!isAutoBinding){
                LayoutInflater inflate = LayoutInflater.from(fContext);
                convertView = inflate.inflate(fLayout, parent, false);
                fBluePrint.bindConvertView(convertView);
                convertView = fBluePrint.getView(convertView, position, fDataArr.getJSONObject(position));
            }else{
                convertView = fBluePrintAutoBind.getView(position, fDataArr.getJSONObject(position));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return convertView;
    }
}
