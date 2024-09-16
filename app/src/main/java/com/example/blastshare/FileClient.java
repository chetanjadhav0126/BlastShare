package com.example.blastshare;

import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class FileClient extends AsyncTask<Void, Void, String> {

    private final String serverIP;
    private final int serverPort;
    private final File fileToSend;

    public FileClient(String serverIP, int serverPort, File fileToSend) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.fileToSend = fileToSend;
    }

    @Override
    protected String doInBackground(Void... params) {
        Socket socket = null;
        try {
            InetAddress serverAddress = InetAddress.getByName(serverIP);
            socket = new Socket(serverAddress, serverPort);

            OutputStream outputStream = socket.getOutputStream();
            sendFile(fileToSend, outputStream);

            Log.d("FileClient", "File sent: " + fileToSend.getName());
            return "File sent";

        } catch (Exception e) {
            Log.e("FileClient", "Error: " + e.getMessage());
            return null;
        } finally {
            try {
                if (socket != null)
                    socket.close();
            } catch (Exception e) {
                Log.e("FileClient", "Error closing socket", e);
            }
        }
    }

    private void sendFile(File file, OutputStream outputStream) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            fileInputStream.close();
            outputStream.close();
        } catch (Exception e) {
            Log.e("FileClient", "Error sending file", e);
        }
    }
}
