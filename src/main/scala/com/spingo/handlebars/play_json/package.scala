package com.spingo.handlebars

import play.api.libs.json._
import com.gilt.handlebars.scala.helper.Helper

package object play_json {

  import com.gilt.handlebars.scala.binding.Binding
  import com.gilt.handlebars.scala.binding.BindingFactory
  import com.gilt.handlebars.scala.binding.FullBinding
  import com.gilt.handlebars.scala.helper.HelperOptions
  import com.gilt.handlebars.scala.logging.Loggable
  implicit def jsValueToPlayJsonBinding(jsonValue: JsValue) =
    new PlayJsonBinding(jsonValue)
  implicit val bindingFactory = PlayJsonBindingFactory

  class EachOrderedHelper[T] extends Helper[T] with Loggable {
    def apply(self: Binding[T], options: HelperOptions[T])(implicit bindingFactory: BindingFactory[T]): String = {
      val arg0 = options.argument(0)
      if (arg0.isDictionary) {
        arg0.asDictionaryCollection.toList.sortBy(_._1).zipWithIndex.map {
          case ((key, value), idx) =>
            options.visit(value,
              Map(
                "key" -> bindingFactory.bindPrimitive(key),
                "index" -> bindingFactory.bindPrimitive(idx)))
        }.mkString(options.argument(1).render)
      } else if (arg0.isCollection) {
        arg0.asCollection.toList.sortBy(_.render).zipWithIndex.map {
          case (value, idx) =>
            options.visit(value,
              Map(
                "index" -> bindingFactory.bindPrimitive(idx)))
        }.mkString(options.argument(1).render)
      } else {
        warn("Could not iterate over argument for {{#each}}")
        ""
      }
    }
  }

  class EachDelimHelper[T] extends Helper[T] with Loggable {
    def apply(self: Binding[T], options: HelperOptions[T])(implicit bindingFactory: BindingFactory[T]): String = {
      val arg0 = options.argument(0)
      if (arg0.isDictionary) {
        arg0.asDictionaryCollection.toList.zipWithIndex.map {
          case ((key, value), idx) =>
            options.visit(value,
              Map(
                "key" -> bindingFactory.bindPrimitive(key),
                "index" -> bindingFactory.bindPrimitive(idx)))
        }.mkString(options.argument(1).render)
      } else if (arg0.isCollection) {
        arg0.asCollection.toList.zipWithIndex.map {
          case (value, idx) =>
            options.visit(value,
              Map(
                "index" -> bindingFactory.bindPrimitive(idx)))
        }.mkString(options.argument(1).render)
      } else {
        warn("Could not iterate over argument for {{#each}}")
        ""
      }
    }
  }

  class IfEqualHelper[T]  extends Helper[T] with Loggable {
    def apply(self: Binding[T], options: HelperOptions[T])(implicit bindingFactory: BindingFactory[T]): String = {
      (options.argument(0), options.argument(1)) match {
        case (FullBinding(left), FullBinding(right)) if (left == right) =>
          options.visit(self)
        case _ =>
          options.inverse(self)
      }
    }
  }

  val jsonHelpers = Map(
    "json" -> Helper[JsValue] { (self, options) =>
      options.argument(0).asOption.map(_.get.toString).getOrElse("undefined")
    },
    "prettyJson" -> Helper[JsValue] { (self, options) =>
      val arg = options.argument(0).getOrElse { JsNull }
      Json.prettyPrint(arg)
    },
    "eachOrdered" -> new EachOrderedHelper[JsValue],
    "eachDelim" -> new EachDelimHelper[JsValue],
    "ifEqual" -> new IfEqualHelper[JsValue]
  )
}
