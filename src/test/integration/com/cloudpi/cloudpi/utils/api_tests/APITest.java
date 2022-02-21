package com.cloudpi.cloudpi.utils.api_tests;

import com.cloudpi.cloudpi.utils.api_tests.junit_config.ControllerTestCaseNameGenerator;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@ActiveProfiles({"test", "controller-test"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Sql(scripts = {"/sql/drop.sql", "/sql/create.sql", "/sql/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayNameGeneration(ControllerTestCaseNameGenerator.class)

@Retention(RetentionPolicy.RUNTIME)
public @interface APITest {
}
