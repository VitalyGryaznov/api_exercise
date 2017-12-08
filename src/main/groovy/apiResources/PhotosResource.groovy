package apiResources

class PhotosResource extends BaseResource {

    final static PHOTOS_ENDPOINT = "photos"

    def static photos_get() {
        return getRes(PHOTOS_ENDPOINT)
    }
}
