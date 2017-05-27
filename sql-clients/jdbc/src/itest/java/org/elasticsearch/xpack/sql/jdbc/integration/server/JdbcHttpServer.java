/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */
package org.elasticsearch.xpack.sql.jdbc.integration.server;

import org.elasticsearch.client.Client;
import org.elasticsearch.xpack.sql.net.client.integration.server.ProtoHttpServer;

public class JdbcHttpServer extends ProtoHttpServer {

    public JdbcHttpServer(Client client) {
        super(client, new SqlProtoHandler(client), "/jdbc/", "sql/");
    }

    @Override
    public String url() {
        return "jdbc:es://" + super.url();
    }
}