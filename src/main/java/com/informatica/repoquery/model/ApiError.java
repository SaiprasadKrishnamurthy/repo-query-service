package com.informatica.repoquery.model;

import lombok.Data;

/**
 * Type that represents an API error that is sent to the API consumers.
 *
 * @author Sai.
 */
@Data
public class ApiError {
    private String errorCode;
    private String description;

    public static ApiError of(final String errorCode, final String description) {
        final ApiError apiError = new ApiError();
        apiError.errorCode = errorCode;
        apiError.description = description;
        return apiError;
    }
}
