package com.example.blastshare;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btn_individual_share = findViewById(R.id.btn_individual_share);
        Button btn_group_share = findViewById(R.id.btn_group_share);

        btn_individual_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iindividual = new Intent(MainActivity.this, individual.class);
                startActivity(iindividual);
            }
        });

        btn_group_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent igroup = new Intent(MainActivity.this, group.class);
                startActivity(igroup);
            }
        });
        
    }
}