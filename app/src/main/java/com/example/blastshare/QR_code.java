package com.example.blastshare;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.blastshare.QRCodeUtils;

public class QR_code extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_qr_code);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageView qrCodeImageView = findViewById(R.id.qrCodeImageView);
        Button scanQRCodeButton = findViewById(R.id.scanQRCodeButton);

        // Generate QR code using local IP address and display it
        String localIpAddress = QRCodeUtils.getLocalIpAddress(this);
        Bitmap qrCodeBitmap = QRCodeUtils.generateQRCode(localIpAddress);
        if (qrCodeBitmap != null) {
            qrCodeImageView.setImageBitmap(qrCodeBitmap);
        }

        // Set up scan button to trigger QR code scanning
        scanQRCodeButton.setOnClickListener(v -> {
            // Start a scanning activity using Intent
            Intent intent = new Intent(QR_code.this, QRCodeScan.class);
            startActivity(intent);
        });

    }
}