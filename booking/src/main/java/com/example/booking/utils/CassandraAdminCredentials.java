package com.example.booking.utils;

import org.apache.ignite.cache.store.cassandra.datasource.Credentials;

/**
 * @author spetsiotis
 */
public class CassandraAdminCredentials implements Credentials {
    /** */
    private static final long serialVersionUID = 0L;

    @Override
    public String getUser() {
        return CassandraHelper.getAdminUser();
    }

    @Override
    public String getPassword() {
        return CassandraHelper.getAdminPassword();
    }
}
