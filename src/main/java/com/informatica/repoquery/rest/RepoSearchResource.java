package com.informatica.repoquery.rest;

import com.informatica.repoquery.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Repo Search REST resource.
 *
 * @author Sai.
 */
@RequestMapping("/api/v1")
@RestController
public class RepoSearchResource {

    private static final String ERROR_CODE_SERVER_ERROR = "RQS00002";

    private final RepoSearchService repoSearchService;

    public RepoSearchResource(final RepoSearchService repoSearchService) {
        this.repoSearchService = repoSearchService;
    }

    @GetMapping("repos")
    public ResponseEntity<RepoSearchResponse> searchRepos(@RequestParam("language") final String language,
                                                          @RequestParam(value = "page", defaultValue = "1") final int page,
                                                          @RequestParam(value = "size", defaultValue = "30") final int size,
                                                          @RequestParam(value = "sort", defaultValue = "STARS") final SortBy sortBy,
                                                          @RequestParam(value = "order", defaultValue = "DESC") final SortDirection order) {
        final RepoSearchRequest repoSearchRequest = new RepoSearchRequest(language, page, size, sortBy, order);
        try {
            return ResponseEntity.ok(repoSearchService.searchReposByLanguage(repoSearchRequest));
        } catch (Exception exception) {
            return ResponseEntity.internalServerError()
                    .body(RepoSearchResponse.errorResponse(repoSearchRequest, ApiError.of(ERROR_CODE_SERVER_ERROR, exception.getMessage())));
        }
    }
}
