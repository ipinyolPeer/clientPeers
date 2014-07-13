package com.peers.peersnetsite.app.db;

/**
 * Created by ipinyol on 7/13/14.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.peers.peersnetsite.app.model.Peer;

import java.util.ArrayList;
import java.util.List;

public class PeerDataSource {
    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private String[] allColumns = { DBHelper.COL_PEER_UUID,
                                    DBHelper.COL_PEER_ISSELF,
                                    DBHelper.COL_PEER_NAME};

    public PeerDataSource(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    /**
     * Insert a new Peer in the DB
     * @param UUID
     * @param isSelf
     * @param name
     * @return The newly created {@link Peer}
     */
    public Peer newPeer(String UUID, boolean isSelf, String name) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_PEER_UUID, UUID);
        values.put(DBHelper.COL_PEER_ISSELF, isSelf);
        values.put(DBHelper.COL_PEER_NAME, name);
        database.insert(DBHelper.TABLE_PEER, null, values);
        Cursor cursor = database.query(DBHelper.TABLE_PEER,
                allColumns, DBHelper.COL_PEER_UUID + " = '" + UUID + "'", null, null, null, null);
        cursor.moveToFirst();
        Peer newPeer = cursorToPeer(cursor);
        cursor.close();
        return newPeer;
    }

    /**
     * Get a peer given its UUID
     * @param UUID
     * @return the {@link Peer}
     */
    public Peer getPeer(String UUID) {
        Cursor cursor = database.query(DBHelper.TABLE_PEER,
                allColumns, DBHelper.COL_PEER_UUID + " = '" + UUID + "'", null, null, null, null);
        cursor.moveToFirst();
        Peer peer = cursorToPeer(cursor);
        cursor.close();
        return peer;
    }

    /**
     * Returns all Peers of the DB.
     * @return The List of {@link Peer}
     */
    public List<Peer> getAllPeers() {
        List<Peer> peers = new ArrayList<Peer>();

        Cursor cursor = database.query(DBHelper.TABLE_PEER,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Peer peer = cursorToPeer(cursor);
            peers.add(peer);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return peers;
    }

    private Peer cursorToPeer(Cursor cursor) {
        Peer peer = new Peer();
        peer.setUUID(cursor.getString(0));
        peer.setSelf(cursor.getInt(1)==1);
        peer.setName(cursor.getString(2));
        return peer;
    }

}
