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

The consumer should expose an endpoint or cli command to do the following task:

1. Connect to a list of services (mocked via the producer) to discover version information about the application serving the request. 
2. Record the version information of all the applications for two environments: `qa` and `prod`
3. Compare the version information and return the following json response

```json
[{
  "application": {
    "name": "{appName}",
    "versions": {
      "qa": "{qa-version}",
      "prod": "{prod-version}"
    },
    "action": "{Update Production|None}"
  }
}]
```
Rules: 
- If the version deployed to Production is one major version newer than QA, then QA needs to be updated. 
- If the version deployed to QA is one major version ahead of Production, then Production needs to be updated.
- If the version deployed to QA is only newer by minor or patch releases, then no update to Production is required, otherwise Production needs to be updated.
- If the version deployed to Production is only newer by less than five minor releases, then no update is required, otherwise QA needs to be updated.

All version numbers will use [Semantic Versioning](https://semver.org).
