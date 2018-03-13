package net.slipp.reactiveweb

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.*

@Configuration
class RoutingConfiguration {
    @Bean
    fun routeFunction(handler: HelloWorldHandler) : RouterFunction<ServerResponse> = router {
        ("/").nest {
            GET("/api/helloworld2") {
                req -> ServerResponse.ok().body(
                    handler.helloworld()
            )
            }
        }
    }

    @Bean
    fun routeHTMLFunction(handler: RacingCarHandler)  = router {
        //accept(MediaType.TEXT_HTML).nest {
        "/".nest {
            GET("/route/name", handler::index)

            POST("/route/name", handler::game)

            GET("/route/result", handler::result)

        }
    }

}