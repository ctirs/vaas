package vaas.template;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1ConfigMap;
import io.kubernetes.client.models.V1ConfigMapList;
import io.kubernetes.client.util.ClientBuilder;
import io.micronaut.scheduling.annotation.Scheduled;
import lombok.extern.slf4j.Slf4j;
import vaas.template.domain.FrontendTemplate;

import javax.inject.Singleton;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS;

@Singleton
@Slf4j
public class K8sTemplateWatcher extends TemplateWatcher {
    private ObjectMapper mapperObj;
    private CoreV1Api api;
    private boolean isActive;

    public K8sTemplateWatcher() {
        log.info("start get configMap");

        try {
            ApiClient client = ClientBuilder.cluster().build();

            // set the global default api-client to the in-cluster one from above
            Configuration.setDefaultApiClient(client);

            // the CoreV1Api loads default api-client from global configuration.
            api = new CoreV1Api();
            mapperObj = new ObjectMapper();
            mapperObj.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapperObj.configure(ALLOW_UNQUOTED_CONTROL_CHARS, true);

            getConfigs();
            isActive = true;
        } catch (Exception e) {
            log.error("during init of K8sTemplateWatcher",e);
            isActive = false;
        }
    }

    @Scheduled(fixedRate = "60s")
    public void getConfigs() {
        if(isActive()) {
            try {
                V1ConfigMapList v1ConfigMapList = api.listConfigMapForAllNamespaces(null, null, false, "kind=FrontendDefinition", null, null, null, null, false);
                List<String> collect = v1ConfigMapList.getItems().stream()
                        .filter(v1 -> v1.getMetadata() != null && v1.getMetadata().getName() != null)
                        .map(v1 -> v1.getMetadata().getName()).collect(Collectors.toList());
                List<String> removedKeys = getCache().keySet().stream().filter(key -> !collect.contains(key))
                        .collect(Collectors.toList());
                removedKeys.forEach(key -> getCache().remove(key));

                for (V1ConfigMap v1ConfigMap : v1ConfigMapList.getItems()) {
                    if (v1ConfigMap != null && v1ConfigMap.getData().keySet().size() == 1) {
                        try {
                            String dataKey = v1ConfigMap.getData().keySet()
                                    .stream()
                                    .findAny()
                                    .get();
                            getCache().put(v1ConfigMap.getMetadata().getName(), mapperObj.readValue(v1ConfigMap.getData().get(dataKey), FrontendTemplate.class));
                        } catch (Exception e) {
                            log.warn("got Exception for Parsing Configmap " + v1ConfigMap.getMetadata().getName(), e);
                        }
                    }
                }
            } catch (Exception e) {
                log.warn("got Exception for accessing Configmaps ", e);
            }
        }
    }

    @Override
    protected boolean isActive() {
        return isActive;
    }


}
