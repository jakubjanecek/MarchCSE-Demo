package models

import anorm.Pk

case class Drink(id: Pk[Long],
                 name: String,
                 price: Int,
                 kind: String)

object Drink {
  
  

}
