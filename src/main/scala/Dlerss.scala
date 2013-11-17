package main.scala

import java.io.File
import java.net.URL
import scala.xml.{Elem, XML}
import sys.process._
import com.typesafe.config.ConfigFactory
import collection.JavaConversions._
import scala.util.matching.Regex


class Dlerss(configurationFile: File) {
  case class Setting(
    name: String,
    url: URL,
    scanTime: Int,
    regexTrue: Regex,
    regexFalse: Regex,
    folder: File
  )


  val conf = ConfigFactory.parseFile(configurationFile)
  val settings = conf.getStringList("names").map { name =>
    Setting(
      name,
      new URL(conf.getString(s"$name.url")),
      conf.getInt(s"$name.scanTime"),
      conf.getString(s"$name.regexTrue").r,
      conf.getString(s"$name.regexFalse").r,
      new File(conf.getString(s"$name.saveFolder"))
    )
  }

  settings.foreach { s =>
    if (!s.folder.isDirectory || !s.folder.canWrite)
      System.err.println(s"Unable to write to folder: ${s.folder.getAbsolutePath}")
  }

  settings foreach { setting =>
    new Thread() {
      override def run() {
        while (true) {
          for (item <- XML.load(setting.url) \\ "item") {
            val title = (item \ "title").text

            if (setting.regexFalse.findFirstIn(title).isEmpty && setting.regexTrue.findFirstIn(title).isDefined) {
              val saveFile = new File(s"${setting.folder.getAbsolutePath}/$title.torrent")

              if (!setting.folder.isDirectory) {
                System.err.println(s"Folder doesn't exist. (${setting.folder})")
              } else if (!setting.folder.canWrite) {
                System.err.println(s"Can't write to folder, check permissions. (${setting.folder})")
              } else if (!saveFile.exists()) {
                (new URL((item \ "link").text) #> saveFile).run()
                println(s"Downloaded: ${saveFile.getName} from ${setting.name}.")
              }
            }
          }
          Thread.sleep(setting.scanTime * 60000)
        }
      }
    }.start()
  }
}

object Dlerss {
  def main(args: Array[String]) {
    val configurationFile = args.headOption.map { filePath =>
      new File(filePath)
    } getOrElse {
      new File("application.conf")
    }

    if (configurationFile.canRead) {
      new Dlerss(configurationFile)
    } else {
      System.err.println(s"Unable to read configuration file: $configurationFile")
    }
  }
}