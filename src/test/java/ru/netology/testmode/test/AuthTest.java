package ru.netology.testmode.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.testmode.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.testmode.data.DataGenerator.Registration.getUser;
import static ru.netology.testmode.data.DataGenerator.getRandomLogin;
import static ru.netology.testmode.data.DataGenerator.getRandomPassword;

class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    /*
    Test numbers:
    1st binary digit - presence of the user
    2nd binary digit - status of the user (1 = active)
    3rd binary digit - login validity
    4th binary digit - password validity
    0*** = 0111
    1000, 1100 are not applicable
     */

    @Test
    @DisplayName("1111 Should successfully login with active registered user")
    void shouldSuccessfullyLoginIfRegisteredActiveUser_1111() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login'] .button__text").click();
        $("#root span").shouldBe(Condition.visible);
        $("#root").shouldHave(Condition.text("Личный кабинет"));
    }

    @Test
    @DisplayName("0111 Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser_0111() {
        var notRegisteredUser = getUser("active");
        $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        $("[data-test-id='action-login'] .button__text").click();
        $("[data-test-id='error-notification']").shouldBe(Condition.visible);
        $(".notification__title").shouldHave(Condition.text("Ошибка"));
        $(".notification__content").shouldHave(Condition.text("Ошибка!"));
        $(".notification__content").shouldHave(Condition.text("Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("1011 Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser_1011() {
        var blockedUser = getRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        $("[data-test-id='action-login'] .button__text").click();
        $("[data-test-id='error-notification']").shouldBe(Condition.visible);
        $(".notification__title").shouldHave(Condition.text("Ошибка"));
        $(".notification__content").shouldHave(Condition.text("Ошибка!"));
        $(".notification__content").shouldHave(Condition.text("Пользователь заблокирован"));
    }

    @Test
    @DisplayName("1101 Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin_1101() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[data-test-id='login'] input").setValue(wrongLogin);
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login'] .button__text").click();
        $("[data-test-id='error-notification']").shouldBe(Condition.visible);
        $(".notification__title").shouldHave(Condition.text("Ошибка"));
        $(".notification__content").shouldHave(Condition.text("Ошибка!"));
        $(".notification__content").shouldHave(Condition.text("Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("1110 Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword_1110() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(wrongPassword);
        $("[data-test-id='action-login'] .button__text").click();
        $("[data-test-id='error-notification']").shouldBe(Condition.visible);
        $(".notification__title").shouldHave(Condition.text("Ошибка"));
        $(".notification__content").shouldHave(Condition.text("Ошибка!"));
        $(".notification__content").shouldHave(Condition.text("Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("1001 Should get error message if login with blocked registered user and wrong login")
    void shouldGetErrorIfBlockedUserAndWrongLogin_1001() {
        var blockedUser = getRegisteredUser("blocked");
        var wrongLogin = getRandomLogin();
        $("[data-test-id='login'] input").setValue(wrongLogin);
        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        $("[data-test-id='action-login'] .button__text").click();
        $("[data-test-id='error-notification']").shouldBe(Condition.visible);
        $(".notification__title").shouldHave(Condition.text("Ошибка"));
        $(".notification__content").shouldHave(Condition.text("Ошибка!"));
        $(".notification__content").shouldHave(Condition.text("Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("1010 Should get error message if login with blocked registered user and wrong password")
    void shouldGetErrorIfBlockedUserWrongPassword_1010() {
        var registeredUser = getRegisteredUser("blocked");
        var wrongPassword = getRandomPassword();
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(wrongPassword);
        $("[data-test-id='action-login'] .button__text").click();
        $("[data-test-id='error-notification']").shouldBe(Condition.visible);
        $(".notification__title").shouldHave(Condition.text("Ошибка"));
        $(".notification__content").shouldHave(Condition.text("Ошибка!"));
        $(".notification__content").shouldHave(Condition.text("Неверно указан логин или пароль"));
    }
}
