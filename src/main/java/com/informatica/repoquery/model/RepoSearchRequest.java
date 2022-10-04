package com.informatica.repoquery.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

/**
 * An internal repo search request.
 *
 * @author Sai.
 */
@EqualsAndHashCode(of = {"language", "page", "size", "sortBy", "sortDirection"})
@Data
public class RepoSearchRequest {
    private final String language;
    private final int page;
    private final int size;
    private final SortBy sortBy;
    private final SortDirection sortDirection;

    @JsonCreator
    public RepoSearchRequest(@JsonProperty("language") final String language,
                             @JsonProperty("page") final int page,
                             @JsonProperty("size") final int size,
                             @JsonProperty("sortBy") final SortBy sortBy,
                             @JsonProperty("sortDirection") final SortDirection sortDirection) {
        this.language = language;
        this.page = page;
        this.size = size;
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
    }
}
