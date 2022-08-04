// Funcoes

// formato def <nome da funcao>(nome do parametro: tipo...) : tipo do retorno = { }

def squareIt(x: Int) : Int = {
  x * x
}

def cubeIt(x : Int) : Int = {x * x * x}
def cubeIt2(x : Int) : Int = {squareIt(x) * x}

println(squareIt(2), cubeIt(3), cubeIt2(3))

def nomeCompleto(nome: String, f: String => String): String = {f(nome)}
def sobrenome(nome: String): String = {nome + " Luz"}
val nome = "Mateus"
var resultado = nomeCompleto(nome, sobrenome)
println(resultado) // Mateus Luz
resultado = nomeCompleto("Mateus", x => x + " Costa Luz")
println(resultado) // Mateus Costa Luz

def transformInt(x : Int, f: Int => Int): Int = {
  f(x)
}

val result = transformInt(2, cubeIt)
println(result)

transformInt(3, x => x * x * x)

transformInt(10, x => x / 2)

transformInt(2, x => {val y = x * 2; y * y})

// EXERCISE
// Strings have a built-in .toUpperCase method. For example, "foo".toUpperCase gives you back FOO.
// Write a function that converts a string to upper-case, and use that function of a few test strings.
// Then, do a same thing using a function literal instead of a separate, name function.

def toUpper(x: String): String = {x.toUpperCase}
println(toUpper("foo"))
val function = (x: String) => x.toUpperCase
println(function("bar"))
var x = "foo"
x = x.toUpperCase()
println(x)

def literalToUpper(s: String, f: String => String): String
val y = literalToUpper("foo bar", x => x.toUpperCase())
println(y)