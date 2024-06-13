package com.example.help_me_unpack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.util.Base64;
import java.util.HashMap;
import java.util.Objects;

@SpringBootApplication
public class HelpMeUnpackApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelpMeUnpackApplication.class, args);
    }


    @Bean
    String solve(){
        String getUrl="https://hackattic.com/challenges/help_me_unpack/problem?access_token=80efb86e80666d6e";
        String postUrl="https://hackattic.com/challenges/help_me_unpack/solve?access_token=80efb86e80666d6e";
        RestTemplate rt=new RestTemplate();
        ResponseEntity<Input> response= rt.getForEntity(getUrl,Input.class);
        Input input=response.getBody();
        System.out.println(input.getBytes()+" : bytes input");

        Output output=generateOutput(input);
        System.out.println(output.toString());

        ResponseEntity<Object> postResponse= rt.postForEntity(postUrl,output,Object.class);
        System.out.println("output: "+postResponse.getBody());
        return "";
    }

    public Output generateOutput(Input input) {
        System.out.println("hello");
        byte[] byteArr= Base64.getDecoder().decode(input.getBytes());
        System.out.println("helloooooooooo");
        for(byte a:byteArr){
            System.out.println(a);
        }

        //        int
//        unsigned int
//        short
//        float
//        double
//        bingend double

        String intStr= combine(byteArr[3],byteArr[2],byteArr[1],byteArr[0]);
//        num + 1scomp(num)= 31 ones
//        add 1 =>   num + 1scomp(num) + 1= 1(31zeros)
//                    num + 2scomp =1(31zeros)
//                      2scomp=1(31zeros)-num
//        we need -num
        Integer intVal;
        if(intStr.charAt(0)=='1') {
            Integer absValue = Integer.parseInt(intStr.substring(1), 2);
            intVal = -(1 << 31) + absValue;
        }
        else{
            intVal=Integer.parseInt(intStr,2);
        }

        String uintStr= combine(byteArr[7],byteArr[6],byteArr[5],byteArr[4]);
        Long uintVal=Long.parseLong(uintStr,2);

        String shortStr= combine(byteArr[9],byteArr[8]);
        Short shortVal;
        if(shortStr.charAt(0)=='1'){
            Short absShortValue=Short.parseShort(shortStr.substring(1),2);
            shortVal= (short) (-(1<<15)+absShortValue);
        }
        else{
            shortVal=Short.parseShort(shortStr,2);
        }


//        here floats and doubles are considered positive
        String floatStr= combine(byteArr[15],byteArr[14],byteArr[13],byteArr[12]);
        Long x=Long.parseLong(floatStr,2);
        float floatVal=Float.intBitsToFloat((int) (x & 0xFFFFFFFF));

        String doubleStr= combine(byteArr[23],byteArr[22],byteArr[21],byteArr[20],byteArr[19],byteArr[18],byteArr[17],byteArr[16]);
        Long absValue1=Long.parseLong(doubleStr.substring(1),2);
        Long longVal=-(1<<63)+absValue1;
        Double doubleVal=Double.longBitsToDouble(new BigInteger(doubleStr,2).longValue());

        String doubleStr1= combine(byteArr[24],byteArr[25],byteArr[26],byteArr[27],byteArr[28],byteArr[29],byteArr[30],byteArr[31]);
        Long absValue2=Long.parseLong(doubleStr1.substring(1),2);
        Long longVal1=-(1<<63)+absValue2;
        Double beDoubleVal=Double.longBitsToDouble(new BigInteger(doubleStr1,2).longValue());



        Output output=new Output(intVal,uintVal,shortVal,floatVal,doubleVal,beDoubleVal);
        return output;
    }


    private Integer twosComplement(String intStr) {
        StringBuilder res=new StringBuilder();

        for(int i=0;i<intStr.length();i++){
            if(intStr.charAt(i)=='0'){
                res.append('1');
            }
            else res.append('0');
        }

        return Integer.parseInt(res.toString());
    }

    String combine(byte ...byteArr){
        StringBuilder resString=new StringBuilder();
//        problem: if byte 1 is 00000001 and byte 2 is 1111111110(-2) -> (00000001+1=(000000010)) (2)
//        it should form 11111110 00000001, but since Integer is 4 bytes(32 bits), it gives 11111111111111111111110 000000001
//        therefore,
        for(byte x:byteArr){
            String temp=Integer.toBinaryString(x & 0xFF);
            String zeros="";
            for(int i=0;i<8-temp.length();i++){
                zeros+="0";
            }
            resString.append(zeros+temp);
        }
        return resString.toString();
    }
}
