package com.englishaoe.lesson.utility.textdistance.methods;

import com.englishaoe.lesson.utility.textdistance.TextDistanceMethod;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.stereotype.Service;

@Service("levenshtein")
public class LevenshteinMethod implements TextDistanceMethod {
    @Override
    public double compare(String fText, String scText) {
        if (fText.length() == 0 || scText.length() == 0) return 0;
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
        int distance = levenshteinDistance.apply(fText, scText);
        int maxLength = Math.max(fText.length(), scText.length());
        return 1.0 - ((double)distance/maxLength);
    }
}
