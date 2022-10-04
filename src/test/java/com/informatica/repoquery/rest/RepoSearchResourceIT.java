package com.informatica.repoquery.rest;

import com.informatica.repoquery.RepoQueryServiceApplication;
import com.informatica.repoquery.model.RepoSearchResponse;
import com.informatica.repoquery.util.SerdeUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * A simple Integration test to ensure the real integration with the underlying repository is okay.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RepoQueryServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RepoSearchResourceIT {

    private static final String SEARCH_REPOS_URI = "http://localhost:%s/api/v1/repos?language=%s";

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    void should_retrieve_response_from_github() {
        String uri = String.format(SEARCH_REPOS_URI,
                port,
                "java");
        final RepoSearchResponse response = SerdeUtils.fromMap(restTemplate.getForEntity(uri, Map.class).getBody());
        assertNotNull(response);
        assertThat(response.getItems().size(), equalTo(30));
    }
}
