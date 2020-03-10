package vaas.template.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActionObject {

  Map<String, String> item;
  Map<String, String> columeTemplate;
  Map<String, String> urlParameters;
  Map<String, Object> inputValues;

}
