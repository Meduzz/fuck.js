package se.chimps.fuckjs.html

trait Tags {
	def tag(name:String, attribute: Seq[Attribute] = Seq(), children:Seq[Tag] = Seq()):RealTag = RealTag(name, attribute, children)
	def div(attribute: Attribute*)(children:Tag*):RealTag = tag("div", attribute, children)
	def span(attribute: Attribute*)(children:Tag*):RealTag = tag("span", attribute, children)
	def p(attribute: Attribute*)(children:Tag*):RealTag = tag("p", attribute, children)
	def form(attribute: Attribute*)(children:Tag*):RealTag = tag("form", attribute, children)
	def input(attribute: Attribute*)(children:Tag*):RealTag = tag("input", attribute, children)
	def button(attribute: Attribute*)(children:Tag*):RealTag = tag("button", attribute, children)
	def a(attribute: Attribute*)(children:Tag*):RealTag = tag("a", attribute, children)
	def label(attribute: Attribute*)(children:Tag*):RealTag = tag("label", attribute, children)
	def ul(attribute: Attribute*)(children:Tag*):RealTag = tag("ul", attribute, children)
	def li(attribute: Attribute*)(children:Tag*):RealTag = tag("li", attribute, children)
	def text(text:String):TextTag = TextTag(text)
}