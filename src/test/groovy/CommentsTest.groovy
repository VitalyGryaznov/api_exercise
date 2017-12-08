import spock.lang.Specification
import steps.CommonSteps
import steps.CommentsSteps
import utils.PathHelpers

class CommentsTest  extends Specification {

    private static final SUCCESS_GET_STATUS_CODE = 200

    private static final ALL_COMMENTS_SCHEMA_SUBPATH = "commentsTestData/allCommentsSchema.json"

    def "I am able to get all comments"() {
        when: 'I request all comments'
        def allCommentsResponse = CommentsSteps.getAllComments()

        then: 'I get success response status code'
        CommonSteps.verifyResponseStatusCode(allCommentsResponse, SUCCESS_GET_STATUS_CODE)

        then: 'response matches json schema'
        CommonSteps.responseMatchesSchemaPath(allCommentsResponse, PathHelpers.getTestDataPath(ALL_COMMENTS_SCHEMA_SUBPATH))
    }
    
}
