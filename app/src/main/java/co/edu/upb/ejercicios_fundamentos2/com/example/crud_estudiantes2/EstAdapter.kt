package co.edu.upb.ejercicios_fundamentos2.com.example.crud_estudiantes2

import android.view.LayoutInflater
import android.view.ScrollCaptureCallback
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

// Viewholder se debe crear internamente con un Inner Class

// El Adaptador de Recicler View necesita estos metodos para funcionar
// Control + i

class EstAdapter (val estList: List<EstModel>): RecyclerView.Adapter<EstAdapter.EstViewHolder>() {

    private var onClickItem: ((EstModel)->Unit)? = null  // Es de tipo de dato / Unit es un void - el simbolo "->" cuando recibas este dato has esto

    // Variable que esta inicializando un metodo, voy a recibir un Modelo y despues voy hacer algo, no sabemos que dato se está trabajando


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = EstViewHolder(

        LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)  // false varias vistas en un adaptador
    )


    override fun getItemCount() = estList.size


    // Que voy hacer despues

    // Va hacer el retorno
    fun setOnClickItem(callback: (EstModel)->Unit){
        this.onClickItem = callback
    }


    override fun onBindViewHolder(holder: EstViewHolder, position: Int) {

        val itemList = estList[position]

        holder.bindView(itemList)

        holder.itemView.setOnClickListener{onClickItem?.invoke(itemList)}
    }



    // Vinculando la configuracion de la xml

    inner class EstViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var id = view.findViewById<TextView>(R.id.tvIdEst)
        private var nombre = view.findViewById<TextView>(R.id.tvNombreEst)
        private var correo = view.findViewById<TextView>(R.id.tvcorreoest)
        private var curso = view.findViewById<TextView>(R.id.tvCursoEst)
        var btnEliminar = view.findViewById<Button>(R.id.btnEliminar)

        // Asignando valores

        fun bindView(est: EstModel) {

            id.text = est.id.toString()
            nombre.text = est.nombre
            correo.text = est.correeo
            curso.text = est.curso

        }


    }

}