package ru.netology.selenide;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class SelenideTest {

    public String generateDate(int days, String pattern){
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void testValidForm(){
        String planingDate = generateDate(4, "dd.MM.yyyy");
        //Запускаем сайт
        open("http://localhost:9999");
        // Вводим город
        $("[data-test-id=city] input")
                .setValue("Уфа");
        // очищаем поле Дата Вводим дату более 3 дней
        $("[data-test-id=date] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(planingDate);
        // Вводим Фамилию и имя Иванов Иван
        $("[data-test-id=name] input")
                .setValue("Иванов Иван");
        // Вводим телефон +71234567890
        $("[data-test-id=phone] input")
                .setValue("+71234567890");
        // Ставим галочку в чек бокс
        $("[data-test-id=agreement]")
                .click();
        // Нажимаем кнопку забронировать
        $$("button")
                .find(Condition.text("Забронировать"))
                .click();
        // Ждем 15 секунд
        $("[data-test-id=notification]")
                .shouldBe(visible, Duration.ofSeconds(15))
                .should(Condition.text("Успешно"))
                .should(Condition.text(planingDate));

    }
}
