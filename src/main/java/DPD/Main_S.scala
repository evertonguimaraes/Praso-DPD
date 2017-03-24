package DPD

import DPD.Model.DependencyType_S

import scala.io.Source

/**
  * Created by Justice on 3/23/2017.
  */
object Main_S {
  val testDsmFile = "files\\dsm\\simpleObserverPattern.dsm"

  def main(args: Array[String]): Unit = {
    val (dependencies, count, adjMatrix, files) = parse(testDsmFile)

    println("dependendies", dependencies)
    adjMatrix.foreach(arr => {arr.foreach(print); println})
    files.foreach(println)
  }

  def parse(path: String): (List[(DependencyType_S.Value, Int)], Int, List[Array[(Int, Int)]], List[String]) = {
    val depLine::countLine::matrix_files = Source.fromFile(path).getLines().toList
    val count = Integer.parseInt(countLine)

    def extractDepArray(arg: String): List[DependencyType_S.Value] =
      arg.replace("[", "").replace("]", "").split(",").map(x => DependencyType_S.withName(x.trim.toUpperCase)).toList

    def extractDepMatrix(lines: List[String]): List[Array[(Int, Int)]] =
      lines.map(l => l.split(" ").map(c => Integer.parseInt(c,2)).zipWithIndex.filter(t => t._1 != 0))

    def fixFilePath(paths: List[String]): List[String] =
      paths.map(l => {
        if (l.endsWith("_java"))
          l.replace(".", "\\").replace("_", ".")
        else l
      })

    val deps = extractDepArray(depLine)
    val depSize = deps.length - 1
    val finalDependencyTuple = deps.zipWithIndex.map((t) => (t._1, math.pow(2,depSize-t._2).toInt))
    (finalDependencyTuple,  // dependency line
      count,                    // size of the matrix
      extractDepMatrix(matrix_files.take(count)),   // the matrix parsed
      fixFilePath(matrix_files.takeRight(count)))   // the file paths fixed
  }
}
