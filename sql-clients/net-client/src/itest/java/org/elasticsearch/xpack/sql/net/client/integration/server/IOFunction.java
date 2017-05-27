/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */
package org.elasticsearch.xpack.sql.net.client.integration.server;

import java.io.IOException;

public interface IOFunction<T, R> {

    R apply(T t) throws IOException;
}
