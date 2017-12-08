package apiResources

class CommentsResource extends BaseResource {

    final static COMMENTS_ENDPOINT = "comments"

    def static comments_get() {
        return getRes(COMMENTS_ENDPOINT)
    }
}
