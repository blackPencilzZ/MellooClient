package com.auvx.melloo.constant;

public interface ResourcePath {
    public interface LocalResourcePath {
        String PATH_BASE = "";

    }

    public interface NetworkResourcePath {
        String PATH_BASE = "http://www.melloo.antipixel.com/";
        String MOMENTS_NEARBY = "/moment/nearby";
    }
}
