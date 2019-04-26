package se.chimps.fuckjs

import org.scalajs.dom.{HashChangeEvent, document, window}
import org.scalajs.dom.raw.Node

import scala.scalajs.js.annotation.JSExportAll

@JSExportAll
object UI {
	var globalMutationHandlers:Seq[PartialFunction[Mutation, Unit]] = Seq()

	def mount(component: Component, selector:String):Unit = {
		component.setup(renderer(selector))
		globalMutationHandlers = globalMutationHandlers ++ Seq(component.handle)
	}

	private[fuckjs] def renderer(selector:String):(Node => Unit) = { node =>
		if (!node.isEqualNode(document.querySelector(selector))) {
			document.querySelector(selector).innerHTML = ""
			document.querySelector(selector).appendChild(node)
		}
	}

	def trigger(mutation:Mutation):Unit = {
		globalMutationHandlers
			.filter(h => h.isDefinedAt(mutation))
			.foreach(h => h(mutation))
	}

	def routing(routingFunc:(HashChangeEvent) => Navigation):Unit = {
		window.addEventListener("hashchange", (e:HashChangeEvent) => {
			val nav = routingFunc(e)

			globalMutationHandlers
				.filter(h => h.isDefinedAt(nav))
				.foreach(h => h(nav))
		})
	}
}

