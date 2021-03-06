# What was tested
I had the following plan for testing:
  - Create a set of tests, that make one GET request for each endpoin, validate response code and json schema
  - Create a set of simple positive tests for each endpoint
  - Create a set of simple negative tests for each endpoint
  - Investigate connections between endpoints. Create complicated tests, based on this connections

 What I actually tested in required time frame:
  - Created a set of tests, that make one GET request for each endpoint, validate response code and json schema
  - Created set of simple positive test from 'posts' endpoint  (see src/test/groovy/PostsTest.groovy). But didn't add some simple positive tests for static data

# Framework design
Tools:
  - The main tool is https://github.com/rest-assured/rest-assured. Used it, because it's very reliable, good documented, flexible tool with a big community
  - Used Spock framework http://spockframework.org/spock/docs/1.1/index.html as a bdd framework. It is simple and provides human readable reports

Framework layers:
  - [src/test/groovy] test scenarios
  - [src/main/groovy/steps] reusable test steps, used in test scenarios
  - [src/main/groovy/apiResources] request for each endpoint used in test steps
  - [src/main/groovy/apiResources/BaseResource.groovy] base requests used in all endpoint requests
  - [src/main/resources/testData] test data (only schemas in the current case) used in test scenarios

# Setup and run
Requirements:
Maven installed
To run:
type in terminal $: mvn clean test

# Where to see reports after running tests
- [build/spock-reports/index.html] human readable html reports
- [build/spock-reports/aggregated_report.json] aggregated report for Jenkins
- [target/surefire-reports] surefire reports for Jenkins

# What wasn't done because of the time frame
- Not all endpoint covered even with simple positive tests
- No positive tests with static test data for "posts" endpoint
- No test data layer for users, static profiles and so on
- Some duplicated code wasn't removed (for example status code constants are the same in each test scenario)
- cleanup after tests
- No good logging. Used only default REST assured logging and some log4j logs. It is possible to implement much better logging (including logging steps, curl requests, one line responses, but I can't push it on public github)

# REST API bugs:
this section contains only bugs for 'posts' endpoint

##Title: Posts: any user is able to create post on behalf of any existing user
Steps to  Reproduce:
#Request
Request method:	POST
Request URI:	https://jsonplaceholder.typicode.com/posts
Body:
{
    "userId": 1,
    "title": "sss",
    "body": "sss"
}
Expected: authorization error
Actual: 201 status code

##Title: Posts: user is able to create empty post
Steps to  Reproduce:
Request method:	POST
Request URI:	https://jsonplaceholder.typicode.com/posts
Body:
{}
Expected: error status code
Actual: 201 status code

##Title: Posts: any values passed in create post request body appears in response body
Steps to  Reproduce:
Request method:	POST
Request URI:	https://jsonplaceholder.typicode.com/posts
Body:
{
"any_key": "any_value"
}
Expected: no "any_key" in response body
Actual: response body
{
"some_key": "some_value",
"id": 101
}

##Title: Posts: any types could be used in created post request body
Steps to reproduce:
Request method:	POST
Request URI:	https://jsonplaceholder.typicode.com/posts
{
 "userId": "any",
 "title": 1.20,
 "body": 2222222
}

Expected: error status code/ types mismatch
Atcual: 201 status code, response body:
{
    "userId": "any",
    "title": 1.2,
    "body": 2222222,
    "id": 101
}

##Title: Posts: while creating post, user is able to set post id
Steps to reproduce:
Request create post with int id>100 or any string id
Request method:	POST
Request URI:	https://jsonplaceholder.typicode.com/posts
{
 "id": "any"
}
Expected: error status code or id value from the request is ignored
Actual: 201 status code, response body:
{
 "id": "any"
}

##Title: Posts: after updating post and getting it again post isn't updated

##Title: Posts: after deleting post and getting it again post isn't deleted


