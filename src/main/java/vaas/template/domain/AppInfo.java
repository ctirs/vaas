package vaas.template.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppInfo {
    private String warehouse;
    private String commitId;
    private String test;
    private String group;
    private String lang;
    private List<String> supportedMonitors;
    private boolean isHome;
    private FrontendTemplate frontendTemplate;

}
