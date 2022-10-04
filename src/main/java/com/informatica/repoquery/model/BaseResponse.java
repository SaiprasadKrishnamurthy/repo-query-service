package com.informatica.repoquery.model;

import java.util.List;

/**
 * Base Response all the API responses must conform to.
 *
 * @author Sai.
 */
public interface BaseResponse {
    List<ApiError> getErrors();
}
