class Machine {
    var water: Int = 400
    var milk : Int = 540
    var beans: Int = 120
    var cups: Int = 9
    var money: Int = 550

    fun check(mutList: List<Int>): Boolean {
        var bool = true
        for (i in 0 until mutList.size) {
            when (i) {
                0 -> {
                    if (water - mutList[i] < 0) {
                        println("Sorry, not enough water!")
                        bool = false
                        break
                    }
                }
                1 -> {
                    if (milk - mutList[i] < 0) {
                        println("Sorry, not enough milk!")
                        bool = false
                        break
                    }
                }
                2 -> {
                    if (beans - mutList[i] < 0) {
                        println("Sorry, not enough coffee beans!")
                        bool = false
                        break
                    }
                }
                3 -> {
                    if (cups - mutList[i] < 0) {
                        println("Sorry, not enough cups!")
                        bool = false
                        break
                    }
                }
                4 -> {
                    if (money - mutList[i] < 0) {
                        println("Sorry, not enough money!")
                        bool = false
                        break
                    }
                }
            }
        }
        return bool
    }
}

enum class State {
    ACTION,
    COFFEE,
    EXIT
}

enum class Transition(val transition: String) {
    BUY("buy"),
    FILL("fill"),
    TAKE("take"),
    REMAINING("remaining"),
    EXIT("exit")
}

val result = Machine()

fun fsm() {
    var state = State.ACTION
    var trans: String
    var coffeeType: String

    while (true) {
        when (state) {
            State.ACTION -> {
                println("Write action (buy, fill, take, remaining, exit):")
                trans = readln()
                when (trans) {
                    "buy" -> {
                        state = State.COFFEE
                    }
                    "fill" -> {
                        fillCoffee()
                        state = State.ACTION
                    }
                    "take" -> {
                        takeMoney()
                        state = State.ACTION
                    }
                    "remaining" -> {
                        remainingCoffee()
                        state = State.ACTION
                    }
                    "exit" -> {
                        state = State.EXIT
                    }
                    else -> {
                        state = State.ACTION
                    }
                }
            }
            State.COFFEE -> {
                println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back")
                coffeeType = readln()
                if (coffeeType == "back") {
                    state = State.ACTION
                } else if (coffeeType == "1" || coffeeType == "2" || coffeeType == "3") {
                    selectCoffee(coffeeType)
                    state = State.ACTION
                } else {
                    state = State.COFFEE
                }
            }
            State.EXIT -> {
                return
            }
        }
    }
}

fun fillCoffee() {
    println("Write how many ml of water do you want to add:")
    result.water += readln().toInt()
    println("Write how many ml of milk do you want to add:")
    result.milk += readln().toInt()
    println("Write how many grams of coffee beans do you want to add:")
    result.beans += readln().toInt()
    println("Write how many disposable cups of coffee do you want to add:")
    result.cups += readln().toInt()
}

fun takeMoney() {
    println("I gave you $${result.money}")
    result.money = 0
}

fun remainingCoffee() {
    println("The coffee machine has:")
    println("${result.water} of water")
    println("${result.milk} of milk")
    println("${result.beans} of coffee beans")
    println("${result.cups} of disposable cups")
    println("$${result.money} of money")
}

fun selectCoffee(coffeeType: String) {
    when (coffeeType) {
        "1" -> {
            val espressoList = mutableListOf(250, 0, 16, 1, 4)
            checkRemaining(espressoList)

        }
        "2" -> {
            val latteList = mutableListOf(350, 75, 20, 1, 7)
            checkRemaining(latteList)
        }
        "3" -> {
            val cappuccinoList = mutableListOf(200, 100, 12, 1, 6)
            checkRemaining(cappuccinoList)
        }
    }
}

fun checkRemaining(coffeeList: List<Int>) {
    if(result.check(coffeeList)) {
        println("I have enough resources, making you a coffee!")
        result.water -= coffeeList[0]
        result.milk -= coffeeList[1]
        result.beans -= coffeeList[2]
        result.cups -= coffeeList[3]
        result.money += coffeeList[4]
    }
}

fun main() {
    fsm()
}