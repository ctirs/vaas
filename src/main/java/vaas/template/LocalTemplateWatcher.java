package vaas.template;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import io.kubernetes.client.models.V1ConfigMap;
import io.micronaut.scheduling.annotation.Scheduled;
import lombok.extern.slf4j.Slf4j;
import vaas.template.domain.FrontendTemplate;

import javax.inject.Singleton;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS;

@Singleton
@Slf4j
public class LocalTemplateWatcher extends TemplateWatcher {
    private final String FILEROOT = "/files";
    private ObjectMapper mapperObj;
    private YAMLMapper yamlMapper;
    private boolean isActive;

    public LocalTemplateWatcher() {
        mapperObj = new ObjectMapper();
        mapperObj.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapperObj.configure(ALLOW_UNQUOTED_CONTROL_CHARS, true);
        yamlMapper = new YAMLMapper();
        isActive = Files.exists(Paths.get(FILEROOT));
        getConfigs();
    }

    @Scheduled(fixedRate = "3s")
    public void getConfigs() {
        if(isActive()) {
            try (Stream<Path> walk = Files.walk(Paths.get(FILEROOT))) {

                List<Path> results = walk.filter(Files::isRegularFile)
                        .collect(Collectors.toList());

                for (Path path : results) {
                    try {
                        V1ConfigMap v1ConfigMap = this.yamlMapper.readValue(Files.newInputStream(path), V1ConfigMap.class);
                        if (v1ConfigMap != null && v1ConfigMap.getData().keySet().size() == 1) {
                            String dataKey = v1ConfigMap.getData().keySet()
                                    .stream()
                                    .findAny()
                                    .get();
                            getCache().put(v1ConfigMap.getMetadata().getName(), mapperObj.readValue(v1ConfigMap.getData().get(dataKey), FrontendTemplate.class));
                        }

                    } catch (Exception e) {
                        log.warn("Error during serialisation", e);
                    }
                }
            } catch (IOException e) {
                //NOOP
            }
        }

    }

    @Override
    public boolean isActive() {
        return isActive;
    }
}
