package se.chimps.fuckjs.html

import org.scalajs.dom
import org.scalajs.dom.Element

trait Attribute {
	def render(e:Element):Unit
	def &(attr:Attribute):Seq[Attribute] = Seq(this, attr)
	def !():Seq[Attribute] = Seq(this)
}

case class TextAttribute(key:String, value:String) extends Attribute {
	override def render(e:Element):Unit = e.setAttribute(key, value)
}
case class EventAttribute(on:String, evt:dom.raw.Event => Unit) extends Attribute {
	override def render(e:Element):Unit = e.addEventListener(on, evt)
}
