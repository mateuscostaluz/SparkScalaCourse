// Flow control

// If / else:
if (1 > 3) println("Impossible!") else println("The world makes sense.")

if (1 > 3) {
  println("Impossible!")
  println("Really?")
} else {
  println("The world makes sense.")
  println("still.")
}

// Matching
val number = 2
number match {
  case 1 => println("One")
  case 2 => println("Two")
  case 3 => println("Three")
  case _ => println("Something else")
}

// Loop
for (x <- 1 to 4) {
  val squared = x * x
  println(squared)
}

// VARIAVEL MUTAVEL NAO BOA PRATICA SCALA \/
var x = 10
while (x >= 0) {
  println(x)
  x -= 1
}

x = 0
do { println(x); x+=1 } while (x <= 10)

// Expressions

{val x = 10; x + 20}

println({val x = 10; x + 20})

// EXERCISE
// Write some code that prints out the first 10 values of the Fibonacci sequence.
// This is the sequence where every number is the sum of the two numbers before it.
// So, the result should be 0, 1, 1, 2, 3, 5, 8, 13, 21, 34

var actualValue = 0
var beforeValue = 0
var summedValue = 0
while (actualValue <= 89) {
  if (actualValue == 0) {
    println(actualValue)
    beforeValue = actualValue
    actualValue += 1
  } else {
    println(actualValue)
    summedValue = beforeValue + actualValue
    beforeValue = actualValue
    actualValue = summedValue
  }
}

def fn( n : Int ) : Int = {
  if (n < 0) {
    -999
  } else if (n == 0) {
    0
  } else if (n == 1 || n == 2) {
    1
  } else {
    fn(n - 1) + fn(n - 2)
  }
}

for (x <- 0 to 9) {
  println(fn(x))
}