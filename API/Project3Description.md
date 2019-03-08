# Project 3: Build your own Rest API

## Due Date: Tuesday, January 22, 2019 11:59 PM
## Objectives: To build a web api service using the express framework.
## Specifications/Requirements
For this project you will be building a Rest API to provide endpoints for making GET, PUT, PUST and DELETE http requests for a `contact` resource. We have provided you with an express template application inside of the Project3 directory. You need to update the `app.js` file to use any routes you create inside of the `routes` directory. Note: all requests should be of the form `http://localhost:3000/api/contacts/`.
Your application will need to have the following functionality:
1. Successfully connect to your database on Mlab. You will need to add the `mongoose` npm package and save it as a dependency (hint: run `npm install --save mongoose`).
2. Create a models directory and inside of there add a file named `contact.js` that will define the Schema for the `contact` resource as well as bind it to the ORM. The Schmema should be as follows:
    1. fullName => string
    2. emailAddress => string
    3. phoneNumber => string
    
3. Inside of your api directory create a `contacts` route and define endpoints for the following requests:
    1. GET `/api/contacts`
    This will return a json object containing the information for all of the contacts in your contact collection.
    2. POST `/api/contacts`
    This will create an instance of a contact in your contacts collection with the data you provide that needs to match the schema you define.
    3. GET `/api/contacts/:email`
    This will return a json object containing the information for the contact in your collection that matches the email request parameter.
    3. PUT `/api/contacts/:email`
    This will update the contact entry in your contacts collection with the values you provide.
    4. DELETE
    `/api/contacts/:email`
    This will delete the contact resource inside of your contacts collection that has the email passed in as the parameter.

Any logic querying the database should be performed in a custom middleware function that you will need to define (hint: create `contactRouter.use('/:email', (req, res, next)=>{}`) inside of your contacts router and use the mongoose `findOne` method.  

You can test your application using the CURL command or Postman.