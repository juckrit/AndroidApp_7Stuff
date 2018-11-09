package com.example.administrator.a7stuff.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.graphics.BitmapCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.a7stuff.R;
import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class StuffListItem extends BaseCustomViewGroup {

    TextView tvName;
    TextView tvPrice;
    ImageView ivImg;


    public StuffListItem(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public StuffListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public StuffListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public StuffListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        inflate(getContext(), R.layout.list_of_stuff, this);
    }

    private void initInstances() {
        // findViewById here

        ivImg = (ImageView) findViewById(R.id.ivImg);
        tvName = (TextView) findViewById(R.id.tvName);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        /*
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.StyleableName,
                defStyleAttr, defStyleRes);

        try {

        } finally {
            a.recycle();
        }
        */
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        BundleSavedState savedState = new BundleSavedState(superState);
        // Save Instance State(s) here to the 'savedState.getBundle()'
        // for example,
        // savedState.getBundle().putString("key", value);

        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        BundleSavedState ss = (BundleSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        Bundle bundle = ss.getBundle();
        // Restore State from bundle here
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width * 2 / 3;
        int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        // Child view
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);
        //Self
        setMeasuredDimension(width, height);
    }


    public void setTvName(String name) {
        this.tvName.setText(name);
    }


    public void setTvPrice(int price) {
        this.tvPrice.setText(String.valueOf(price)+ " บาท");
    }


    public void setIvImg(String path) {

        int size = 10; //minimize  as much as you want
        if (path != null) {
            Bitmap bitmapOriginal = BitmapFactory.decodeFile(path);
            if (bitmapOriginal != null) {
                int bitmapByteCount = BitmapCompat.getAllocationByteCount(bitmapOriginal);

                if (bitmapByteCount > 50000000) {
                    Bitmap bitmapsimplesize = Bitmap.createScaledBitmap(bitmapOriginal, bitmapOriginal.getWidth() / size, bitmapOriginal.getHeight() / size, true);
                    this.ivImg.setImageBitmap(bitmapsimplesize);
                } else {
//                this.ivImg.setImageBitmap(bitmapOriginal);
                    this.ivImg.setImageURI(Uri.fromFile(new File(path)));
                }
                bitmapOriginal.recycle();
            } else {
                // Toast.makeText(getContext(),"File "+ path+"Not Exist",Toast.LENGTH_SHORT).show();
                this.ivImg.setImageDrawable(getResources().getDrawable(R.drawable.coke));

            }

        } else {
//        this.ivImg.setImageURI(Uri.fromFile(new File(path))); //work but out of mem error
            //       this.ivImg.setImageBitmap(bitmapsimplesize);
            this.ivImg.setImageDrawable(getResources().getDrawable(R.drawable.coke));
        }
    }


}
