package io.micronaut.opensearch.test_resources;

import io.micronaut.testresources.testcontainers.AbstractTestContainersProvider;
import org.testcontainers.elasticsearch.OpenSearchContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A test resource provider which will spawn an OpenSearch test container.
 */
public class OpenSearchTestResourceProvider extends AbstractTestContainersProvider<OpenSearchContainer> {

    public static final String OPENSEARCH_HOSTS = "opensearch.http-hosts";
    public static final String SIMPLE_NAME = "opensearch";
    public static final String DEFAULT_IMAGE = "opensearchproject/opensearch";
//    public static final String DEFAULT_TAG = "2.12.0";
    public static final String DISPLAY_NAME = "opensearch";

    @Override
    public List<String> getResolvableProperties(Map<String, Collection<String>> propertyEntries, Map<String, Object> testResourcesConfig) {
        return Collections.singletonList(OPENSEARCH_HOSTS);
    }

    @Override
    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    @Override
    protected String getSimpleName() {
        return SIMPLE_NAME;
    }

    @Override
    protected String getDefaultImageName() {
        return DEFAULT_IMAGE;
    }

    @Override
    protected OpenSearchContainer createContainer(DockerImageName imageName, Map<String, Object> requestedProperties, Map<String, Object> testResourcesConfig) {
        OpenSearchContainer elasticsearchContainer = new OpenSearchContainer(imageName);
        elasticsearchContainer.withEnv("xpack.security.enabled", "false");
        return elasticsearchContainer;
    }

    @Override
    protected Optional<String> resolveProperty(String propertyName, OpenSearchContainer container) {
        if (ELASTICSEARCH_HOSTS.equals(propertyName)) {
            return Optional.of("http://" + container.getHttpHostAddress());
        }
        return Optional.empty();
    }

    @Override
    protected boolean shouldAnswer(String propertyName, Map<String, Object> requestedProperties, Map<String, Object> testResourcesConfig) {
        return ELASTICSEARCH_HOSTS.equals(propertyName);
    }
}