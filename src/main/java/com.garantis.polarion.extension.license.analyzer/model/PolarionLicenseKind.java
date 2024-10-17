package com.garantis.polarion.extension.license.analyzer.model;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum PolarionLicenseKind {

    NAMED("named"),
    CONCURRENT("concurrent");

    private final String id;

    private static final Map<String, PolarionLicenseKind> INTERNAL = Stream.of(values()).collect(Collectors.toMap(PolarionLicenseKind::getId, (v) ->{
        return v;
    }));

    private PolarionLicenseKind(String id) {
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public boolean isConcurrent() {
        return this == CONCURRENT;
    }

    public static PolarionLicenseKind getById(String id) {
        return INTERNAL.get(id);
    }

    public static PolarionLicenseKind getByBool(boolean isConcurrent) {
        return isConcurrent ? CONCURRENT : NAMED;
    }

    public static boolean isConcurrent(PolarionLicenseKind kind) {
        return kind == CONCURRENT;
    }

    public static boolean contains(String id) {
        return INTERNAL.containsKey(id);
    }
}
