package com.pheiot.bamboo.common.utils.base;

import org.junit.Test;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class PropertiesUtilTest {

    @Test
    public void loadProperties() {
        Properties p1 = PropertiesUtil.loadFromFile("classpath://application.properties");
        assertThat(p1.get("bamboo.min")).isEqualTo("1");
        assertThat(p1.get("bamboo.max")).isEqualTo("10");

        Properties p2 = PropertiesUtil.loadFromString("bamboo.min=1\nbamboo.max=10\nisOpen=true");
        assertThat(PropertiesUtil.getInt(p2, "bamboo.min", 0)).isEqualTo(1);
        assertThat(PropertiesUtil.getInt(p2, "bamboo.max", 0)).isEqualTo(10);
        assertThat(PropertiesUtil.getInt(p2, "bamboo.maxA", 0)).isEqualTo(0);

        assertThat(PropertiesUtil.getLong(p2, "bamboo.min", 0L)).isEqualTo(1);
        assertThat(PropertiesUtil.getLong(p2, "bamboo.max", 0L)).isEqualTo(10);
        assertThat(PropertiesUtil.getLong(p2, "bamboo.maxA", 0L)).isEqualTo(0);

        assertThat(PropertiesUtil.getDouble(p2, "bamboo.min", 0d)).isEqualTo(1);
        assertThat(PropertiesUtil.getDouble(p2, "bamboo.max", 0d)).isEqualTo(10);
        assertThat(PropertiesUtil.getDouble(p2, "bamboo.maxA", 0d)).isEqualTo(0);

        assertThat(PropertiesUtil.getString(p2, "bamboo.min", "")).isEqualTo("1");
        assertThat(PropertiesUtil.getString(p2, "bamboo.max", "")).isEqualTo("10");
        assertThat(PropertiesUtil.getString(p2, "bamboo.maxA", "")).isEqualTo("");

        assertThat(PropertiesUtil.getBoolean(p2, "isOpen", false)).isTrue();
    }

}
