package se.chimps.fuckjs

import org.scalajs.dom.raw.Event

trait EventHandler {

	def trigger[T <: Event](m:Mutation):Function1[T, Unit] = { e:T => {
		handle(m)
	}}

	def trigger[T <: Event](map:T => Mutation):Function1[T, Unit] = { e:T => {
		handle(map(e))
	}}


	def handle:PartialFunction[Mutation, Unit]
}
