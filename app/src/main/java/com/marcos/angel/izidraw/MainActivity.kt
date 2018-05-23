package com.marcos.angel.izidraw

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.drawing_view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawing_view)

        val drawing: DrawingView = findViewById(R.id.drawingView)
        val btnVistas: Button = findViewById(R.id.btnVista)
        val btnDelete1: Button = findViewById(R.id.btnDelete1)
        val btnDelete2: Button = findViewById(R.id.btnDelete2)
        val btnDelete3: Button = findViewById(R.id.btnDelete3)
        //val btnIsometrico: Button = findViewById(R.id.btnIsometrico)

        btnVistas.setOnClickListener(View.OnClickListener {
            drawing.generateView()
        })

        btnDelete1.setOnClickListener(View.OnClickListener {
            drawing.remove1()
        })

        btnDelete2.setOnClickListener(View.OnClickListener {
            drawing.remove2()
        })

        btnDelete3.setOnClickListener(View.OnClickListener {
            drawing.remove3()
        })

        //btnIsometrico.setOnClickListener(View.OnClickListener {
        //    drawing.generateIsometric()
        //})
    }
}
