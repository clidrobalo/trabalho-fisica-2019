
import kotlin.math.*


public fun main(args: Array<String>) {

    //-------------------------------------CONSTANTES----------
    val g = 9.807
    val alcanceDefault : Double = 130.0


    var opcao =  menuPrincipal()
    when(opcao) {
        "1" -> simulacao2(g, alcanceDefault)
        "0" -> terminar()
        else -> {
            println("|-----------------------------------------------------------|")
            println("|- Opção invalida. Tente novamente.")
            println("|-----------------------------------------------------------|")
            menuPrincipal()
        }
    }
}

fun simulacao2(g : Double, alcanceDefault: Double) {
    var opcao : String

    do {
        var x :Double = 0.0

        println("\n|************************************************************")
        var y0 = 15.5
        println("|- Altura: " + y0)


        println("|------------------------------------------------------------")
        var v0 = 20.0
        println("|- Velocidade inicial: " + v0)

        println("|------------------------------------------------------------")
        print("|- Introduza o ângulo de inclinação(0-90 graus): ")
        var angUser = readLine()!!.toDouble()
        var ang = Math.toRadians(angUser) // converte o que o usuário digitou pra ser calculado aqui dentro em radianos.


        if( ang < 0) {
            println("|------------------------------------------------------------")
            println("|- O angulo é invalido. Só aceita valores positivos.")
            simulacao2(g, alcanceDefault)
        }


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

        println("|------------------------------------------------------------")
        print("|- Pretende desenhar outro grafico (S/N)?: ")
        opcao = readLine()!!.toString()

    }while(opcao.toLowerCase() == "s");
}


