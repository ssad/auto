    db.account.insert({
        "_class": "Account",
        "login": "test@auto",
        "password": "341b3ef9c74000a36b3974c166e90f6b",
        "role": "User",
        "status": "Active",
        "createdBy": "support",
        "createdDate": new Date(),
        "lastModifiedBy": "support",
        "lastModifiedDate": new Date()
    });

    db.app.insert({
        "_class": "App",
        "apiKey": "key123",
        "secret": "secret",
        "callbackUrl": "",
        "summary": {
            "name":"test app",
            "iconUrl":"",
            "description":"somewhere",
            "slug":""
        }
    });
/*
    to test oauth use url POST http://localhost:8080/auto/oauth/token?client_id=key123&client_secret=secret&username=test@auto&password=Passw0rd&grant_type=password
*/