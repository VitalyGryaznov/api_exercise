import spock.lang.Specification
import steps.CommonSteps
import steps.AlbumsSteps
import utils.PathHelpers

class AlbumsTest extends Specification {

    private static final SUCCESS_GET_STATUS_CODE = 200

    private static final ALL_ALBUMS_SCHEMA_SUBPATH = "albumsTestData/allAlbumsSchema.json"
    
    def "I am able to get all albums"() {
        when: 'I request all albums'
        def allAlbumsResponse = AlbumsSteps.getAllAlbums()

        then: 'I get success response status code'
        CommonSteps.verifyResponseStatusCode(allAlbumsResponse, SUCCESS_GET_STATUS_CODE)

        then: 'response matches json schema'
        CommonSteps.responseMatchesSchemaPath(allAlbumsResponse, PathHelpers.getTestDataPath(ALL_ALBUMS_SCHEMA_SUBPATH))
    }
}
