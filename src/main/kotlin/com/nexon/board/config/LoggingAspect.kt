package com.nexon.board.config

import lombok.extern.slf4j.Slf4j
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class LoggingAspect {

    private val logger = LoggerFactory.getLogger(LoggingAspect::class.java)

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    fun logAround(joinPoint: ProceedingJoinPoint): Any? {
        val startTime = System.currentTimeMillis()

        logger.info("Request to: ${joinPoint.signature}")
        logger.info("Arguments: ${joinPoint.args.joinToString()}")

        val result = joinPoint.proceed()

        logger.info("Response: $result")
        logger.info("Execution time: ${System.currentTimeMillis() - startTime} ms")

        return result
    }
}
