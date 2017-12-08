package apiResources

class AlbumsResource extends BaseResource{

    final static ALBUMS_ENDPOINT = "albums"

    def static albums_get() {
        return getRes(ALBUMS_ENDPOINT)
    }
}
