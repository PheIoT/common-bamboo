package com.pheiot.bamboo.common.utils.assertj;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;

import static com.pheiot.bamboo.common.utils.assertj.JsonPathAssert.assertThatJson;

public class JsonPathAssertTest {

    @BeforeClass
    public static void config() {
        TestConfig.setDefaults();
    }

    @Test
    public void jsonPathAsString_shouldReadStringValue() {
        DocumentContext documentContext = JsonPath.parse("{\"value\":\"foo\"}");

        assertThatJson(documentContext).jsonPathAsString("$.value").isEqualTo("foo");
    }

    @Test
    public void jsonPathAsInteger_shouldReadNumericValue() {
        DocumentContext documentContext = JsonPath.parse("{\"value\":10}");

        assertThatJson(documentContext).jsonPathAsInteger("$.value").isEqualTo(10);
    }

    @Test
    public void jsonPathAsList_shouldReadArray() {
        DocumentContext documentContext = JsonPath.parse("[1,2,3]");

        assertThatJson(documentContext).jsonPathAsListOf("$", Integer.class).containsExactly(1, 2, 3);
    }

    @Test
    public void jsonPathAsBigDecimal_shouldReadNumericValue() {
        DocumentContext documentContext = JsonPath.parse("0.3");

        assertThatJson(documentContext).jsonPathAsBigDecimal("$").isEqualTo(new BigDecimal("0.3"));
    }

    @Test
    public void jsonPathAsBoolean_shouldReadBoolean() {
        DocumentContext documentContext = JsonPath.parse("true");

        assertThatJson(documentContext).jsonPathAsBoolean("$").isTrue();
    }

    @Test
    public void jsonPath_shouldCheckForExistence() {
        DocumentContext documentContext = JsonPath.parse("{\"value\":1}");

        assertThatJson(documentContext).jsonPath("$.value").isNotNull();
    }

    @Test
    public void jsonPath_shouldCheckForNull() {
        DocumentContext documentContext = JsonPath.parse("{\"value\":null}");

        assertThatJson(documentContext).jsonPath("$.value").isNull();
    }
}
