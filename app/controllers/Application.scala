package controllers

import play.api._
import data.Form
import data.Forms._
import play.api.mvc._
import models.Drink
import anorm.NotAssigned

object Application extends Controller {

  private val form = Form(
    mapping(
      "name" -> nonEmptyText(1, 100),
      "price" -> number(max = 1000),
      "kind" -> nonEmptyText(1, 20)
    )
        ((name, price, kind) => Drink(NotAssigned, name, price, kind))
        ((drink: Drink) => Some(drink.name, drink.price, drink.kind))
  )

  def index = Action {
    implicit request =>
      Ok(views.html.index(Drink.list, messages))
  }

  def buy(id: Long) = Action {
    Drink.find(id) match {
      case Some(drink) => Redirect(routes.Application.index).flashing("success" -> "You bought a delicious %s for $%d.".format(drink.name, drink.price))
      case None => Redirect(routes.Application.index).flashing("error" -> "Drink not found!")
    }
  }

  def addDrink = Action {
    Ok(views.html.addDrink(form))
  }

  def addDrinkSubmit = Action {
    implicit request =>
      val bindedForm = form.bindFromRequest
      bindedForm.fold(
        errorForm => BadRequest(views.html.addDrink(errorForm)),
        drink => {
          if (Drink.create(drink)) {
            Redirect(routes.Application.index).flashing("success" -> "OK")
          }
          else {
            Redirect(routes.Application.index).flashing("error" -> "ERROR")
          }
        }
      )
  }

  private def messages(implicit request: RequestHeader): Seq[String] = request.flash.data.map(_._2).toSeq

}
