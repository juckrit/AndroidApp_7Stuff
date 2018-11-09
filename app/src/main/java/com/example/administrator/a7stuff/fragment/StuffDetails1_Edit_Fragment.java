package com.example.administrator.a7stuff.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.BitmapCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.a7stuff.Object.Stuff;
import com.example.administrator.a7stuff.R;
import com.example.administrator.a7stuff.manager.Database;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class StuffDetails1_Edit_Fragment extends Fragment {
    Stuff stuff;
    ImageView ivForEdit;
    EditText nameForEdit;
    EditText priceForEdit;
    Button btnForEdit;
    Uri imageUri;
    Bitmap bitmap2;
    private boolean isSelecteed;

    private Callbacks mCallbacks;


    public interface Callbacks {
        //Callback for when button clicked.
        public void onButtonClicked(Stuff stuff);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Activities containing this fragment must implement its callbacks
        mCallbacks = (Callbacks) context;

    }

    public StuffDetails1_Edit_Fragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static StuffDetails1_Edit_Fragment newInstance(Stuff stuff) {
        StuffDetails1_Edit_Fragment fragment = new StuffDetails1_Edit_Fragment();
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
        showToast("Created" + String.valueOf(stuff.getId()));
        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stuff_details_edit, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here

        ivForEdit = (ImageView) rootView.findViewById(R.id.ivForEdit);
        nameForEdit = (EditText) rootView.findViewById(R.id.nameForEdit);
        priceForEdit = (EditText) rootView.findViewById(R.id.priceForEdit);
        btnForEdit = (Button) rootView.findViewById(R.id.btnForEdit);
        showToast(String.valueOf(stuff.getId()));
        PushDownAnim.setPushDownAnimTo(btnForEdit)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String newNameStr = nameForEdit.getText().toString();
                        String newPriceStr = priceForEdit.getText().toString();

                        String newName;
                        int newPrice;
                        String newPath;

                        if (TextUtils.isEmpty(newNameStr)) {
                            newName = "";
                        } else {
                            newName = newNameStr;
                        }
                        if (TextUtils.isEmpty(newPriceStr)) {
                            newPrice = 0;
                        } else {
                            newPrice = Integer.valueOf(newPriceStr);
                        }

                        if (isSelecteed) {
                            newPath = getRealPathFromURI(imageUri);
                            if (Database.getInstance().updateItem(stuff.getId(), newName, newPrice, newPath)) {
                                showToast("Update Completed for id" + String.valueOf(stuff.getId()));

                            } else {
                                showToast("Update Fail" + String.valueOf(stuff.getId()));
                            }
                        } else {
                            if (Database.getInstance().updateItem(stuff.getId(), newName, newPrice, stuff.getImgPath())) {

                                showToast("Update Completed for id" + String.valueOf(stuff.getId()));
                            } else {
                                showToast("Update Fail" + String.valueOf(stuff.getId()));
                            }

                        }
                        Stuff updatedStuff = Database.getInstance().findStuff(stuff.getId());
                        mCallbacks.onButtonClicked(updatedStuff);
                    }

                });

        nameForEdit.setText(stuff.getName());
        priceForEdit.setText(String.valueOf(stuff.getPrice()));
        setIvImg(stuff.getImgPath());
        ivForEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                //Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                //intent.setData(uri);
                intent.setType("image/*");
                startActivityForResult(intent, 999);

            }

        });
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

        int size = 30; //minimize  as much as you want
        if (path != null) {
            Bitmap bitmapOriginal = BitmapFactory.decodeFile(path);
            if (bitmapOriginal != null) {
                int bitmapByteCount = BitmapCompat.getAllocationByteCount(bitmapOriginal);
                if (bitmapByteCount > 10000000) {

                    Bitmap bitmapsimplesize = Bitmap.createScaledBitmap(bitmapOriginal, bitmapOriginal.getWidth() / size, bitmapOriginal.getHeight() / size, true);

                    this.ivForEdit.setImageBitmap(bitmapsimplesize);
                } else {
//
                    this.ivForEdit.setImageBitmap(bitmapOriginal);
                }
            }

        } else {
            // Toast.makeText(getContext(),"File "+ path+"Not Exist",Toast.LENGTH_SHORT).show();
            this.ivForEdit.setImageDrawable(getResources().getDrawable(R.drawable.coke));

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }

        imageUri = data.getData();

        String s = getRealPathFromURI(imageUri);


//        mBitmap = null;
        try {
            bitmap2 = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (requestCode == 999) {
            isSelecteed = true;
            ivForEdit.setImageBitmap(bitmap2);


        }

    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = Contextor.getInstance().getContext().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    private void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

}
