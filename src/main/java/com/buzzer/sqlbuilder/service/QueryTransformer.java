package com.buzzer.sqlbuilder.service;

public interface QueryTransformer {
    StringBuilder transform(StringBuilder sql);
}
