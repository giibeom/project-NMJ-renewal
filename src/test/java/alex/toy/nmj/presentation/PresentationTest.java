package alex.toy.nmj.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PresentationTest {

    public static final String REQUEST_MEMBER_URL = "/api/members";

    @Autowired
    protected MockMvc mockMvc;
}
