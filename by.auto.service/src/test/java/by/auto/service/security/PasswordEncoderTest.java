package by.auto.service.security;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PasswordEncoderTest {
    @Test
    public void shouldEncodePassword() {
        final String encodedPassword = PasswordEncoder.encode("password");

        assertThat(encodedPassword, is("9d7bfa706e0332e48cfc15d40237f09b"));
    }
}
