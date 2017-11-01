package com.example.booking.utils;

import org.apache.ignite.cache.store.cassandra.datasource.Credentials;

/**
 * @author spetsiotis
 */
public class CassandraRegularCredentials implements Credentials {
    private static final long serialVersionUID = 0L;

    @Override
    public String getUser() {
        return CassandraHelper.getRegularUser();
    }

    @Override
    public String getPassword() {
        return CassandraHelper.getRegularPassword();
    }
}
