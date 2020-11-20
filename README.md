# TypeName

This is a Scala micro-library that provides:

- a `typeName[A]` method that returns a string representation of `A` for all types in Scala 2, and for most concrete types in Scala 3; and
- a `TypeName[A]` typeclass for demanding that a type has a name.

The intent is that you can use this instead of `TypeTag` or other heavy machinery.

TypeName is compiled for Scala **2.12**, **2.13**, and **3.0.0-M1**.


```scala
libraryDependencies += "org.tpolecat" %% "typename" % <version>
```

#### Discussion and Demo

The actual names that are generated depend on your Scala version and what's in scope. They're good for things like error reporting, but don't depend on them being stable through space (i.e., equivalent types may give different names, depending on what's in scope when you ask) or time (names seem to be the same in 2.12 and 2.13, but they're very different in 3.0).

Anyway it's still useful. In Scala 2 the results look a bit like this.

```
Welcome to Scala 2.13.3 (OpenJDK 64-Bit Server VM, Java 11.0.7).
Type in expressions for evaluation. Or try :help.

scala> import org.tpolecat.typename._
import org.tpolecat.typename._

scala> typeName[Map[String, ClassLoader]]
val res1: String = Map[String,ClassLoader]

scala> def foo[A](a: A)(implicit ev: TypeName[A]) = s"The type of $a is ${ev.value}"
def foo[A](a: A)(implicit ev: org.tpolecat.typename.TypeName[A]): String

scala> foo(1.23)
val res2: String = The type of 1.23 is Double

scala> foo(List(Some(1), None, Some(2)))
val res3: String = The type of List(Some(1), None, Some(2)) is List[Option[Int]]
```

In Scala 3 the results are a little different, but still fine.

```
cala> import org.tpolecat.typename._

scala> typeName[Map[String, ClassLoader]]
val res0: String = scala.collection.immutable.Map[scala.Predef.String, java.lang.ClassLoader]

scala> def foo[A](a: A)(implicit ev: TypeName[A]) = s"The type of $a is ${ev.value}"
def foo[A](a: A)(implicit ev: org.tpolecat.typename.TypeName[A]): String

scala> foo(1.23)
val res1: String = The type of 1.23 is scala.Double

scala> foo(List(Some(1), None, Some(2)))
val res2: String = The type of List(Some(1), None, Some(2)) is scala.collection.immutable.List[scala.Option[scala.Int]]
```

#### Known Limitations

In Scala 2 (this correctly *doesn't* work in Scala 3) you can ask for `typeName[A]` for an unconstrainted type variable `A` and it will happily tell you `"A"`, which is almost certainly a bug if you're doing that. You need to introduce `A` like `[A: TypeName]` if you need its name.

In Scala 3 you sometimes can't get the names of nasty types, for example the type of `List(Vector(1), Set(1))`. I don't know what the limits are.



