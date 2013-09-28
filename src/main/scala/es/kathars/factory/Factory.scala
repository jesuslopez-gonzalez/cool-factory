/*
 * Copyright (c) 2013 Jesús López-González
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.kathars.factory

import language.experimental.macros
import scala.annotation.StaticAnnotation
import scala.reflect.macros.Context

object Factory { 

  class buildable extends StaticAnnotation { 
    def macroTransform(annottees: Any*) = macro macroTransformImpl
  }

  def macroTransformImpl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = { 
    import c.universe._
    import Flag._

    val classDef @ ClassDef(_, className, _, template) = annottees.head.tree
    val Template(parents, self, body) = template

    lazy val objectConstructor =
      q"""def ${nme.CONSTRUCTOR}() = { super.${nme.CONSTRUCTOR}(); () }"""

    lazy val dummyDef: DefDef =
      q"def dummy: Int = 1234567890"

    val newObjectBody: List[Tree] = List(objectConstructor, dummyDef)
    val newObjectTemplate = Template(parents, template.self, newObjectBody)
    val newObjectDef = ModuleDef(Modifiers(), classDef.name.toTermName, newObjectTemplate)

    c.Expr[Any](Block(List(classDef, newObjectDef), Literal(Constant(()))))
  }
}