/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.pointsmsgserver.point;

import com.example.pointsmsgserver.rest.PointsQueueEndpoint;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.messaging.MessagingFraction;

/**
 * @author spetsiotis
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Swarm container = new Swarm();

        container.fraction(MessagingFraction.createDefaultFraction().defaultServer((s) -> {
            s.enableRemote();
            s.jmsQueue("pointsQueue");
        }));
        //                .fraction(new NamingFraction().remoteNamingService())
        //                .fraction(new ManagementFraction()
        //                        .securityRealm("ApplicationRealm", (realm) -> {
        //                            realm.inMemoryAuthentication((authn) -> {
        //                                authn.add("admin", "password", true);
        //                            });
        //                            realm.inMemoryAuthorization((authz) -> {
        //                               authz.add("admin", "guest");
        //                            });
        //                        }));

        container.start();

        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
        deployment.addPackage(Main.class.getPackage());
        deployment.addPackage(PointsQueueEndpoint.class.getPackage());

        container.deploy(deployment);
    }
}
