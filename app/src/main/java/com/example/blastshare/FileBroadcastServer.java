package com.example.blastshare;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FileBroadcastServer extends Thread {

    public static final int PORT = 8988;
    private final File fileToSend;

    public FileBroadcastServer() {
        // For demo purposes, use a sample file
        this.fileToSend = new File("/sdcard/sample_file.txt");
    }

    public FileBroadcastServer(File fileToSend) {
        this.fileToSend = fileToSend;
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            Log.d("BroadcastServer", "Server is listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                Log.d("BroadcastServer", "Client connected: " + clientSocket.getInetAddress());

                new ClientHandler(clientSocket, fileToSend).start();
            }

        } catch (Exception e) {
            Log.e("BroadcastServer", "Error: " + e.getMessage());
        } finally {
            try {
                if (serverSocket != null)
                    serverSocket.close();
            } catch (Exception e) {
                Log.e("BroadcastServer", "Error closing server socket", e);
            }
        }
    }

    static class ClientHandler extends Thread {
        private final Socket clientSocket;
        private final File fileToSend;

        public ClientHandler(Socket socket, File file) {
            this.clientSocket = socket;
            this.fileToSend = file;
        }

        @Override
        public void run() {
            try (OutputStream outputStream = clientSocket.getOutputStream();
                 FileInputStream fileInputStream = new FileInputStream(fileToSend)) {

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fileInputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                Log.d("BroadcastServer", "File sent to client: " + clientSocket.getInetAddress());

            } catch (Exception e) {
                Log.e("BroadcastServer", "Error sending file to client", e);
            } finally {
                try {
                    clientSocket.close();
                } catch (Exception e) {
                    Log.e("BroadcastServer", "Error closing client socket", e);
                }
            }
        }
    }
}
