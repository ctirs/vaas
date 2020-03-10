package vaas.template;

import vaas.template.domain.FrontendTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public abstract class TemplateWatcher {

    private ConcurrentHashMap<String, FrontendTemplate> cache = new ConcurrentHashMap<>();

    public Optional<FrontendTemplate> getTemplate(String site, String name) {
        if(!isActive()) {
            return Optional.empty();
        }

        FrontendTemplate frontendTemplate =
                cache.get(name.toLowerCase());

        if (frontendTemplate == null) {
            return Optional.empty();
        }
        FrontendTemplate resultingTemplate = frontendTemplate
                .toBuilder().build();
        if (resultingTemplate.getSites().get(site) == null) {
            return null;
        }
        return Optional.of(resultingTemplate.mergeTemplates(resultingTemplate.getSites().get(site)));

    }

    protected ConcurrentHashMap<String, FrontendTemplate> getCache() {
        return cache;
    }

    public Collection<String> getTemplateList(String site) {
        if(!isActive()) {
            return List.of();
        }

        return cache.keySet().stream()
                .filter(t -> getTemplate(site, t) != null)
                .collect(Collectors.toList());
    }

    public abstract void getConfigs();
    protected abstract boolean isActive();

}
