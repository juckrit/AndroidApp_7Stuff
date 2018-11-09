package com.example.administrator.a7stuff.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;

import com.example.administrator.a7stuff.Object.ListOfStuff;
import com.example.administrator.a7stuff.Object.Stuff;
import com.example.administrator.a7stuff.view.StuffListItem;

public class StuffAdapter extends BaseAdapter {
    ListOfStuff listOfStuff;

    public ListOfStuff getListOfStuff() {
        return listOfStuff;
    }

    public void setListOfStuff(ListOfStuff listOfStuff) {
        this.listOfStuff = listOfStuff;
    }

    @Override
    public int getCount() {
        return listOfStuff.getStuffList().size();
    }

    @Override
    public Object getItem(int position) {
        return listOfStuff.getStuffList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        StuffListItem item;
        if (convertView != null) {
            item = (StuffListItem) convertView;
        } else {
            item = new StuffListItem(parent.getContext());
        }
        Stuff stuff = (Stuff) getItem(position);
        item.setTvName(stuff.getName());
        item.setTvPrice(stuff.getPrice());
        item.setIvImg(stuff.getImgPath());
//        if (lastPositionInteger.getValue() < position) {
//            Animation animation = AnimationUtils.loadAnimation(parent.getContext(), R.anim.up_from_bottom);
//            item.startAnimation(animation);
//            lastPositionInteger.setValue(position);
//        }
        return item;
    }
}
