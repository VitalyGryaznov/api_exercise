package steps

import apiResources.PostsResource
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import io.restassured.response.ValidatableResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static org.hamcrest.Matchers.is

class PostsSteps {

    private static final Logger log = LoggerFactory.getLogger(PostsSteps.class.getName());

    static getAllPosts() {
        log.info("getting all posts")
        return PostsResource.posts_get()
    }

    static getPostById(postId) {
        log.info("getting post with id $postId")
        return PostsResource.posts_postId_get(postId)
    }

    static createPost(userId, postTitle, postBody) {
        log.info("creating post with userId = $userId, postTitle = $postTitle, postBody = $postBody")
        return PostsResource.posts_post(userId, postTitle, postBody)
    }

    static updatePost(postId, postTitle, postBody) {
        log.info("updating post with postId = $postId using data: postTitle = $postTitle, postBody = $postBody")
        return PostsResource.posts_put(postId, postTitle, postBody)
    }

    static deletePost(postId) {
        log.info("deleting post postId = $postId")
        return PostsResource.posts_delete(postId)
    }

    static verifyThatNumberOfPostsIsEqualOrBiggerThan(validatableResponse, schemaPath, int minNumberOfPosts) {
        log.info("verifying that number of posts is equal or bigger than $minNumberOfPosts")

        log.debug("# parse json schema to map")
        def jsonFile = new File(schemaPath)
        def jsonSlurper = new JsonSlurper()
        def object = jsonSlurper.parse(jsonFile)
        assert object instanceof Map

        log.debug("# updating json schema")
        object.'minItems' = minNumberOfPosts
        JsonBuilder builder = new JsonBuilder(object)

        log.debug("# making verification")
        return CommonSteps.responseMatchesSchemaString(validatableResponse, builder.toString())
    }

    static extractPostIdFromResponse(ValidatableResponse response) {
        log.debug("extracting post id from post validatable response")
        response.extract().response().path("id")
    }

    static verifyThatPostResponseContainsExpectedData(ValidatableResponse response, postId, tittle, body) {
        log.info("verifying that response  contains postId = $postId, tittle = $tittle, body = $body")
        response.assertThat().body("id", is(postId))
        response.assertThat().body("title", is(tittle))
        response.assertThat().body("body", is(body))
    }

    static verifyThatPostResponseContainsExpectedData(ValidatableResponse response, postId, tittle, body, userId) {
        log.info("verifying that response  contains postId = $postId, tittle = $tittle, body = $body, userId = $userId")
        response.assertThat().body("userId", is(userId))
        response.assertThat().body("id", is(postId))
        response.assertThat().body("title", is(tittle))
        response.assertThat().body("body", is(body))
    }


}
