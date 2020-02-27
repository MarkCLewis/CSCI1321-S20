package basics

import java.io.FileOutputStream
import java.io.BufferedOutputStream
import java.io.ObjectOutputStream
import java.io.ObjectInputStream
import java.io.BufferedInputStream
import java.io.FileInputStream

case class Student(name: String, id: String, gpa: Double)

object StreamIO extends App {
  // val students = List(
  //   Student("Pat", "0123456", 2.3),
  //   Student("Patrick", "6543210", 3.78),
  //   Student("Patricia", "0987694", 3.98)
  // )
  // val os = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("students.bin")))
  // os.writeObject(students)
  // os.close()

  val is = new ObjectInputStream(new BufferedInputStream(new FileInputStream("students.bin")))
  val students = is.readObject()
  println(students)
  is.close()
}