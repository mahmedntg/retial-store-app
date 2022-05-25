# Retail Store

### Introduction
Retail Store API is a simple project that is calculating discounts for different
types of users, following specific Discount rules:

1. If the user is an employee of the store, he gets a 30% discount.
2. If the user is an affiliate of the store, he gets a 10% discount.
3. If the user has been a customer for over 2 years, he gets a 5% discount.
4. For every $100 on the bill, there would be a $ 5 discount (e.g. for $ 990, you get $ 45 as
a discount).
5. The percentage based discounts do not apply on groceries.
6. A user can get only one of the percentage based discounts on a bill

### Install & Run using docker-compose
- Clone the repository
- use mvn to clean and package the following:-
  1. gateway project: gateway for the store service API
  2. store project that contains pay API
- move to the project folder that contains docker-compose.yml file
-  run this command: docker-compose up -d --force-recreate  --renew-anon-volumes  this will create three docker images as following:-
  1. gateway-container: gateway
  2. mongo: mongo DB for storing the provides user types
  3. store-container: store image
- The app should be up and running on `localhost:8080`

### Tooling
- Swagger - API documentation
- JWT - authentication mechanism
- mongodb - db for the store app
- eureka client
- spring eureka server as service discovery

### UML Class Diagram
![](store-app.png)

### Testing
To run all tests, execute the following command
```
./mvnw clean test
```

### Demo Data
`UserConfig` class will load test users when the application starts.
Password is `test1` to all users.
Usernames are as follows:
```
employee
affiliate
customer
```
### Sample request flow
- Get authentication token
```
Request
POST /api/auth/signin
{
  "username": "employee@retail.com",
  "password": "secret"
}
```
```$xslt
Response
{
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZmZpbGlhdGUiLCJpYXQiOjE2NTM0NTQ3ODEsImV4cCI6MTY1MzU0MTE4MX0.rQz-TlZ8cztEDWnkjP-qd9RrlUC55AQnaZoblRd5s02Mx51kb91zjsFrOL8YdnKWktUCOvOtw9gNHYNIHTwfcg",
}
```
- Calculate discount for the logged in user
```$xslt
Request
POST /api/store/pay
HEADER: Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZmZpbGlhdGUiLCJpYXQiOjE2NTM0NTQ3ODEsImV4cCI6MTY1MzU0MTE4MX0.rQz-TlZ8cztEDWnkjP-qd9RrlUC55AQnaZoblRd5s02Mx51kb91zjsFrOL8YdnKWktUCOvOtw9gNHYNIHTwfcg
PAYLOAD
{
    "bill": {
        "items": [
            {
                "type": "GROCERY",
                "price": 2.3
            },
            {
                "type": "TECHNOLOGY",
                "price": 549
            },
            {
                "type": "GROCERY",
                "price": 522.3
            }
        ]
    }
}
```
```$xslt
Response
{
  968.700
}
```
