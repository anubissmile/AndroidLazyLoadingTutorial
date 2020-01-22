package com.example.lazyloading.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class ListViewBaseAdapterListPOJO extends BaseAdapter {

    private Context fContext;
    private int fLayout;
    private List fObjList;
    private ListViewBaseAdapterBluePrint fBluePrint;
    private ListViewBaseAdapterAutoBindingBluePrint fBluePrintAutoBind;

    private boolean isAutoBind = false;

    public ListViewBaseAdapterListPOJO(Context context, int layout, List<?> objList, ListViewBaseAdapterBluePrint bluePrint){
        fContext = context;
        fLayout = layout;
        fObjList = objList;
        fBluePrint = bluePrint;
    }

    public ListViewBaseAdapterListPOJO(Context context, List<?> objList, ListViewBaseAdapterAutoBindingBluePrint bluePrintAutoBind){
        fContext = context;
        fObjList = objList;
        fBluePrintAutoBind = bluePrintAutoBind;
        isAutoBind = true;
    }

    public void manipulateDataSetChange(int index, Object obj){
        fObjList.set(index, obj);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return fObjList.size();
    }

    @Override
    public Object getItem(int position) {
        return fObjList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(!isAutoBind){
            LayoutInflater inflate = LayoutInflater.from(fContext);
            convertView = inflate.inflate(fLayout, parent, false);
            fBluePrint.bindConvertView(convertView);
            convertView = fBluePrint.getView(convertView, position, fObjList.get(position));
        } else {
            convertView = fBluePrintAutoBind.getView(position, fObjList.get(position));
        }
        return convertView;
    }
}
