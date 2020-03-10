package vaas.template.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Template {

  /**
   * templateOverrideId is used to split Templates i reusable Parts, there are some already predefined templates that you might just use.
   */
  private String templateOverrideId;
  /**
   * Indicates the Type of the template currently Supported Templates are GRID_NEW, DATA_TABLE_SELECT, SNACKBAR
   * GRID_NEW gives you the possibility to add certain Widgets.
   * Data Table select offers you to present the Data inn Table.
   * SNACKBAR is a popup that can be used to highlight a certain information to the user.
   */
  private TemplateType templateType;
  /**
   * Contains a list of requests that might be necessary to serve the initial view. Currently only one request is supported per template.
   * This will soon change.
   */
  private List<RequestOperations> requestOperations;

  /**
   * Defines needed preselection to enable the Frontend to do certain requests. These values will be used to enrich postParams field
    */
  private List<Map<String, Object>> selectorOptions;
  /**
   * Can be used to reduce the received data array (only makes sense if the Data which the backend returns is ordered)
   */
  private Integer startData;
  /**
   * Can be used to reduce the received data array (only makes sense if the Data which the backend returns is ordered)
   * -1 or being null will result into no reducing.
   */
  private Integer endData;

  /**
   * This field is needed in the DATA_TABLE_SELECT template
   * defines a how a certain Column in the data table will look like. The following options are mandatory:
   * text: will be used to shown as Header Text
   * value: will be used to identify the value in your data structur. Supports . in Strings to access nested elements.
   * Optional Values:
   * class: gives the possibility to add a css class to the col
   * format: javascript code which can be used to format data user friendly - the entry is always called item e.g. item.substr(3,5) to display only a certain part of the String
   * filterFunction: javascript code which can be used to write an own filter function for your data "value.includes(input)" only needed for INPUT Types
   * filterLabel: Text that will be displayed at the filter useful would be here to show what the filter is doing e.g. "contains"
   * filterType: Type of the filter currently supported INPUT | DROPDOWN
   * uiType: possible to give a entry additionally information e.g. add a color to a certain value right now supported chip,
   * color: only need if uiType chip is set. Javascript code to get the color "item.site == 'erfurt' ? 'green' : 'red'"
   */
  private List<Map<String, String>> dataCols;
  /**
   * Only useful for DATA_TABLE_SELECT and indicates if the table as selectable rows (e.g. to use these rows to trigger a certain Action)
   */
  private boolean selectable;
  /**
   * if the Data is selectable it is sometimes useful to store only the technical id in the selected Store
   */
  private String selectId;
  /**
   * Data from endpoint is sometimes nested to make it easier to find data which should be used e.g. for a data table this field is used.
   */
  private String rootElement;
  /**
   * Defines a list of Actions which can be triggered from this Template. (Submit Button, Manual Interaction and so on)
   */
  private List<TemplateAction> actions;
  /**
   * This field is needed in the GRID_NEW and SNACKBAR template.
   * text: Will be the Headline of the widget or the label to explain it e.g. "Group id",
   * type: there are different Types of widgets NONE (default) | INPUT | DROPDOWN | DATEPICKER | BUTTON | ICON_BUTTON
   * link: if this is a Widget to enable the user to select/write something then this variable can be used later if mapped to a certain parm e.g. (Up tp 9 Parm are supported) "parm1",
   * size: Size of the widget e.g. the complete display width would be 'xs12' - 'xs3' would be 25% of the display width and so on.
   * class: Custom css classes can be added here.
   * buttonClass: if the UiType is ICON_BUTTON then we can add a color here e.g. "cyan",
   * actionId: if the widget is a Button or ann Icon Button an action can be triggered this is the name to link the correct action e.g. "getAction"
   */
  private List cardProperties;

  // just for Backward compatibility
  private List<Map<String, String>> progressStatus;
  // just for Backward compatibility
  private List<Map<String, Object>> additionalClasses;
  // just for Backward compatibility
  // TODO remove me after migrate Progress view
  private List<Map<String, Object>> extraOptions;

  // just for backward comparability
  // TODO remove me after migrate Progress View
  private String fontSize;
  // just for backward comparability
  // TODO remove me after migrate Progress View
  private String flexSize;
  // just for backward comparability
  // TODO remove me after migrate Progress View
  private String needed;
  // just for backward comparability
  // TODO remove me after migrate Progress View
  private String current;
  // just for backward comparability
  // TODO remove me after migrate Progress View
  private String name;
  // just for backward comparability
  // TODO remove me after migrate from Single Request Per Template to Multi
  private HashMap<String, String> postParams;
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.PUBLIC)
  // just for backward comparability
  // TODO remove me after migrate from Single Request Per Template to Multi
  private String tokenId;
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.PUBLIC)
  // just for backward comparability
  // TODO remove me after migrate from Single Request Per Template to Multi
  private String methode;
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.PUBLIC)
  // just for backward comparability
  // TODO remove me after migrate from Single Request Per Template to Multi
  private String callUrl;
  @Setter(AccessLevel.PUBLIC)
  @Getter(AccessLevel.NONE)
  // just for backward comparability
  // TODO remove me after migrate from Single Request Per Template to Multi
  private String callObject;
  // just for backward comparability
  // TODO remove me after migrate from Single Request Per Template to Multi
  private Integer refreshInterval;
  // just for backward comparability
  // TODO remove me after migrate from Single Request Per Template to Multi
  private String endpoint;

  public Template mergeTemplate(Template override) {
    if (override.getStartData() != null) {
      this.setStartData(override.getStartData());
    }
    if (override.getEndData() != null) {
      this.setEndData(override.getEndData());
    }
    if (override.getRefreshInterval() != null) {
      this.setRefreshInterval(override.getRefreshInterval());
    }
    if(override.getCallUrl() != null) {
      this.setCallUrl(override.getCallUrl());
    }
    if(override.getProgressStatus() != null ) {
      this.setProgressStatus(override.getProgressStatus());
    }
    if(override.getSelectorOptions() != null) {
      this.setSelectorOptions(override.getSelectorOptions());
    }
    if(override.getCardProperties() != null) {
      this.setCardProperties(override.getCardProperties());
    }

    if (override.getTemplateOverrideId() != null) {
      this.setTemplateOverrideId(override.getTemplateOverrideId());
    }
    if(override.getDataCols() != null) {
      this.setDataCols(override.getDataCols());
    }
    return this;
  }

  @JsonIgnore
  public String getTokenId() {
    return tokenId;
  }

  @JsonIgnore
  public String getMethode() {
    return methode;
  }

  @JsonIgnore
  public String getCallUrl() {
    return callUrl;
  }

  @JsonIgnore
  public String getCallObject() {
    return callObject;
  }

  @JsonProperty
  public void setTokenId(String tokenId) {
    this.tokenId = tokenId;
  }

  @JsonProperty
  public void setMethode(String methode) {
    this.methode = methode;
  }

  @JsonProperty
  public void setCallUrl(String callUrl) {
    this.callUrl = callUrl;
  }

  @JsonProperty
  public void setCallObject(String callObject) {
    this.callObject = callObject;
  }


}
