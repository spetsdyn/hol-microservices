package com.example.booking.cache;

import com.unknown.cache.startup.AbstractStartUpIgnition;

import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * Created by spetsiotis on 2/21/17.
 */
@Singleton
@Startup
public class StartUpIgnition extends AbstractStartUpIgnition {

    @Override
    protected String getConfigLocation() {
        return "com/unknown/cache/ignite-config.xml";
    }

    @Override
    protected boolean loadCacheOnStartup() {
        return true;
    }
}
