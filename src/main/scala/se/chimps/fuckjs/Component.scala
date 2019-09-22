package se.chimps.fuckjs

import org.scalajs.dom.raw.Node
import se.chimps.fuckjs.html.{Attributes, RealTag, Tags}

trait Component extends Tags with Attributes {
	private var render:Node => Unit = _
	private var parent:Component = _

	private[fuckjs] def setup(render:Node =>Unit):Unit = {
		this.render = render
		update()
	}

	private[fuckjs] def parent(parent:Component):Unit = {
		this.parent = parent
	}

	def view():RealTag

	def subcomponent(component:Component):RealTag = {
		component.parent(this)
		component.view()
	}

	def update():Unit = {
		if (parent != null) {
			parent.update()
		} else if (render != null) {
			render(view().render())
		} else {
			println(s"[${this.getClass.getSimpleName}] No renderer are set.")
		}
	}
}
