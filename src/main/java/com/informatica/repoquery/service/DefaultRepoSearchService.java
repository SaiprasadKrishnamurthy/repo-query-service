package com.informatica.repoquery.service;

import com.informatica.repoquery.model.*;
import com.informatica.repoquery.model.github.GithubSearchRepositoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static java.util.Collections.emptyList;

/**
 * Default implementation that searches the repo in GITHUB for a given request.
 *
 * @author Sai.
 */
@Slf4j
@Service
public class DefaultRepoSearchService implements RepoSearchService {

    private final RestTemplate restTemplate;

    /**
     * Templated Github search repo uri context.
     */
    private static final String GITHUB_REPO_SEARCH_CONTEXT_URI = "/search/repositories?q=language:%s&sort=%s&order=%s&page=%s&per_page=%s";
    private static final String ERROR_CODE_CANNOT_FETCH_FROM_GITHUB = "RQS00001";

    public DefaultRepoSearchService(@Qualifier("githubRestTemplate") final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public RepoSearchResponse searchReposByLanguage(final RepoSearchRequest repoSearchRequest) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String uri = String.format(GITHUB_REPO_SEARCH_CONTEXT_URI,
                repoSearchRequest.getLanguage(),
                SortBy.hyphenise(repoSearchRequest.getSortBy()),
                repoSearchRequest.getSortDirection().toString().toLowerCase(),
                repoSearchRequest.getPage(),
                repoSearchRequest.getSize());
        if (log.isDebugEnabled()) {
            log.debug(" Github search uri: {}", uri);
        }
        final ResponseEntity<GithubSearchRepositoryResponse> response = restTemplate.getForEntity(uri, GithubSearchRepositoryResponse.class);
        final GithubSearchRepositoryResponse body = response.getBody();
        stopWatch.stop();
        if (body != null) {
            return new RepoSearchResponse(repoSearchRequest, stopWatch.getTotalTimeMillis(), body.getItems(), emptyList());
        } else {
            return new RepoSearchResponse(repoSearchRequest,
                    stopWatch.getTotalTimeMillis(),
                    emptyList(),
                    Collections.singletonList(ApiError.of(ERROR_CODE_CANNOT_FETCH_FROM_GITHUB, "Cannot fetch the data from GitHub")));
        }
    }
}
