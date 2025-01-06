package paperless.services;

import paperless.config.ElasticSearchConfig;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

@Component
public class ElasticSearchService {
    private final ElasticsearchClient esClient;

    private static final Logger log = LoggerFactory.getLogger(ElasticSearchService.class);

    @Autowired
    public ElasticSearchService(ElasticsearchClient esClient) throws IOException {
        this.esClient = esClient;

        if (!esClient.indices().exists(
                i -> i.index(ElasticSearchConfig.DOCUMENTS_INDEX_NAME)
        ).value()) {
            esClient.indices().create(c -> c
                    .index(ElasticSearchConfig.DOCUMENTS_INDEX_NAME)
            );
        }
    }

    public List<String> searchDocumentsByKeyword(String keyword) {
        List<String> documentIds = new ArrayList<>();

        try {
            SearchResponse<Map<String, Object>> response = esClient.search(s -> s
                            .index(ElasticSearchConfig.DOCUMENTS_INDEX_NAME)
                            .query(q -> q
                                    .match(m -> m
                                            .field("documentText")
                                            .query(keyword)
                                    )
                            ),
                    (Class<Map<String, Object>>) (Class<?>) Map.class
            );

            //iterate through results to extract ids
            response.hits().hits().forEach(hit -> {
                String documentId = hit.id();
                documentIds.add(documentId);
            });

            return documentIds;

        } catch (IOException e) {
            log.error("Failed to search for keyword='" + keyword + "' in Elasticsearch: ", e);
            return Collections.emptyList();
        }
    }


    public void deleteDocumentById(String id) {
        DeleteResponse result = null;
        try {
            result = esClient.delete(d -> d.index(ElasticSearchConfig.DOCUMENTS_INDEX_NAME).id(id));
        } catch (IOException e) {
            log.warn("Failed to delete document id=" + id + " from elasticsearch: " + e);
        }
        if ( result==null )
            return;
        if (result.result() != Result.Deleted )
            log.warn(result.toString());
    }

}
