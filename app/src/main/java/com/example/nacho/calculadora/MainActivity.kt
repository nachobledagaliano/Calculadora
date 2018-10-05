package com.example.nacho.calculadora

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var resultado=0.toDouble()
    var resultadoR: String = "0"
    var operacion = ""
    var memoria=0f
    var sistema: String = ""
    var resume = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sistema = "decimal"
    }

    fun numero(v: View)
    {
        val num = v as Button;
        val textnum = v.text.toString()
        pantalla.text= pantalla.text.toString() + textnum
    }

    fun limpiar(v: View)
    {
        pantalla.text = ""
        resultado=0.toDouble()
        resultadoR="0"
    }

    fun borrar(v: View)
    {
        pantalla.text = pantalla.text.substring(0,pantalla.text.length-1)
    }

    fun operacion(v: View)
    {

        try {
            resultado = pantalla.text.toString().toDouble()

        }
        catch (e: NumberFormatException){}

        if(!pantalla.text.toString().equals(""))
        {
            resultadoR = toDecimal(pantalla.text.toString(), sistema)
            println(resultadoR + " " + sistema)
        }

        pantalla.text=""

        operacion = (v as Button).text.toString()
    }

    fun calcular(v: View)
    {
        when(operacion)
        {
            "+" ->  resultado += pantalla.text.toString().toFloat()
            "-" ->  resultado -= pantalla.text.toString().toFloat()
            "/" ->  resultado /= pantalla.text.toString().toFloat()
            "*" ->  resultado *= pantalla.text.toString().toFloat()
            else -> resultado = 0.toDouble()
        }
        pantalla.text = resultado.toString()
    }

    fun calcularR(v: View)
    {
        var res: Int
        try
        {
            res = resultadoR.toInt()
            println(resultadoR + " " + toDecimal(pantalla.text.toString(), sistema).toInt())

            when (operacion) {
                "+" -> res += toDecimal(pantalla.text.toString(), sistema).toInt()
                "-" -> res -= toDecimal(pantalla.text.toString(), sistema).toInt()
                "/" -> res /= toDecimal(pantalla.text.toString(), sistema).toInt()
                "*" -> res *= toDecimal(pantalla.text.toString(), sistema).toInt()
                else -> res = 0
            }
        }
        catch(e: NumberFormatException){return}

        when(sistema)
        {
            "binary" -> pantalla.text = toBinary(res.toString(),"decimal")
            "decimal" -> pantalla.text = toDecimal(res.toString(),"decimal")
            "hexadecimal" -> pantalla.text = toHexadecimal(res.toString(),"decimal")
            else -> println("No hay sistema numerico")
        }
    }

    fun sumarMemoria(v:View)
    {
        memoria += pantalla.text.toString().toFloat()
    }

    fun restarMemoria(v:View)
    {
        memoria -= pantalla.text.toString().toFloat()
    }

    fun mostrarMemoria(v:View)
    {
        pantalla.text = memoria.toString()
    }

    fun limpiarMemoria(v:View)
    {
        memoria = 0f
    }

    fun binario(view: View)
    {
        btn_2.visibility = View.INVISIBLE
        btn_3.visibility = View.INVISIBLE
        row79.visibility = View.INVISIBLE
        row46.visibility = View.INVISIBLE

        rowAC.visibility = View.INVISIBLE
        rowDF.visibility = View.INVISIBLE

        pantalla.text = toBinary(pantalla.text.toString(),sistema)
        sistema = "binary"
    }

    fun decimal(view: View)
    {
        btn_2.visibility = View.VISIBLE
        btn_3.visibility = View.VISIBLE
        row79.visibility = View.VISIBLE
        row46.visibility = View.VISIBLE

        rowAC.visibility = View.INVISIBLE
        rowDF.visibility = View.INVISIBLE



        pantalla.text = toDecimal(pantalla.text.toString(),sistema)
        sistema = "decimal"
    }

    fun hexadecimal(view:View)
    {
        btn_2.visibility = View.VISIBLE
        btn_3.visibility = View.VISIBLE

        rowAC.visibility = View.VISIBLE
        rowDF.visibility = View.VISIBLE
        row79.visibility = View.VISIBLE
        row46.visibility = View.VISIBLE

        pantalla.text = toHexadecimal(pantalla.text.toString(),sistema)
        sistema="hexadecimal"
    }

    fun toBinary(n: String, from: String): String
    {
        if(from.equals("binary"))
            return n
        else if(n=="")
            return n

        var num = if(from.equals("decimal")) n.toInt() else toDecimal(n,from).toInt()  //Si es binario primero lo convierte a decimal

        var resto: Int = num % 2
        var cociente: Int = num / 2
        var res: String = ""


        do {
            res += resto.toString()

            resto = cociente % 2
            cociente /= 2
        }while(cociente!=0)

        res += resto

        return res.reversed()
    }

    fun toDecimal(n: String, from: String): String
    {
        if(from.equals("decimal"))
            return n;
        else if(n=="")
            return n

        var num = n.reversed()
        var res: Double = 0.toDouble()
        var conversor:Int = if (from.equals("binary")) 2 else if(from.equals("hexadecimal")) 16 else 1

        var caract = "ABCDEF"

        for(i in 0..n.length-1)
        {
            var numero = if(caract.contains(num[i].toString())) 10+caract.indexOf(num[i].toString()) else num[i].toString()

            res += numero.toString().toDouble() * (Math.pow(conversor.toDouble(), i.toDouble()))
        }
        return res.toInt().toString()
    }

    fun toHexadecimal(n: String, from: String): String
    {
        if(from.equals("hexadecimal"))
            return n
        else if(n=="")
            return n


        var num = if(from.equals("decimal")) n.toInt() else toDecimal(n,from).toInt()  //Si es binario primero lo convierte a decimal

        var resto: Int = num % 16
        var cociente: Int = num / 16
        var res: String = ""
        var caract = "ABCDEF"


        do {
            if(resto>=10)
                res += caract[resto - 10]
            else
                res += resto.toString()

            resto = cociente % 16
            cociente /= 16
        }while(cociente!=0)

        if(resto>=10)
            res += caract[resto - 10]
        else if(resto>0)
            res += resto

        return res.reversed()
    }
}
