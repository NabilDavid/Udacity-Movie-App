package com.example.nabil.finalproject.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.nabil.finalproject.Models.ModelMovies;

import java.util.ArrayList;

public class DataBaseController {


    static final String    DATABASE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 1;
    public static final String DataBase_TABLE = "movie";
    //---------------------------------------------
    public static final String COLUMN_ID ="ID";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_OVERVIEW = "overview";
    public static final String COLUMN_RATING = "rating";
    public static final String COLUMN_DATE = "date";
    private final Context context;

    SQLiteDatabase sqLiteDatabase;
    DBhelper dBhelper;



    public DataBaseController(Context c)
   {
      this.context=c;      
    }

    public  class DBhelper extends SQLiteOpenHelper
    {

        public DBhelper(Context context)
        {

            super(context, DATABASE_NAME, null, DATABASE_VERSION);

        }


        public void onCreate(SQLiteDatabase db)
        {
            final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + DataBase_TABLE + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT NOT NULL, " +
                    COLUMN_IMAGE + " TEXT, " +
                    COLUMN_OVERVIEW + " TEXT, " +
                    COLUMN_RATING + " TEXT, " +
                    COLUMN_DATE + " TEXT);";

                  db.execSQL(SQL_CREATE_MOVIE_TABLE);

        }


        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)

        {
            db.execSQL("DROP TABLE IF EXISTS " +DataBase_TABLE);
             onCreate(db);

        }


    }

    public ArrayList<ModelMovies> getData()
    {
        ArrayList<ModelMovies>array=new ArrayList<>();
        String [] coulms={COLUMN_ID,COLUMN_DATE,COLUMN_IMAGE,COLUMN_OVERVIEW,COLUMN_RATING,COLUMN_TITLE};
        Cursor cursor=sqLiteDatabase.query(DataBase_TABLE,coulms,null,null,null,null,null);

        for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
        {
            ModelMovies modelMovies=new ModelMovies();
            modelMovies.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
            modelMovies.setDate( cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
            modelMovies.setPhoto( cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE)));
            modelMovies.setDesc( cursor.getString(cursor.getColumnIndex(COLUMN_OVERVIEW)));
            modelMovies.setRate(cursor.getString(cursor.getColumnIndex(COLUMN_RATING)));
            modelMovies.setID(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
            array.add(modelMovies);
        }
        return array;
    }
    public void openData()
    {
        DBhelper dBhelper=new DBhelper(context);
        sqLiteDatabase=dBhelper.getWritableDatabase();

    }

    public long insertDataBase(ModelMovies movie)
    {
            openData();
            String id = movie.getID();
            Cursor cursor = sqLiteDatabase.query(DataBaseController.DataBase_TABLE, new String[]{DataBaseController.COLUMN_ID}, DataBaseController.COLUMN_ID + "=?", new String[]{id}, null, null, null);
            if (cursor.moveToFirst()) {
                close();
                Toast.makeText(this.context, "already in favoirates", Toast.LENGTH_SHORT).show();
                return 0;
            }
            else {

                ContentValues values = new ContentValues();
                values.put(COLUMN_TITLE, movie.getTitle());
                values.put(COLUMN_IMAGE, movie.getPhoto());
                values.put(COLUMN_ID, movie.getID());
                values.put(COLUMN_OVERVIEW, movie.getDesc());
                values.put(COLUMN_DATE, movie.getDate());
                values.put(COLUMN_RATING, movie.getRate());
                long Id = sqLiteDatabase.insert(DataBase_TABLE, null, values);
                close();
                Toast.makeText(this.context, "Added to Favorate", Toast.LENGTH_SHORT).show();


                return Id;
            }
    }








    public void close(){
        sqLiteDatabase.close();
    }

}
