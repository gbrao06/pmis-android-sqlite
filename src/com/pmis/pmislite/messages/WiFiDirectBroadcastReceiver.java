package com.pmis.pmislite.messages;

import java.net.InetAddress;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.ConnectionInfoListener;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

/**
 * A BroadcastReceiver that notifies of important Wi-Fi p2p events.
 */
@SuppressLint("ValidFragment") public class WiFiDirectBroadcastReceiver extends BroadcastReceiver {

    private WifiP2pManager mManager;
    private Channel mChannel;
    private MyWiFiActivity mActivity;
    
    //available peerList
    private ArrayList<WifiP2pDevice> mPeerList=new ArrayList<WifiP2pDevice>();
    
    public WiFiDirectBroadcastReceiver(WifiP2pManager manager, Channel channel,
            MyWiFiActivity activity) {
        super();
        this.mManager = manager;
        this.mChannel = channel;
        this.mActivity = activity;
    }



	@Override
	public void onReceive(Context arg0, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Check to see if Wi-Fi is enabled and notify appropriate activity
        	 int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
             if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                 // Wifi Direct is enabled
            	 Toast.makeText(mActivity, "Wi-Fi Direct is enabled.", Toast.LENGTH_SHORT).show();
             } else {
                 // Wi-Fi Direct is not enabled
            	 Toast.makeText(mActivity, "Wi-Fi Direct is disabled.", Toast.LENGTH_SHORT).show();
             }
        } 
        else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            // Call WifiP2pManager.requestPeers() to get a list of current peers
        	// request available peers from the wifi p2p manager. This is an
            // asynchronous call and the calling activity is notified with a
            // callback on PeerListListener.onPeersAvailable()
        	 if (mManager != null) {
                 mManager.requestPeers(mChannel, mPeerListListener);
             }
            // Log.d(WiFiDirectActivity.TAG, "P2P peers changed");
             
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            // Respond to new connection or disconnections
        	if (mManager == null) {
                return;
            }

            NetworkInfo networkInfo = (NetworkInfo) intent
                    .getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);

            if (networkInfo.isConnected()) {

                // We are connected with the other device, request connection
                // info to find group owner IP

                mManager.requestConnectionInfo(mChannel, mConnectionInfoListener);
            }
            
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            // Respond to this device's wifi state changing
     }            
            
}//end of receive method
	
        
        private PeerListListener mPeerListListener = new PeerListListener() {
            @Override
            public void onPeersAvailable(WifiP2pDeviceList peerList) {

                // Out with the old, in with the new.
                mPeerList.clear();
                mPeerList.addAll(peerList.getDeviceList());
                
                // If an AdapterView is backed by this data, notify it
                // of the change.  For instance, if you have a ListView of available
                // peers, trigger an update.
             //   ((WiFiPeerListAdapter) getListAdapter()).notifyDataSetChanged();
              //  if (mPeerList.size() == 0) {
               //     Log.d(MyWiFiActivity.TAG, "No devices found");
                //    return;
                //}
            }
        };
        
        private ConnectionInfoListener mConnectionInfoListener = new ConnectionInfoListener() {
        	@Override
            public void onConnectionInfoAvailable(final WifiP2pInfo info) {

                // InetAddress from WifiP2pInfo struct.
        		String hostAddress=info.groupOwnerAddress.getHostAddress();
                //InetAddress groupOwnerAddress = info.groupOwnerAddress.getHostAddress();

                // After the group negotiation, we can determine the group owner.
                if (info.groupFormed && info.isGroupOwner) {
                    // Do whatever tasks are specific to the group owner.
                    // One common case is creating a server thread and accepting
                    // incoming connections.
                } else if (info.groupFormed) {
                    // The other device acts as the client. In this case,
                    // you'll want to create a client thread that connects to the group
                    // owner.
                }
            }
       };
    


}//end of broadcast receiver

