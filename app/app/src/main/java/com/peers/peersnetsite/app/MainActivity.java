package com.peers.peersnetsite.app;

import android.app.Dialog;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.peers.peersnetsite.app.db.DBHelper;
import com.peers.peersnetsite.app.db.MessageDataSource;
import com.peers.peersnetsite.app.db.PeerDataSource;
import com.peers.peersnetsite.app.model.Message;
import com.peers.peersnetsite.app.model.Peer;

import java.util.UUID;

public class MainActivity extends ListActivity {
    private MessageDataSource messageDS;
    private PeerDataSource peerDS;
    private Peer peer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("my", DBHelper.tablePeerSQL);
        Log.i("my",DBHelper.tableMessageSQL);

        peerDS = new PeerDataSource(this);
        peerDS.open();

        messageDS = new MessageDataSource(this);
        messageDS.open();

        this.peer = peerDS.newPeer(UUID.randomUUID().toString(), false, "conan");
        List<Peer> peers = peerDS.getAllPeers();
        Log.i("my", "Name:" + peers.get(0).getName());
        Log.i("my", "UUID:" + peers.get(0).getUUID());
        List<Message> values = messageDS.getAllMessages();

        List<String> aux = new ArrayList<String>();
        for (Message m:values) {
            //Peer paux = peerDS.getPeer(m.getFrom_Peer_UUID());
            aux.add(m.getMessage() + "- " + m.getDate());
        }
        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Message> adapter = new ArrayAdapter<Message>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    // Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Message> adapter = (ArrayAdapter<Message>) getListAdapter();
        Message message = null;
        switch (view.getId()) {
            case R.id.add:
                String[] comments = new String[] { "A Cool Message", "Very nice message",
                        "I Hate this message man" };
                int nextInt = new Random().nextInt(3);
                // save the new comment to the database
                Date date = new Date();
                message= messageDS.newMessage(UUID.randomUUID().toString(), comments[nextInt],
                        null, Message.dateFormat.format(date), null, peer.getUUID());
                adapter.add(message);
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        messageDS.open();
        peerDS.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        messageDS.close();
        peerDS.close();
        super.onPause();
    }

}
