/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.booking.utils;

import com.datastax.driver.core.*;
import org.apache.ignite.cache.store.cassandra.datasource.DataSource;
import org.apache.ignite.cache.store.cassandra.session.pool.SessionPool;
import org.apache.ignite.internal.util.typedef.internal.U;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author spetsiotis
 */
public class CassandraHelper {
    private static final Logger logger = Logger.getLogger(CassandraHelper.class.getName());
    /** */
    private static final ResourceBundle CREDENTIALS = ResourceBundle.getBundle("com/example/cassandra/credentials");

    /** */
    private static final ResourceBundle CONNECTION = ResourceBundle.getBundle("com/example/cassandra/connection");

    /** */
//    private static final ResourceBundle KEYSPACES = ResourceBundle.getBundle("com/example/cassandra/keyspaces");

    /** */
    private static final ApplicationContext connectionContext = new ClassPathXmlApplicationContext("com/example/cassandra/connection-settings.xml");

    /** */
    private static DataSource adminDataSrc;

    /** */
    private static DataSource regularDataSrc;

    /** */
    private static Cluster adminCluster;

    /** */
    private static Cluster regularCluster;

    /** */
    private static Session adminSes;

    /** */
    private static Session regularSes;

    private CassandraHelper() {
    }

    public static String getCacheConfigPath() {
        return CONNECTION.getString("cache.config.path");
    }

    /**
     * @return
     */
    public static String getAdminUser() {
        return CREDENTIALS.getString("admin.user");
    }

    /**
     * @return
     */
    public static String getAdminPassword() {
        return CREDENTIALS.getString("admin.password");
    }

    /**
     * @return
     */
    public static String getRegularUser() {
        return CREDENTIALS.getString("regular.user");
    }

    /** */
    public static String getRegularPassword() {
        return CREDENTIALS.getString("regular.password");
    }

    /**
     * @return
     */
//    public static String[] getTestKeyspaces() {
//        return KEYSPACES.getString("keyspaces").split(",");
//    }
    public static String getContactPort() {
        logger.log(Level.INFO, "Port = {0}", CONNECTION.getString("contact.port"));
        return CONNECTION.getString("contact.port");
    }

    /**
     * @return
     */
    public static String[] getContactPointsArray() {
        String[] points = CONNECTION.getString("contact.points").split(",");
        logger.log(Level.INFO, "Cassandra points = {0}", points.length);
        if (points.length == 0)
            throw new RuntimeException("No Cassandra contact points specified");

        for (int i = 0; i < points.length; i++) {
            points[i] = points[i].trim();
            System.out.println("Cassandra point: " + points[i]);
        }
        return points;
    }

    /**
     * @return
     */
    public static List<InetAddress> getContactPoints() {
        String[] points = getContactPointsArray();

        List<InetAddress> contactPoints = new LinkedList<>();

        for (String point : points) {
            if (point.contains(":"))
                continue;

            try {
                contactPoints.add(InetAddress.getByName(point));
            } catch (Throwable e) {
                throw new IllegalArgumentException("Incorrect contact point '" + point +
                        "' specified for Cassandra cache storage", e);
            }
        }

        return contactPoints;
    }

    /**
     * @return
     */
    public static List<InetSocketAddress> getContactPointsWithPorts() {
        String[] points = getContactPointsArray();

        List<InetSocketAddress> contactPoints = new LinkedList<>();

        for (String point : points) {
            if (!point.contains(":"))
                continue;

            String[] chunks = point.split(":");

            try {
                contactPoints.add(InetSocketAddress.createUnresolved(chunks[0].trim(), Integer.parseInt(chunks[1].trim())));
                logger.log(Level.INFO, "#################Cassandra = {0}:{1}", new Object[]{chunks[0].trim(), chunks[1].trim()});
            } catch (Throwable e) {
                throw new IllegalArgumentException("Incorrect contact point '" + point +
                        "' specified for Cassandra cache storage", e);
            }
        }

        return contactPoints;
    }

//    /** */
//    public static void dropTestKeyspaces() {
//        String[] keyspaces = getTestKeyspaces();
//
//        for (String keyspace : keyspaces) {
//            try {
//                executeWithAdminCredentials("DROP KEYSPACE IF EXISTS " + keyspace + ";");
//            }
//            catch (Throwable e) {
//                throw new RuntimeException("Failed to drop keyspace: " + keyspace, e);
//            }
//        }
//    }

