package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Hello Scala Enthusiasts."))
  }

  def buy(id: Long) = Action {
    NotImplemented
  }

  def addDrink = Action {
    NotImplemented
  }

  def addDrinkSubmit = Action {
    NotImplemented
  }

}
