package com.example.traductor_espanol_turco.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.traductor_espanol_turco.model.Perfil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// DataStore guarda el perfil y los favoritos en el teléfono.
// Los datos se conservan al cerrar y abrir la app.
private val Context.dataStore by preferencesDataStore(name = "traductor_datastore")

class AlmacenLocal(private val context: Context) {

    private val claveNombre = stringPreferencesKey("perfil_nombre")
    private val claveCorreo = stringPreferencesKey("perfil_correo")
    private val claveEdad = stringPreferencesKey("perfil_edad")
    private val claveFavoritos = stringSetPreferencesKey("favoritos_ids")

    // Perfil guardado (si no hay nada, devuelve un perfil vacío)
    val perfil: Flow<Perfil> = context.dataStore.data.map { datos ->
        Perfil(
            nombre = datos[claveNombre] ?: "",
            correo = datos[claveCorreo] ?: "",
            edad = datos[claveEdad] ?: ""
        )
    }

    suspend fun guardarPerfil(perfil: Perfil) {
        context.dataStore.edit { datos ->
            datos[claveNombre] = perfil.nombre
            datos[claveCorreo] = perfil.correo
            datos[claveEdad] = perfil.edad
        }
    }

    // IDs de las palabras favoritas
    val favoritos: Flow<Set<Int>> = context.dataStore.data.map { datos ->
        (datos[claveFavoritos] ?: emptySet()).mapNotNull { it.toIntOrNull() }.toSet()
    }

    suspend fun guardarFavoritos(ids: Set<Int>) {
        context.dataStore.edit { datos ->
            datos[claveFavoritos] = ids.map { it.toString() }.toSet()
        }
    }
}
