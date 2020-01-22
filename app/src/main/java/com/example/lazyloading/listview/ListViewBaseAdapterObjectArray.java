package com.example.lazyloading.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ListViewBaseAdapterObjectArray extends BaseAdapter {

    private Context fContext;
    private int fLayout;
    private Object[] fObjArr;
    private ListViewBaseAdapterBluePrint fBluePrint;
    private ListViewBaseAdapterAutoBindingBluePrint fBluePrintAutoBind;

    private boolean isAutoBinding;

    public ListViewBaseAdapterObjectArray(Context cContext, int cLayout, Object[] cObjArr, ListViewBaseAdapterBluePrint cBluePrint){
        fContext = cContext;
        fLayout = cLayout;
        fObjArr = cObjArr;
        fBluePrint = cBluePrint;
        isAutoBinding = false;
    }

    public ListViewBaseAdapterObjectArray(Object[] cObjArr, ListViewBaseAdapterAutoBindingBluePrint cBluePrint){
        fObjArr = cObjArr;
        fBluePrintAutoBind = cBluePrint;
        isAutoBinding = true;
    }



    @Override
    public int getCount() {
        return fObjArr.length;
    }

    @Override
    public Object getItem(int position) {
        return fObjArr[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(!isAutoBinding){
            LayoutInflater inflate = LayoutInflater.from(fContext);
            convertView = inflate.inflate(fLayout, parent, false);
            fBluePrint.bindConvertView(convertView);
            convertView = fBluePrint.getView(convertView, position, fObjArr[position]);
        } else {
            convertView = fBluePrintAutoBind.getView(position, fObjArr[position]);
        }
        return convertView;
    }
}
