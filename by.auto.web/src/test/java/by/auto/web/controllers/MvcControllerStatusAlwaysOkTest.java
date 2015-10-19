package by.auto.web.controllers;

import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Preconfigured {@link MvcControllerTest} with 200 OK status.
 */
public class MvcControllerStatusAlwaysOkTest extends MvcControllerTest {
    @Override
    protected void preconfigureMockMvcBuilder(final DefaultMockMvcBuilder mockMvcBuilder) {
        mockMvcBuilder.alwaysExpect(status().isOk());
    }
}
