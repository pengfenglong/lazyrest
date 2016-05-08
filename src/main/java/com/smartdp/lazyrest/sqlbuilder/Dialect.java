package com.smartdp.lazyrest.sqlbuilder;

public interface Dialect {

    public String createCountSelect(String sql);

    public String createPageSelect(String sql, int limit, int offset);
}