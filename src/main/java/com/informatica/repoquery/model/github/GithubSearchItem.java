package com.informatica.repoquery.model.github;

import com.fasterxml.jackson.annotation.*;
import com.informatica.repoquery.model.SearchView;
import lombok.Data;

/**
 * A minimalistic model that represents a search response from the GitHub Repo Search API.
 * All fields in the original Github response are intentionally ignored as they aren't needed.
 * Most of the 'heavy-lifting' and 'field mapping' is delegated to Jackson during deserialisation/serialisation.
 *
 * @author Sai.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubSearchItem implements SearchView {

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class Owner {
        private final String login;

        @JsonCreator
        public Owner(@JsonProperty("login") final String login) {
            this.login = login;
        }
    }

    private Long id;
    private String repoName;
    private String repoUrl;

    @JsonIgnore
    private Owner owner;

    @JsonCreator
    public GithubSearchItem(@JsonProperty("id") Long id,
                            @JsonProperty(value = "repoName") @JsonAlias({"full_name"}) final String full_name,
                            @JsonProperty("repoUrl") @JsonAlias({"html_url"}) final String html_url,
                            @JsonProperty("repoOwner") @JsonAlias({"owner"}) final Owner repoOwner
    ) {
        this.id = id;
        this.repoName = full_name;
        this.repoUrl = html_url;
        this.owner = repoOwner;
    }

    @Override
    public String getOwnerLogin() {
        return this.owner.login;
    }

}