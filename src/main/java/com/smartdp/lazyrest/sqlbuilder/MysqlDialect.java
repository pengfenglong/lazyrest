package com.smartdp.lazyrest.sqlbuilder;

import java.io.Serializable;

public class MysqlDialect implements Dialect, Serializable {

    private static final long serialVersionUID = 1;

    public String createCountSelect(String sql) {
        return "select count(*) from (" + sql + ") a";
    }

    public String createPageSelect(String sql, int limit, int offset) {
        return String.format("%s limit %d , %d", sql, limit, offset);
    }

}
