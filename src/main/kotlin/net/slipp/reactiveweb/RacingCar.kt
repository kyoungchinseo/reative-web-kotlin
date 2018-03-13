package net.slipp.reactiveweb

import org.springframework.stereotype.Service
import java.nio.charset.Charset

@Service
class RacingCar {
    var carList = arrayListOf<Car>()
    var round:Int = 0
    var nameList = listOf<String>()

    fun init() {
        carList.clear()
    }

    fun makeCarList(inputString: String) {
        var nameStr = inputString.split("=","+")
        nameList = nameStr.subList(1,nameStr.size)
        makeCarList(nameList)
    }

    fun makeCarList(list: List<String>) {
        for(item in list) {
            carList.add(Car(item,""))
        }
    }

    fun initRound(round: Int) {
        this.round = round
    }

    fun play() {
        for (item in carList) {
            item.status="";
        }

        for( x in 1.. round) {
            for (y in carList.indices) {
                carList[y].moveCar()
            }
        }
    }

    fun getWinner():String {
        var winner = ""
        var max = carList.maxBy { it.status.length ?: 0 }?.status
        for (i in carList.indices) {
            if (carList[i].status == max) {
                winner += carList[i].name + ","
            }
        }
        return winner.substring(0, winner.length - 1)
    }

}