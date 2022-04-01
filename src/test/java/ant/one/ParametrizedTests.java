package ant.one;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.awt.desktop.ScreenSleepEvent;
import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class ParametrizedTests {
    @BeforeEach
    void precondition() {
        Selenide.open("https://www.binance.com/");
    }

    @AfterEach
    void closeBrowser() {
        Selenide.closeWebDriver();
    }

    @ValueSource(strings = {"123", "qwerty"})
    @ParameterizedTest(name = "Проверка поля  email \"{0}\"")
    void emailFieldTest(String testData) {
        Selenide.$("#header_login").click();
        Selenide.$("input[name='email']").setValue(testData);
        Selenide.$("input[name='password']").click();
        Selenide.$$(".css-tmpver").find(text("Пожалуйста, введите правильный адрес электронной почты")).shouldBe(visible);
    }

    @CsvSource(value = {
            "asd@mail.ru| 111222",
            "asd@gmail.com| we-=we-="
    }, delimiter = '|')
    @ParameterizedTest(name = "Проверка отображения кода доступа \"{0}\"")
    void accessWindowTest(String email, String password) {
        Selenide.$("#header_login").click();
        Selenide.$("input[name='email']").setValue(email);
        Selenide.$("input[name='password']").setValue(password).pressEnter();
        Selenide.sleep(5000);
        Selenide.$$(".css-gvz19r").find(text("Security Verification")).shouldBe(visible);
    }

    @MethodSource(value = "mixedArgumentsTestDataProvider")
    @ParameterizedTest(name = "Name {2}")
    void mixedArgumentsTest(String email, String password) {
        Selenide.$("#header_login").click();
        Selenide.$("input[name='email']").setValue(email);
        Selenide.$("input[name='password']").setValue(password).pressEnter();
        Selenide.sleep(5000);
        Selenide.$$(".css-gvz19r").find(text("Security Verification")).shouldBe(visible);;
    }
    static Stream<Arguments> mixedArgumentsTestDataProvider() {
        return Stream.of(
                Arguments.of("asd@mail.ru", "111222"),
                Arguments.of("asd@gmail.com", "we-=we-=")
        );
    }
}
