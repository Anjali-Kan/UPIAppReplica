const functions = require('firebase-functions');
const admin = require('firebase-admin');

let addNewUser = functions.auth.user().onCreate((user) => {
        let docId = user.uid;
        const db = admin.firestore();
        const FieldValue = admin.firestore.FieldValue;
        //get the user collection
        let data = {
            balance: 0,
            phoneNumber: user.phoneNumber,
            timestamp: FieldValue.serverTimestamp()
        };

        return db.collection('users').doc(docId).set(data).then(r => {
            console.log("user " + user.phoneNumber + " successfully added!");
            return Promise.resolve()
        }).catch(r => {
            console.log("Some error occured in user creation!");
            return Promise.reject(r)
        });

    })

module.exports={'addNewUser':addNewUser};