package se.chimps.fuckjs.html

import org.scalajs.dom
import org.scalajs.dom.Element

trait Tag {

	def &>(tag:Tag):Seq[Tag] = Seq(this, tag)
	def !>():Seq[Tag] = Seq(this)

}

case class RealTag(name:String, attribs:Seq[Attribute], children:Seq[Tag]) extends Tag {
	def tag:String = {
		var t = if (name.contains("#")) {
			name.split("#")(0)
		} else {
			name
		}

		if (t.contains(".")) {
			t.split("\\.")(0)
		} else {
			t
		}
	}

	def id:Option[String] = {
		val id = if (name.contains("#")) {
			Some(name.split("#")(1))
		} else {
			None
		}

		id.map(i => {
			if (i.contains(".")) {
				i.split("\\.")(0)
			} else {
				i
			}
		})
	}

	def clazz:Option[String] = {
		val c = if (name.contains(".")) {
			name.split("\\.").drop(1)
		} else {
			Array[String]()
		}

		if (c.nonEmpty) {
			Some(c.mkString(" "))
		} else {
			None
		}
	}

	def render():Element = {
		val el = dom.document.createElement(tag)

		var attrs = id match {
			case Some(i) => attribs ++ Seq(TextAttribute("id", i))
			case None => attribs
		}

		attrs = clazz match {
			case Some(clz) => {
				// make sure we dont overwrite existing class attributes
				val clazz = attrs.filter(_.isInstanceOf[TextAttribute])
					.map(_.asInstanceOf[TextAttribute])
					.find(_.key == "class") match {
					case Some(a) => a.copy(value = a.value ++ " " ++ clz)
					case None => TextAttribute("class", clz)
				}

				attrs ++ Seq(clazz)
			}
			case None => attrs
		}

		attrs.foreach { _.render(el) }

		children.foreach {
			case c:RealTag => el.appendChild(c.render())
			case TextTag(text) => el.textContent = text
		}

		el
	}
}

case class TextTag(text:String) extends Tag
