package vaas.template.domain;

import lombok.Data;

@Data
public class TemplateAction {

  /**
   * Is used to link action trigger and action description
   */
  private String actionId;
  /**
   * defines the Url to where the action is sent
   */
  private String url;
  /**
   * defines the request body which will be needed for this request (linked parm will be replaced)
   */
  private String object;
  /**
   * defines Parameter that will be replaced in the url
   */
  private String urlParameters;
  /**
   * defines the token to use e.g. uid or zalos
   */
  private String tokenId;
  /**
   * GET|POST and so on
   */
  private String methode;
  /**
   * Sometimes the purpose of an action is to fill another template.
   */
  private String fillTemplate;
}
