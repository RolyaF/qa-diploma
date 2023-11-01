package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;


public class DataHelper {

    private DataHelper() {
    }
    private static final Faker fakerRu = new Faker(new Locale("ru"));

    public static String getValidHolder() {
        return fakerRu.name().fullName().toUpperCase();
    }
    public static String getMonth(int month) {
        return LocalDate.now().plusMonths(month).format(DateTimeFormatter.ofPattern("MM"));
    }
    public static String getYear(int year) {
        return LocalDate.now().plusYears(year).format(DateTimeFormatter.ofPattern("yy"));
    }
    public static String getValidCvc() {
        return fakerRu.numerify("###");
    }
    public static CardInfo getValidApprovedCardNumber() {
        return new CardInfo(
                "4444 4444 4444 4441", getMonth(1), getYear(1), getValidHolder(), getValidCvc());
    }
    public static CardInfo getValidDeclinedCardNumber() {
        return new CardInfo(
                "4444 4444 4444 4442", getMonth(1), getYear(1), getValidHolder(), getValidCvc());
    }
    public static CardInfo getInvalidCardNumber() {
        return new CardInfo(
                "0000 0000", getMonth(1), getYear(1), getValidHolder(), getValidCvc());
    }
    public static CardInfo getEmptyCardNumber() {
        return new CardInfo(
                "", getMonth(1), getYear(1), getValidHolder(), getValidCvc());
    }
    public static CardInfo getEmptyMonth() {
        return new CardInfo(
                "4444 4444 4444 4441", "", getYear(1), getValidHolder(), getValidCvc());
    }
    public static CardInfo getEmptyYear() {
        return new CardInfo(
                "4444 4444 4444 4441", getMonth(1), "", getValidHolder(), getValidCvc());
    }
    public static CardInfo getEmptyHolder() {
        return new CardInfo(
                "4444 4444 4444 4441", getMonth(1), getYear(1), "", getValidCvc());
    }
    public static CardInfo getEmptyCvc() {
        return new CardInfo(
                "4444 4444 4444 4441", getMonth(1), getYear(1), getValidHolder(), "");
    }
    public static CardInfo getEmptyAllFields() {
        return new CardInfo(
                "", "", "", "", "");
    }
    @Value
    public static class CardInfo {
        String cardNumber;
        String month;
        String year;
        String holder;
        String cvc;
    }
}