package steps

import apiResources.AlbumsResource
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class AlbumsSteps {

    private static final Logger log = LoggerFactory.getLogger(PostsSteps.class.getName());

    static getAllAlbums() {
        log.info("getting all albums")
        return AlbumsResource.albums_get()
    }
}
