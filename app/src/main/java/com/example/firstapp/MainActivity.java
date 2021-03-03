package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button connectBtn;
    Button showBtn;
    BluetoothAdapter ButhAdpr;
    IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectBtn = findViewById(R.id.connect);
        ButhAdpr = BluetoothAdapter.getDefaultAdapter();
        intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        showBtn = findViewById(R.id.show);

        if (!ButhAdpr.isEnabled()) {
            connectBtn.setText("CONNECT");
        } else {
            connectBtn.setText("DISCONNECT");
        }

        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ButhAdpr.isEnabled()) {
                    ButhAdpr.enable();
                    connectBtn.setText("DISCONNECT");
                    Toast.makeText(MainActivity.this, "BlueTooth is on!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    ButhAdpr.disable();
                    connectBtn.setText("CONNECT");
                    Toast.makeText(MainActivity.this, "BlueTooth is off!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ButhAdpr.isEnabled()) {

                    ButhAdpr.startDiscovery();

                    BroadcastReceiver mReceiver = new BroadcastReceiver() {
                        @Override
                        public void onReceive(Context context, Intent intent) {
                            String action = intent.getAction();

                            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                                // Get the BluetoothDevice object from the Intent
                                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                                Toast.makeText(MainActivity.this, device.getName() + "\n" + device.getAddress(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    };
                    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                    registerReceiver(mReceiver, filter);

                }else{
                    Toast.makeText(MainActivity.this, "Firstly turn BlueTooth on",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}