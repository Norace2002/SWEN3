package OCR.service;

import OCR.config.ElasticSearchConfig;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class ElasticSearchService {
    private final ElasticsearchClient esClient;

    Logger logger = LogManager.getLogger();

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
        logger.debug("Text from document: " + documentText);

        // do indexing with ElasticSearch
        IndexResponse response = esClient.index(i -> i
                .index(ElasticSearchConfig.DOCUMENTS_INDEX_NAME)
                .id(documentID)
                .document(documentMap)
        );
        if ( response.result()!=Result.Created && response.result()!=Result.Updated )
            // shouldn't crash the system, just not able to fulltext-search
            logger.error("ElasticSearch-Service failed to index document with id " + documentID);
        else {
            logger.debug("Successfully indexed document with id " + documentID);
        }
    }
}
