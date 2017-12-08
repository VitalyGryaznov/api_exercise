import spock.lang.Specification
import steps.CommonSteps
import steps.TodosSteps
import utils.PathHelpers

class TodosTest extends Specification {

    private static final SUCCESS_GET_STATUS_CODE = 200

    private static final ALL_TODOS_SCHEMA_SUBPATH = "todosTestData/allTodosSchema.json"

    def "I am able to get all todos"() {
        when: 'I request all todos'
        def allTodosResponse = TodosSteps.getAllTodos()

        then: 'I get success response status code'
        CommonSteps.verifyResponseStatusCode(allTodosResponse, SUCCESS_GET_STATUS_CODE)

        then: 'response matches json schema'
        CommonSteps.responseMatchesSchemaPath(allTodosResponse, PathHelpers.getTestDataPath(ALL_TODOS_SCHEMA_SUBPATH))
    }
}
