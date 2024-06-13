package com.example.help_me_unpack;

import lombok.Data;

@Data
public class Output {
    int INT;
    long uint;
    short SHORT;
    float FLOAT;
    double DOUBLE;
    double big_endian_double;

    Output(int INT, long uint, short SHORT, float FLOAT, double DOUBLE, double big_endian_double){
        this.INT=INT;
        this.uint=uint;
        this.SHORT=SHORT;
        this.FLOAT=FLOAT;
        this.DOUBLE=DOUBLE;
        this.big_endian_double=big_endian_double;
    }
}
