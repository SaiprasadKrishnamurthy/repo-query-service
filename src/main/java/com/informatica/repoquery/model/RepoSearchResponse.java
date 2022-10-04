package com.informatica.repoquery.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Response returned to the REST API client when a search repository API is called.
 *
 * @author Sai.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RepoSearchResponse implements BaseResponse {
    // Embed the request to have a self contained correlation.
    private RepoSearchRequest repoSearchRequest;
    private long searchTimeInMillis;
    private List<? extends SearchView> items;
    private List<ApiError> errors;

    public static RepoSearchResponse errorResponse(final RepoSearchRequest repoSearchRequest, final ApiError apiError) {
        return new RepoSearchResponse(repoSearchRequest, 0, null, Collections.singletonList(apiError));
    }
}
