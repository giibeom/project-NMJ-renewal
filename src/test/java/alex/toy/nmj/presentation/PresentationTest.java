package alex.toy.nmj.presentation;

import alex.toy.nmj.util.DatabaseCleanup;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("presentation")
@SpringBootTest
@AutoConfigureMockMvc
class PresentationTest {

    public static final String REQUEST_MEMBER_URL = "/api/members";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @BeforeEach
    public void setUp() {
        databaseCleanup.execute();
    }
}
