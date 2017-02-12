package fr.insalyon.telecom.dia;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.*;

/**
 * Created by borga on 06/02/17.
 */

public class Server implements Runnable{
    private static ForkJoinPool pool;
    private static ConcurrentHashMap<String, String> chashmap = new ConcurrentHashMap<>();;
    private static ServerSocket socket;
    private static Thread serverThread;
    private static int parallelism;

    Server(int port) throws IOException {
        socket = new ServerSocket(port);
    }

    Server(int port, int parallelism, int timeout) throws IOException {
        socket = new ServerSocket(port);
        socket.setSoTimeout(timeout);
        this.parallelism = parallelism;
        pool = new ForkJoinPool(parallelism);
    }

     public void run() {
        while(true) {
            try {
                System.out.println("Waiting for client on port " +
                        socket.getLocalPort() + "...");
                Socket client = socket.accept();

                Task task = new Task(client, chashmap);
                pool.execute(task);

            }catch(SocketTimeoutException s) {
                pool.shutdown();
                System.out.println("Socket timed out!");
                break;
            }catch(IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    static ForkJoinPool getPool() {
         return pool;
    }

    static ServerSocket getSocket() {
        return socket;
    }

    void start() {
        serverThread = new Thread(this);
        serverThread.start();
    }

    static void stop() {
        try {
            serverThread.interrupt();
        } catch(Exception e) {
            System.out.println("Error on shutdown...");
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        int parallelism = Integer.parseInt(args[1]);
        int timeout = Integer.parseInt(args[2]);

        try {
            new Server(port, parallelism, timeout).start();
        } catch(Exception e) {
            System.out.println("Tell a port number!");
            e.printStackTrace();
        }
    }
}
