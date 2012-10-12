package com.netflix.frigga.cluster;

public interface AsgNameProvider<T> {

    String extractAsgName(T object);

}
