package com.informatica.repoquery.model;

/**
 * A view contract to define the required fields for the search item.
 * Kept as a flat model (and not nested objects) for keeping it minimal.
 *
 * @author Sai.
 */
public interface SearchView {
    Long getId();

    void setId(Long id);

    String getRepoUrl();

    void setRepoUrl(String repoUrl);

    String getRepoName();

    void setRepoName(String repoName);

    String getOwnerLogin();

    default void setOwnerLogin(String ownerLogin) {
        // do nothing
    }
}
