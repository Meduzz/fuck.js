package se.chimps.fuckjs

import org.scalajs.dom.raw.Node
import se.chimps.js.vdom.{Tag, VDiff, VDom}

trait VDomSupport extends VDom with VDiff { parent:Component =>
	private var shadow:Tag = null
	private var root:Node = null

	override def view():Node = {
		val tag = virtual()

		if (shadow != null) {
			val patches = diff(shadow, tag)

			applyPatches(root, patches)
		} else {
			root = renderTag(tag)
		}

		root
	}

	override def update():Unit = {
		if (root != null) {
			view()
		} else {
			parent.update()
		}
	}

	def virtual():Tag
}
