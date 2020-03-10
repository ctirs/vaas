package vaas.template.domain;

import lombok.*;

import java.util.HashMap;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestOperations {
    /**
     * Indicates the refreshinterval for a certain endpoint
     * e.g. 30 will refresh the specified request every 30 seconds
     * Special in this case are 0 - which leads to an initial request and then never again. This will be useful e.g. to gather certain values for e.g. a Dropdown Menu
     * Also -1 is special this will indicate that it is not triggered automatically at all
     */
    private Integer refreshInterval;
    /**
     * Endpoints must be unique in a Frontend definition - this can be later used to monitor your requests and write Checks
     */
    private String endpoint;
    /**
     * When entering the Screen several "Pre Selection" oh the user might be needed. E.g. if the Template helps to interact
     * with a machine it might be useful to tell beforehand as a user which machine the you actually control. These
     * information can be transfered here.
     */
    private HashMap<String, String> postParams;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.PUBLIC)
    /**
     * Token that will be used to execute the request e.g. uid or zalos
     */
    private String tokenId;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.PUBLIC)
    /**
     * GET | POST and so on
     */
    private String methode;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.PUBLIC)
    /**
     * URL that will be used in the request some things will be autmatically replaced for you
     * e.g. {SITE} is the site which is used in the URl, {zalosUrl} will grap for specific site already the correct Zalos URL
     */
    private String callUrl;
    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.NONE)
    /**
     * The RequestBody that might be necessary in order to do the request.
     */
    private String callObject;

}
