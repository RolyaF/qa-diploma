package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$x;

public class PaymentPage {
    private final SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private final SelenideElement monthField = $("[placeholder='08']");
    private final SelenideElement yearField = $("[placeholder='22']");
    private final SelenideElement cardHolderField = $$("fieldset input").get(3);
    private final SelenideElement cvcField = $("[placeholder='999']");
    private final SelenideElement continueButton = $("fieldset button");
    private final SelenideElement successNotification = $x("//*[contains(text(),'Операция одобрена Банком')]");
    private final SelenideElement errorNotification = $x("//*[contains(text(),'Ошибка! Банк отказал в проведении операции.')]");
    private final SelenideElement emptyFieldNotification = $x("//*[contains(text(),'Поле обязательно для заполнения')]");

    public PaymentPage validPayCard(DataHelper.CardInfo info) {
        cardNumberField.setValue(info.getCardNumber());
        monthField.setValue(DataHelper.generateRandomMonth());
        yearField.setValue(DataHelper.generateRandomYear());
        cardHolderField.setValue(DataHelper.generateFullName());
        cvcField.setValue(DataHelper.generateCvc());
        continueButton.click();
        return new PaymentPage();
    }
    public PaymentPage inValidPayCard(DataHelper.CardInfo info) {
        cardNumberField.setValue(DataHelper.generateCardNumber());
        monthField.setValue(DataHelper.generateRandomMonth());
        yearField.setValue(DataHelper.generateRandomYear());
        cardHolderField.setValue(DataHelper.generateFullName());
        cvcField.setValue(DataHelper.generateCvc());
        continueButton.click();
        return new PaymentPage();
    }

    public PaymentPage emptyField(DataHelper.CardInfo info) {
        continueButton.click();
        return new PaymentPage();
    }

    public void verifySuccessPayVisibility() {
        successNotification.shouldBe(visible, Duration.ofSeconds(10));
    }

    public void verifyDeclinePayVisibility() {
        errorNotification.shouldBe(visible, Duration.ofSeconds(10));
    }

    public void verifyEmptyField() {
        emptyFieldNotification.shouldBe(visible, Duration.ofSeconds(10));
    }
}