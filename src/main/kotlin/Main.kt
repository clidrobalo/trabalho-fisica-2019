
import kotlin.math.*

//-------------------------------------CONSTANTES----------
val larguraGrafico : Int = 75
val alturaGrafico : Int = 30
val grafico = Chart(larguraGrafico, alturaGrafico)

public fun main(args: Array<String>) {

    //-------------------------------------CONSTANTES----------
    val g = 9.807
    val alcanceDefault : Double = 130.0


    var opcao =  menuPrincipal()
    when(opcao) {
        "1" -> simulacao(g, alcanceDefault)
        "3" -> configuracao()
        "0" -> terminar()
        else -> {
            println("|-----------------------------------------------------------|")
            println("|- Opção invalida. Tente novamente.")
            println("|-----------------------------------------------------------|")
            menuPrincipal()
        }
    }
}

fun menuPrincipal()  : String {
    println()
    println("|***********************************************************|")
    println("|********    Simulação lançamento projétil     *************|")
    println("|************************************************************")
    println("|-----------------------------------------------------------|")
    println("|[1] Iniciar simulação")
    println("|[3] Configuração")
    println("|[0] Encerrar")
    println("|-----------------------------------------------------------|")
    print("|Escolha uma opcao: ")
    var opcao = readLine()!!.toString()
    return opcao
}

fun simulacao(g : Double, alcanceDefault: Double) {
    var opcao : String

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
    menuPrincipal()
}

fun configuracao() {

}

fun terminar() {
    println("|------------------------------------------------------------")
    println("|- Simulação terminada.")
    println("|------------------------------------------------------------")
    System.exit(0)
}
/*
    CALCULAR E OBTER TEMPO DE VOO
 */
fun obterTempoVoo(v0y : Double, y0 : Double, g : Double) : Double{
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

    var v0 = (1/cos(ang))*sqrt((0.5 * g * (alcanceDefault*alcanceDefault)) / (alcanceDefault*tan(ang) + h))

    return v0
}
