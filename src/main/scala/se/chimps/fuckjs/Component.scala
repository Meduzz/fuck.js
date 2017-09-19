package se.chimps.fuckjs

import org.scalajs.dom.raw.{Event, Node}

trait Component extends MutationHandler {
	private var tagSelector:String = _

	def register(id:String):Unit = this.tagSelector = id

	// call UI and ask it to repaint ourselfes.
	def update():Unit = UI.render(tagSelector)
	def view():Node
	def trigger[T <: Event](m:Mutation):Function1[T, Unit] = {(e) => {
		if (handle(m)) {
			update()
		}
	}}
	def trigger[T <: Event](map:(T) => Mutation):Function1[T, Unit] = {(e) => {
		if (handle(map(e))) {
			update()
		}
	}}
}
