// Copyright (c) 2020-2021 by Rob Norris
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package org.tpolecat.typename

import scala.quoted._

trait TypeNamePlatform {

  inline given [A]: TypeName[A] =
    ${TypeNamePlatform.impl[A]}

}

object TypeNamePlatform {

  def impl[A](using t: Type[A], ctx: Quotes): Expr[TypeName[A]] =
    '{TypeName[A](${Expr(Type.show[A])})}

}
