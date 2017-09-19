package se.chimps.fuckjs

import org.scalajs.dom.document

import scala.scalajs.js.annotation.JSExportAll

@JSExportAll
object UI {
	var componentRegister:Map[String, Component] = Map()
	var globalMutationHandlers:Seq[PartialFunction[Mutation, Unit]] = Seq()

	def mount(component: Component, selector:String):Unit = {
		component.register(selector)
		componentRegister = componentRegister ++ Map(selector -> component)
		render(selector)
	}

	def componentFor(selector:String):Option[Component] = componentRegister.get(selector)

	def render(id:String):Unit = {
		if (componentRegister.contains(id)) {
			val component = componentRegister(id)
			val tag = component.view()

			if (!tag.isEqualNode(document.querySelector(id))) {
				document.querySelector(id).innerHTML = ""
				document.querySelector(id).appendChild(tag)
			}
		}
	}

	def trigger(any:Mutation):Unit = {
		globalMutationHandlers.filter(h => h.isDefinedAt(any)).foreach(h => h(any))
	}

	def on(handler:PartialFunction[Mutation, Unit]):Unit = globalMutationHandlers = globalMutationHandlers ++ Seq(handler)

	def route(selector:String):Router = Router(selector)
}