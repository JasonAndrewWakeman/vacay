# Overview

What a fun project! tried some new things with open api 3.0 (swagger) and api param validation. 
Focused on testing and organizing the business logic in the service and Role classes. 
Things to improve would be parameterizing configuration vars and using captors in tests.
Should have leveraged an in memory db instead of mocking it with seed data. 

NOTE: REQUIRES JAVA 17

To utilize api try running with ./gradlew bootRun and navigating to 

http://localhost:8080/swagger-ui/index.html#/

or for api spec http://localhost:8080/v3/api-docs

can also send http directly to http://localhost:8080/employees/ , http://localhost:8080/employee/5/

NOTE: the ending '/' is required

curl -X 'POST' \
'http://localhost:8080/work-statement/' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{
"employeeId": 23,
"workedDays": 223
}'

etc.

Started project with https://start.spring.io/ to generate gradle java 17 spring project
added a few libraries, but nothing extreme. 

I look forward to discussing design decisions and receiving any and all feedback if the opportunity arises! 

Thanks!
