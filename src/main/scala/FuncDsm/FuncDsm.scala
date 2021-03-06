package FuncDsm

import java.io._

import DPD._

import scala.collection.mutable.ListBuffer
import scala.io.Source

// read all lines in file
// use tokenizer to break up into csv columns
// parse csv into a case class
// verify parsed csv is valid
// map the "other" function to its index in the list
// map the "dependency" to a string representation
// group the lines by function
// the group list keys become the file (function list) list
// the group list values is mapped to a line (string) representation
// print the depenecy, size, matrix and functions list

object FuncDsm {

  case class Csv(function: String, file: String, line: Int, dependsOnFunction: String, dependsOnType: DependencyType.Value, dependsOnFile: String)

  def oldMain(args: Array[String]): Unit = {
    val files = getListOfFiles(new File("D:\\Code\\Tools\\art_tools\\scripts\\project399\\csv"), "csv")
    files.par.foreach(f => genDsm(f.getAbsolutePath))
  }

  def getListOfFiles(dir: File, ext: String): List[File] = dir.listFiles.filter(f => f.isFile && f.getName.endsWith(ext)).toList

  def genDsm(file: String): Unit = {
    try {
      val csv = getCsvFromFile(file)
      val dsmFile = new File(new File(file).getAbsolutePath + ".dsm")
      if (csv.isEmpty || dsmFile.exists()) return
      val genDsm = new GenDsm(csv)
      val pw = new PrintWriter(dsmFile)
      println("writing dsm for: " + dsmFile.getName)
      pw.write(genDsm.printStr)
      pw.close()
    } catch {
      case e: Exception => println("\n***Error processing " + file + "\n" + e)
    }
  }

  def getFilePath(file: String): String = getClass.getClassLoader.getResource(file).getPath

  def getCsvFromFile(file: String): List[Csv] = {
    val lines = Source.fromFile(file).getLines()
    lines.toList.tail.map(l => tokenize(l))
  }

  def tokenize(line: String): Csv = {
    val tokens = ListBuffer[String]()
    val iter = line.iterator
    var sb = new StringBuilder()
    while (iter.hasNext) {
      var ch = iter.next()
      if (ch == ',') {
        tokens += sb.toString
        sb = new StringBuilder()
      }
      else if (ch == '(') {
        while (ch != ')') {
          sb.append(ch)
          ch = iter.next()
        }
        sb.append(ch) // add closing ")"
      }
      else sb.append(ch)
    }
    tokens += sb.toString

    // empty string buffer
    def dotFile(file: String): String = file.replace(".java", "").replace("\\", ".")

    Csv(tokens(0), tokens(1), tokens(2).toInt, tokens(3), DependencyType.withName(tokens(4).toUpperCase()), dotFile(tokens(5)))
  }
}
