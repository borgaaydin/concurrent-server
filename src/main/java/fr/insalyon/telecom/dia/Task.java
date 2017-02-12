package fr.insalyon.telecom.dia;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RecursiveAction;

/**
 * Created by borga on 08/02/17.
 */
class Task extends RecursiveAction {
    private Socket serverSocket;
    private ConcurrentHashMap chashmap;



    Task(Socket s, ConcurrentHashMap chashmap) {
        this.serverSocket = s;
        this.chashmap = chashmap;
    }

    @Override
    protected void compute() {
        try {
            System.out.println("NEW CLIENT!");

            InputStream is = serverSocket.getInputStream();
            PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
            // create new input stream reader
            InputStreamReader isr = new InputStreamReader(is);
            // create new buffered reader
            BufferedReader br = new BufferedReader(isr);

            while (true) {
                String message = br.readLine();
                System.out.println("[server] msg received : "+message);
                String[] splitMessage = message.split("\\s");

                switch (splitMessage[0]) {
                    case "PUT":
                        if(splitMessage[1] != null) {
                            String data = br.readLine();
                            chashmap.put(splitMessage[1], data);
                            System.out.println("[server] data written on chashmap : "+splitMessage[1]+", "+data);
                            System.out.println("[server] PUT response : ok");
                            out.println("ok");
                        }
                        break;
                    case "GET":
                        if(splitMessage[1] != null) {
                            System.out.println("[server] GET response (key="+splitMessage[1]+") : "+chashmap.get(splitMessage[1]));
                            out.println(chashmap.get(splitMessage[1]));
                        }
                        break;
                    case "DEL":
                        chashmap.remove(splitMessage[1]);
                        break;
                    case "KEYS":
                        chashmap.forEach((k,v)->out.println(k+", "+v));
                        break;
                    default:
                        System.out.println("Please use one of the following commands:");
                        System.out.println("PUT [key]");
                        System.out.println("GET [key]");
                        System.out.println("DEL [key]");
                        System.out.println("KEYS");
                        break;
                }
                if(message.equals("exit")) break;
            }
        }
        catch(Exception e) {
            System.out.println("buffer error! "+e);
            e.printStackTrace();
        }

    }
}
