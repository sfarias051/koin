package org.koin.spark

import org.koin.Koin
import org.koin.KoinContext
import org.koin.core.bean.Definition
import org.koin.dsl.context.Context
import org.koin.dsl.context.ParameterHolder

/**
 * Declare a Spark controller
 */
inline fun <reified T : Any> Context.controller(name: String = "", noinline definition: Definition<T>) {
    val def = bean(name, definition)
    def.bind(SparkController::class)
}

/**
 * Tag interface for Spark controllers
 */
internal interface SparkController


/**
 * Run all Spark controllers (function)
 */
fun runControllers() {
    (org.koin.standalone.StandAloneContext.koinContext as KoinContext).runSparkControllers()
}

/**
 * Run all Spark controllers
 */
fun KoinContext.runSparkControllers() {
    Koin.logger.log("** Run Spark Controllers **")
    beanRegistry.definitions
            .filter { it.types.contains(SparkController::class) }
            .forEach { def ->
                Koin.logger.log("Creating $def ...")
                instanceFactory.retrieveInstance<Any>(def, ParameterHolder())
            }
}