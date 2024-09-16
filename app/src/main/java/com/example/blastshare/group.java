package com.example.blastshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class group extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_group);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btncreategroup = findViewById(R.id.btncreategroup);
        ImageButton btnjoingroup = findViewById(R.id.btnjoingroup);

        btncreategroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createGroupServer();
            }
        });

        btnjoingroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                joinGroupServer();
            }
        });
    }

    private void createGroupServer() {
        // Start the server
        new FileBroadcastServer().start();

        // Generate QR code with server IP and port
        String serverInfo = QRCodeUtils.getIPAddress() + ":" + FileBroadcastServer.PORT;
        Intent intent = new Intent(group.this, QR_code.class);
        intent.putExtra("SERVER_INFO", serverInfo);
        startActivity(intent);

        // Navigate to group management screen
        Intent mgmtIntent = new Intent(group.this, group_management.class);
        startActivity(mgmtIntent);
    }

    private void joinGroupServer() {
        // Start QR code scanner to get server IP and port
        Intent intent = new Intent(group.this, QRCodeScan.class);
        startActivityForResult(intent, 1002);
    }

    // Handle the result from QR code scanning
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1002) {
            String serverInfo = data.getStringExtra("SERVER_INFO");
            // Navigate to SendReceiveActivity with serverInfo
            Intent intent = new Intent(group.this, send_receive.class);
            intent.putExtra("SERVER_INFO", serverInfo);
            startActivity(intent);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

