package com.example.help_me_unpack;

public class Temp {
    public static void main(String[] args) {
//        Byte x=-2;
//        System.out.println(Integer.toBinaryString(x));

        String y="10000101011101111110011111010011";
//        01111010 10001000 00011000 00101100
        int absValue = Integer.parseInt(y.substring(1), 2);
        System.out.println(y.substring(1)+" : "+Integer.parseInt(y.substring(1),2));
        System.out.println(Integer.toBinaryString(1 << (y.length() - 1)) + " : "+ (1 << (y.length() - 1)) );
        int decimalInteger = -(1 << (y.length() - 1)) + absValue;
        System.out.println(decimalInteger+"kk");
        System.out.println(Long.parseLong(y,2)&0xFFFF);



    }
}
