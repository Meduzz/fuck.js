package se.chimps.fuckjs

import org.scalajs.dom.raw.{Event, Node}

trait Component {
	private var render:(Node)=>Unit = null

	private[fuckjs] def setup(render:(Node)=>Unit):Unit = {
		this.render = render
		update()
	}

	def handle:PartialFunction[Mutation, Unit]
	def view():Node

	def update():Unit = render(view())

	def trigger[T <: Event](m:Mutation):Function1[T, Unit] = {e:T => {
		handle(m)
	}}

	def trigger[T <: Event](map:(T) => Mutation):Function1[T, Unit] = {e:T => {
		handle(map(e))
	}}
}
