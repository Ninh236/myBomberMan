package com.ninh236.mybomberman.engine.core;

public interface Resource<T> {

    T load(final String string, final int type);

}