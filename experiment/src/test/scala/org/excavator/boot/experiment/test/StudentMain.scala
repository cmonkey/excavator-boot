package org.excavator.boot.experiment.test

case class Student(age:Int, grades: List[Int])

object StudentMain extends App{

  val grades = List(3, 4, 1)
  val student = Student(22, grades)
  if(student.age < 20){
    student.grades(5)
  }else{
    student.grades(4)
  }
}
