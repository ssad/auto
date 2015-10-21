package by.auto.web.json;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MediaTypeTest {
    @Test
    public void shouldBeUTF8() {
        assertThat(MediaType.APPLICATION_JSON_CHARSET_UTF8, is("application/json;charset=UTF-8"));
    }
}
