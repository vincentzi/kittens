name := "preowned-kittens"

// Custom keys for this build.
val gitHeadCommitSha = taskKey[String]("Determines the current git commit SHA")
val makeVersionProperties = taskKey[Seq[File]]("Creates a version.properties file we can find at runtime.")

// gitHeadCommitSha := Process("git rev-parse HEAD").lines.head
ThisBuild / gitHeadCommitSha := "8451caf93a691067978b0b49907cec505e38e7f8"

// Common settings/definitions for the build
def PreownedKittenProject(name: String): Project = (
	Project(name, file(name))
	settings(
		version := "1.0",
		organization := "com.preownedkittens",
		libraryDependencies ++= Seq(
			"org.specs2" %% "specs2-core" % "4.3.6" % Test,
			"org.specs2" %% "specs2-html" % "4.3.6" % Test,
			"junit" % "junit" % "4.12",
			"com.novocode" % "junit-interface" % "0.11"
			// "org.pegdown" % "pegdown" % "1.0.2" % "test"
		),
		Test / fork := true,
		Test / testOptions += Tests.Argument("html", "html.outDir", "target/generated/test-reports")
	)
)



// Projects in this build

lazy val common = (
	PreownedKittenProject("common")
	settings(
		makeVersionProperties := {
			val propFile = (Compile / resourceManaged).value / "version.properties"
			val content = "version=%s" format (gitHeadCommitSha.value)
			IO.write(propFile, content)
			Seq(propFile)
		}
	)
)

val analytics = (
	PreownedKittenProject("analytics")
	dependsOn(common)
	settings()
)

val website = (
	PreownedKittenProject("website")
	dependsOn(common)
	settings()
)








