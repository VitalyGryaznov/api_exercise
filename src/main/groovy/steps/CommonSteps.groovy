package steps

import com.github.fge.jsonschema.core.load.Dereferencing
import com.github.fge.jsonschema.core.load.configuration.LoadingConfiguration
import com.github.fge.jsonschema.main.JsonSchemaFactory
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import io.restassured.response.ValidatableResponse

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema

class CommonSteps {

    static final JsonSchemaFactory schemaFactory = JsonSchemaFactory.newBuilder()
            .setLoadingConfiguration(LoadingConfiguration.newBuilder().dereferencing(Dereferencing.INLINE).freeze()).freeze();

    static def responseMatchesSchemaPath(ValidatableResponse response, String schemaPath) {
        def inputFile = new File(schemaPath)
        def object = new JsonSlurper().parse(inputFile)
        assert object instanceof Map
        JsonBuilder builder = new JsonBuilder(object)
        response.assertThat().body(matchesJsonSchema(builder.toString()).using(schemaFactory));
    }

    static def responseMatchesSchemaString(ValidatableResponse response, String schemaString) {
        response.assertThat().body(matchesJsonSchema(schemaString).using(schemaFactory));
    }

    static def verifyResponseStatusCode(ValidatableResponse response, int statusCode) {
        response.assertThat().statusCode(statusCode)
    }
}
