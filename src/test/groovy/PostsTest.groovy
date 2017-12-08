import spock.lang.Specification
import spock.lang.Unroll
import steps.CommonSteps
import steps.PostsSteps
import utils.DataUtils
import utils.PathHelpers

class PostsTest extends Specification {

    private static final SUCCESS_GET_STATUS_CODE = 200

    private static final SUCCESS_POST_STATUS_CODE = 201

    private static final SUCCESS_PUT_STATUS_CODE = 200

    private static final SUCCESS_DELETE_STATUS_CODE = 200

    private static final NOT_FOUND_STATUS_CODE = 404

    private static final ALL_POSTS_SCHEMA_SUBPATH = "postsTestData/allPostsSchema.json"

    private static final POST_SCHEMA_SUBPATH = "postsTestData/postSchema.json"

    private static final POST_UPDATE_SCHEMA_SUBPATH = "postsTestData/postUpdateSchema.json"


    def "I am able to get all posts"() {
        when: 'I request all posts'
        def allPostsResponse = PostsSteps.getAllPosts()

        then: 'I get success response status code'
        CommonSteps.verifyResponseStatusCode(allPostsResponse, SUCCESS_GET_STATUS_CODE)

        then: 'response matches json schema'
        CommonSteps.responseMatchesSchemaPath(allPostsResponse, PathHelpers.getTestDataPath(ALL_POSTS_SCHEMA_SUBPATH))
    }

    def "I am able to get post by postId [#postId]"() {
        when: 'I request posts [#postId]'
        def response = PostsSteps.getPostById(postId)

        then: 'I get success response status code'
        CommonSteps.verifyResponseStatusCode(response, SUCCESS_GET_STATUS_CODE)

        then: 'response matches json schema'
        CommonSteps.responseMatchesSchemaPath(response, PathHelpers.getTestDataPath(POST_SCHEMA_SUBPATH))

        expect: 'post has expected id'
        PostsSteps.extractPostIdFromResponse(response) == postId

        where: 'post id = [#postId]'
        postId << [7]
    }

    @Unroll
    def "I am able to create post [#testPermutation]"() {
        when: 'I create new post'
        def response = PostsSteps.createPost(userId, postTitle, postBody)
        def postId = PostsSteps.extractPostIdFromResponse(response)

        then: 'i get success response status code'
        CommonSteps.verifyResponseStatusCode(response, SUCCESS_POST_STATUS_CODE)

        then: 'response matches json schema'
        CommonSteps.responseMatchesSchemaPath(response, PathHelpers.getTestDataPath(POST_SCHEMA_SUBPATH))

        then: 'response contains expected data'
        PostsSteps.verifyThatPostResponseContainsExpectedData(response, postId, postTitle, postBody, userId)

        where: '[#testPermutation]'
        testPermutation       | postTitle                                | postBody                                 | userId
        ""                    | "test title"                             | "test post "                             | 1
        "long title"          | DataUtils.getRandomString(100, "x")      | "some post"                              | 2
        "long post body"      | "test title"                             | DataUtils.getRandomString(5000, "x")     | 5
        "long title and post" | DataUtils.getRandomString(100, "x")      | DataUtils.getRandomString(5000, "x")     | 2
        "special characters"  | DataUtils.getStringOfSpecialCharacters() | DataUtils.getStringOfSpecialCharacters() | 10

    }

    @Unroll
    def "I am able to get created post [#testPermutation]"() {
        given: 'There is created by me post'
        def response = PostsSteps.createPost(userId, postTitle, postBody)
        def postId = PostsSteps.extractPostIdFromResponse(response)

        when: 'I request my post by postId'
        response = PostsSteps.getPostById(postId)

        then: 'I get success response status code'
        CommonSteps.verifyResponseStatusCode(response, SUCCESS_GET_STATUS_CODE)

        then: 'response matches json schema'
        CommonSteps.responseMatchesSchemaPath(response, PathHelpers.getTestDataPath(POST_SCHEMA_SUBPATH))

        then: 'response contains expected data'
        PostsSteps.verifyThatPostResponseContainsExpectedData(response, postId, postTitle, postBody, userId)

        where: '[#testPermutation]'
        testPermutation       | postTitle                                | postBody                                 | userId
        ""                    | "test title"                             | "test post "                             | 1
        "long Title"          | DataUtils.getRandomString(100, "x")      | "some post"                              | 2
        "long post body"      | "test Title"                             | DataUtils.getRandomString(5000, "x")     | 5
        "long title and post" | DataUtils.getRandomString(100, "x")      | DataUtils.getRandomString(5000, "x")     | 2
        "special characters"  | DataUtils.getStringOfSpecialCharacters() | DataUtils.getStringOfSpecialCharacters() | 10
    }

