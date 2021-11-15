# Java Practice API #

This project contains several sub-projects that provide a way to practice the skills of producing and consuming APIs

## Sub-Projects ##

### Producer API ### 

This project will start a Spring Boot application that exposes the following endpoint

`http://localhost:8081/api/{environment}/{appName}/version`

and return a JSON document representing the version information of that `{appName}` deployed in that `{environment}`.

The version follows the semver.org Semantic Versioning schema. 

#### Example ####
`curl http://localhost:8081/api/qa/app1/version`
```json
{
  "applicationName": "app1",
  "environment": "qa",
  "version": "1.2.3"
}
```

### Consumer API ###

Several sub-projects will be devoted to the consumption of the above API by implementing different methods for doing so such as Spring Web and Spring Webflux. 

