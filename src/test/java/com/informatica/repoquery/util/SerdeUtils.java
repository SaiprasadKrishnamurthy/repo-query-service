package com.informatica.repoquery.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.informatica.repoquery.model.ApiError;
import com.informatica.repoquery.model.RepoSearchRequest;
import com.informatica.repoquery.model.RepoSearchResponse;
import com.informatica.repoquery.model.github.GithubSearchItem;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A simple utility to serialise/deserialise json to pojo and vice versa to be used in tests.
 *
 * @author Sai.
 */
public final class SerdeUtils {
    private SerdeUtils() {
    }

    /**
     * Gets RepoSearchResponse from json. This had to be done explicitly as the Jackson and Polymorphic types
     * (SearchView and GitHubSearchItem) wasn't playing well during deserialisation.
     * This is not elegant, but works. Can be improved.
     */
    public static RepoSearchResponse fromJson(final String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            final Map<String, Object> raw = objectMapper.readValue(json, new TypeReference<>() {
            });
            final RepoSearchRequest repoSearchRequest = objectMapper.convertValue(raw.get("repoSearchRequest"), RepoSearchRequest.class);
            List<ApiError> apiErrors = null;
            List<GithubSearchItem> results = null;
            if (raw.get("items") != null) {
                final List<?> items = objectMapper.convertValue(raw.get("items"), List.class);
                results = items.stream()
                        .map(item -> objectMapper.convertValue(item, GithubSearchItem.class)).collect(Collectors.toList());
            }
            if (raw.get("errors") != null) {
                final List<?> errors = objectMapper.convertValue(raw.get("errors"), List.class);
                apiErrors = errors.stream()
                        .map(item -> objectMapper.convertValue(item, ApiError.class)).collect(Collectors.toList());
            }
            return new RepoSearchResponse(repoSearchRequest, 1L, results, apiErrors);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static RepoSearchResponse fromMap(final Map map) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            final String json = objectMapper.writeValueAsString(map);
            return fromJson(json);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
