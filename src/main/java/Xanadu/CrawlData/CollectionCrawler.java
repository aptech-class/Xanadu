package Xanadu.CrawlData;


import Xanadu.Entities.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Getter
@Slf4j
@Component
public class CollectionCrawler {
    private final String collectionsEndpoint = "https://unitedbyblue.com/collections";

    public List<Collection> gets(String endpoint, String filter) throws URISyntaxException {
        WebClient client = WebClient.create();
        return client
                .mutate()
                .codecs(config -> config.defaultCodecs().maxInMemorySize(5 * 1024 * 1024))
                .build()
                .method(HttpMethod.GET)
                .uri(new URI(endpoint + ".json?" + filter))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ResCollections.class)
                .block()
                .getCollections();
    }

    public Collection get(String endpoint) throws URISyntaxException {
        WebClient client = WebClient.create();
        return client
                .mutate()
                .codecs(config -> config.defaultCodecs().maxInMemorySize(5 * 1024 * 1024))
                .build()
                .method(HttpMethod.GET)
                .uri(new URI(endpoint + ".json"))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ResCollection.class)
                .block()
                .getCollection();
    }

    @Getter
    @Setter
    public static class ResCollections {
        @JsonProperty
        private List<Collection> collections;
    }

    @Getter
    @Setter
    public static class ResCollection {
        @JsonProperty
        private Collection collection;
    }
}
