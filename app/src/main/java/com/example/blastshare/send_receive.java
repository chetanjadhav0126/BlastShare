package com.example.blastshare;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;

public class send_receive extends AppCompatActivity {

    private Button btnSend, btnReceive;
    private String serverIP;
    private int serverPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_send_receive);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnSend = findViewById(R.id.btn_send);
        btnReceive = findViewById(R.id.btn_receive);

        // Get server IP and port from intent
        String serverInfo = getIntent().getStringExtra("SERVER_INFO");
        String[] parts = serverInfo.split(":");
        serverIP = parts[0];
        serverPort = Integer.parseInt(parts[1]);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectFileAndSend();
            }
        });

        btnReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                receiveFile();
            }
        });
    }

    private void selectFileAndSend() {
        // Implement file picker to select file
        // After selecting file, start FileClient to send file
        String filePath = "/sdcard/sample_file.txt"; // Replace with actual file path
        new FileClient(serverIP, serverPort, new File(filePath)).execute();
    }

    private void receiveFile() {
        // Start FileServer to receive file
        new FileServer(serverPort).execute();
    }
}