package paperless.services;

import paperless.config.ElasticSearchConfig;
import paperless.mapper.DocumentDTO;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import lombok.extern.slf4j.Slf4j;
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

    /*
    public Optional<DocumentDTO> getDocumentById(String id) {
        try {
            GetResponse<DocumentDTO> response = esClient.get(g -> g
                            .index(ElasticSearchConfig.DOCUMENTS_INDEX_NAME)
                            .id(String.valueOf(id)),
                    DocumentDTO.class
            );
            return (response.found() && response.source()!=null) ? Optional.of(response.source()) : Optional.empty();
        } catch (IOException e) {
            log.error("Failed to get document id=" + id + " from elasticsearch: " + e);
            return Optional.empty();
        }
    }*/

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


    public boolean deleteDocumentById(String id) {
        DeleteResponse result = null;
        try {
            result = esClient.delete(d -> d.index(ElasticSearchConfig.DOCUMENTS_INDEX_NAME).id(id));
        } catch (IOException e) {
            log.warn("Failed to delete document id=" + id + " from elasticsearch: " + e);
        }
        if ( result==null )
            return false;
        if (result.result() != Result.Deleted )
            log.warn(result.toString());
        return result.result()==Result.Deleted;
    }

}
