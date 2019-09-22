package se.chimps.fuckjs.html

object Implicits {
	implicit def attributes(attrs:Seq[Attribute]):Attributes = new Attributes(attrs)
	implicit def tags(tags:Seq[Tag]):Tags = new Tags(tags)

	class Attributes(attrs:Seq[Attribute]) {
		def &(attr:Attribute):Seq[Attribute] = attrs ++ Seq(attr)
	}

	class Tags(tags:Seq[Tag]) {
		def &>(tag:Tag):Seq[Tag] = tags ++ Seq(tag)
	}
}
