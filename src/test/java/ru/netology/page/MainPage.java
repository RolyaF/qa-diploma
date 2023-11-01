package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {
    private static final SelenideElement buyButton = $(".button_size_m");
    private static final SelenideElement buyCreditButton = $(".button_view_extra");
    private final SelenideElement form = $("form");

    public MainPage() {
        buyButton.shouldBe(visible);
        buyCreditButton.shouldBe(visible);
        form.shouldBe(hidden);
    }
    public PaymentPage paymentButtonClick() {
        buyButton.click();
        form.shouldBe(visible);
        return new PaymentPage();
    }

    public CreditPage creditButtonClick() {
        buyCreditButton.click();
        form.shouldBe(visible);
        return new CreditPage();
    }
}