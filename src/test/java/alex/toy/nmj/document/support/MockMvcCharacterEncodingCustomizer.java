package alex.toy.nmj.document.support;

import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcBuilderCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder;

import java.nio.charset.StandardCharsets;

/**
 * MockMvc의 공통적인 동작을 지정합니다.
 * `@AutoconfigureMockMvc`를 통해 MockMvc를 주입받는 상황에서만 적용됩니다.
 */
@Component
public class MockMvcCharacterEncodingCustomizer implements MockMvcBuilderCustomizer {
    @Override
    public void customize(ConfigurableMockMvcBuilder<?> builder) {
        builder.alwaysDo(result ->
                result.getResponse().setCharacterEncoding(StandardCharsets.UTF_8.name())
        );
    }
}
