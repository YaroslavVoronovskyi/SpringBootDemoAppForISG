package com.gmail.voronovskyi.yaroslav.isg.springboot.it;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TestContainersTest extends AbstractTestcontainers {

    @Test
    void testDbStart() {
        assertThat(MY_SQL_CONTAINER.isCreated());
        assertThat(MY_SQL_CONTAINER.isRunning());
    }
}
