package fr.insalyon.telecom.dia;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.*;

/**
 * Created by borga on 06/02/17.
 */

public class Main {

    static void run(int port, int parallelism)throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(100000);

        ForkJoinPool pool = new ForkJoinPool(parallelism);
        ConcurrentHashMap<String, String> chashmap = new ConcurrentHashMap<>();

        while(true) {
            try {
                System.out.println("Waiting for client on port " +
                        serverSocket.getLocalPort() + "...");
                Socket client = serverSocket.accept();

                Task task = new Task(client, chashmap);
                pool.execute(task);

            }catch(SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            }catch(IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        int parallelism = Integer.parseInt(args[1]);
        try {
            run(port, parallelism);
        } catch(Exception e) {
            System.out.println("Tell a port number!");
            e.printStackTrace();
        }
    }
}
