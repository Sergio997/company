package com.company.common.dtos.enums;

import lombok.Getter;
import lombok.Setter;

public enum ErrorCode {

    /* 404 */
    NOT_FOUND(
            "404-001",
            "Not found.",
            404),

    FILE_NOT_FOUND(
            "404-002",
            "File not found.",
            404),

    USER_NOT_FOUND(
            "404-003",
            "User not found",
            404),

    /* 400 */

    BAD_REQUEST(
            "400-001",
            "Bad request",
            400),

    INVALID_FORMAT(
            "400-002",
            "Invalid date or time format",
            400),

    BAD_CREDENTIALS(
            "400-003",
            "Bad credentials",
            400
    ),

    USER_ALREADY_EXIST(
            "400-006",
            "USer already exist",
            400
    ),


    /* 401 */

    NOT_AUTHORIZED(
            "401-001",
            "Not authorized",
            401),

    JWT_EXPIRE(
            "401-003",
            "JWT is expire",
            401),

    /* 409 */
    TEST_ERROR_CODE(
            "409-001",
            "Test.",
            409),

    NOT_ALLOWED(
            "409-005",
            "Action not allowed",
            409),


    /* 500 */
    UNKNOWN_SERVER_ERROR(
            "500-001",
            "Unforeseen and unhandled error",
            500),

    FAILED_FILE_UPLOAD(
            "500-002",
            "Error occurred while uploading file.",
            500),

    FAILED_TO_PERSIST_DATA(
            "500-003",
            "Failed to save data",
            500),

    FAILED_ZIP_FILE_GENERATION(
            "500-004",
            "Failed to generate Zip file",
            500),

    ERROR_HASH_INTO_MD5(
            "500-009",
            "Error for format to MD5",
            500),


    /* 501 */
    NOT_IMPLEMENTED(
            "501-001",
            "Method has not been implemented",
            501);


    @Getter
    private Data data;

    ErrorCode(String code, String description, int httpResponseCode) {
        this.data = new Data(code, description, httpResponseCode);
    }

    /**
     * Bean class to make rendering this enums' content easier. For instance:
     *
     * @see {@link com.servicequik.client.backend.controller.RestExceptionHandler}
     */
    public final class Data {

        @Getter
        private String code;

        @Getter
        @Setter
        private String description;

        @Getter
        private int httpResponseCode;

        @Setter
        @Getter
        private String label;

        Data(String code, String description, int httpResponseCode) {
            this.code = code;
            this.description = description;
            this.httpResponseCode = httpResponseCode;
        }
    }
}
