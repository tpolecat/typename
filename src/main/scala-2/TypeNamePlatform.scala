// Copyright (c) 2020 by Rob Norris
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package org.tpolecat.typename

import scala.reflect.macros.blackbox.Context

trait TypeNamePlatform {

  implicit def typeName[T]: TypeName[T] =
    macro TypeNamePlatform.typeName_impl[T]

}

object TypeNamePlatform {

  def typeName_impl[T](c: Context): c.Expr[TypeName[T]] = {
    import c.universe._
    val TypeApply(_, List(typeTree)) = c.macroApplication
    c.Expr(q"org.tpolecat.typename.TypeName(${typeTree.toString})")
  }

}