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

> libraryDependencies += "se.chimps.fuckjs" %%% "fuckjs" % "Beta5"


## Fuck.js terminology

    Mutation = Event from DOM.eventHandler or other component. Think case classes mutating your state.
    Component = Big chunk of html, the state you need and the logic to make it live

## The moving parts

### [UI](se/chimps/fuckjs/UI.scala)

Are used to mount components to their positions, update them if needed. 
And allow compontents to communicate with eachother (via events) in a very loosely coupled manner.
You will most likely only come in contact with this class in your main, or if you communicate between components.

### [Component](se/chimps/fuckjs/Component.scala)

This trait is the base of the framework and hides a chunk of the framework logic. 
The state of the component are what you make it.
Each component you create will have to extend this trait. There are only one method you must implement.
```scala
  def view():RealTag
```
The latter are actually inherited from MutationHandler which can be a base for components that does not require an UI.

#### view():RealTag
The view() method lets you design this components part of the dom. You are now forced to use the inhouse html generating capabilities. Dont worry, it's flexible.

#### update():Unit
Update is a method that you can use everywhere when you want the framework to refresh the DOM.

#### subcomponent(parent:Component):RealTag
This method lets you compose components, and even a long chain of components will update correctly. Keep in mind though that it is the top most parent that is actually rerendering at the end of an update() chain.

### [EventHandler](se/chimps/fuckjs/EventHandler.scala)

To make your component act on stuff you also need to implement the EventHandler trait. This trait also gives you access to the trigger methods, which lets you act on actions in the page itself.

````scala
  def handle:PartialFunction[Mutation, Unit]
````

#### handle:PartialFunction[Mutation, Unit]
The handle partial function, will be called with all mutations you trigger. When you handle said mutations, you decide if the DOM need an update or not.

#### trigger(mutation:Mutation):Function[Event, Unit]
There 2 versions of this function, the are both helper functions that makes it easier to trigger eventhandling in your DOM and back to your component with the Mutations you've defined.
The second version of this function, lets you create a mutation from the raw event, so you can dig out data from it if needed.

### [Mutation](se/chimps/fuckjs/Mutation.scala)

Pretty much anything that changes the state of the component will inherit from this trait. Think case classes extending this trait, no biggie.

## Generating html

The `Component` trait extends [Tags](se/chimps/fuckjs/html/Tags.scala) and [Attributes](se/chimps/fuckjs/html/Attributes.scala) traits, that give you instant access to the most common tags and attributes. But if you miss a tag or an attribute, it's no biggie to add it.

### Tags

At the base of generating html tags, are the Tag trait. There are 2 implementations of that trait, RealTag and TextTag. The code will look something like this:

```scala
  div()(
    ul()(
      li()(
        a(href("#/red"))(text("red"))
      ),li()(
        a(href("#/blue"))(text("blue"))
      )
    ),subcomponent(nav)
  )
```

#### RealTag

RealTag is a representation of a tag, like a div, p etc. It can have attributes, and it can have children. There are shorthand methods for a couple of the most common tags in the Tags trait. But there is also a method to generate any tags missing through the `tag(name:String, attributes:Seq[Attribute], children:Seq[Tag]):RealTag` method.

#### TextTag

TextTag is a placeholder that holds text. If the framework discovers a TextTag, it will put the text in the parents textContent during render.

### Attributes

There are 2 types of attributes, EventAttributes to listen to events. And TextAttributes for everything else.

#### TextAttribute

A textAttribute would be id="asdf" or value="asdf", there are shortcuts for a couple of these in the Attributes trait. But there's also a method to generate anything you might need in there through `attribute(key:String, value:String):Attribute`

## Example

[Todo example](https://github.com/Meduzz/fuck.js-todo-example)

## TODO

- [ ] Add rant about modern frontend development.
- [x] Add examples. See [Todo example](https://github.com/Meduzz/fuck.js-todo-example)
- [ ] Add tests. I was bummed when I could not use my standard scala goto test-tool tbh!
- [ ] The current way of remounting tags, prolly leaks memory in terms of eventHandlers that never unsubscribed, this should be investigated.
- [x] Find a better way to rip data from input fields, into events THEY trigger (ie bindings). To be used with Component.trigger methods.

## Current drawbacks.

1. No tests! Oo until I find time to invest in a test-tool for this...
2. You can only make components.