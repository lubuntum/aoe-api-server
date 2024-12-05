package com.englishaoe.lesson.utility.textdistance.methods;

import com.englishaoe.lesson.utility.textdistance.TextDistanceMethod;
import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.springframework.stereotype.Service;

@Service("JaroWinkler")
public class JaroWinklerMethod implements TextDistanceMethod {

    @Override
    public double compare(String fText, String scText) {
        JaroWinklerDistance jaroWinklerDistance = new JaroWinklerDistance();
        return jaroWinklerDistance.apply(fText, scText);
    }
}
