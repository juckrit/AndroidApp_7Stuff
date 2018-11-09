package com.example.administrator.a7stuff.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;

import com.example.administrator.a7stuff.Object.ListOfStuff;
import com.example.administrator.a7stuff.Object.Stuff;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class Database {

    SQLiteDatabase database;
    Stuff stuff;
    ListOfStuff listOfStuff;

    private static Database instance;

    public static Database getInstance() {
        if (instance == null)
            instance = new Database();
        return instance;
    }

    private Context mContext;

    private Database() {
        mContext = Contextor.getInstance().getContext();
        database = mContext.openOrCreateDatabase("Database", Context.MODE_PRIVATE, null);

    }

    public void createDB() {
        database.execSQL("CREATE TABLE IF NOT EXISTS item (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT,price INTEGER,imgpath TEXT) ");
        database.execSQL("CREATE TABLE IF NOT EXISTS favorite (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT,price INTEGER,imgpath TEXT) ");
    }

    public void createData() {
        String name = "Coke";
        int price = 13;
        String imgPath = "";
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("price", price);
        cv.put("imgpath", imgPath);


        database.insert("item", null, cv);

    }

    public ListOfStuff readData() {
        Cursor c = database.rawQuery("SELECT * FROM item", null);
        int idIndex = c.getColumnIndex("id");
        int nameIndex = c.getColumnIndex("name");
        int priceIndex = c.getColumnIndex("price");
        int imgPathIndex = c.getColumnIndex("imgpath");
        //c.moveToFirst();
        listOfStuff = new ListOfStuff();
        List<Stuff> stuffs = new ArrayList<>();
        if (c.moveToFirst()) {
            stuffs.clear();
            do {
                stuff = new Stuff();
                stuff.setId(c.getInt(idIndex));
                stuff.setName(c.getString(nameIndex));
                stuff.setPrice(c.getInt(priceIndex));
                stuff.setImgPath(c.getString(imgPathIndex));

                stuffs.add(stuff);
            } while (c.moveToNext());
        } else {

        }
//        if (c != null && !c.isClosed()) {
//            c.close();
//        }
        listOfStuff.setStuffList(stuffs);
        return listOfStuff;
    }

    public boolean insertNewItem(String name, int price, String imgPath) {

        try {

            ContentValues cv = new ContentValues();
            cv.put("name", name);
            cv.put("price", price);
            cv.put("imgpath", imgPath);
            database.insert("item", null, cv);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteItem(int itemId) {
        try {
            SQLiteDatabase db = database;
            db.execSQL("DELETE FROM " + "item" + " WHERE " + "id" + "='" + itemId + "'");

            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean updateItem(int id,String newName,int newPrice , String newPaht) {
        try {

            ContentValues cv = new ContentValues();

            cv.put("name", newName);
            cv.put("price", newPrice);
            cv.put("imgpath", newPaht);

            String whereCl = "id=" + id;
            database.update("item", cv, whereCl, null);

            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Stuff findStuff(int thisId) {
        Cursor c = database.rawQuery("SELECT * FROM item WHERE id ="+thisId, null);
        int idIndex = c.getColumnIndex("id");
        int nameIndex = c.getColumnIndex("name");
        int priceIndex = c.getColumnIndex("price");
        int imgPathIndex = c.getColumnIndex("imgpath");
        //c.moveToFirst();
        Stuff findedStuff = new Stuff();
        if (c.moveToFirst()) {
            do {
                findedStuff = new Stuff();
                findedStuff.setId(c.getInt(idIndex));
                findedStuff.setName(c.getString(nameIndex));
                findedStuff.setPrice(c.getInt(priceIndex));
                findedStuff.setImgPath(c.getString(imgPathIndex));


            } while (c.moveToNext());
        } else {

        }
//        if (c != null && !c.isClosed()) {
//            c.close();
//        }

        return findedStuff;
    }

}
