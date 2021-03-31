// Copyright (c) 2020-2021 by Rob Norris
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package test

import org.tpolecat.typename._
import java.util.TreeMap
import java.net.URI

class Test extends munit.FunSuite {

  test("ground type") {
    assertEquals(typeName[String], "java.lang.String")
  }

  test("parameterized type") {
    assertEquals(typeName[List[Int]], "scala.collection.immutable.List[scala.Int]")
  }

  test("array (historically problematic)") {
    assertEquals(typeName[Array[Int]], "scala.Array[scala.Int]")
  }

  test("abstracted") {
    def foo[A: TypeName]: String = s"The name is ${typeName[A]}"
    assertEquals(foo[Array[Double]], "The name is scala.Array[scala.Double]")
  }

  test("fully-qualified") {
    assertEquals(typeName[TreeMap[ClassLoader, URI]], "java.util.TreeMap[java.lang.ClassLoader, java.net.URI]")
  }

  test("wildcard (shorthand)") {
    assertEquals(typeName[List[_]], "scala.collection.immutable.List[_ >: scala.Nothing <: scala.Any]")
  }

}