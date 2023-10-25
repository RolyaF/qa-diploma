package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import com.codeborne.selenide.junit5.TextReportExtension;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.CreditPage;
import ru.netology.page.MainPage;
import io.qameta.allure.Allure;
import ru.netology.page.PaymentPage;

import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.SQLHelper.getLastPayUserStatusMySQL;

public class PaymentCreditPageTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("Should successfully payment by approved debit card")
    void shouldSuccessfullyPaymentByApprovedDebitCard() {
        var CardInfo = DataHelper.getFirstCardNumberAndStatus();
        var PaymentPage = MainPage.openPaymentPage(CardInfo);
        PaymentPage.validPayCard(CardInfo);
        PaymentPage.verifySuccessPayVisibility();
    }

    @Test
    @DisplayName("Should show error when payment by declined debit card")
    void shouldShowErrorWhenPaymentByDeclinedDebitCard() {
        var CardInfo = DataHelper.getSecondCardNumberAndStatus();
        var PaymentPage = MainPage.openPaymentPage(CardInfo);
        PaymentPage.validPayCard(CardInfo);
        PaymentPage.verifyDeclinePayVisibility();
    }


    @Test
    @DisplayName("Should successfully payment by approved credit card")
    void shouldSuccessfullyPaymentByApprovedCreditCard() {
        var CardInfo = DataHelper.getFirstCardNumberAndStatus();
        var CreditPage = MainPage.openCreditPage(CardInfo);
        CreditPage.validPayCard(CardInfo);
        CreditPage.verifySuccessPayVisibility();
    }

    @Test
    @DisplayName("Should show error when payment by declined credit card")
    void shouldShowErrorWhenPaymentByDeclinedCreditCard() {
        var CardInfo = DataHelper.getSecondCardNumberAndStatus();
        var CreditPage = MainPage.openCreditPage(CardInfo);
        CreditPage.validPayCard(CardInfo);
        CreditPage.verifyDeclinePayVisibility();
    }


    @Test
    @DisplayName("Should show error when card number field empty in debit card")
    void shouldShowErrorWhenCardNumberFieldEmptyInDebitCard() {
        var CardInfo = DataHelper.getFirstCardNumberAndStatus();
        var PaymentPage = MainPage.openPaymentPage(CardInfo);
        PaymentPage.emptyField(CardInfo);
        PaymentPage.verifyEmptyField();
    }

    @Test
    @DisplayName("Should show error when card number field empty in credit card")
    void shouldShowErrorWhenCardNumberFieldEmptyInCreditCard() {
        var CardInfo = DataHelper.getFirstCardNumberAndStatus();
        var CreditPage = MainPage.openCreditPage(CardInfo);
        CreditPage.emptyField(CardInfo);
        CreditPage.verifyEmptyField();
    }

    @Test
    @DisplayName("Should show error when pay debit card with invalid card number")
    void shouldShowErrorWhenPayDebitCardWithInvalidCardNumber() {
        var CardInfo = DataHelper.getFirstCardNumberAndStatus();
        var PaymentPage = MainPage.openPaymentPage(CardInfo);
        PaymentPage.inValidPayCard(CardInfo);
        PaymentPage.verifyDeclinePayVisibility();
    }

    @Test
    @DisplayName("Should show error when pay credit card with invalid card number")
    void shouldShowErrorWhenPayCreditCardWithInvalidCardNumber() {
        var CardInfo = DataHelper.getFirstCardNumberAndStatus();
        var CreditPage = MainPage.openCreditPage(CardInfo);
        CreditPage.inValidPayCard(CardInfo);
        CreditPage.verifyDeclinePayVisibility();
    }

    @Test
    @DisplayName("Should get MySQL status when payment by approved debit card")
    void shouldGetMySQLStatusWhenPaymentByApprovedDebitCard() {
        var CardInfo = DataHelper.getFirstCardNumberAndStatus();
        var PaymentPage = MainPage.openPaymentPage(CardInfo);
        PaymentPage.validPayCard(CardInfo);
        var PaymentStatus = SQLHelper.getLastPayUserStatusMySQL();
        Assertions.assertEquals("APPROVED", PaymentStatus);
    }

    @Test
    @DisplayName("Should get MySQL amount when payment by approved debit card")
    void shouldGetMySQLAmountWhenPaymentByApprovedDebitCard() {
        var CardInfo = DataHelper.getFirstCardNumberAndStatus();
        var PaymentPage = MainPage.openPaymentPage(CardInfo);
        PaymentPage.validPayCard(CardInfo);
        var PaymentAmount = (SQLHelper.getLastPayUserAmountMySQL());
        Assertions.assertEquals(45000, PaymentAmount);
    }

    @Test
    @DisplayName("Should get MySQL status when payment by declined debit card")
    void shouldGetMySQLStatusWhenPaymentByDeclinedDebitCard() {
        var CardInfo = DataHelper.getSecondCardNumberAndStatus();
        var PaymentPage = MainPage.openPaymentPage(CardInfo);
        PaymentPage.validPayCard(CardInfo);
        var PaymentStatus = SQLHelper.getLastPayUserStatusMySQL();
        Assertions.assertEquals("DECLINED", PaymentStatus);
    }

    @Test
    @DisplayName("Should get MySQL amount when payment by declined debit card")
    void shouldGetMySQLAmountWhenPaymentByDeclinedDebitCard() {
        var CardInfo = DataHelper.getSecondCardNumberAndStatus();
        var PaymentPage = MainPage.openPaymentPage(CardInfo);
        PaymentPage.validPayCard(CardInfo);
        var PaymentAmount = (SQLHelper.getLastPayUserAmountMySQL());
        Assertions.assertEquals(0, PaymentAmount);
    }

    @Test
    @DisplayName("Should get MySQL status when payment by approved credit card")
    void shouldGetMySQLStatusWhenPaymentByApprovedCreditCard() {
        var CardInfo = DataHelper.getFirstCardNumberAndStatus();
        var CreditPage = MainPage.openCreditPage(CardInfo);
        CreditPage.validPayCard(CardInfo);
        var CreditStatus = SQLHelper.getLastPayOnCreditUserStatusMySQL();
        Assertions.assertEquals("APPROVED", CreditStatus);
    }

    @Test
    @DisplayName("Should get MySQL status when payment by declined credit card")
    void shouldGetMySQLStatusWhenPaymentByDeclinedCreditCard() {
        var CardInfo = DataHelper.getSecondCardNumberAndStatus();
        var CreditPage = MainPage.openCreditPage(CardInfo);
        CreditPage.validPayCard(CardInfo);
        var CreditStatus = SQLHelper.getLastPayOnCreditUserStatusMySQL();
        Assertions.assertEquals("DECLINED", CreditStatus);
    }

    @Test
    @DisplayName("Should get PostgreSQL status when payment by approved debit card")
    void shouldGetPostgreSQLStatusWhenPaymentByApprovedCard() {
        var CardInfo = DataHelper.getFirstCardNumberAndStatus();
        var PaymentPage = MainPage.openPaymentPage(CardInfo);
        PaymentPage.validPayCard(CardInfo);
        var PaymentStatus = SQLHelper.getLastPayUserStatusPostgreSQL();
        Assertions.assertEquals("APPROVED", PaymentStatus);
    }

    @Test
    @DisplayName("Should get PostgreSQL amount when payment by approved debit card")
    void shouldGetPostgreSQLAmountWhenPaymentByApprovedDebitCard() {
        var CardInfo = DataHelper.getFirstCardNumberAndStatus();
        var PaymentPage = MainPage.openPaymentPage(CardInfo);
        PaymentPage.validPayCard(CardInfo);
        var PaymentAmount = (SQLHelper.getLastPayUserAmountPostgreSQL());
        Assertions.assertEquals(45000, PaymentAmount);
    }

    @Test
    @DisplayName("Should get PostgreSQL status when payment by declined debit card")
    void shouldGetPostgreSQLStatusWhenPayFromDeclinedDebitCard() {
        var CardInfo = DataHelper.getSecondCardNumberAndStatus();
        var PaymentPage = MainPage.openPaymentPage(CardInfo);
        PaymentPage.validPayCard(CardInfo);
        var PaymentStatus = SQLHelper.getLastPayUserStatusPostgreSQL();
        Assertions.assertEquals("DECLINED", PaymentStatus);
    }

    @Test
    @DisplayName("Should get PostgreSQL amount when payment by declined debit card")
    void shouldGetPostgreSQLAmountWhenPaymentByDeclinedDebitCard() {
        var CardInfo = DataHelper.getSecondCardNumberAndStatus();
        var PaymentPage = MainPage.openPaymentPage(CardInfo);
        PaymentPage.validPayCard(CardInfo);
        var PaymentAmount = (SQLHelper.getLastPayUserAmountPostgreSQL());
        Assertions.assertEquals(0, PaymentAmount);
    }

    @Test
    @DisplayName("Should get PostgreSQL status when payment by approved credit card")
    void shouldGetPostgreSQLStatusWhenPaymentByApprovedCreditCard() {
        var CardInfo = DataHelper.getFirstCardNumberAndStatus();
        var CreditPage = MainPage.openCreditPage(CardInfo);
        CreditPage.validPayCard(CardInfo);
        var CreditStatus = SQLHelper.getLastPayOnCreditUserStatusPostgreSQL();
        Assertions.assertEquals("APPROVED", CreditStatus);
    }

    @Test
    @DisplayName("Should get PostgreSQL status when payment by declined credit card")
    void shouldGetPostgreSQLStatusWhenPaymentByDeclinedCreditCard() {
        var CardInfo = DataHelper.getSecondCardNumberAndStatus();
        var CreditPage = MainPage.openCreditPage(CardInfo);
        CreditPage.validPayCard(CardInfo);
        var CreditStatus = SQLHelper.getLastPayOnCreditUserStatusPostgreSQL();
        Assertions.assertEquals("DECLINED", CreditStatus);
    }
}