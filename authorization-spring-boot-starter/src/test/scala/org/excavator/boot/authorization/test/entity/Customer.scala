package org.excavator.boot.authorization.test.entity

class Customer(val age: Int, val name: String, val phone: String) {

  def getFieldMap = {
    val map = new java.util.HashMap[String, String]
    map.put("age", age.toString)
    map.put("name", name)
    map.put("phone", phone)

    map
  }
}
