import io.querydsl.Application
import io.querydsl.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootContextLoader
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * Created by rai on 17/1/25.
 */
@SpringBootTest
@ContextConfiguration(classes = Application.class, loader = SpringBootContextLoader.class)
class UserQueryServiceTest extends Specification {
    @Autowired
    private UserService userService

    def "test 1 : "() {
        given:
        String queryConditions = "name(en=davis746317784) and defaultAddress(city=GZ)"

        when:
        def result = userService.queryUserByCriterias(queryConditions)

        then:
        result != null
        result.size() > 0
    }
}
