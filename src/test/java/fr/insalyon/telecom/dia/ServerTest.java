package fr.insalyon.telecom.dia;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by borga on 05/10/16.
 */
public class ServerTest {

    @Test
    public void simpleTest() throws Exception {
        assertThat(Main.run(1,4))
                .isNotEmpty()
                .isEqualTo("I");
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