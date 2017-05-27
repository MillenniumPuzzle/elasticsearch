/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */
package org.elasticsearch.xpack.sql.expression.function.scalar.script;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.xpack.sql.type.DataType;
import org.elasticsearch.xpack.sql.type.DataTypes;

import static java.lang.String.format;

public class ScriptTemplate {

    private final String template;
    private final Params params;
    // used for sorting based on scripts
    private final DataType outputType;

    public ScriptTemplate(String template) {
        this(template, Params.EMPTY, DataTypes.KEYWORD);
    }

    public ScriptTemplate(String template, Params params, DataType outputType) {
        this.template = template;
        this.params = params;
        this.outputType = outputType;
    }

    public String template() {
        return template;
    }

    public Params params() {
        return params;
    }

    public List<String> aggRefs() {
        return params.asAggRefs();
    }

    public Map<String, String> aggPaths() {
        return params.asAggPaths();
    }

    public DataType outputType() {
        return outputType;
    }

    public Script toPainless() {
        return new Script(ScriptType.INLINE, Script.DEFAULT_SCRIPT_LANG, bindTemplate(), params.asParams());
    }

    private String bindTemplate() {
        List<String> binding = params.asCodeNames();
        return binding.isEmpty() ? template : String.format(Locale.ROOT, template, binding.toArray());
    }

    @Override
    public int hashCode() {
        return Objects.hash(template, params, outputType);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        ScriptTemplate other = (ScriptTemplate) obj;
        return Objects.equals(template, other.template)
                && Objects.equals(params, other.params)
                && Objects.equals(outputType, other.outputType);
    }

    @Override
    public String toString() {
        return bindTemplate();
    }

    public static String formatTemplate(String template, Object... params) {
        return format(Locale.ROOT, template.replace("{}", "params.%%s"), params);
    }
}