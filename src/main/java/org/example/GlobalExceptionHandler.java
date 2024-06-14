package org.example;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex) {
        // Логика обработки ошибки, например, логирование
        return "error"; // Возвращает имя шаблона для страницы ошибки
    }
}