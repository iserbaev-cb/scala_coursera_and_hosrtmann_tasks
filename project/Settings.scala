import sbt.Keys._
import sbt._

object Settings {
  def commonSettings2_13() = Seq(
    scalaVersion := "2.13.3",
    Compile / scalacOptions ++= compilerSettings_2_13, // https://gist.github.com/guilgaly/b73ad98384051ecdc7be39b1f053fc87
    addCompilerPlugin(
      ("org.typelevel" %% "kind-projector" % "0.11.0").cross(CrossVersion.full)
    )
  )

  private val compilerSettings_2_13 = Seq(
    "-encoding",
    "utf-8", // Specify character encoding used by source files.
    "-Ybackend-parallelism", //
    "8",
    "-explaintypes", // Explain type errors in more detail.
    "-feature", // Emit warning and location for usages of features that should be imported explicitly.
    "-unchecked", // Enable additional warnings where generated code depends on assumptions.
    "-Xcheckinit", // Wrap field accessors to throw an exception on uninitialized access.
    "-Ymacro-annotations", // Enable support for macro annotations, formerly in macro paradise.

    // ********** -language: Enable or disable language features *****************
    // "-language:dynamics", //Allow direct or indirect subclasses of scala.Dynamic
    "-language:existentials", // Existential types (besides wildcard types) can be written and inferred
    "-language:higherKinds", // Allow higher-kinded types
    "-language:implicitConversions", // Allow definition of implicit functions called views
    // "-language:postfixOps", // Allow postfix operator notation, such as 1 to 10 toList (not recommended)
    // "-language:reflectiveCalls", // Allow reflective access to members of structural types
    "-language:experimental.macros", // Allow macro definition (besides implementation and application)

    // ********** Warning Settings ***********************************************
    "-Werror", // Fail the compilation if there are any warnings.
    "-Wdead-code", //  Warn when dead code is identified.
    "-Wextra-implicit", // Warn when more than one implicit parameter section is defined.
    // "-Wmacros:none", // Do not inspect expansions or their original trees when generating unused symbol warnings.
    // "-Wmacros:before", // Only inspect unexpanded user-written code for unused symbols. (Default)
    "-Wmacros:after", // Only inspect expanded trees when generating unused symbol warnings.
    // "-Wmacros:both", // Inspect both user-written code and expanded trees when generating unused symbol warnings.
    "-Wnumeric-widen", // Warn when numerics are widened.
    "-Woctal-literal", // Warn on obsolete octal syntax.
    // "-Wself-implicit", // Warn when an implicit resolves to an enclosing self-definition.
    "-Wunused:imports", //Warn if an import selector is not referenced.
    "-Wunused:patvars", // Warn if a variable bound in a pattern is unused.
    "-Wunused:privates", // Warn if a private member is unused.
    "-Wunused:locals", // Warn if a local definition is unused.
    "-Wunused:explicits", // Warn if an explicit parameter is unused.
    "-Wunused:implicits", // Warn if an implicit parameter is unused.
    "-Wunused:params", // Enable -Wunused:explicits,implicits.
    "-Wvalue-discard", // Warn when non-Unit expression results are unused.

    // ********** -Xlint: Enable recommended warnings ****************************
    "-Xlint:adapted-args", // Warn if an argument list is modified to match the receiver.
    "-Xlint:nullary-unit", // Warn when nullary methods return Unit.
    "-Xlint:inaccessible", // Warn about inaccessible types in method signatures.
    "-Xlint:nullary-override", // Warn when non-nullary def f() overrides nullary def f.
    "-Xlint:infer-any", // Warn when a type argument is inferred to be Any.
    // "-Xlint:missing-interpolator", // A string literal appears to be missing an interpolator id.
    "-Xlint:doc-detached", // A Scaladoc comment appears to be detached from its element.
    "-Xlint:private-shadow", // A private field (or class parameter) shadows a superclass field.
    "-Xlint:type-parameter-shadow", // A local type parameter shadows a type already in scope.
    "-Xlint:poly-implicit-overload", // Parameterized overloaded implicit methods are not visible as view bounds.
    "-Xlint:option-implicit", // Option.apply used implicit view.
    "-Xlint:delayedinit-select", // Selecting member of DelayedInit.
    "-Xlint:package-object-classes", // Class or object defined in package object.
    "-Xlint:stars-align", // Pattern sequence wildcard must align with sequence component.
    "-Xlint:constant", // Evaluation of a constant arithmetic expression results in an error.
    "-Xlint:nonlocal-return", // A return statement used an exception for flow control.
    "-Xlint:implicit-not-found", // Check @implicitNotFound and @implicitAmbiguous messages.
    "-Xlint:serial", // @SerialVersionUID on traits and non-serializable classes.
    "-Xlint:valpattern", // Enable pattern checks in val definitions.
    "-Xlint:eta-zero", // Warn on eta-expansion (rather than auto-application) of zero-ary method.
    "-Xlint:eta-sam", // Warn on eta-expansion to meet a Java-defined functional interface that is not explicitly annotated with @FunctionalInterface.
    "-Xlint:deprecation" // Enable linted deprecations.
  )