    /**
     * @param statement
     * @param args
     * @return
     */
    public static ResultSet executeWithAdminCredentials(String statement, Object... args) {
        if (args == null || args.length == 0)
            return adminSession().execute(statement);

        PreparedStatement ps = adminSession().prepare(statement);
        return adminSession().execute(ps.bind(args));
    }

    /**
     * @param statement
     * @param args
     * @return
     */
    @SuppressWarnings("UnusedDeclaration")
    public static ResultSet executeWithRegularCredentials(String statement, Object... args) {
        if (args == null || args.length == 0)
            return regularSession().execute(statement);

        PreparedStatement ps = regularSession().prepare(statement);
        return regularSession().execute(ps.bind(args));
    }

    /**
     * @param statement
     */
    @SuppressWarnings("UnusedDeclaration")
    public static ResultSet executeWithAdminCredentials(Statement statement) {
        return adminSession().execute(statement);
    }

    /**
     * @param statement
     * @return
     */
    @SuppressWarnings("UnusedDeclaration")
    public static ResultSet executeWithRegularCredentials(Statement statement) {
        return regularSession().execute(statement);
    }

    /** */
    public static synchronized DataSource getAdminDataSrc() {
        if (adminDataSrc != null)
            return adminDataSrc;

        return adminDataSrc = (DataSource) connectionContext.getBean("cassandraAdminDataSource");
    }

    /**
     * @return
     */
    @SuppressWarnings("UnusedDeclaration")
    public static synchronized DataSource getRegularDataSrc() {
        if (regularDataSrc != null)
            return regularDataSrc;

        return regularDataSrc = (DataSource) connectionContext.getBean("cassandraRegularDataSource");
    }

    /** */
    public static void testAdminConnection() {
        try {
            adminSession();
        } catch (Throwable e) {
            throw new RuntimeException("Failed to check admin connection to Cassandra", e);
        }
    }

    /** */
    public static void testRegularConnection() {
        try {
            regularSession();
        } catch (Throwable e) {
            throw new RuntimeException("Failed to check regular connection to Cassandra", e);
        }
    }

    /** */
    public static synchronized void releaseCassandraResources() {
        try {
            if (adminSes != null && !adminSes.isClosed())
                U.closeQuiet(adminSes);
        } finally {
            adminSes = null;
        }

        try {
            if (adminCluster != null && !adminCluster.isClosed())
                U.closeQuiet(adminCluster);
        } finally {
            adminCluster = null;
        }

        try {
            if (regularSes != null && !regularSes.isClosed())
                U.closeQuiet(regularSes);
        } finally {
            regularSes = null;
        }

        try {
            if (regularCluster != null && !regularCluster.isClosed())
                U.closeQuiet(regularCluster);
        } finally {
            regularCluster = null;
        }

        SessionPool.release();
    }

    /** */
    private static synchronized Session adminSession() {
        if (adminSes != null)
            return adminSes;

        try {
            Cluster.Builder builder = Cluster.builder();
            builder = builder.withCredentials(getAdminUser(), getAdminPassword());
            builder.addContactPoints(getContactPoints());
            builder.addContactPointsWithPorts(getContactPointsWithPorts());

            adminCluster = builder.build();
            return adminSes = adminCluster.connect();
        } catch (Throwable e) {
            throw new RuntimeException("Failed to create admin session to Cassandra database", e);
        }
    }

    /** */
    private static synchronized Session regularSession() {
        if (regularSes != null)
            return regularSes;

        try {
            Cluster.Builder builder = Cluster.builder();
            builder = builder.withCredentials(getRegularUser(), getRegularPassword());
            builder.addContactPoints(getContactPoints());
            builder.addContactPointsWithPorts(getContactPointsWithPorts());

            regularCluster = builder.build();
            return regularSes = regularCluster.connect();
        } catch (Throwable e) {
            throw new RuntimeException("Failed to create regular session to Cassandra database", e);
        }
    }
}
