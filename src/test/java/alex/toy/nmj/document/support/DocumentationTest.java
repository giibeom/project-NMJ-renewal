package alex.toy.nmj.document.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@Import({MockMvcCharacterEncodingCustomizer.class})
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc
public class DocumentationTest {

    @Autowired
    protected MockMvc mockMvc;

}
