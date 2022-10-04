package com.informatica.repoquery.model.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * A minimalistic model that represents a search response from the GitHub Repo Search API.
 *
 * @author Sai.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@ToString
public class GithubSearchRepositoryResponse {
    private List<GithubSearchItem> items;
}
