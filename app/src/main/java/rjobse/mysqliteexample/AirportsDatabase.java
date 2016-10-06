package rjobse.mysqliteexample;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by devc0 on 9/13/2016.
 */
public class AirportsDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "airports.db";
    private static final int DATABASE_VERSION = 1;

    public AirportsDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // Hier de CRUD methoden
    public Cursor getAirports() {
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT icao, name, iso_country, longitude, latitude FROM airports ORDER BY iso_country";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
//        db.close();
        return c;
    }

    public Cursor getIsoCodes(){
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT DISTINCT iso_country FROM airports ORDER BY iso_country";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        db.close();
        return c;
    }
}