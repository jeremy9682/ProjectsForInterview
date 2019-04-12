function Node(value){
	this.value = value;
	this.front = null;
	this.next = null;
	this.getValue = function(){
		return this.value;
		};
	
}


function DoubleLinkedList(newComparator){
	this.size = 0;
	this.head = null;
	this.tail =null;
	this.comparator = newComparator;
	}

	DoubleLinkedList.prototype = {
	
		constructor:DoubleLinkedList,

		getHead: function(){
			return this.head;
		},

		addToFront: function(element){	
	
	let newOne = new Node(element);
		if(this.size == 0){
			this.head = newOne;
			this.tail = newOne;
			size++;
		}else{
			let oldHead = this.head;
			newOne.next = oldHead;
			oldHead.front = newOne;
			this.head = newOne;
			size++;
			}	
	},

	addToEnd : function(element){

	let newOne = new Node(element);
	if(this.size == 0){
			this.head = newOne;
			this.tail = newOne;
			this.size++;
		}else{
			let oldTail = this.tail;
			newOne.front = oldTail;
			oldTail.next = newOne;
			this.tail = newOne;
			this.size++;
			}	
	},

	Insert : function(element, start, end){
	
	let newNode = new Node(element);

	if(this.size == 0){
		this.head == newNode;
		this,tail == newNode;
		this.size++;
	}else{
		newNode.font = start;
		newNode.next = end;
		start.next = newNode;
		end.font = newNode;
		this.size++;
		}
	},

SizeofList :  function(){
	return this.size;
	},

empty : function(){	
		if(size == 0){
			return true;
		}else{
			return false;
			}

		}
		
	};






