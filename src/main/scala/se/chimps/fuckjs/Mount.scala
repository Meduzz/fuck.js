package se.chimps.fuckjs

object Mount {
	def apply(c:Component):Mount = new Mount(c)
	def unapply(arg:Mount):Option[Component] = Some(arg.c)
}

class Mount(val c:Component) extends Action {
}