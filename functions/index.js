// import {addNewUser} from './myfunctions/initialiseNewUser'
// import {addNewUser} from "./myfunctions/initialiseNewUser";

const initialiseNewUser = require('./myfunctions/initialiseNewUser')
const newTransactionAdded = require('./myfunctions/watchTransactions')
const functions = require('firebase-functions');
// import * as functions from 'firebase-functions'
// import * as admin from 'firebase-admin'
const admin = require('firebase-admin');
admin.initializeApp();

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//   functions.logger.info("Hello logs!", {structuredData: true});
//   response.send("Hello from Firebase!");
// });

module.exports={'addNewUser':initialiseNewUser.addNewUser,'transactionAdded':newTransactionAdded};
