package com.trust.messaging;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author NIsaev on 18.12.2019
 */

public class Player {
    private ServerSocket serverSocket;

    public Player(int port) {
        try {
            // server is listening on port
            serverSocket = new ServerSocket(port);
        } catch (IOException t) {
            t.printStackTrace();
        }
    }

    public void start() {
        // running infinite loop for getting
        // client request
        while (true) {
            Socket socket = null;

            try {
                // socket object to receive incoming client requests
                socket = serverSocket.accept();

                System.out.println("A new client is connected : " + socket);

                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                System.out.println("Assigning new thread for this client");

                // create a new thread object
                Thread t = new ClientHandler(socket, dis, dos);

                // Invoking the start() method
                t.start();

            } catch (Exception e) {
                try {
                    socket.close();
                    e.printStackTrace();
                } catch (Exception t) {
                }
            }
        }

    }
}
