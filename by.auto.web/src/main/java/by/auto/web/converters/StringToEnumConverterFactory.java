package by.auto.web.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.util.StringUtils;

/**
 * @author Ihar
 * @see <a href="http://static.springsource.org/spring/docs/3.0.x/spring-framework-reference/html/validation.html#core-convert-ConverterFactory-SPI">
 * 5.5.2 ConverterFactory</a>
 */

public class StringToEnumConverterFactory implements ConverterFactory<String, Enum> {

    public <T extends Enum> Converter<String, T> getConverter(final Class<T> targetType) {
        return new StringToEnumConverter<>(targetType);
    }

    protected final class StringToEnumConverter<T extends Enum> implements Converter<String, T> {

        private final Class<T> enumType;

        public StringToEnumConverter(final Class<T> enumType) {
            this.enumType = enumType;
        }

        public T convert(final String source) {
            return (T) Enum.valueOf(this.enumType, StringUtils.capitalize(source.trim().toLowerCase()));
        }
    }
}

