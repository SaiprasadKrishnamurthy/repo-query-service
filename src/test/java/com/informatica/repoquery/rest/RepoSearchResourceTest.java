package com.informatica.repoquery.rest;

import com.informatica.repoquery.model.*;
import com.informatica.repoquery.model.github.GithubSearchItem;
import com.informatica.repoquery.util.SerdeUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = RepoSearchResource.class)
public class RepoSearchResourceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RepoSearchService repoSearchService;


    @DisplayName("Should return a valid response")
    @Test
    public void valid_response() throws Exception {
        final RepoSearchRequest givenRequest = new RepoSearchRequest("java", 1, 10, SortBy.STARS, SortDirection.DESC);
        when(repoSearchService.searchReposByLanguage(givenRequest))
                .thenReturn(new RepoSearchResponse(givenRequest, 1L, new ArrayList<>() {{
                    add(new GithubSearchItem(1L, "name", "url", new GithubSearchItem.Owner("user-id")));
                }}, Collections.emptyList()));
        final MvcResult mvcResult = mockMvc.perform(get("/api/v1/repos?language=java&page=1&size=10&sort=STARS&order=DESC")
                        .contentType("application/json"))
                .andExpect(status().isOk()).andReturn();
        final RepoSearchResponse actualResponse = SerdeUtils.fromJson(mvcResult.getResponse().getContentAsString());
        assertNotNull(actualResponse);
        assertThat(actualResponse.getRepoSearchRequest(), equalTo(givenRequest));
        assertThat(actualResponse.getItems().size(), equalTo(1));
        assertNull(actualResponse.getErrors());
        assertThat(actualResponse.getItems().get(0).getRepoUrl(), equalTo("url"));
        assertThat(actualResponse.getItems().get(0).getRepoName(), equalTo("name"));
    }

    @DisplayName("Should return a server error in case of any errors (eg: Timeout)")
    @Test
    public void server_error() throws Exception {
        final RepoSearchRequest givenRequest = new RepoSearchRequest("java", 1, 10, SortBy.STARS, SortDirection.DESC);
        when(repoSearchService.searchReposByLanguage(givenRequest))
                .thenThrow(new ResourceAccessException("Cannot access the underlying resource"));
        final MvcResult mvcResult = mockMvc.perform(get("/api/v1/repos?language=java&page=1&size=10&sort=STARS&order=DESC")
                        .contentType("application/json"))
                .andExpect(status().isInternalServerError()).andReturn();
        final RepoSearchResponse actualResponse = SerdeUtils.fromJson(mvcResult.getResponse().getContentAsString());
        assertNotNull(actualResponse);
        assertThat(actualResponse.getRepoSearchRequest(), equalTo(givenRequest));
        assertNull(actualResponse.getItems());
        assertNotNull(actualResponse.getErrors());
        assertThat(actualResponse.getErrors().size(), equalTo(1));
        assertThat(actualResponse.getErrors().get(0).getErrorCode(), equalTo("RQS00002"));
    }
}