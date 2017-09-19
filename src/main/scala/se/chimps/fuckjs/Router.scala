package se.chimps.fuckjs

import org.scalajs.dom.{HashChangeEvent, window}

import scala.util.matching.Regex

object Router {
	def apply(selector:String):Router = new Router(selector)
}

class Router(selector:String) {
	var routingHandlerRegister:Seq[Route] = Seq()

	/**
		* Chainable for the lulz.
		*
		* Note that any actions returned will be ran on this selector.
		* If you return a mount action, that tag will be mounted on this selector.
		* If you return a trigger action, the currently mounted component will be called.
		* ... and if there's not component mounted, the event will be dropped.
		*
		* @param url your expected url like /about in somepage.com/#/about
		* @param func your handler, accepting the old url, and any path params.
		* @return returns an action, which might in turn trigger a mutation or a component mount, or both.
		*/
	def on(url:String)(func:((String, Map[String, String]) => Action)):Router = {

		val (regex, names) = regexify(url)
		val route = Route(regex, func, names, selector)
		routingHandlerRegister = routingHandlerRegister ++ Seq(route)

		this
	}


	// Stolen and modified from CAMELTOW...
	private def regexify(url:String):(Regex, Seq[String]) = {
		if (url.contains(":")) {
			var pathParamNames = Seq[String]()
			val pathParamNameRegex = ":([a-zA-Z0-9]*|[a-zA-Z0-9]*(.*))".r

			val matches = url.substring(1).split("/").map {
				case pathParamNameRegex(paramName, regex) => {
					val providedRegexPath = paramName.indexOf("(")
					val pathParamName = if (providedRegexPath != -1) {
						paramName.substring(0, providedRegexPath)
					} else {
						paramName
					}

					pathParamNames = pathParamNames ++ Seq(pathParamName)
					if (regex != null) {
						regex
					} else {
						"([a-zA-Z0-9]+)"
					}
				}
				case pathParamNameRegex(name) => {
					"([a-zA-Z0-9]+)"
				}
				case u => u
			}

			(s"/${matches.mkString("/")}".r, pathParamNames)
		} else {
			(url.r, Seq())
		}
	}

	window.addEventListener("hashchange", (e:HashChangeEvent) => {
		if (routingHandlerRegister.nonEmpty) {
			val url = e.newURL.split("#").reverse.head
			val old = e.oldURL.split("#").reverse.head

			routingHandlerRegister.find(r => {
				val m = r.regex.pattern.matcher(url)
				m.matches()
			}) match {
				case Some(route) => {
					val regexMatch = route.regex.pattern.matcher(url)
					regexMatch.matches()
					val data:Map[String, String] = if (regexMatch.groupCount() > 0) {
						val groups = 1.to(regexMatch.groupCount()).map(regexMatch.group)
						route.names.zip(groups).toMap[String, String]
					} else {
						Map()
					}

					route.handler(old, data) match {
						case Mount(c) => UI.mount(c, route.selector)
						case Trigger(m) => UI.componentFor(route.selector) match {
							case Some(c) => {
								c.handle(m)
								UI.render(route.selector)
							}
							case None =>
						}
						case MountAndTrigger(c, m) => {
							c.handle(m)
							UI.mount(c, route.selector)
						}
						case _ =>
					}
				}
				case None =>
			}
		}
	})
}

case class Route(regex:Regex, handler:((String, Map[String, String]) => Action), names:Seq[String], selector:String)