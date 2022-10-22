package br.com.pucpr.pucprmunicipio.di

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.pucpr.pucprmunicipio.database.AppDatabase
import br.com.pucpr.pucprmunicipio.database.dao.MunicipioDAO
import br.com.pucpr.pucprmunicipio.model.Municipio
import br.com.pucpr.pucprmunicipio.repository.FirebaseAuthRepository




import br.com.pucpr.pucprmunicipio.repository.MunicipioRepository
import br.com.pucpr.pucprmunicipio.ui.fragment.DetalhesMunicipioFragment
import br.com.pucpr.pucprmunicipio.ui.fragment.ListaMunicipiosFragment

import br.com.pucpr.pucprmunicipio.ui.recyclerview.adapter.MunicipioAdapter
import br.com.pucpr.pucprmunicipio.ui.viewmodel.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


private const val NOME_BANCO_DE_DADOS = "pucprmunicipio.db"
private const val NOME_BANCO_DE_DADOS_TESTE = "pucprmunicipio-test.db"

val testeDatabaseModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            NOME_BANCO_DE_DADOS_TESTE
        ).fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(IO).launch {
                        val dao: MunicipioDAO by inject()
                        dao.salva(
                            Municipio(
                                nome = "Macaé",
                                codigo = 100
                            ), Municipio(
                                nome = "São Gonçalo",
                                codigo = 101
                            ),
                            Municipio(
                                nome = "Araruama",
                                codigo = 103
                            ), Municipio(
                                nome = "Cabo Frio",
                                codigo = 104
                            )
                        )
                    }
                }
            }).build()
    }
}

val databaseModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            NOME_BANCO_DE_DADOS
        ).build()
    }
}

val daoModule = module {
    single<MunicipioDAO> { get<AppDatabase>().municipioDao() }

    single<MunicipioRepository> { MunicipioRepository(get()) }


    single<SharedPreferences> { PreferenceManager.getDefaultSharedPreferences(get())}
    single<FirebaseAuthRepository> { FirebaseAuthRepository(get()) }
}

val uiModule = module {
    factory<DetalhesMunicipioFragment> { DetalhesMunicipioFragment() }
    factory<ListaMunicipiosFragment> { ListaMunicipiosFragment() }

    factory<MunicipioAdapter> { MunicipioAdapter(get()) }

}

val viewModelModule = module {
    viewModel<MunicipiosViewModel> { MunicipiosViewModel(get()) }
    viewModel<DetalhesMunicipioViewModel> { (id: Long) -> DetalhesMunicipioViewModel(id, get()) }
    viewModel<LoginViewModel> {LoginViewModel(get())}
    viewModel<EstadoAppViewModel> {EstadoAppViewModel()}
    viewModel<CadastroUsuarioViewModel> {CadastroUsuarioViewModel(get())}
    viewModel<MinhaContaViewModel> {MinhaContaViewModel(get())}
}

val firebaseModule = module{
    single<FirebaseAuth> { Firebase.auth}
}