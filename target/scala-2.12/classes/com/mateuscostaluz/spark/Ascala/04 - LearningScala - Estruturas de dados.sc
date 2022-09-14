// Estruturas de dados

// Tuplas
// Listas imutaveis
// Tal como campos de banco de dados ou colunas
val captainStuff = ("Picard", "Enterprise-D", "NCC-1701-D")
println(captainStuff)

// Referenciando elementos individuais com indice BASE-UM
println(captainStuff._1)
println(captainStuff._2)
println(captainStuff._3)

val picardsShip = "Picard" -> "Enterprise-D"
println(picardsShip._2)

// Podem conter tipos diferentes
val aBunchOfStuff = ("Kirk", 1964, true)

// Listas (coleção)
// São como Tuplas, mas com mais funcionalidade
// Não podem conter tipos diferentes

val shipList = List("Enterprise", "Defiant", "Voyager", "Deep Space Nine")

// Referenciando elementos individuais com indice BASE-ZERO
println(shipList(0))
// Tuplas começam com 1 enquanto listas começam com 0

// Operador HEAD e TAIL
println(shipList.head)
println(shipList.tail)
// HEAD devolve o primeiro elemento
// TAIL devolve os elementos restantes (como uma sub-lista, ignorando o HEAD)

// Percorredo a lista
for (ship <- shipList) {println(ship)}

// MAP serve para aplicar uma função à cada elemento da coleção
val backwardShips = shipList.map((ship: String) => {ship.reverse})
for (ship <- backwardShips) {println(ship)}

// reduce() para combinar todos os itens em uma coleção usando uma função
val numberList = List(1, 2, 3, 4, 5)
val sum = numberList.reduce((number: Int, sum: Int) => {sum + number})
println(sum)

// Teste para entender funcionamento
val numberPairList = numberList.map((number: Int) => {(if (number % 2 == 0) number else null)})
val numberPairListFilter = numberList.filter((number: Int) => number % 2 == 0)
val numberPairListFilterFinal = numberPairList.filter((number: Any) => number != null)

// filter() remove coisas da lista
val iHateFives = numberList.filter((number: Int) => number != 5)
val iHateThrees = numberList.filter(_ != 3)
//                /\ maneira mais fácil de filtrar

// Concatenação de listas
val moreNumbers = List(6, 7, 8)
val lotsOfNumbers = numberList ++ moreNumbers

val reversed = numberList.reverse
val sorted = reversed.sorted
val lotsOfDuplicates = numberList ++ numberList
val distinctValues = lotsOfDuplicates.distinct
val maxValue = numberList.max
val total = numberList.sum
val minValue = numberList.min
val hasThree = iHateThrees.contains(3)

// Map (chave-valor)
val shipMap = Map("Kirk" -> "Enterprise", "Picard" -> "Enterprise-D", "Sisko" -> "Deep Space Nine", "Janeway" -> "Voyager")
println(shipMap("Janeway"))
println(shipMap.contains("Archer"))
val archersShip = util.Try(shipMap("Archer")) getOrElse "Unknow"
println(archersShip)

// EXERCISE
// Create a list of the numbers 1-20; your job is to print out numbers that are evenly divisible by three. (Scala's
// modula operator, like other languages, is %, which gives you the remainder after division. For example, 9 % 3 = 0
// because 9 is evenly divisible by 3.) Do this first by iterating through all the items in the list and testing each
// one as you go. Then, do it again by using a filter function on the list instead.

val exerciseNumberList = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20)
exerciseNumberList.map((number: Int) => if (number % 3 == 0) println(number))
val numberListThree = exerciseNumberList.filter(_ %3 == 0)
numberListThree.foreach(println)