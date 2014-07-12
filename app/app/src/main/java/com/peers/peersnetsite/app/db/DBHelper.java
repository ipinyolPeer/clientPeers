package com.peers.peersnetsite.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ipinyol on 7/12/14.
 */

public class DBHelper extends SQLiteOpenHelper{
    private String tablePeer =  "CREATE TABLE peer(" +
                                "uuid   TEXT PRIMARY KEY," +
                                "isSelf INTEGER );";

    private String tableMessage =   "CREATE TABLE message(" +
                                    "uuid   TEXT PRIMARY KEY," +
                                    "message TEXT," +
                                    "url    TEXT," +
                                    "referTo_Message_UUID TEXT," +
                                    "from_Peer_UUID TEXT," +
                                    "FOREIGN KEY(referTo_Message_UUID) REFERENCES tableMessage(uuid)," +
                                    "FOREIGN KEY(from_Peer_UUID) REFERENCES peer(uuid));";

    private String sqlCreate = tablePeer + tableMessage;
    public DBHelper(Context context, String name,
                                CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Migrate both versions
        //db.execSQL("DROP TABLE IF EXISTS Usuarios");
        //db.execSQL(sqlCreate);
    }
}
