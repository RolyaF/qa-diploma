package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.MainPage;
import ru.netology.page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;

public class PaymentPageTest {
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @BeforeEach
    public void setUp() {
        var url = System.getProperty("app.url") + ":" + System.getProperty("app.port") + "/";
        open(url);
    }
    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

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

    // Ошибка при вводе невалидного номера карты
    @Test
    @DisplayName("Should show error when card number - invalid")
    public void shouldShowErrorWhenCardNumberInvalid() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getInvalidCardNumber();
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
}
