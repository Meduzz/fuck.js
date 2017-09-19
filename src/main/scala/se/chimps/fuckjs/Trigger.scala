package se.chimps.fuckjs

object Trigger {
	def apply(m:Mutation):Trigger = new Trigger(m)
	def unapply(arg:Trigger):Option[Mutation] = Some(arg.m)
}

class Trigger(val m:Mutation) extends Action
