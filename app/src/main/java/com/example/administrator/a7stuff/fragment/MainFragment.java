package com.example.administrator.a7stuff.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.a7stuff.Object.ListOfStuff;
import com.example.administrator.a7stuff.Object.Stuff;
import com.example.administrator.a7stuff.R;
import com.example.administrator.a7stuff.adapter.StuffAdapter;
import com.example.administrator.a7stuff.manager.Database;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class MainFragment extends Fragment {


    public interface FragmentListener {
        void onStuffClicked(Stuff stuff);
    }

    SwipeRefreshLayout mSwipeRefreshLayout;
    ListView mListView;
    StuffAdapter stuffAdapter;
    FloatingActionButton floatingActionButton;
    View textEntryView;
    Bitmap bitmap2;
    Uri imageUri;
    boolean isSelecteed;
    ListOfStuff list;


    public MainFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        mListView = (ListView) rootView.findViewById(R.id.listView);
        floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fabBtn);

        Database.getInstance().createDB();
        //Database.getInstance().createData();
        stuffAdapter = new StuffAdapter();
        updateListView();


        floatingActionButton.setOnClickListener(fabClick);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //showToast(String.valueOf(position));
                Stuff stuff = list.getStuffList().get(position);
                FragmentListener fragmentListener = (FragmentListener) getActivity();
                fragmentListener.onStuffClicked(stuff);
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Are you sure want to delete this item?");

// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Stuff stuff = list.getStuffList().get(position);
                        Database.getInstance().deleteItem(stuff.getId());
                        updateListView();


                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

                return true;
            }
        });
    }

    public void updateListView(){
        list = Database.getInstance().readData();
//        stuffAdapter = new StuffAdapter();
        stuffAdapter.setListOfStuff(list);
        mListView.setAdapter(stuffAdapter);
        stuffAdapter.notifyDataSetChanged();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        ImageButton imageButton = (ImageButton) textEntryView.findViewById(R.id.imageButton);
        imageUri = data.getData();

        String s = getRealPathFromURI(imageUri);


        //showToast(s);

//        mBitmap = null;
        try {
            bitmap2 = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (requestCode == 999) {
            isSelecteed = true;
            imageButton.setImageBitmap(bitmap2);


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

    @Override
    public void onResume() {
        super.onResume();
        //showToast("resume");
        updateListView();
    }

    /*******************
     * Listenner
     *****************/
    View.OnClickListener fabClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LayoutInflater factory = LayoutInflater.from(getContext());
            textEntryView = factory.inflate(R.layout.dialog_new_item, null);
            final EditText input1 = (EditText) textEntryView.findViewById(R.id.EditTextName);
            final EditText input2 = (EditText) textEntryView.findViewById(R.id.EditTextPrice);
            final ImageButton imageButton = (ImageButton) textEntryView.findViewById(R.id.imageButton);

//                input1.setText(fname);
//                input2.setText(lname);
//                input3.setText(String.valueOf(age));
//                input4.setText(String.valueOf(weight));
//                input5.setText(String.valueOf(height));
//                imageButton.setImageBitmap(mBitmapFromDatabase);

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    //Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    //intent.setData(uri);
                    intent.setType("image/*");
                    startActivityForResult(intent, 999);

                }

            });


            final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
            alert.setIcon(R.drawable.ic_add)
                    .setTitle("Create new Stuff")
                    .setView(textEntryView)
                    .setPositiveButton("Save",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {

                                    String nameStr = input1.getText().toString();
                                    String priceStr = input2.getText().toString();


                                    int priceToBeInsert;
                                    String nameToBeInsert;
                                    String imgPath;
                                    if (TextUtils.isEmpty(priceStr)) {
                                        priceToBeInsert = 0;
                                    } else {
                                        priceToBeInsert = Integer.valueOf(priceStr);
                                    }
                                    if (TextUtils.isEmpty(nameStr)) {
                                        nameToBeInsert = "";
                                    } else {
                                        nameToBeInsert = nameStr;
                                    }
                                    boolean isSuccess;
                                    //Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imageUri);
                                    if (isSelecteed) {
                                        //updateMemberToDb(memberId, familyId, firstName, lasttName, age, weight, height, bitmap2);
                                        imgPath = getRealPathFromURI(imageUri);
                                        isSuccess = Database.getInstance().insertNewItem(nameToBeInsert, priceToBeInsert, imgPath);
                                    } else {
                                        imgPath = null;
                                        //bitmap2 = mBitmapFromDatabase;
                                        //updateMemberToDb(memberId, familyId, firstName, lasttName, age, weight, height, bitmap2);
                                        isSuccess = Database.getInstance().insertNewItem(nameToBeInsert, priceToBeInsert, imgPath);

                                    }
                                    if (isSuccess) {
                                        showToast("Success");
                                    } else {
                                        showToast("Fail");

                                    }
                                    isSelecteed =false;

                                    updateListView();
//                                    stuffAdapter.setListOfStuff(Database.getInstance().readData());
//                                    stuffAdapter.notifyDataSetChanged();


                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    /*
                                     * User clicked cancel so do some stuff
                                     */
                                }
                            });
            alert.show();


        }

    };

    /*************
     *
     * Inner Class
     ***********/


}
