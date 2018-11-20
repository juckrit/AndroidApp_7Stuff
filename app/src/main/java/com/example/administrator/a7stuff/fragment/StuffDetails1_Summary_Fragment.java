package com.example.administrator.a7stuff.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.graphics.BitmapCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.a7stuff.BuildConfig;
import com.example.administrator.a7stuff.Object.Stuff;
import com.example.administrator.a7stuff.R;

import java.io.File;
import java.io.IOException;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class StuffDetails1_Summary_Fragment extends Fragment {
    Stuff stuff;
    TextView tvName;
    TextView tvPrice;
    ImageView ivImg;
    String pathForIntent;


    public StuffDetails1_Summary_Fragment() {
        super();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (pathForIntent != null) {
            inflater.inflate(R.menu.menu, menu);
            //หลักการคือ Activity มันจะสร้างเมนูตัวมันเอง (ถ้ามีการสร้าง) และมันจะมาถาม fragment ว่ามีการสร้างมั้ย ถ้ามีมันก็จะสร้างรวมกัน
            //พอสร้างเสร็จ menu ของ fragment จะไม่แสดง จนกว่าจะไปประกาศ setHasOptionsMenu(true); ใน init ของ Fragment
            MenuItem menuItem = (MenuItem) menu.findItem(R.id.action);

            ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
            shareActionProvider.setShareIntent(getSharedIntent());
        }
    }

    private Intent getSharedIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.setType("text/plain");
  //      intent.putExtra(Intent.EXTRA_SUBJECT,"Subject");
  //     intent.putExtra(Intent.EXTRA_TEXT,"Text");
        try {
            intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(getContext(),
                    BuildConfig.APPLICATION_ID + ".provider",
                    createImageFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        intent.setType("image/jpeg");
        return intent;


    }

    private File createImageFile() throws IOException {
        File image;
        String path = stuff.getImgPath();

        image = new File(stuff.getImgPath());
        return image;


    }

    @SuppressWarnings("unused")
    public static StuffDetails1_Summary_Fragment newInstance(Stuff stuff) {
        StuffDetails1_Summary_Fragment fragment = new StuffDetails1_Summary_Fragment();
        Bundle args = new Bundle();
        args.putParcelable("Stuff", stuff);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        stuff = getArguments().getParcelable("Stuff");
        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stuff_details_summary, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
        setHasOptionsMenu(true);
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here

        tvName = (TextView) rootView.findViewById(R.id.tvName);
        tvPrice = (TextView) rootView.findViewById(R.id.tvPrice);
        ivImg = (ImageView) rootView.findViewById(R.id.ivImg);
        pathForIntent = stuff.getImgPath();

        tvName.setText(stuff.getName());
        tvPrice.setText(String.valueOf(stuff.getPrice()));
        setIvImg(stuff.getImgPath());
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
    }

    private void setIvImg(String path) {

        int size = 10; //minimize  as much as you want
        if (path != null) {
            Glide.with(getActivity())
                    .load(new File(path)) // Uri of the picture
                    .into(this.ivImg);
//            Bitmap bitmapOriginal = BitmapFactory.decodeFile(path);
//            this.ivImg.setImageBitmap(bitmapOriginal);


        } else {
            // Toast.makeText(getContext(),"File "+ path+"Not Exist",Toast.LENGTH_SHORT).show();
            this.ivImg.setImageDrawable(getResources().getDrawable(R.drawable.coke));

        }

    }
//        this.ivImg.setImageURI(Uri.fromFile(new File(path))); //work but out of mem error
    //       this.ivImg.setImageBitmap(bitmapsimplesize);
}


