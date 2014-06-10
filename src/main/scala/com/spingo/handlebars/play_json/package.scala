package com.spingo.handlebars

import play.api.libs.json._
import com.gilt.handlebars.helper.Helper

package object play_json {
  implicit def jsValueToPlayJsonBinding(jsonValue: JsValue) =
    new PlayJsonBinding(jsonValue)
  implicit val bindingFactory = PlayJsonBindingFactory

  val jsonHelpers = Map("json" -> Helper[JsValue] { (context, options) =>
    options.argument(0).asOption.map(_.get.toString).getOrElse("undefined")
  })
}
