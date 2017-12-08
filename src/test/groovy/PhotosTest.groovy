import spock.lang.Specification
import steps.CommonSteps
import steps.PhotosSteps
import utils.PathHelpers

class PhotosTest extends Specification {

    private static final SUCCESS_GET_STATUS_CODE = 200

    private static final ALL_PHOTOS_SCHEMA_SUBPATH = "photosTestData/allPhotosSchema.json"

    def "I am able to get all photos"() {
        when: 'I request all photos'
        def allPhotosResponse = PhotosSteps.getAllPhotos()

        then: 'I get success response status code'
        CommonSteps.verifyResponseStatusCode(allPhotosResponse, SUCCESS_GET_STATUS_CODE)

        then: 'response matches json schema'
        CommonSteps.responseMatchesSchemaPath(allPhotosResponse, PathHelpers.getTestDataPath(ALL_PHOTOS_SCHEMA_SUBPATH))
    }
}
