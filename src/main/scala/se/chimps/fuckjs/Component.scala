package se.chimps.fuckjs

import org.scalajs.dom.raw.{Event, Node}

trait Component {
	private var render:Node => Unit = _
	private var parent:Component = _

	private[fuckjs] def setup(render:Node =>Unit):Unit = {
		this.render = render
		update()
	}

	def subComponentOf(p:Component):Unit = this.parent = p

	def handle:PartialFunction[Mutation, Unit]
	def view():Node

	def update():Unit = {
		if (render != null) {
			render(view())
		} else if (parent != null) {
			parent.update()
		} else {
			println(s"[${this.getClass.getSimpleName}] Nor renderer or parent are set.")
		}
	}

	def trigger[T <: Event](m:Mutation):Function1[T, Unit] = { e:T => {
		handle(m)
	}}

	def trigger[T <: Event](map:T => Mutation):Function1[T, Unit] = { e:T => {
		handle(map(e))
	}}
}
