package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.devtools.v115.autofill.model.CreditCard;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.CreditPage;
import ru.netology.page.MainPage;
import ru.netology.page.PaymentPage;
import io.qameta.allure.Allure;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Selenide.open;

public class PaymentCreditPageTest {
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @BeforeEach
    public void setUp() {
        open("http://localhost:8080/");
    }
    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    // ДЕБЕТОВАЯ КАРТА
    // Успешная оплата утвержденной дебетовой картой
    @Test
    @DisplayName("Should successfully payment by approved debit card")
    public void shouldSuccessfullyPaymentByApprovedDebitCard() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getValidApprovedCardNumber();
        PaymentPage paymentPage = mainPage.paymentButtonClick();
        paymentPage.validPayCard(CardInfo);
        paymentPage.getSuccessNotification();
        Assertions.assertEquals("APPROVED", SQLHelper.getLastPayUserStatus());
    }

    //Неуспешная оплата отклоненной дебетовой карты
    @Test
    @DisplayName("Should show error when payment by declined debit card")
    void shouldShowErrorWhenPaymentByDeclinedDebitCard() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getValidDeclinedCardNumber();
        PaymentPage paymentPage = mainPage.paymentButtonClick();
        paymentPage.validPayCard(CardInfo);
        paymentPage.getErrorNotification();
        Assertions.assertEquals("DECLINED", SQLHelper.getLastPayUserStatus());
    }

    //Ошибка, если поле "Номер карты" пустое
    @Test
    @DisplayName("Should show error when card number field empty")
    void shouldShowErrorWhenCardNumberFieldEmpty() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getEmptyCardNumber();
        PaymentPage paymentPage = mainPage.paymentButtonClick();
        paymentPage.validPayCard(CardInfo);
        paymentPage.invalidCardFormat("Неверный формат");
    }

    //Ошибка, если поле "Месяц" пустое
    @Test
    @DisplayName("Should show error when month field empty")
    void shouldShowErrorWhenMonthFieldEmpty() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getEmptyMonth();
        PaymentPage paymentPage = mainPage.paymentButtonClick();
        paymentPage.validPayCard(CardInfo);
        paymentPage.invalidCardFormat("Неверный формат");
    }

    //Ошибка, если поле "Год" пустое
    @Test
    @DisplayName("Should show error when year field empty")
    void shouldShowErrorWhenYearFieldEmpty() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getEmptyYear();
        PaymentPage paymentPage = mainPage.paymentButtonClick();
        paymentPage.validPayCard(CardInfo);
        paymentPage.invalidCardFormat("Неверный формат");
    }

    //Ошибка, если поле "Владелец" пустое
    @Test
    @DisplayName("Should show error when holder field empty")
    void shouldShowErrorWhenHolderFieldEmpty() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getEmptyHolder();
        PaymentPage paymentPage = mainPage.paymentButtonClick();
        paymentPage.validPayCard(CardInfo);
        paymentPage.invalidCardFormat("Поле обязательно для заполнения");
    }

    //Ошибка, если поле "CVC" пустое
    @Test
    @DisplayName("Should show error when CVC field empty")
    void shouldShowErrorWhenCvcFieldEmpty() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getEmptyCvc();
        PaymentPage paymentPage = mainPage.paymentButtonClick();
        paymentPage.validPayCard(CardInfo);
        paymentPage.invalidCardFormat("Неверный формат");
    }

    //Ошибка, если все поля пустые
    @Test
    @DisplayName("Should show error when all fields empty")
    void shouldShowErrorWhenAllFieldsEmpty() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getEmptyAllFields();
        PaymentPage paymentPage = mainPage.paymentButtonClick();
        paymentPage.validPayCard(CardInfo);
        paymentPage.invalidCardFormat("Неверный формат");
    }

    // Запись в БД корректной суммы при оплате утвержденной дебетовой картой
    @Test
    @DisplayName("Should get amount when payment by approved debit card")
    public void shouldGetAmountWhenPaymentByApprovedDebitCard() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getValidApprovedCardNumber();
        PaymentPage paymentPage = mainPage.paymentButtonClick();
        paymentPage.validPayCard(CardInfo);
        paymentPage.getSuccessNotification();
        Assertions.assertEquals("45000", SQLHelper.getLastPayUserAmount());
    }

    // Запись в БД корректной суммы при оплате отклоненной дебетовой картой
    @Test
    @DisplayName("Should get amount when payment by declined debit card")
    public void shouldGetAmountWhenPaymentByDeclinedDebitCard() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getValidApprovedCardNumber();
        PaymentPage paymentPage = mainPage.paymentButtonClick();
        paymentPage.validPayCard(CardInfo);
        paymentPage.getSuccessNotification();
        Assertions.assertEquals("0", SQLHelper.getLastPayUserAmount());
    }

    // КРЕДИТНАЯ КАРТА
    // Успешная оплата утвержденной кредитной картой
    @Test
    @DisplayName("Should successfully payment by approved credit card")
    public void shouldSuccessfullyPaymentByApprovedCreditCard() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getValidApprovedCardNumber();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.validPayCard(CardInfo);
        creditPage.getSuccessNotification();
        Assertions.assertEquals("APPROVED", SQLHelper.getLastPayOnCreditUserStatus());
    }

    //Неуспешная оплата отклоненной кредитной картой
    @Test
    @DisplayName("Should show error when payment by declined credit card")
    void shouldShowErrorWhenPaymentByDeclinedCreditCard() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getValidDeclinedCardNumber();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.validPayCard(CardInfo);
        creditPage.getErrorNotification();
        Assertions.assertEquals("DECLINED", SQLHelper.getLastPayOnCreditUserStatus());
    }

    //Ошибка, если поле "Номер карты" пустое
    @Test
    @DisplayName("Should show error when card number field empty in credit")
    void shouldShowErrorWhenCardNumberFieldEmptyInCredit() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getEmptyCardNumber();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.validPayCard(CardInfo);
        creditPage.invalidCardFormat("Неверный формат");
    }

    //Ошибка, если поле "Месяц" пустое
    @Test
    @DisplayName("Should show error when month field empty in credit")
    void shouldShowErrorWhenMonthFieldEmptyInCredit() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getEmptyMonth();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.validPayCard(CardInfo);
        creditPage.invalidCardFormat("Неверный формат");
    }

    //Ошибка, если поле "Год" пустое
    @Test
    @DisplayName("Should show error when year field empty in credit")
    void shouldShowErrorWhenYearFieldEmptyInCredit() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getEmptyYear();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.validPayCard(CardInfo);
        creditPage.invalidCardFormat("Неверный формат");
    }

    //Ошибка, если поле "Владелец" пустое
    @Test
    @DisplayName("Should show error when holder field empty in credit")
    void shouldShowErrorWhenHolderFieldEmptyInCredit() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getEmptyHolder();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.validPayCard(CardInfo);
        creditPage.invalidCardFormat("Поле обязательно для заполнения");
    }

    //Ошибка, если поле "CVC" пустое
    @Test
    @DisplayName("Should show error when CVC field empty in credit")
    void shouldShowErrorWhenCvcFieldEmptyInCredit() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getEmptyCvc();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.validPayCard(CardInfo);
        creditPage.invalidCardFormat("Неверный формат");
    }

    //Ошибка, если все поля пустые
    @Test
    @DisplayName("Should show error when all fields empty in credit")
    void shouldShowErrorWhenAllFieldsEmptyInCredit() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getEmptyAllFields();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.validPayCard(CardInfo);
        creditPage.invalidCardFormat("Неверный формат");
    }

    // Запись в БД корректной суммы при оплате утвержденной кредитной картой
    @Test
    @DisplayName("Should get amount when payment by approved credit card")
    public void shouldGetAmountWhenPaymentByApprovedCreditCard() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getValidApprovedCardNumber();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.validPayCard(CardInfo);
        creditPage.getSuccessNotification();
        Assertions.assertEquals("45000", SQLHelper.getLastPayUserAmount());
    }

    // Запись в БД корректной суммы при оплате отклоненной дебетовой картой
    @Test
    @DisplayName("Should get amount when payment by declined credit card")
    public void shouldGetAmountWhenPaymentByDeclinedCreditCard() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getValidApprovedCardNumber();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.validPayCard(CardInfo);
        creditPage.getSuccessNotification();
        Assertions.assertEquals("0", SQLHelper.getLastPayUserAmount());
    }
}
