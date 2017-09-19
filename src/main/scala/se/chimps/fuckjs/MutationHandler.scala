package se.chimps.fuckjs

trait MutationHandler {

	def handle:PartialFunction[Mutation, Boolean]

}