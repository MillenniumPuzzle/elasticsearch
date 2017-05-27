/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */
package org.elasticsearch.xpack.sql.querydsl.agg;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.xpack.sql.querydsl.container.Sort;
import org.elasticsearch.xpack.sql.querydsl.container.Sort.Direction;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

import static org.elasticsearch.search.aggregations.AggregationBuilders.dateHistogram;

public class GroupByDateAgg extends GroupingAgg {

    private final String interval;
    
    public GroupByDateAgg(String id, String propertyPath, String fieldName, String interval) {
        this(id, propertyPath, fieldName, interval, emptyList(), emptyList(), emptyMap());
    }

    public GroupByDateAgg(String id, String propertyPath, String fieldName, String interval, List<LeafAgg> subAggs, List<PipelineAgg> subPipelines, Map<String, Direction> order) {
        super(id, propertyPath, fieldName, subAggs, subPipelines, order);
        this.interval = interval;
    }

    public String interval() {
        return interval;
    }

    @Override
    protected AggregationBuilder toGroupingAgg() {
        DateHistogramAggregationBuilder dhab = dateHistogram(id())
                .field(fieldName())
                .dateHistogramInterval(new DateHistogramInterval(interval));
        if (!order().isEmpty()) {
            for (Entry<String, Sort.Direction> entry : order().entrySet()) {
                String key = entry.getKey();
                boolean asc = entry.getValue() == Direction.ASC;
                // special cases
                if (GROUP_KEY_SORTING.equals(key)) {
                    dhab.order(BucketOrder.key(asc));
                }
                else if (GROUP_COUNT_SORTING.equals(key)) {
                    dhab.order(BucketOrder.count(asc));
                }
                else {
                    dhab.order(BucketOrder.aggregation(key, asc));
                }
            }
        }

        return dhab;
    }

    @Override
    protected GroupingAgg clone(String id, String propertyPath, String fieldName, List<LeafAgg> subAggs, List<PipelineAgg> subPipelines, Map<String, Direction> order) {
        return new GroupByDateAgg(id, propertyPath, fieldName, interval, subAggs, subPipelines, order);
    }
}
