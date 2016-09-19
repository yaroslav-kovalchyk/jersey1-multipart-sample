/*
 * Copyright © 2005 - 2016. TIBCO Software Inc. All Rights Reserved.
 */
package com.jaspersoft.jasperserver.client.sample.multipart.legacy;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.MultiPart;

import javax.ws.rs.core.MediaType;

/**
 * <p></p>
 *
 * @author yaroslav.kovalchyk
 * @version $Id$
 */
public class Application {
    public static void main(String ... args){
        Client client = Client.create();
        final String jrsUrl = "http://build-master.jaspersoft.com:5580/jrs-pro-feature-full-domain-api";
//        final String jrsUrl = "http://localhost:8080/jasperserver-pro";
        WebResource webResource = client.resource(jrsUrl + "/rest_v2/resources/public" +
                "?j_password=superuser&j_username=superuser");
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        MultiPart multipartEntity = new FormDataMultiPart()
                .field("resource", classloader.getResourceAsStream("resource.json"),
                        MediaType.valueOf("application/repository.domain+json"))
                .field("securityFile", classloader.getResourceAsStream("securitySchema.xml"),
                        MediaType.valueOf("application/xml"))
                .field("bundles.bundle[0]", classloader.getResourceAsStream("supermart_domain.properties"),
                        MediaType.valueOf("application/properties"))
                .field("bundles.bundle[1]", classloader.getResourceAsStream("supermart_domain_es.properties"),
                        MediaType.valueOf("application/properties"));
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE)
                .accept("application/repository.domain+json").post(ClientResponse.class, multipartEntity);
        System.out.println("Response status: " + response.getStatus());
        final String s = response.getEntity(String.class);
        System.out.println("Response body: " + s);

    }
}
