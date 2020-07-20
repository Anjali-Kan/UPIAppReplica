const functions = require('firebase-functions');
const admin = require('firebase-admin');

async function writeToUsers(transactionId, fromUser, toUser, amount) {
    //TODO CHAIN promises if debited fromUser then add to toUser and if transactions added to history then play with balance
    const db = admin.firestore()
    const userRef = db.collection('users');
    let snapshot = await userRef.where('phoneNumber', '==', fromUser).get();
    console.log(snapshot)
    let promises=[]
    snapshot.forEach((UserObj)=>{
        let finalBal=UserObj.data().balance - amount
        promises.push(UserObj.ref.set({'balance':finalBal},{'merge':true}))
    })

    // await snapshot[0].ref.set({'balance':finalBal},{'merge':true})

    snapshot = await userRef.where('phoneNumber', '==', toUser).get();
    snapshot.forEach((UserObj)=>{
        let finalBal=UserObj.data().balance +amount
        promises.push(UserObj.ref.set({'balance':finalBal},{'merge':true}))
    })

    await Promise.all(promises)


}

let newTransactionadded=
    functions.firestore.document('transactions/{transactionId}').
    onCreate((snapshot, context) =>{

        const newDoc= snapshot.data();
        const transactionId=snapshot.id;
        const fromUser=newDoc.from;
        const toUser=newDoc.to;
        //TODO write changes in those user's nodes
        const amount= newDoc.amount
        return writeToUsers(transactionId,fromUser,toUser,amount)

    })

module.exports={'newTransactionAdded':newTransactionadded,'wt':writeToUsers}
