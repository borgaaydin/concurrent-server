package fr.insalyon.telecom.dia;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ForkJoinPool;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by borga on 05/10/16.
 */
public class ServerTest {
    private ForkJoinPool pool;
    private Socket client1;

    private int port = 6767;


    @Before
    public void init() {
        try {
            new Server(port, 4, 100000).start();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void simpleTest() throws Exception {

        pool = Server.getPool();
        client1 = new Socket("localhost", port);
        String message;

        BufferedReader br = new BufferedReader(new InputStreamReader(client1.getInputStream()));
        PrintWriter out = new PrintWriter(client1.getOutputStream(), true);

        out.println("PUT 1");
        out.println("Hello World!");

        message = br.readLine();

        assertThat(message)
                .isNotEmpty()
                .isEqualTo("ok");

        out.println("GET 1");
        message = br.readLine();

        assertThat(message)
                .isNotEmpty()
                .isEqualTo("Hello World!");
    }

    @After
    public void exit() {
        try {
            Server.stop();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void Test100() throws Exception {
//        assertThat(RomanNumberConverter.convert(110))
//                .isNotEmpty()
//                .isEqualTo("CX");
//    }
//
//    @Test
//    public void three() throws Exception {
//        assertThat(RomanNumberConverter.convert(3))
//                .isNotEmpty()
//                .isEqualTo("III");
//    }
//
//    @Test
//    public void six() throws Exception {
//        assertThat(RomanNumberConverter.convert(6))
//                .isNotEmpty()
//                .isEqualTo("VI");
//    }
//
////
////    @Test
////    public void TestString() throws Exception {
////        assertThat(RomanNumberConverter.convert("M"))
////                .isNotEmpty()
////                .isEqualTo("1000");
////    }
//
//    @Test
//    public void checkNull() throws Exception {
//
//    }
}