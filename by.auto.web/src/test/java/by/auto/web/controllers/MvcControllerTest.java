package by.auto.web.controllers;

import org.junit.Before;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebAppConfiguration
@ContextConfiguration(locations = {
        "classpath:/META-INF/config/mvc-test-mocks-config.xml",
        "file:src/main/webapp/WEB-INF/config/webmvc-config.xml"
})
public class MvcControllerTest {

    @Inject
    protected WebApplicationContext wac;

    protected MockMvc mockMvc;

    private DefaultMockMvcBuilder mockMvcBuilder;

    @Before
    public void buildMockMvc() {
        mockMvcBuilder = MockMvcBuilders.<DefaultMockMvcBuilder>webAppContextSetup(this.wac);
        mockMvcBuilder.alwaysDo(print());
        mockMvcBuilder.alwaysExpect(content().encoding("UTF-8"));

        preconfigureMockMvcBuilder(mockMvcBuilder);

        mockMvc = mockMvcBuilder.build();
    }

    /**
     * You can override this method, if you need to preconfigure mockMvc builder with behaviour applied to all tests.
     */
    protected void preconfigureMockMvcBuilder(final DefaultMockMvcBuilder mockMvcBuilder) {
    }
}
