# fuck.js
Because life is so much better with scala.

## Intro

I like riot.js and its update function, but I liked it more before it got painful to use.
I enjoy Vue because it allows you to hack and leaves the decisions to you.
I love elm because of its rfp and immutable.

Here you get bits of all three in one of the most beautiful languages out there. And you dont have to wait for 40 mins for node modules to download and compile.

## Get started

Add these lines to scala.js part of your build.sbt and you should be set:

> resolvers += "se.chimps.fuckjs" at "http://yamr.kodiak.se/maven"

> libraryDependencies += "se.chimps.fuckjs" %%% "fuckjs" % "Beta2"


## Fuck.js terminology

    Mutation = Event from DOM.eventHandler or other component. Think case classes mutating your state.
    Component = Big chunk of html, the state you need and the logic to make it live
    Action = Can be both an event, or an command to mount a component or both. Emitted by your route settings.
    Router = A Router, with a twist.

## The moving parts

### [UI](se/chimps/fuckjs/UI.scala)

Are used to mount components to their positions, update them if needed. 
And allow compontents to communicate with eachother (via events) in a very loosely coupled manner.
You will most likely only come in contact with this class in your main, or if you communicate between components.

### [Component](se/chimps/fuckjs/Component.scala)

This trait is the base of the framework and hides a chunk of the framework logic. 
The state of the component are what you make it.
Each component you create will have to extend this trait. There are 2 methods you must implement.
```scala
  def view():Node
  def handle:PartialFunction[Mutation, Boolean]
```
The latter are actually inherited from MutationHandler which can be a base for components that does not require an UI.

#### view():Node
The view() method lets you design this components part of the dom. You can use anything that can generate scala-js-dom Nodes.

#### handle:PartialFunction[Mutation, Boolean]
The handle partial function, will be called with all mutations you trigger, and the boolean you must return signal to the framework whether you think it should repaint the DOM or not. It will obey you.

#### update():Unit
Update is a method that you can use everywhere when you want the framework to refresh the DOM. The only real place when you _need_ to call this though, are when you communicate with other components, and they've changed your state.

#### trigger(mutation:Mutation):Function[Event, Unit]
There 2 versions of this function, the are both helper functions that makes it easier to trigger eventhandling in your DOM and back to your component with the Mutations you've defined.
The second version of this function, lets you create a mutation from the raw event, so you can dig out data from it if needed.

### [Mutation](se/chimps/fuckjs/Mutation.scala)

Pretty much anything that changes the state of the component will inherit from this trait. Think case classes extending this trait, no biggie.

### [Router](se/chimps/fuckjs/Router.scala)

This router does, about the same thing that all other routers out there do. But in a more rfp way through it's actions. And it does per selector. If this is of any use, is unclear, but I find it useful.
The router will only use the hash-value at the moment. The main method on the router is chainable.
```scala
def on(url:String)(func:((String, Map[String, String]) => Action)):Router
```
While this definition certainly are a mouthful, it's really not that bad once you use it.
First you enter the url you want to react to, incl path-params ```/a/:param``` and even regexes ```/a/:param(.*)``` if the default regex ```([a-zA-Z0-9]+)``` wont do. Then you provide a method that takes the OLD url, and a map that will contain any path-params you defined in your route. Having access to the previous url, allows you to make optimisations. But it could also turn out to be just another place to fuck up. :) You are expected to return an Action with each matched url.

### [Action](se/chimps/fuckjs/Action.scala)

This trait is the base of all actions a router can take when a route matches.
Atm that includes:

#### Mount(c:Component)
Mount a component. Since the router are bound to a selector, we already know where to mount the component.

#### Trigger(m:Mutation)
Trigger a mutation. The framwork has a way to dig out which component are mounted at the routers selector, and triggers the mutation on what ever's mounted.

#### MountAndTrigger(c:Component, m:Mutation)
First mount c, then trigger m on that component.

## Example

[Todo example](https://github.com/Meduzz/fuck.js-todo-example)

## TODO

- [ ] Add rant about modern frontend development.
- [x] Add examples. See [Todo example](https://github.com/Meduzz/fuck.js-todo-example)
- [ ] Add tests. I was bummed when I could not use my standard scala goto test-tool tbh!
- [ ] Find/Build a hyperscript &/| pug template thingie to use with views, scalatags are way to tedious to use.
- [ ] The current way of remounting tags, prolly leaks memory in terms of eventHandlers that never unsubscribed, this should be investigated.
- [ ] Find a better way to rip data from inputfields, into events THEY trigger. To be used with Component.trigger methods.
- [ ] Get some kind of vdom solution, so we dont have to repaint all of the components on update().

## Current drawbacks.

1. Templates ie. view:Node in components are tedious to setup and design.
2. Callbacks from DOM into component does not have a good way to dig out the relevant data, instead you kind of have to listen onkeyup and wait for a keyCode == 13 and then querySelector the value.
3. No tests! Oo until I find time to invest in a test-tool for this...
4. Repainting a component, means repainting ALL of the components html. Should get better with a vdom.