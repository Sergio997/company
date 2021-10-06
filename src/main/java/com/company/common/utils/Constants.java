package com.company.common.utils;

public interface Constants {

    String AUTHORIZATION = "Authorization";

    String BEARER = "Bearer ";

    interface ValidationRegex {
        String TIME_FORMAT = "H:mm:ss";
        String NAME_REGEX = "^[a-zA-Z]+$";
        String DATE_FORMAT = "dd.MM.yyyy";
        String ZONE_ID_ZERO_TIME_ZONE = "GMT";
        String ZONED_DATE_TIME_FORMAT = "yyyy-MM-dd'T'hh:mm:ss";
        String DELIVERY_ZONED_DATE_TIME_FORMAT = "yyyy-MM-dd'T'hh:mm:ss'Z'";
        String LOGIN_REGEX = "^([A-z0-9_-]+\\.)*[A-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
    }

    interface Message {
        String NOT_EMPTY = "can not empty!";
        String WRONG_PATTERN = "Wrong pattern!";
        String EMAIL_MESSAGE = "Wrong pattern for com.slata.backend.notification.email!";
        String FIRST_NAME_MESSAGE = "Invalid first name (minimum of 2 characters, letters only)";
        String SECOND_NAME_MESSAGE = "Invalid second name (minimum of 2 characters, letters only)";
    }

}
