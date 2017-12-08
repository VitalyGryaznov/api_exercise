package apiResources

import groovy.json.JsonOutput

class PostsResource extends BaseResource {

    final static POSTS_ENDPOINT = "posts"

    def static posts_get() {
        return getRes(POSTS_ENDPOINT)
    }

    def static posts_get(parameter, key) {
        return getRes(POSTS_ENDPOINT, parameter, key)
    }

    def static posts_postId_get(postId) {
        return getRes(POSTS_ENDPOINT + "/" + postId)
    }

    def static posts_post(userId, postTitle, postBody) {
        def output = JsonOutput.toJson(['userId': userId, 'title': postTitle, 'body': postBody])
        return postRes(POSTS_ENDPOINT, output)
    }

    def static posts_put(postId, postTitle, postBody) {
        def output = JsonOutput.toJson(['title': postTitle, 'body': postBody])
        return putRes(POSTS_ENDPOINT + "/" + postId, output)
    }

    def static posts_delete(postId) {
        return deleteRes(POSTS_ENDPOINT + "/" + postId)
    }
}
