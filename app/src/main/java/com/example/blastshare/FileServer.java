package com.example.blastshare;

import android.os.AsyncTask;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer extends AsyncTask<Void, Void, String> {

    public static final int PORT = 8988;
    private int port;
    private String saveFilePath = "/sdcard/received_file.txt";

    public FileServer() {
        this.port = PORT;
    }

    public FileServer(int port) {
        this.port = port;
    }

    @Override
    protected String doInBackground(Void... params) {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            Log.d("FileServer", "Server: Socket opened");

            clientSocket = serverSocket.accept();
            Log.d("FileServer", "Server: connection done");

            InputStream inputStream = clientSocket.getInputStream();
            saveReceivedFile(inputStream, saveFilePath);

            Log.d("FileServer", "File received: " + saveFilePath);
            return saveFilePath;

        } catch (Exception e) {
            Log.e("FileServer", "Error: " + e.getMessage());
            return null;
        } finally {
            try {
                if (serverSocket != null)
                    serverSocket.close();
                if (clientSocket != null)
                    clientSocket.close();
            } catch (Exception e) {
                Log.e("FileServer", "Error closing socket", e);
            }
        }
    }

    private void saveReceivedFile(InputStream inputStream, String filePath) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, length);
            }
            fileOutputStream.close();
        } catch (Exception e) {
            Log.e("FileServer", "Error saving file", e);
        }
    }
}
