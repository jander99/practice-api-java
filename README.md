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
  "environment": "dev",
  "version": "1.2.3"
}
```

### Consumer API ###

Several sub-projects will be devoted to the consumption of the above API by implementing different methods for doing so such as Spring Web and Spring Webflux. 

The consumer should expose an endpoint or cli command to do the following task:

1. Connect to a list of services (mocked via the producer) to discover version information about the application serving the request. 
2. Record the version information of all the applications for two environments: `dev` and `prod`
3. Compare the version information and return the following json response

```json
[{
  "application": {
    "name": "{appName}",
    "versions": [{
      "environment": "dev",
      "version": "dev-version"
      }, 
      {
        "environment": "prod",
        "version": "pr-version"
      }],
    "action": "{Update Production|No Action}"
  }
}]
```
Rules: 
- If the version deployed to Production is newer than Dev, then no action is needed. 
- If the version deployed to Dev is newer than Production, then Production needs to be updated.

Example Acceptance Criteria:
```gherkin
Given a Dev version of 1.2.9-SNAPSHOT
And a Prod version of 1.2.5
When I compute the action required
Then I will recommend to "Upgrade Production"
```

```gherkin
Given a Dev version of 2.3.11-SNAPSHOT
And a Prod version of 2.4
When I compute the action requestion
Then I was recommend "No Action"
```

All version numbers will use [Semantic Versioning](https://semver.org).
