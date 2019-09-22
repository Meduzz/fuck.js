package se.chimps.fuckjs.html

import org.scalajs.dom.raw.Event

trait Attributes {
	def on[T <: Event](action:String, handler:T => Unit):Attribute = EventAttribute(action, handler)
	def attribute(key:String, value:String):Attribute = TextAttribute(key, value)
	def href(url:String):Attribute = attribute("href", url)
	def value(value:String):Attribute = attribute("value", value)
	def typ(typ:String):Attribute = attribute("type", typ)
	def clazz(clz:String):Attribute = attribute("class", clz)
	def title(title:String):Attribute = attribute("title", title)
	def action(action:String):Attribute = attribute("action", action)
	def id(id:String):Attribute = attribute("id", id)
	def name(name:String):Attribute = attribute("name", name)
	def placeholder(text:String):Attribute = attribute("placeholer", text)
}
