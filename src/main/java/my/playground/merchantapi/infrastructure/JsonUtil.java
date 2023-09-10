package my.playground.merchantapi.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.stereotype.Component;

@Component
public class JsonUtil {

  private final static ObjectMapper objectMapper = new ObjectMapper();

  static {
    objectMapper.registerModule(new ParameterNamesModule());
  }

  public <T> T readValue(InputStream inputStream, Class<T> valueType) {
    try {
      return objectMapper.readValue(inputStream, valueType);
    } catch (IOException ex) {
      throw new RuntimeException("cannot read data to object", ex);
    }
  }

}
