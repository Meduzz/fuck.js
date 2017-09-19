package se.chimps.fuckjs

object MountAndTrigger {
	def apply(c:Component, m:Mutation):MountAndTrigger = new MountAndTrigger(c, m)
	def unapply(arg:MountAndTrigger):Option[(Component, Mutation)] = Some(arg.c, arg.m)
}

class MountAndTrigger(val c:Component, val m:Mutation) extends Action
