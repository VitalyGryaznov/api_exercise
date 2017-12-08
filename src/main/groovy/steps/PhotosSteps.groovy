package steps

import apiResources.PhotosResource
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class PhotosSteps {

    private static final Logger log = LoggerFactory.getLogger(PhotosSteps.class.getName());

    static getAllPhotos() {
        log.info("getting all photos")
        return PhotosResource.photos_get()
    }
}
