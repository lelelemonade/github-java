package org.carrot.githubjava.config;

import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class GithubConfig {
    @Value("${carrot.github.token}")
    private String githubToken;

    @Bean
    public GitHub getMyGithub() throws IOException {
        return new GitHubBuilder()
                .withOAuthToken(githubToken)
                .build();

    }
}
