package OCR.service;

import OCR.config.ElasticSearchConfig;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class ElasticSearchService {
    private final ElasticsearchClient esClient;

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

    public void indexDocument(String documentID, String documentText) throws IOException {

        //elastic search can not index a text so we transform it.
        Map<String, Object> documentMap = new HashMap<>();
        documentMap.put("documentText", documentText);
        System.out.println("Text aus dem Dokument: " + documentText);

        // do indexing with ElasticSearch
        IndexResponse response = esClient.index(i -> i
                .index(ElasticSearchConfig.DOCUMENTS_INDEX_NAME)
                .id(documentID)
                .document(documentMap)
        );
        if ( response.result()!=Result.Created && response.result()!=Result.Updated )
            System.out.println("Failed to index Document");
        else
            System.out.println("Successfully indexed Document");
    }
}
