package com.example.help_me_unpack;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class HelpMeUnpackApplicationTests {

    @Autowired
    HelpMeUnpackApplication hma;
//    @Test
//    void contextLoads() {
//    }

    @Test
    void testSolution(){
        Input input=new Input();
// veryold
//        input.setBytes("0+d3hdthF8xxpgAAiEY1Qx59rYSVZYFAQIFllYStfR4=");

//        1
//        input.setBytes("3chCi0NZS9VA6QAAQDUERCG+AUClrGpAQGqspUABviE=");

//        2
//        input.setBytes("lvJ2hHlb4dHmgwAAR6rPP1QXEtmj7ERAQETso9kSF1Q=");

//        3
//        5fRYgXtuceq8wQAAsAzpQ6o3xM+dFGBAQGAUnc/EN6o=

//        4
//        vjmDhTZj5c9CCQAA8KAhROUUGPK6doJAQIJ2uvIYFOU=

//        5
//        S8fSgnXybc3hKgAAVzelQ/iEUSnsPnxAQHw+7ClRhPg=

//        6
//        leKtjcJ3WvV9cgAAWf2zQzFrEhjt5mtAQGvm7RgSazE=

//        7
//        0RyWjqiz8sQawAAAHpyFQzb+xZ1ajmFAQGGOWp3F/jY=
        input.setBytes("0RyWjqiz8sQawAAAHpyFQzb+xZ1ajmFAQGGOWp3F/jY=");
        Output output= hma.generateOutput(input);
        System.out.println(output);

//        1
//        Output(INT=-1958557475, uint=-716482237, SHORT=-5824, FLOAT=1.1010101E29, DOUBLE=1.011111000100001E31, big_endian_double=1.0100000010100101E24)
// unsigned int 3578485059

//         2
//         Output(INT=-2072579434, uint=3521207161, SHORT=-31770, FLOAT=1.010101E31, DOUBLE=1.011101010100001E28, big_endian_double=1.001011011001101E28)
//        output: {rejected=invalid float decoded, expected 1.622383952140808 but got 1.0101009831712378e+31}

//        3
//        Output(INT=-2124876571, uint=3933302395, SHORT=-15940, FLOAT=466.09912, DOUBLE=1.101111010101001E29, big_endian_double=1.100010011001111E31)
//    output: {rejected=invalid double decoded, expected 128.6442641098377 but got 1.101111010101001e+29}

//        4
//        Output(INT=-2054997570, uint=3487916854, SHORT=-30398, FLOAT=646.51465, DOUBLE=590.8410379296662, big_endian_double=590.8410379296662)
//output: {rejected=invalid short decoded, expected 2370 but got -30398}

//        5
//        Output(INT=-2100115637, uint=3446534773, SHORT=10977, FLOAT=330.43234, DOUBLE=451.93253452151794, big_endian_double=451.93253452151794)
//output: {rejected=invalid short decoded, expected -18039 but got 10977}

//        6
//        Output(INT=-1917984107, uint=4116346818, SHORT=29309, FLOAT=359.97928, DOUBLE=223.21638110730558, big_endian_double=223.21638110730558)
//output: {rejected=invalid double decoded, expected 223.21644214246183 but got 223.21638110730558}


//        7
//        Output(INT=-1902764847, uint=3304240040, SHORT=-16358, FLOAT=267.21967, DOUBLE=140.4485005251933, big_endian_double=140.4485005251933)
//output: {rejected=invalid double decoded, expected 140.44856156034956 but got 140.4485005251933}
    }

}
