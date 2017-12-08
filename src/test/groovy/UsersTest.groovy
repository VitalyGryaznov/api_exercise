import spock.lang.Specification
import steps.CommonSteps
import steps.UsersSteps
import utils.PathHelpers

class UsersTest extends Specification {

    private static final SUCCESS_GET_STATUS_CODE = 200

    private static final ALL_USERS_SCHEMA_SUBPATH = "usersTestData/allUsersSchema.json"

    def "I am able to get all users"() {
        when: 'I request all users'
        def allUsersResponse = UsersSteps.getAllUsers()

        then: 'I get success response status code'
        CommonSteps.verifyResponseStatusCode(allUsersResponse, SUCCESS_GET_STATUS_CODE)

        then: 'response matches json schema'
        CommonSteps.responseMatchesSchemaPath(allUsersResponse, PathHelpers.getTestDataPath(ALL_USERS_SCHEMA_SUBPATH))
    }
}
