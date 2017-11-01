package com.example.booking.cache;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.logging.Logger;

/**
 * Created by spetsiotis on 2/21/17.
 */
@Singleton
@Startup
public class StartUpIgnition {

    private static final Logger logger = Logger.getLogger(StartUpIgnition.class.getName());
    private Ignite ignite;


    @PostConstruct
    public void setUp() {
        intializeCache();
        logger.info("Ignite cache initialized!!!");
    }

    @PreDestroy
    public void preDestroy() {
        ignite.destroyCache("");
    }

    private void intializeCache() {
        logger.info("Initiliazing ignite cache!!!");
        ignite = Ignition.start("com/example/cache/ignite-config.xml");
        ignite.getOrCreateCache("cassandraspace.showtiming");

    }
}
