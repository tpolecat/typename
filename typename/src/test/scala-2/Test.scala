// Copyright (c) 2020-2021 by Rob Norris
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package test

import org.tpolecat.typename._
import java.util.TreeMap
import java.net.URI

class Test extends munit.FunSuite {

  test("ground type") {
    assertEquals(typeName[String], "String")
  }

  test("parameterized type") {
    assertEquals(typeName[List[Int]], "List[Int]")
  }

  test("array (historically problematic)") {
    assertEquals(typeName[Array[Int]], "Array[Int]")
  }

  test("abstracted") {
    def foo[A: TypeName]: String = s"The name is ${typeName[A]}"
    assertEquals(foo[Array[Double]], "The name is Array[Double]")
  }

  test("fully-qualified") {
    assertEquals(typeName[TreeMap[ClassLoader, URI]], "java.util.TreeMap[ClassLoader,java.net.URI]")
  }

  test("wildcard (shorthand)") {
    assertEquals(typeName[List[_]], "List[_]")
  }

  test("wildcard (as forSome)") {
    assertEquals(typeName[List[a] forSome { type a }], "List[_]")
  }

  test("bounded wildcard (shorthand)") {
    assertEquals(typeName[List[_ <: ClassLoader]], "List[_ <: ClassLoader]")
  }

  test("bounded wildcard (as forSome)") {
    assertEquals(typeName[List[a] forSome { type a <: ClassLoader }], "List[_ <: ClassLoader]")
  }

  test("non-wildcard existential") {
    assertEquals(typeName[(A, Ordering[A]) forSome { type A }], "(A, Ordering[A])( forSome { type A })")
  }

  test("dependent existential") {
    assertEquals(typeName[List[a.B] forSome { val a: { type B }}], "List[a.B]( forSome { val a: AnyRef{type B} })")
  }

}