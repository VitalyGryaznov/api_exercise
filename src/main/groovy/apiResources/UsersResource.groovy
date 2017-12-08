package apiResources

class UsersResource extends BaseResource {

    final static USERS_ENDPOINT = "users"

    def static users_get() {
        return getRes(USERS_ENDPOINT)
    }
}
