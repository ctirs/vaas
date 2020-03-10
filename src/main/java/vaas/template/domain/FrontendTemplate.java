package vaas.template.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder(toBuilder=true)
@NoArgsConstructor
@AllArgsConstructor
public class FrontendTemplate {
    /**
     * List of Templates that a Frontend currently haves
     */
    private List<Template> templates;
    /**
     * indicates if a site should fit on screen or not (currently unsupported)
     */
    private Boolean scrollable;
    /**
     * Puts some additional information as the Title of the application (useful for standalone sites)
     */
    private Boolean hasHeader;
    /**
     * indicates if this site is secured via uid or not (default unsecured/ only reachable via vpn or office network)
     */
    private Boolean secured;
    /**
     * provides information if this Template should be present for a certain site and additionally it is possible to override certai aspects of the Site.
     * e.g. some information that could be presented in polish sites might not be allowed to the german data protection rules.
     *
     */
    private Map<String, FrontendTemplate> sites;

    public FrontendTemplate mergeTemplates(FrontendTemplate frontendTemplate) {
        if(frontendTemplate.scrollable != null) {
            this.scrollable = frontendTemplate.scrollable;
        }
        if(frontendTemplate.hasHeader != null) {
            this.hasHeader = frontendTemplate.hasHeader;
        }
        if(frontendTemplate.secured != null) {
            this.secured = frontendTemplate.secured;
        }
        if(frontendTemplate.getTemplates() != null) {
            for (int i=0;i<frontendTemplate.getTemplates().size();i++) {
                this.templates.set(i,
                        this.templates.get(i).mergeTemplate(frontendTemplate.getTemplates().get(i)));
            }
        }
        this.sites = null;
        return this;
    }
}
