import java.math.RoundingMode
import kotlin.math.*

fun main(args: Array<String>) {

    //-------------------------------------CONSTANTES----------
    val larguraGrafico : Int = 75
    val alturaGrafico : Int = 30
    val alcanceDefault : Double = 130.0
    val g = 9.807
    val grafico = Chart(larguraGrafico, alturaGrafico)


    var opcao : String

    println()
    println("|************************************************************")
    println("*************               Exemplo            **************")
    println("|************************************************************")
    println()

    do {
        var x :Double = 0.0

        println("\n|************************************************************")
        print("|- Introduza a altura (m): ")
        var y0 = readLine()!!.toDouble()

        println("|------------------------------------------------------------")
        print("|- Introduza o ângulo de inclinação(0-90 graus): ")
        var angUser = readLine()!!.toDouble()
        var ang = Math.toRadians(angUser) // converte o que o usuário digitou pra ser calculado aqui dentro em radianos.

        println("|------------------------------------------------------------")
        print("|- Introduza a velocidade inicial (m/s): ")
        var v0 = readLine()!!.toDouble()

        var y = y0
        var v0x = v0 * cos(ang)
        var v0y = v0 * sin(ang)

        var tVoo = obterTempoVoo(v0y, y0, g)

        println("|------------------------------------------------------------")
        println( "|- TEMPO DE VOO -> " +  tVoo + " segundos")

        // -- ALCANCE
        var alcance = v0x * tVoo

        while(y >=0){

//          CALCULAR E OBTER POSICAO NA VERTICAL
//          Equação da trajetória em y indicada pela prof. não contém o tempo
            y = y0 + (tan(ang) * x) - ((g*(x*x)/(2*(v0*v0) * (cos(ang) * cos(ang)))))

            //Settar coordenadas
            grafico.ponto(x,y)
            //Deslocar projetil um passo na horizontal
            x++

            if (x >= alcance){ // Se o x a ser iterado iguala ou passa do real alcance (v0x*t) então sai do laço para parar de desenhar!
                break
            }
        }

        //Desenhar Grafico
        grafico.draw()

        analisarDistanciaHorizontal(alcance, alcanceDefault, ang, y0, g)

        println("|------------------------------------------------------------")
        print("|- Pretende desenhar outro grafico (S/N)?: ")
        opcao = readLine()!!.toString()

    }while(opcao.toLowerCase() == "s");

    println("|------------------------------------------------------------")
    println("|- Simulação terminada.")
    println("|------------------------------------------------------------")

}

/*
    CALCULAR E OBTER TEMPO DE VOO
 */
fun obterTempoVoo(v0y : Double, y0 : Double, g : Double) : Double{
    //return (v0y + sqrt(v0y * v0y + 2 * g * y0)) / g
    return (v0y / g) + sqrt((v0y/g)*(v0y/g) + ((2*y0)/g))
}

fun analisarDistanciaHorizontal(alcance : Double, alcanceDefault : Double, ang: Double, h: Double, g : Double) {

    println("\n|- O salto atingiu " + alcance + " metros de alcance")

    if(round(alcance) > alcanceDefault) {
        println("|------------------------------------------------------------")
        println("|- O salto ultrapassou " + alcanceDefault + " metros.")
        println("|------------------------------------------------------------")
        println("|-| Velocidade recomendada: " + obterVelocidadeRecomendada(ang,h, alcanceDefault, g) + " m/s.")
    } else if(round(alcance) < alcanceDefault){
        println("|------------------------------------------------------------")
        println("|- O salto não atingiu " + alcanceDefault + " metros.")
        println("|------------------------------------------------------------")
        println("|-| Velocidade recomendada: " + obterVelocidadeRecomendada(ang,h,alcanceDefault, g) + " m/s.")
    } else {
        println("|------------------------------------------------------------")
        println("|- Parabéns! O salto atingiu " + alcanceDefault + " metros.")
    }
}


/*
    CALCULAR E OBTER VELOCIDADE RECOMENDADA PARA ALCANÇAR O OBJETIVO (alcalceDefault)
 */
fun obterVelocidadeRecomendada(ang : Double, h: Double, alcanceDefault:Double, g : Double): Double {
    //var ang = Math.toRadians(35.0)
    println("--> " + cos(ang))
    //Vo = (0.5*Ay*(X/cosθ)²)
    // / (10+(sinθ)*(X/cosθ))
    var numerador = 0.5 * 9.8 * Math.pow(750/cos(ang), 2.0)
    var denominador = 10 + sin(ang) * (750/ cos(ang))
    //var v0 =  numerador / denominador

    //var v0 = sqrt((9.8 * 750) / sin(2*ang)) // Essa formula funciona
    var v0 = sqrt((g * alcanceDefault) / sin(2*ang))

//    var dividendo = alcanceDefault * g
//    var divisor = sin(ang)
//    //garantir que o divisor sempre seja positiv0 antes de realizar raiz quadrada
//    if(divisor < 0){
//        divisor *= -1
//    }
//    //var velocidade = sqrt(dividendo/divisor)//.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
//    var velocidade = (1/cos(ang)) * sqrt((0.5*g*alcanceDefault*alcanceDefault)/alcanceDefault * tan(ang))
    return v0
}

//fun obterVelocidadeRecomendada(ang : Double, h: Double): Double { // todo ainda não funciona
//
//    var velocidade = 130/(cos(ang)*calculaT(h, ang))
//
//    println(velocidade)
//    return velocidade
//}

fun calculaT (h: Double, ang: Double, g : Double) : Double{
    // (((g * t²)/2 - h) / sen &) * cos & * t =  130

    val a = (2-h)*g
    val b = cos(ang)
    val c = -130*sin(ang)
    var t1: Double
    var t2: Double

    // Báskra:

    var delta = (b*b)+((-4)*(a)*(c));

    t1 = (-b + sqrt(delta)) / (2*a)
    t2 = (-b - sqrt(delta)) / (2*a)

    println("T2: "+t1)
    println("T1: "+t2)

    var t : Double

    if (t1==t2){
        t = t1;
    } else {
        if (t1>t2){
            t = t1
        } else {
            t=t2
        }
    }
    return t;

}