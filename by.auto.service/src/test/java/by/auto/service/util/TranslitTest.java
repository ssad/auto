package by.auto.service.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TranslitTest {
    @Test
    public void toTranslit() {
        // Arrange

        // Act
        final String translit = Translit.toTranslit("Ёлки палки :) почёму нет тесТов для классА?! Why?");

        // Assert
        assertThat(translit, is("Elki palki :) pochemu net tesTov dlya klassA?! Why?"));
    }

    @Test
    public void generateTranslitName() {
        // Arrange

        // Act
        final String translitName = Translit.generateTranslitName("Привет как дела? Русский и English, спец. символы:-_%$#@()/    много пробелов и цифры 93787209378-+?");

        // Assert
        assertThat(translitName, is("privet-kak-dela-russkii-i-english-spec-simvoly-_-mnogo-probelov-i-cifry-93787209378-"));
    }
}