/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */
package org.elasticsearch.xpack.sql.cli;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.elasticsearch.xpack.sql.net.client.ConnectionConfiguration;

//
// Supports the following syntax
//
// http(s)://[host|ip]
// http(s)//[host|ip]:port/(prefix)
//

public class CliConfiguration extends ConnectionConfiguration {

    private IpAndPort ipAndPort;
    private String originalUrl;
    private String urlFile = "/";

    public CliConfiguration(String u, Properties props) {
        super(props);
        originalUrl = u;
        parseUrl(u);
    }

    private void parseUrl(String u) {
        if (u.endsWith("/")) {
            u = u.substring(0, u.length() - 1);
        }


        // remove space
        u = u.trim();

        String hostAndPort = u;

        int index = u.indexOf("://");
        if (index > 0) {
            u = u.substring(index + 3);
        }

        index = u.indexOf("/");

        //
        // parse host
        //
        if (index >= 0) {
            hostAndPort = u.substring(0, index);
            if (index + 1 < u.length()) {
                urlFile = u.substring(index);
            }
        }

        // default host
        String host = "localhost";
        // is there a host ?

        // look for port
        index = hostAndPort.indexOf(":");
        if (index > 0) {
            if (index + 1 >= hostAndPort.length()) {
                throw new IllegalArgumentException("Invalid port specified");
            }
            host = hostAndPort.substring(0, index);
            String port = hostAndPort.substring(index + 1);

            ipAndPort = new IpAndPort(host, Integer.parseInt(port));
        }
        else {
            ipAndPort = new IpAndPort(u);
        }
    }

    public URL asUrl() {
        // TODO: need to assemble all the various params here
        try {
            return new URL(isSSL() ? "https" : "http", ipAndPort.ip, port(), urlFile);
        } catch (MalformedURLException ex) {
            throw new IllegalArgumentException("Cannot connect to server " + originalUrl, ex);
        }
    }

    private int port() {
        return ipAndPort.port > 0 ? ipAndPort.port : 9200;
    }
}