package steps

import apiResources.UsersResource
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class UsersSteps {
    private static final Logger log = LoggerFactory.getLogger(UsersSteps.class.getName());

    static getAllUsers() {
        log.info("getting all users")
        return UsersResource.users_get()
    }
}
