// Copyright (c) 2020 by Rob Norris
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package org.tpolecat.typename

import scala.quoted._

trait TypeNamePlatform {

  inline given [A] as TypeName[A] =
    ${TypeNamePlatform.impl[A]}

}

object TypeNamePlatform {

  // https://users.scala-lang.org/t/obtaining-the-name-of-a-type-scala-3/6902/7
  def impl[A](using t: Type[A], ctx: QuoteContext): Expr[TypeName[A]] =
    '{TypeName[A](${Expr(t.show)})}

}
