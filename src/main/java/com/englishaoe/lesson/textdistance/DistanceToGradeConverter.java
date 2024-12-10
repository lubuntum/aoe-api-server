package com.englishaoe.lesson.textdistance;

public class DistanceToGradeConverter {
    public static double LOWER_DISTANCE = 0.8;
    public static int convert(double distance){
        return (distance >= LOWER_DISTANCE) ?  1 : 0;
    }
}
