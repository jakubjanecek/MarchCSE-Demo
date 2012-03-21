package models

import anorm._
import anorm.SqlParser._
import play.api.db.DB
import play.api.Play.current

case class Drink(id: Pk[Long],
                 name: String,
                 price: Int,
                 kind: String)

object Drink {

  private val aDrink = {
    get[Pk[Long]]("id") ~
        str("name") ~
        int("price") ~
        str("kind") map {
      case id ~ name ~ price ~ kind => Drink(id, name, price, kind)
    }
  }

  def find(id: Long): Option[Drink] = {
    DB.withConnection {
      implicit connection =>
        SQL(
          """
            SELECT * FROM drink WHERE id = {id}
          """
        ).on(
          'id -> id
        ).as(aDrink.singleOpt)
    }
  }

  def list: Seq[Drink] = {
    DB.withConnection {
      implicit connection =>
        SQL(
          """
            SELECT * FROM drink
          """
        ).as(aDrink *)
    }
  }

  def create(drink: Drink): Boolean = {
    DB.withConnection {
      implicit connection =>
        SQL(
          """
            INSERT INTO drink (name, price, kind) VALUES ({name}, {price}, {kind})
          """
        ).on(
          'name -> drink.name,
          'price -> drink.price,
          'kind -> drink.kind
        ).executeUpdate() == 1
    }
  }

}
