package by.auto.service.security;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

public class PasswordEncoder {
    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordEncoder.class);

    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("security.properties"));
        } catch (final IOException ex) {
            LOGGER.error("Unable to open security.properties file!");
            LOGGER.error(ex.getMessage());
        }
    }


    public static String encode(final String password) {
        final String saltedPassword = getSaltedPassword(password, properties.getProperty("password.salt"));
        return createDigest(saltedPassword);
    }

    private static String createDigest(final String password) throws SecurityException {
        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            final String digest = String.format("%032x", new BigInteger(1, md.digest()));

            return digest;
        } catch (final NoSuchAlgorithmException ex) {
            throw new SecurityException(ex);
        }
    }

    private static String getSaltedPassword(final String password, final String salt) {
        return
                StringUtils.overlay(
                        StringUtils.swapCase(password),
                        salt,
                        3,
                        3);
    }
}
