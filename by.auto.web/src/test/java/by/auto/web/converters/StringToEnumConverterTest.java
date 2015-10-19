package by.auto.web.converters;

import by.auto.domain.common.enums.ItemStatus;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class StringToEnumConverterTest {
    @Test
    public void shouldConvertFromUpperCase() {
        @SuppressWarnings("unchecked") final
        StringToEnumConverterFactory.StringToEnumConverter<ItemStatus> converter =
                new StringToEnumConverterFactory().new StringToEnumConverter(ItemStatus.class);

        final ItemStatus itemStatus  = converter.convert("PENDING");
        assertThat(itemStatus, is(not(nullValue())));
        assertThat(itemStatus, sameInstance(ItemStatus.Pending));
    }

    @Test
    public void shouldConvertFromLowerCase() {
        @SuppressWarnings("unchecked") final
        StringToEnumConverterFactory.StringToEnumConverter<ItemStatus> converter =
                new StringToEnumConverterFactory().new StringToEnumConverter(ItemStatus.class);

        final ItemStatus itemStatus  = converter.convert("pending");
        assertThat(itemStatus, is(not(nullValue())));
        assertThat(itemStatus, sameInstance(ItemStatus.Pending));
    }

    @Test
    public void shouldConvertFromMixedCase() {
        @SuppressWarnings("unchecked") final
        StringToEnumConverterFactory.StringToEnumConverter<ItemStatus> converter =
                new StringToEnumConverterFactory().new StringToEnumConverter(ItemStatus.class);

        final ItemStatus itemStatus  = converter.convert("pEnDiNg");
        assertThat(itemStatus, is(not(nullValue())));
        assertThat(itemStatus, sameInstance(ItemStatus.Pending));
    }
}
