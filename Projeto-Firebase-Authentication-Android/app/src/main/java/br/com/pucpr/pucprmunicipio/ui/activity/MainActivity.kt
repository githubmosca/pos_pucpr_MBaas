package br.com.pucpr.pucprmunicipio.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import br.com.pucpr.pucprmunicipio.R
import br.com.pucpr.pucprmunicipio.ui.viewmodel.EstadoAppViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.main_activity.*
import org.koin.android.viewmodel.ext.android.viewModel
private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private val controlador by lazy {
        findNavController(R.id.main_activity_nav_host)
    }
    private val viewModel: EstadoAppViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        controlador.addOnDestinationChangedListener { _,
                                                      destination,
                                                      _ ->
            title = destination.label
            viewModel.componentes.observe(this, Observer {
                it?.let { temComponentes ->
                    if(temComponentes.appBar){
                        supportActionBar?.show()
                    } else {
                        supportActionBar?.hide()
                    }
                    if(temComponentes.bottomNavigation) {
                        main_activity_bottom_navigation.visibility = VISIBLE
                    } else {
                        main_activity_bottom_navigation.visibility = GONE
                    }
                }
            })
        }
        main_activity_bottom_navigation
            .setupWithNavController(controlador)
    }


}
