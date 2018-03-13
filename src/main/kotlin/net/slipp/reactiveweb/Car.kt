package net.slipp.reactiveweb

data class Car(val name:String, var status:String="") {
    fun moveCar() {
        val random = (Math.random() * 9).toInt() + 1
        //status = if(random > 4) status + random else status
        if (random > 4) {
            status = status + "&nbsp;".repeat(5)
        }
    }
}

