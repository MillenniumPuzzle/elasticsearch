/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */
package org.elasticsearch.xpack.sql.plugin.sql.rest;

import java.io.IOException;

import org.elasticsearch.ExceptionsHelper;
import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.common.ParseField;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.ObjectParser;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.rest.BaseRestHandler;
import org.elasticsearch.rest.BytesRestResponse;
import org.elasticsearch.rest.RestChannel;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.xpack.sql.plugin.sql.action.SqlAction;
import org.elasticsearch.xpack.sql.plugin.sql.action.SqlRequest;

import static org.elasticsearch.rest.RestRequest.Method.GET;
import static org.elasticsearch.rest.RestRequest.Method.POST;
import static org.elasticsearch.rest.RestStatus.OK;

public class RestSqlAction extends BaseRestHandler {

    public RestSqlAction(Settings settings, RestController controller) {
        super(settings);
        controller.registerHandler(GET, "/_sql", this);
        controller.registerHandler(POST, "/_sql", this);
    }

    @Override
    protected RestChannelConsumer prepareRequest(RestRequest request, NodeClient client) throws IOException {
        Payload p = null;
        
        try {
            p = Payload.from(request);
        } catch (IOException ex) {
            return channel -> error(channel, ex);
        }
        
        final String query = p.query;

        return channel -> client.executeLocally(SqlAction.INSTANCE, new SqlRequest(query, null), new CursorRestResponseListener(channel));
    }
    
    private void error(RestChannel channel, Exception ex) {
        logger.debug("failed to parse sql request", ex);
        BytesRestResponse response = null;
        try {
            response = new BytesRestResponse(channel, ex);
        } catch (IOException e) {
            response = new BytesRestResponse(OK, BytesRestResponse.TEXT_CONTENT_TYPE, ExceptionsHelper.stackTrace(e));
        }
        channel.sendResponse(response);
    }
}

class Payload {
    static final ObjectParser<Payload, Void> PARSER = new ObjectParser<>("sql/query");

    static {
        PARSER.declareString(Payload::setQuery, new ParseField("query"));
    }

    String query;

    static Payload from(RestRequest request) throws IOException {
        Payload payload = new Payload();
        try (XContentParser parser = request.contentParser()) {
            Payload.PARSER.parse(parser, payload, null);
        }

        return payload;
    }
    public void setQuery(String query) {
        this.query = query;
    }
}