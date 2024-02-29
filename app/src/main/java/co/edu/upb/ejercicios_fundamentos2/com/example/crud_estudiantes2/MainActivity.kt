package co.edu.upb.ejercicios_fundamentos2.com.example.crud_estudiantes2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.upb.ejercicios_fundamentos2.com.example.crud_estudiantes2.databinding.ActivityMainBinding
import io.github.muddz.styleabletoast.StyleableToast

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var SQLiteHelper : SqlHelper

    private var adapter: EstAdapter? = null

    private lateinit var estModel: EstModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        SQLiteHelper = SqlHelper(this)

        getStudents()

// Botones VISTA ( GUARDAR, LISTAR, ACTUALIZAR)

        with(binding){

            btnEnviar.setOnClickListener {
                val nombreEst = etNombreEst.text.toString()
                val correoEst = etCorreoEst.text.toString()
                val cursoEst = etCursoEst.text.toString()
                addStudent(nombreEst,correoEst,cursoEst)
            }

            btnListar.setOnClickListener { getStudents() }

            btnActualizar.setOnClickListener{
                val nombreEst = etNombreEst.text.toString()
                val correoEst = etCorreoEst.text.toString()
                val cursoEst = etCursoEst.text.toString()
                updateStudent(nombreEst,correoEst,cursoEst)

            }

        }

        adapter?.setOnClickItem {
            Toast.makeText(this,it.nombre,Toast.LENGTH_LONG).show()

            // Botones VISTA ( GUARDAR, LISTAR, ACTUALIZAR)
            with(binding){

                etNombreEst.setText(it.nombre)
                etCorreoEst.setText(it.correeo)
                etCursoEst.setText(it.curso)
                estModel = it
            }

        }

    }

    private fun updateStudent(nombre: String, correo: String, curso: String){

        if(nombre.equals(estModel.nombre)

            && nombre.equals(estModel.nombre)
            && correo.equals(estModel.correeo)
            && curso.equals(estModel.curso)
            ){
            StyleableToast.makeText(
                this,
                "No se actualiza el estudiante",
                R.style.error_toast
            ).show()
            limpiarCampos()
            return

        }

        if(estModel == null){

            StyleableToast.makeText(
                this,
                "Debe dar click a un estudiante de la lista",
                R.style.error_toast
            ).show()
            return

        }


    }

    private fun getStudents(){

        val estList = SQLiteHelper.getListEst()

        adapter = EstAdapter(estList)

        initRecyclerView()

    }




    private fun addStudent(nombre: String, correo: String, curso: String) {
        if (nombre.isEmpty()|| curso.isEmpty()){
            StyleableToast.makeText(
                this,
                "Debe ingresar el nombre y el  curso del estudiante",
                R.style.error_toast
                ).show()
        }else if ( curso.length < 5 || curso.length > 6){
            StyleableToast.makeText(
                this,
                "El curso debe de tener maximo 6 y minimo 5 caracteres",
                R.style.error_toast
            ).show()
        }else  {
            val est = EstModel(null, nombre,correo, curso)
            val status = SQLiteHelper.insertStudent(est)
            if(status >- 1){
                StyleableToast.makeText(
                    this,
                    "estudiante agregado",
                    R.style.success_toast
                ).show()
                limpiarCampos()
            }else{
                StyleableToast.makeText(
                    this,
                    "error al guardar el estudiante",
                    R.style.error_toast
                ).show()
            }
        }
    }


    private fun initRecyclerView(){
        binding.rvListaestudiantes.adapter = adapter
        binding.rvListaestudiantes.layoutManager = LinearLayoutManager(this)

    }


    private fun limpiarCampos(){
        binding.etCorreoEst.text!!.clear()
        binding.etNombreEst.text!!.clear()
        binding.etCursoEst.text!!.clear()

    }





    }


