# gateway for the retail-store microserivce
# first use docker-compose file to run the required services
1- to call the gateway first to be authenticated

# hit localhost:8080/api/auth/signin   POST request
{
"username":"employee",
"password":"test1"
}

or

{
"username":"affiliate",
"password":"test1"
}

or

{
"username":"customer",
"password":"test1"
}

# your will get a response contains JWT token to use it for calling any authenticated service

{
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZmZpbGlhdGUiLCJpYXQiOjE2NTM0MTU5MzMsImV4cCI6MTY1MzUwMjMzM30.Ao9N0Sm2MIqTDSpNGryuVI7A0bOfTwIaGdO-4ZLLHV54tpUB8Z5f8zsLf79XuNMlXowaNCxlys3Jd-4QlLvGyg",
}


2- call the retail-store service
# hit localhost:8080/api/store/pay   POST request

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


# you will get the payable amount ex: 858.900
