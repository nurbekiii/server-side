package com.trust.messaging;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

// ClientHandler class
public class ClientHandler extends Thread {

    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket socket;

    // Constructor
    public ClientHandler(Socket socket, DataInputStream dis, DataOutputStream dos) {
        this.socket = socket;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {
        String received = null;
        int counter = 0;
        // Ask user what he wants
        try {
            dos.writeUTF("[Server] What do you want?");
        } catch (Exception t) {
        }

        while (true) {
            try {
                // receive the answer from client
                received = dis.readUTF();
                counter++;

                dos.writeUTF(received + " " + counter);

            } catch (SocketException ex) {
                try {
                    System.out.println("1.Closing client with socket " + socket);
                    this.join();
                } catch (InterruptedException e) {
                }
            } catch (EOFException ex) {
                try {
                    System.out.println("1.Closing client with socket " + socket);
                    this.join();
                } catch (InterruptedException e) {
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
