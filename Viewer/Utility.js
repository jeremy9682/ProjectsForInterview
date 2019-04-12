
function Utility(utilityName, utilityDescription){
	this.Name = utilityName;
	this.Description = utilityDescription;
} 



Utility.prototype={
	constructor: Utility,
	info: function(){
		document.writeln("Name: " + this.Name + ", Description: " + this.Description);
	}
};