  def commonSettings2_12() = Seq(
    scalaVersion := "2.12.10",
    Compile / scalacOptions ++= compilerSettings_2_12,
    addCompilerPlugin(
      ("org.scalamacros" % "paradise" % "2.1.0").cross(CrossVersion.full)
    ),
    addCompilerPlugin(
      ("org.typelevel" %% "kind-projector" % "0.11.0").cross(CrossVersion.full)
    )
  )

  private val compilerSettings_2_12 = Seq(
    "-deprecation", // Emit warning and location for usages of deprecated APIs.
    "-encoding",
    "utf-8", // Specify character encoding used by source files.
    "-explaintypes", // Explain type errors in more detail.
    "-feature", // Emit warning and location for usages of features that should be imported explicitly.
    "-language:existentials", // Existential types (besides wildcard types) can be written and inferred
    "-language:experimental.macros", // Allow macro definition (besides implementation and application)
    "-language:higherKinds", // Allow higher-kinded types
    "-language:implicitConversions", // Allow definition of implicit functions called views
    "-unchecked", // Enable additional warnings where generated code depends on assumptions.
    "-Xcheckinit", // Wrap field accessors to throw an exception on uninitialized access.
    "-Xfatal-warnings", // Fail the compilation if there are any warnings.
    "-Xfuture", // Turn on future language features.
    "-Xlint:adapted-args", // Warn if an argument list is modified to match the receiver.
    "-Xlint:by-name-right-associative", // By-name parameter of right associative operator.
    "-Xlint:constant", // Evaluation of a constant arithmetic expression results in an error.
    "-Xlint:delayedinit-select", // Selecting member of DelayedInit.
    "-Xlint:doc-detached", // A Scaladoc comment appears to be detached from its element.
    "-Xlint:inaccessible", // Warn about inaccessible types in method signatures.
    "-Xlint:infer-any", // Warn when a type argument is inferred to be `Any`.
    "-Xlint:missing-interpolator", // A string literal appears to be missing an interpolator id.
    "-Xlint:nullary-override", // Warn when non-nullary `def f()' overrides nullary `def f'.
    "-Xlint:nullary-unit", // Warn when nullary methods return Unit.
    "-Xlint:option-implicit", // Option.apply used implicit view.
    "-Xlint:package-object-classes", // Class or object defined in package object.
    "-Xlint:poly-implicit-overload", // Parameterized overloaded implicit methods are not visible as view bounds.
    "-Xlint:private-shadow", // A private field (or class parameter) shadows a superclass field.
    "-Xlint:stars-align", // Pattern sequence wildcard must align with sequence component.
    "-Xlint:type-parameter-shadow", // A local type parameter shadows a type already in scope.
    "-Xlint:unsound-match", // Pattern match may not be typesafe.
    "-Yno-adapted-args", // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.
    "-Ypartial-unification", // Enable partial unification in type constructor inference
    "-Ywarn-dead-code", // Warn when dead code is identified.
    "-Ywarn-extra-implicit", // Warn when more than one implicit parameter section is defined.
    "-Ywarn-inaccessible", // Warn about inaccessible types in method signatures.
    "-Ywarn-infer-any", // Warn when a type argument is inferred to be `Any`.
    "-Ywarn-nullary-override", // Warn when non-nullary `def f()' overrides nullary `def f'.
    "-Ywarn-nullary-unit", // Warn when nullary methods return Unit.
    "-Ywarn-numeric-widen", // Warn when numerics are widened.
    //      "-Ywarn-unused:implicits",           // Warn if an implicit parameter is unused.
    //      "-Ywarn-unused:imports",             // Warn if an import selector is not referenced.
    "-Ywarn-unused:locals", // Warn if a local definition is unused.
    //      "-Ywarn-unused:params",              // Warn if a value parameter is unused.
    "-Ywarn-unused:patvars", // Warn if a variable bound in a pattern is unused.
    "-Ywarn-unused:privates", // Warn if a private member is unused.
    "-Ywarn-value-discard" // Warn when non-Unit expression results are unused.
  )
}