    @Unroll
    def "I am able to update post [#testPermutation]"() {
        given: 'There is created by me post'
        def response = PostsSteps.createPost(userId, postTitle, postBody)
        def postId = PostsSteps.extractPostIdFromResponse(response)

        when: 'I update this post'
        response = PostsSteps.updatePost(postId, postTitle, postBody)

        then: 'I get success response status code'
        CommonSteps.verifyResponseStatusCode(response, SUCCESS_PUT_STATUS_CODE)

        then: 'response matches json schema'
        CommonSteps.responseMatchesSchemaPath(response, POST_UPDATE_SCHEMA_SUBPATH)

        then: 'response contains expected data'
        PostsSteps.verifyThatPostResponseContainsExpectedData(response, postId, postTitle, postBody)

        where: '[#testPermutation]'
        testPermutation       | postTitle                                | postBody                                 | userId
        ""                    | "test title"                             | "test post "                             | 1
        "long Title"          | DataUtils.getRandomString(100, "x")      | "some post"                              | 2
        "long post body"      | "test Title"                             | DataUtils.getRandomString(5000, "x")     | 5
        "long title and post" | DataUtils.getRandomString(100, "x")      | DataUtils.getRandomString(5000, "x")     | 2
        "special characters"  | DataUtils.getStringOfSpecialCharacters() | DataUtils.getStringOfSpecialCharacters() | 10
    }

    @Unroll
    def "post contains expected details after updating it [#testPermutation]"() {
        given: 'there is an updated post post'
        def response = PostsSteps.createPost(userId, postTitle, postBody)
        def postId = PostsSteps.extractPostIdFromResponse(response)
        PostsSteps.updatePost(postId, postTitle, postBody)

        when: 'I request updated post by postId'
        response = PostsSteps.getPostById(postId)

        then: 'I get success response status code'
        CommonSteps.verifyResponseStatusCode(response, SUCCESS_GET_STATUS_CODE)

        then: 'response matches json schema'
        CommonSteps.responseMatchesSchemaPath(response, PathHelpers.getTestDataPath(POST_SCHEMA_SUBPATH))

        then: 'response contains expected data'
        PostsSteps.verifyThatPostResponseContainsExpectedData(response, postId, postTitle, postBody, userId)

        where: '[#testPermutation]'
        testPermutation       | postTitle                                | postBody                                 | userId
        ""                    | "test title"                             | "test post "                             | 1
        "long Title"          | DataUtils.getRandomString(100, "x")      | "some post"                              | 2
        "long post body"      | "test Title"                             | DataUtils.getRandomString(5000, "x")     | 5
        "long title and post" | DataUtils.getRandomString(100, "x")      | DataUtils.getRandomString(5000, "x")     | 2
        "special characters"  | DataUtils.getStringOfSpecialCharacters() | DataUtils.getStringOfSpecialCharacters() | 10
    }


    def "I am able to delete post"() {
        given: 'there is a post'
        def response = PostsSteps.createPost(userId, postTitle, postBody)
        def postId = PostsSteps.extractPostIdFromResponse(response)

        when: 'I delete this post'
        response = PostsSteps.deletePost(postId)

        then: 'I get success response status code'
        CommonSteps.verifyResponseStatusCode(response, SUCCESS_DELETE_STATUS_CODE)

        where:
        postTitle | postBody | userId
        "sss"     | "sss"    | 1
    }

    def "After deleting post, it disappears from the posts"() {
        given: 'I deleted a post'
        def response = PostsSteps.createPost(userId, postTitle, postBody)
        def postId = PostsSteps.extractPostIdFromResponse(response)
        response = PostsSteps.deletePost(postId)
        CommonSteps.verifyResponseStatusCode(response, SUCCESS_DELETE_STATUS_CODE)

        when: 'I request deleted post by postId'
        response = PostsSteps.getPostById(postId)

        then: 'I get "not found" satus code'
        CommonSteps.verifyResponseStatusCode(response, NOT_FOUND_STATUS_CODE)

        where:
        postTitle | postBody | userId
        "title"   | "body"   | 1
    }

    def "total number of posts is equal or bigger than expected number"() {
        when: 'I request all posts'
        def response = PostsSteps.getAllPosts()

        then: 'I get success response status code'
        CommonSteps.verifyResponseStatusCode(response, SUCCESS_GET_STATUS_CODE)

        then: 'number of posts equal  or is bigger then expected number'
        PostsSteps.verifyThatNumberOfPostsIsEqualOrBiggerThan(response, PathHelpers.getTestDataPath(ALL_POSTS_SCHEMA_SUBPATH), minNumberOfPosts)

        where:
        minNumberOfPosts << [100]
    }

}
