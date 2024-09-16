package com.example.blastshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class individual extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_individual);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btncreate_server = findViewById(R.id.btncreate_server);
        ImageButton btnjoin_server = findViewById(R.id.btnjoin_server);

        btncreate_server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createServer();
            }
        });
        btnjoin_server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                joinServer();
            }
        });
    }
    
    private void createServer() {
        // Start the server
        new FileServer().execute();

        // Generate QR code with server IP and port
        String serverInfo = QRCodeUtils.getIPAddress() + ":" + FileServer.PORT;
        Intent intent = new Intent(individual.this, QR_code.class);
        intent.putExtra("SERVER_INFO", serverInfo);
        startActivity(intent);
    }

    private void joinServer() {
        // Start QR code scanner to get server IP and port
        Intent intent = new Intent(individual.this, QRCodeScan.class);
        startActivityForResult(intent, 1001);
    }

    // Handle the result from QR code scanning
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1001) {
            String serverInfo = data.getStringExtra("SERVER_INFO");
            // Navigate to SendReceiveActivity with serverInfo
            Intent intent = new Intent(individual.this, send_receive.class);
            intent.putExtra("SERVER_INFO", serverInfo);
            startActivity(intent);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}