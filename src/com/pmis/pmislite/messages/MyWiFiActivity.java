package com.pmis.pmislite.messages;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import com.pmis.pmislite.R;
import com.pmis.pmislite.projects.NewProjectActivity;
import com.pmis.pmislite.projects.ProjectListActivity;
import com.pmis.pmislite.selected.PmisSelectedProjectAccordianActivity;
import com.pmis.pmislite.teams.TeamListActivity;
 
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
 
public class MyWiFiActivity extends Activity {
     
	WifiP2pManager mManager;
	Channel mChannel;
	BroadcastReceiver mReceiver;
    IntentFilter mIntentFilter;
    
    private Button mDiscoverButton;
    private Button mConnectButton;
    private TextView mDevices;
    
    public ArrayAdapter mAdapter;
    private ArrayList<WifiP2pDevice> mDeviceList = new ArrayList<WifiP2pDevice>();
    WifiP2pDevice mSelectedDevice;
    
    protected static final int CHOOSE_FILE_RESULT_CODE = 20;
    int flag=0;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.wifi_direct_view);
         
 //       mDiscover = (Button) findViewById(R.id.wifi_direct_discover_button);
  //      mDiscover.setOnClickListener(this);

   //     mDevices = (TextView) findViewById(R.id.wifi_direct_peer_devices_list);

        
        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);//mChannel is used for app connects to wifi-direct
        mReceiver = new WiFiDirectBroadcastReceiver(mManager, mChannel, this);
        
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        
        //mDiscover = (Button) findViewById(R.id.wifi_direct_discover_button);
        mDiscoverButton.setOnClickListener(mDiscoverButtonHandler);
        
      //mConnectButton = (Button) findViewById(R.id.wifi_direct_connect_button);
        mConnectButton.setOnClickListener(mConnectButtonHandler);
        //display the devices and select one
        
       //obtain a peer from the WifiP2pDeviceList
       
        WifiP2pDevice device = null;
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
        
        mManager.connect(mChannel, config, new ActionListener() {

            @Override
            public void onSuccess() {
                //success logic
            	
            	FileServerAsyncTask task=new FileServerAsyncTask (MyWiFiActivity.this.getApplicationContext(),null);
            }

            @Override
            public void onFailure(int reason) {
                //failure logic
            }
        });

    }  

    View.OnClickListener mConnectButtonHandler = new View.OnClickListener() {

        public void onClick(View v) {
                // Picking the first device found on the network.
                WifiP2pDevice device = mSelectedDevice;

                WifiP2pConfig config = new WifiP2pConfig();
                config.deviceAddress = device.deviceAddress;
                config.wps.setup = WpsInfo.PBC;

                mManager.connect(mChannel, config, new ActionListener() {

                    @Override
                    public void onSuccess() {
                        // WiFiDirectBroadcastReceiver will notify us. Ignore for now.
                    }

                    @Override
                    public void onFailure(int reason) {
                        Toast.makeText(MyWiFiActivity.this, "Connect failed. Retry.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
    };

    View.OnClickListener mDiscoverButtonHandler = new View.OnClickListener() {

        public void onClick(View v) {
        	mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() {
                    
                }

                @Override
                public void onFailure(int reasonCode) {
                    
                }
            });
            

        }
    };


    public void sendData()
	{
		Context context = this.getApplicationContext();
		String host = null;
		int port = 0;
		int len;
		Socket socket = new Socket();
		byte buf[]  = new byte[1024];
		//....
		try {
		    /**
		     * Create a client socket with the host,
		     * port, and timeout information.
		     */
		    socket.bind(null);
		    socket.connect((new InetSocketAddress(host, port)), 500);

		    /**
		     * Create a byte stream from a JPEG file and pipe it to the output stream
		     * of the socket. This data will be retrieved by the server device.
		     */
		    OutputStream outputStream = socket.getOutputStream();
		    ContentResolver cr = context.getContentResolver();
		    InputStream inputStream = null;
		    inputStream = cr.openInputStream(Uri.parse("path/to/picture.jpg"));
		    while ((len = inputStream.read(buf)) != -1) {
		        outputStream.write(buf, 0, len);
		    }
		    outputStream.close();
		    inputStream.close();
		} catch (FileNotFoundException e) {
		    //catch logic
		} catch (IOException e) {
		    //catch logic
		}

		/**
		 * Clean up any open sockets when done
		 * transferring or if an exception occurred.
		 */
		finally {
		    if (socket != null) {
		        if (socket.isConnected()) {
		            try {
		                socket.close();
		            } catch (IOException e) {
		                //catch logic
		            }
		        }
		    }
		}
	}
    
    /* register the broadcast receiver with the intent values to be matched */
	@Override
	protected void onResume() {
	    super.onResume();
	    mReceiver = new WiFiDirectBroadcastReceiver(mManager, mChannel, this);
	    registerReceiver(mReceiver, mIntentFilter);
	}
	/* unregister the broadcast receiver */
	
	@Override
	protected void onPause() {
	    super.onPause();
	    unregisterReceiver(mReceiver);
	}
        	
}