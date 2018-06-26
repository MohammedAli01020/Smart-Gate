package com.example.mohamed.smartgate.ui;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mohamed.smartgate.R;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class BluetoothActivity extends AppCompatActivity {

    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final int REQUEST_ENABLE_BT = 506;
    private String mPassword;
    private ListView mListView;
    private ArrayAdapter<String> mAdapter;
    private BluetoothAdapter mBtAdapter;
    private String mDataToSendToBluetooth;
    private Toast mToast;

    private OutputStream outputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        mPassword = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        if (TextUtils.isEmpty(mPassword))
            throw new NullPointerException("intent not found!");

        if (mPassword.equals("321")) {
            mDataToSendToBluetooth = "f";
        } else {
            mDataToSendToBluetooth = "t";
        }

        mListView = findViewById(R.id.lv_list_bluetooth);

        ArrayList<String> strings = new ArrayList<>();

        mAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, strings);

        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String info = mAdapter.getItem(i);

                String address = null;
                if (info != null) {
                    address = info.substring(info.length() - 17);
                }
                Log.d("address", "address: " + address);

                mBtAdapter = BluetoothAdapter.getDefaultAdapter();

                BluetoothDevice device = mBtAdapter.getRemoteDevice(address);

                ParcelUuid[] uuids = device.getUuids();
                BluetoothSocket socket = null;

                try {
                    socket = device.createRfcommSocketToServiceRecord(uuids[0].getUuid());
                    socket.connect();

                    outputStream = socket.getOutputStream();

                    write(mDataToSendToBluetooth);

                    showToast("data sent successfully");

                    startActivity(new Intent(BluetoothActivity.this, LoginActivity.class));
                    finish();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);

        start();
    }

    private void showToast(String message) {
        if (mToast != null) {
            mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
            mToast.show();
        }
    }

    public void write(String s) throws IOException {
        outputStream.write(s.getBytes());
    }

/*
    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket
            // because mmSocket is final.
            mmDevice = device;

            mmSocket = getSocket(mmDevice);
        }

        public void run() {
            mBtAdapter = BluetoothAdapter.getDefaultAdapter();
            // Cancel discovery because it otherwise slows down the connection.
            mBtAdapter.cancelDiscovery();

            try {
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                mmSocket.connect();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and return.
                try {
                    mmSocket.close();
                } catch (IOException closeException) {
                    Log.e("MainActivity", "Could not close the client socket", closeException);
                }
            }


            // The connection attempt succeeded. Perform work associated with
            // the connection in a separate thread.
            //manageMyConnectedSocket(mmSocket);
        }

        // Closes the client socket and causes the thread to finish.
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e("MainActivity", "Could not close the client socket", e);
            }
        }
    }

    private BluetoothSocket getSocket(BluetoothDevice device) {
        BluetoothSocket tmp = null;
        try {
            // Get a BluetoothSocket to connect with the given BluetoothDevice.
            // MY_UUID is the app's UUID string, also used in the server code.
            tmp = device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        } catch (IOException e) {
            Log.e("MainActivity", "Socket's create() method failed", e);
        }

        return tmp;
    }

    //create new class for connect thread
    private class SendingThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        //creation of the connect thread
        SendingThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
        }

        //write method
        void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream

                if (mToast != null) {
                    mToast.cancel();
                }
                mToast = Toast.makeText(BluetoothActivity.this, "data send sucessfully", Toast.LENGTH_SHORT);
                mToast.show();

            } catch (IOException e) {
                //if you cannot write, close the application
                Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
*/

    public void start() {
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        if (!mBtAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
        // Add previously paired devices to the array
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                Log.d("bluetooth device:", device.getName() + "\n" + device.getAddress());
                mAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {

                Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
                // Add previously paired devices to the array
                if (pairedDevices.size() > 0) {
                    for (BluetoothDevice device : pairedDevices) {
                        Log.d("bluetooth device:", device.getName() + "\n" + device.getAddress());
                        mAdapter.add(device.getName() + "\n" + device.getAddress());
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }


}
