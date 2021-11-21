package ru.apolonov;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class YaTest {

    @DisplayName("Поиск в ya.ru слово Selenide")
    @Tag("BLOCKER")
    @Test
    void selenideSearchTest() {
        open("https://ya.ru");
        $("#text").setValue("Selenide");
        $("button[type=submit]").click();
        $("li.serp-item").shouldHave(text("Selenide"));
    }

    @DisplayName("Поиск в ya.ru слово Allure")
    @Tag("BLOCKER")
    @Test
    void allureSearchTest() {
        open("https://ya.ru");
        $("#text").setValue("Allure");
        $("button[type=submit]").click();
        $("li.serp-item").shouldHave(text("Allure"));
    }

    @ValueSource(strings = {"Selenide", "Allure"})
    @DisplayName("Поиск в Яндекс")
    @Tag("BLOCKER")
    @ParameterizedTest(name = "Поиск в ya.ru слова {0}")
    void commonYaSearchTest(String searchQuery) {
        open("https://ya.ru");
        $("#text").setValue(searchQuery);
        $("button[type=submit]").click();
        $("li.serp-item").shouldHave(text(searchQuery));
    }

    //так никто не делает, просто пример
    @Disabled
    @ValueSource(strings = {
            "Selenide_лаконичные и стабильные UI тесты на Java",
            "Allure_Beauty Tips, Trends & Product Reviews"
    })
    @DisplayName("Поиск в Яндекс")
    @Tag("BLOCKER")
    @ParameterizedTest(name = "Поиск в ya.ru слова {0}")
    void YaSearchTest(String searchString) {
        String[] strings = searchString.split("_");
        open("https://ya.ru");
        $("#text").setValue(strings[0]);
        $("button[type=submit]").click();
        $("li.serp-item").shouldHave(text(strings[1]));
    }

    @CsvSource(value = {
            "Selenide| лаконичные и стабильные UI тесты на Java",
            "Allure| Beauty Tips, Trends & Product Reviews"
    },
    delimiter = '|')
    @DisplayName("Поиск в Яндекс")
    @Tag("BLOCKER")
    @ParameterizedTest(name = "Поиск в ya.ru слова {0} и проверка отображения текста {1}")
    void commonYandexSearchTest(String searchQuery, String expectedResult) {
        open("https://ya.ru");
        $("#text").setValue(searchQuery);
        $("button[type=submit]").click();
        $("li.serp-item").shouldHave(text(expectedResult));
    }

    static Stream<Arguments> YandexSearchTest() {
        return Stream.of(
          Arguments.of("Selenide", List.of("лаконичные и стабильные UI тесты на Java")),
          Arguments.of("Allure", List.of("Beauty Tips, Trends & Product Reviews"))
        );
    }

    @MethodSource
    @DisplayName("Поиск в Яндекс")
    @Tag("BLOCKER")
    @ParameterizedTest(name = "Поиск в ya.ru слова {0} и проверка отображения текста {1}")
    void YandexSearchTest(String searchQuery, List<String> expectedResult) {
        open("https://ya.ru");
        $("#text").setValue(searchQuery);
        $("button[type=submit]").click();
        $("li.serp-item").shouldHave(text(expectedResult.get(0)));
    }

    @EnumSource(SearchQuery.class)
    @DisplayName("Поиск в Яндекс")
    @Tag("BLOCKER")
    @ParameterizedTest(name = "Поиск в ya.ru слова {0} и проверка отображения текста {1}")
    void enumYandexSearchTest(SearchQuery searchQuery) {
        open("https://ya.ru");
        $("#text").setValue(searchQuery.name());
        $("button[type=submit]").click();
        $("li.serp-item").shouldHave(text(searchQuery.name()));
    }
}
