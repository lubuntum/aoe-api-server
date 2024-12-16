package com.englishaoe.lesson.utility.filter;

import java.util.List;
import java.util.stream.Collectors;

public class EntityFilter {
    public static interface AttributeExtractor<T,R>{
        R extract(T entity);
    }

    public static <T,R> List<R> filterEntitiesByUniqueAttribute(
            List<T> entities,
            AttributeExtractor<T, R> extractor){
        return entities
                .stream()
                .map(extractor::extract)
                .distinct()
                .collect(Collectors.toList());
    }

}
