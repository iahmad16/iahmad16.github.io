name := """Alwakalat-Admin-Dashboard"""
organization := "com.alwakalat"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.12"

libraryDependencies += guice

libraryDependencies += ws

libraryDependencies ++= Seq(
  javaWs
)
