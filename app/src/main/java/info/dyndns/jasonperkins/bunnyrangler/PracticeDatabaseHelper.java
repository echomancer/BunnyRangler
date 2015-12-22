package info.dyndns.jasonperkins.bunnyrangler;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by Jason on 12/22/2015.
 */
public class PracticeDatabaseHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = String.valueOf(R.string.bunny_db_name);
    private static final int DATABASE_VERSION = 1;

    public PracticeDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    static {
        // register our models
        cupboard().register(Bunny.class);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // this will ensure that all tables are created
        cupboard().withDatabase(db).createTables();
        // add indexes and other database tweaks if you want

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this line  will upgrade your database, adding columns and new tables.
        // Note that existing columns will not be converted from what they originally were
        cupboard().withDatabase(db).upgradeTables();

        // If version upgrading from version 1 to 2, lets initialize the furColor column to be orange
        // Removing this line since it breaks the app - JTP 12/22/2015 11:48 am
        /*
        if (newVersion == 2) {
            ContentValues cv = new ContentValues();
            cv.put("furColor", "black");
            cupboard().withDatabase(db).update(Bunny.class, cv);
        }
        */

    }
}
