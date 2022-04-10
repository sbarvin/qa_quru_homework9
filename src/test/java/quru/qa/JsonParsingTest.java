package quru.qa;

import org.junit.jupiter.api.Test;
import quru.qa.domain.UserRoles;
import org.codehaus.jackson.map.ObjectMapper;

import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class JsonParsingTest {

    @Test
    void parseJsonTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        UserRoles ur = mapper.readValue(Paths.get("src/test/resources/files/test.json").toFile(), UserRoles.class);
        assertThat(ur.roles).contains("admin", "superuser");
    }

}
