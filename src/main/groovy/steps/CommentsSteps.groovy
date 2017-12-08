package steps

import apiResources.CommentsResource
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class CommentsSteps {

    private static final Logger log = LoggerFactory.getLogger(CommentsSteps.class.getName());

    static getAllComments() {
        log.info("getting all comments")
        return CommentsResource.comments_get()
    }
}
