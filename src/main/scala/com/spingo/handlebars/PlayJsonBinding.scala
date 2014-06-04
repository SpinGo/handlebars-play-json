package com.spingo.handlebars

import com.gilt.handlebars.context.{ BindingFactory, FullBinding, Binding, VoidBinding }
import com.gilt.handlebars.logging.Loggable
import java.lang.reflect.Method
import play.api.libs.json._

class PlayJsonBinding(val data: JsValue) extends FullBinding[JsValue] with Loggable {
  override def toOption = if (isValueless) None else Some(data)
  override def toString = s"PlayJsonBinding(${data})"

  lazy val renderString =
    if (isTruthy)
      data match {
        case JsString(s)  => s
        case JsNumber(n)  => n.toString
        case JsBoolean(b) => b.toString
        case _ => data.toString
      }
    else
      ""

  lazy val isTruthy = data match {
    case JsBoolean(t)   => t
    case JsString(s)    => s != ""
    case JsNumber(n)    => n != 0
    case JsNull         => false
    case t: JsUndefined => false
    case _ => true
  }

  lazy val isDictionary = data.isInstanceOf[JsObject]
  lazy val isCollection = data.isInstanceOf[JsArray] && ! isDictionary

  val isUndefined = false
  protected lazy val isValueless = data match {
    case JsNull => true
    case t: JsUndefined => true
    case _ => false
  }

  def traverse(key: String, args: List[Any] = List.empty): Binding[JsValue] =
    data match {
      case m: JsObject => (m \ key) match {
        case u: JsUndefined =>
          info("Could not traverse key ${key} in ${m}")
          VoidBinding[JsValue]
        case value =>
          new PlayJsonBinding(value)
      }
      case _ => VoidBinding[JsValue]
    }

  lazy val asCollection =
    data match {
      case JsArray(m) =>
        m map (new PlayJsonBinding(_))
      case _ =>
        Seq(this)
    }

  lazy val asDictionaryCollection = {
    data match {
      case JsObject(m) => m map { case (k,v) => (k, new PlayJsonBinding(v)) }
      case _ => Seq()
    }
  }
}

object JsonValueBindingFactory extends BindingFactory[JsValue] {
  def apply(_model: JsValue): PlayJsonBinding =
    new PlayJsonBinding(_model)

  def bindPrimitive(v: String) = apply(JsString(v))
  def bindPrimitive(model: Int) = apply(JsNumber(model))
}

object PlayJsonBinding {
  implicit def jsValueToPlayJsonBinding(jsonValue: JsValue) =
    new PlayJsonBinding(jsonValue)
  implicit val bindingFactory = JsonValueBindingFactory
}
