package net.slipp.reactiveweb

import com.sun.security.ntlm.Server
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.reactive.function.BodyExtractor
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Mono
import reactor.core.publisher.Signal.subscribe
import reactor.core.publisher.toMono


@Component
class RacingCarHandler(var racingCar: RacingCar) {

    var model = mapOf<String, List<String>>()

    fun index(req:ServerRequest) = ok().render("index")

    fun game(req: ServerRequest ) = req.body(BodyExtractors.toMono(String::class.java))
            .flatMap {
                var nameStr = it.split("=","+")
                print(nameStr)
                racingCar.init()
                racingCar.makeCarList(nameStr.subList(1,nameStr.size))
                model = mapOf("names" to nameStr.subList(1,nameStr.size))
                it.toMono()
            }.flatMap {
                ServerResponse.ok().render("game",model)
            }

//        val data:Mono<String> = req.body(BodyExtractors.toMono(String::class.java))
//        //data.subscribe(System.out::println)
//        var model = mapOf<String, List<String>>()
//        var names = data.flatMap {
//                    var nameStr = it.split("=","+")
//                    racingCar.makeCarList(nameStr.subList(1,nameStr.size))
//                    println(nameStr)
//                    it.toMono()//mapOf("names" to nameStr.subList(1,nameStr.size))
//                }
//
//        var namelist = names.flatMap{
//            var nameStr = it.split("=","+")
//            model = mapOf("names" to nameStr.subList(1,nameStr.size))
//            println(model)
//            model.toMono()
//
//        }.subscribe(System.out::println)


        //return ServerResponse.ok().render("game",model)
                //}
    //}

//    fun game(request: ServerRequest): Mono<ServerResponse> {
//        //var nameList = names.split("=","+")
//        //racingCar.makeCarList(nameList.subList(1,nameList.size))
//        //model.addAttribute("names", nameList.subList(1,nameList.size))
//        //return Mono.just("game")
//        //print(req.pathVariable("names").toString())
//        //val nameList = request.body
//        println(request.body())
//
//        return ServerResponse.ok().render("game")
//    }

    fun result(req: ServerRequest): Mono<ServerResponse> {
    //fun result(req: ServerRequest) =
            req.queryParam("turn").map {
                var value = it.toInt()
                racingCar.initRound(value)
                racingCar.play()
                it.toInt()
            }

        //return Mono.just("result")
        var model = mapOf("cars" to racingCar.carList, "winner" to racingCar.getWinner())

        return ServerResponse.ok().render("result", model)
    }


}