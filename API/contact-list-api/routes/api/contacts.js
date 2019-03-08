var express = require('express');
var contactRouter = express.Router();
var Contact = require('../../models/contact')


contactRouter.use('/', (req, res, next)=>{
    console.log("I run first before I retrieve all the books")
    next()
  })

  contactRouter.use('/:email', (req, res, next)=>{
    Contact.findOne({ emailAddress:  req.params.email }, (err,contact)=>{
      if(err)
          res.status(500).send(err)
      else {
          req.contact = contact;
          next()
      }
    })
  })

  contactRouter.route('/')
    .get((req, res) => {
      Contact.find({}, (err, contacts) => {
        res.json(contacts)
      })
    })  
    .post((req, res) => {
      let contact = new Contact(req.body); 
      contact.save((err) => console.log(err));
      res.status(201).send(contact) 
    });
  


    contactRouter.route('/:email')
  .get((req, res) => {
    //querying the database for a book that matches the bookID parameter from the URL request
    Contact.findOne({emailAddress:req.params.email}, (err, contact) => {
     // res.json(book)
      // when using middleware
      res.json(req.contact)
       }) 
  })
  .put((req,res) => {
    //first retrieve the instance of the resource you want
    //update its values
    Contact.findOne({emailAddress:req.params.email}, (err, contact) => {
      //  book.title = req.body.title;
        //book.author = req.body.author;
        //book.save()
        //res.json(book)
        
        // when using middleware
        req.contact.fullName = req.body.fullName;
        req.contact.phoneNumber = req.body.phoneNumber;
        req.contact.save();
        res.json(contact)
    
  })
})
  .delete((req,res)=>{
    Contact.findOne({emailAddress:req.params.email}, (err,contact ) => {
        // with middleware
        //  req.book.remove(err => {
        contact.remove(err => {
            if(err){
                res.status(500).send(err)
            }
            else{
                res.status(204).send('removed')
            }
        })
    })
  })
  

module.exports = contactRouter;