var mongoose = require('mongoose');
var Schema = mongoose.Schema;
const contactModel = new Schema({
    fullName : { type: String   },
    emailAddress: { type: String },
    phoneNumber: {type: String}
})
//export default mongoose.model('book', bookModel)
module.exports = mongoose.model('Contact', contactModel);