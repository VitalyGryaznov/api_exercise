package apiResources

import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.response.ValidatableResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.unitils.util.WriterOutputStream

import static io.restassured.RestAssured.given

class BaseResource {

    private static final Logger log = LoggerFactory.getLogger(BaseResource.class.getName());

    private static final JSON = "application/json"

    //We have only one domain, so it's ok to put it here. In other case it's better to put it in config
    private static final DOMAIN = "https://jsonplaceholder.typicode.com/"

    def static getRes(resource) {
        log.info("GET -------------------------->" + resource);
        final StringWriter writer = new StringWriter();
        final PrintStream captor = new PrintStream(new WriterOutputStream(writer), true);
        ValidatableResponse validatableResponse =
                given()
                        .filter(new RequestLoggingFilter())
                        .filter(ResponseLoggingFilter.logResponseIfStatusCodeIs(200))
                        .when()
                        .get(DOMAIN + resource)
                        .then()

        log.info("RESPONSE <----------------------- ");

        return validatableResponse
    }

    def static getRes(resource, param1key, param1value) {
        log.info("GET -------------------------->" + resource);
        ValidatableResponse validatableResponse =
                given()
                        .param(param1key, param1value)
                        .when()
                        .get(DOMAIN + resource)
                        .then()

        log.info("RESPONSE <----------------------- ");

        return validatableResponse
    }

    def static postRes(resource, output) {
        log.info("POST -------------------------->" + resource);
        ValidatableResponse validatableResponse =
                given()
                        .header("Content-Type", JSON)
                        .body(output)
                        .filter(new RequestLoggingFilter())
                        .filter(ResponseLoggingFilter.logResponseIfStatusCodeIs(201))
                        .when()
                        .post(DOMAIN + resource)
                        .then()
        log.info("RESPONSE <----------------------- ");

        return validatableResponse
    }

    def static putRes(resource, output) {
        log.info("PUT -------------------------->" + resource);
        ValidatableResponse validatableResponse =
                given()
                        .header("Content-Type", JSON)
                        .body(output)
                        .filter(new RequestLoggingFilter())
                        .filter(ResponseLoggingFilter.logResponseIfStatusCodeIs(200))
                        .when()
                        .put(DOMAIN + resource)
                        .then()
        log.info("RESPONSE <----------------------- ");

        return validatableResponse
    }

    def static deleteRes(resource) {
        log.info("DELETE -------------------------->" + resource);
        ValidatableResponse validatableResponse =
                given()
                        .filter(new RequestLoggingFilter())
                        .filter(ResponseLoggingFilter.logResponseIfStatusCodeIs(200))
                        .when()
                        .delete(DOMAIN + resource)
                        .then()
        log.info("RESPONSE <----------------------- ");

        return validatableResponse
    }
}
