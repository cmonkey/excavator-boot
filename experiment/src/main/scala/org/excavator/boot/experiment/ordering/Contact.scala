package org.excavator.boot.experiment.ordering

import scala.collection.immutable.SortedSet
import scala.math.Ordering

final case class Contact(
                        lastName:String,
                        firstName:String,
                        phoneNumber:String
                        )

object Contact extends App{
  // Wrong -- never do this
  implicit val ordering:Ordering[Contact] =
    (x:Contact, y:Contact) =>
    x.lastName.compareTo(y.lastName) <||>
      x.firstName.compareTo(y.firstName) <||>
      x.phoneNumber.compareTo(x.phoneNumber)

  private implicit class IntExtensions(val num:Int) extends AnyVal {
    def `<||>`(other: => Int): Int =
      if(num == 0) other else num
  }

  val agenda1 = List(
    Contact("Nedelcu", "Alexandru", "0738293904"),
    Contact("Nedelcu", "Amelia", "0745029304"),
  )

  agenda1.sorted

  val agenda2 = List(
    Contact("Nedelcu", "Amelia", "0745029304"),
    Contact("Nedelcu", "Alexandru", "0738293904"),
  )

  agenda2.sorted

  agenda1.sorted == agenda2.sorted

  val set1 = SortedSet(agenda1:_*)
  val set2 = SortedSet(agenda2:_*)
}
