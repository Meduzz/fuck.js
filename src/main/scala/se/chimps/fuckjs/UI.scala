package se.chimps.fuckjs

import org.scalajs.dom.raw.Node
import org.scalajs.dom.{HashChangeEvent, document, window}

import scala.scalajs.js.annotation.JSExportAll

@JSExportAll
object UI {
	private var globalMutationHandlers:Seq[PartialFunction[Mutation, Unit]] = Seq()

	def register(component:Component, selector:String = null):Unit = {
		if (selector != null) {
			component.setup(renderer(selector))
		}

		component match {
			case eventHandler:EventHandler =>
				globalMutationHandlers = globalMutationHandlers ++ Seq(eventHandler.handle)
			case _ =>
		}
	}

	private[fuckjs] def renderer(selector:String):Node => Unit = { node =>
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

	def routing(routingFunc:HashChangeEvent => Mutation):Unit = {
		window.addEventListener("hashchange", (e:HashChangeEvent) => {
			val nav = routingFunc(e)

			globalMutationHandlers
				.filter(h => h.isDefinedAt(nav))
				.foreach(h => h(nav))
		})
	}
}

