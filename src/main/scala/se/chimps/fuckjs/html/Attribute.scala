package se.chimps.fuckjs.html

import org.scalajs.dom.Element
import org.scalajs.dom.raw.Event

trait Attribute {
	def render(e:Element):Unit
	def &(attr:Attribute):Seq[Attribute] = Seq(this, attr)
	def !():Seq[Attribute] = Seq(this)
}

case class TextAttribute(key:String, value:String) extends Attribute {
	override def render(e:Element):Unit = e.setAttribute(key, value)
}
case class EventAttribute[T <: Event](on:String, evt:T => Unit) extends Attribute {
	override def render(e:Element):Unit = e.addEventListener(on, evt)
}
