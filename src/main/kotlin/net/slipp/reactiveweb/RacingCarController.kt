package net.slipp.reactiveweb

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@Controller
class RacingCarController(var racingGame: RacingCar) {

    @GetMapping("/name")
    fun inputNames(): Mono<String> {
        racingGame.init()
        return Mono.just("index")
    }

    @PostMapping("/name")
    fun getNames(@RequestBody names:String, model: Model): Mono<String> {
        racingGame.makeCarList(names)
        model["names"] = racingGame.nameList
        return Mono.just("game")
    }

    @GetMapping("/result")
    fun getTurn(@RequestParam("turn") turn:Int, model:Model): Mono<String> {
        racingGame.initRound(turn)
        racingGame.play()
        model["cars"] = racingGame.carList
        model["winner"] = racingGame.getWinner()
        return Mono.just("result")
    }
}