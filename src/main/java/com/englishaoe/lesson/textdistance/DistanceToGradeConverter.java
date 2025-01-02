package com.englishaoe.lesson.textdistance;

public class DistanceToGradeConverter {
    public static double LOWER_DISTANCE = 0.9;
    public static int convert(double distance){
        return (distance >= LOWER_DISTANCE) ?  1 : 0;
    }
}
