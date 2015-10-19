if (db.account.find({"_id": ObjectId("111111111111111111111111")}).size() === 0) {
    db.account.insert({
        _id: new ObjectId("111111111111111111111111"),
        _class: "Account",
        "login": "support",
        "password": "341b3ef9c74000a36b3974c166e90f6b",
        "role": "Admin",
        "status": "Active",
        "createdBy": "support",
        "createdDate": new Date(),
        "lastModifiedBy": "support",
        "lastModifiedDate": new Date()
    });
}