<?php 
	//declare(strict_type=1);
	class studentObject{

		public $name = "";
		public $grade = "";

		public function __constuct(string $name){
			$this->name = $name;
			$this->grade = "NG";
		}

		public function __toString(){
			return "Name: {$name} <br> Grade: {$grade}";
		}

		public function getName() : string{
			return $this->name;
		}
		public function getGrade() : string{
			return $this->grade;
		}

		public function setName($newName){
			$this->name = $newName;
		}

		public function setGrade($newGrade){
			$this->grade = $newGrade;
		}
	}


 ?>