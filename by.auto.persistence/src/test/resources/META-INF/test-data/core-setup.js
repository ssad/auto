// Put here core initialization logic if necessary.
// Usually you want to clean up all the collections in order to perform reproducible tests.

db.account.remove({});
db.generatedToken.remove({});
db.category.remove({});
