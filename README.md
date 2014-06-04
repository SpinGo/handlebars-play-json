handlebars-play-json
====================

Play-JSON data bindings for handlebars.scala

# Installation:

```scala
libraryDependencies += "com.spingo" %% "handlebars-play-json" % "0.2.1"
```

# Example:

Main.scala:

```scala
package fun

import com.gilt.handlebars.Handlebars
import com.spingo.handlebars.PlayJsonBinding._
import play.api.libs.json._

object Main extends App {
  val json = Json.parse("""
    {
      "lucky": [1,2,3,5,8],
      "name": { "first":  "Tim"},
      "favorites": {
        "color": "blue",
        "language": "scala"
      }
    }""")

  val template = """
My name is {{name.first}}.

These are my favorite #'s:
{{#lucky}}
- {{this}}{{/lucky}}

These are my favorite things:
{{#each favorites}}
- {{@key}}: {{this}}{{/each}}
"""
  val hb = Handlebars(template)

  println(hb(json))
}
```
