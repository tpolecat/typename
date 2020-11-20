// Copyright (c) 2020 by Rob Norris
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package org.tpolecat

package object typename {

  def typeName[A](implicit ev: TypeName[A]): String =
    ev.value

}