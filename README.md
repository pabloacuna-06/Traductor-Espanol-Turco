# ¡Hola Merhaba!

## Descripción del proyecto

¡Hola Merhaba! es una aplicación Android desarrollada en Kotlin para niños de alrededor de 10 años. Permite aprender vocabulario básico en español y turco mediante categorías, traducciones y frases cortas de ejemplo.

Al seleccionar una palabra, se muestra su traducción y una frase en ambos idiomas. También permite grabar y reproducir la voz del usuario para practicar.

El vocabulario está incluido en la aplicación y funciona sin internet, sin utilizar servicios externos de traducción.

Proyecto desarrollado para la asignatura Desarrollo de Aplicaciones Móviles de Duoc UC.

## Integrantes

- Pablo Acuña Escobar
- Eloy Contreras
- Cristóbal Cargnino

## Funcionalidades implementadas

- Navegación entre categorías, palabras, detalle, favoritos y perfil.
- Cuatro categorías: Animales, Juguetes, Colores y Alimentos.
- Dieciséis palabras con traducciones y frases de ejemplo en español y turco.
- Grabación y reproducción de voz mediante el micrófono del dispositivo.
- Solicitud de permiso del micrófono y mensajes ante permisos rechazados o errores de audio.
- Formulario de perfil con nombre, correo y edad, con validaciones y mensajes de error por campo.
- Almacenamiento local del perfil y de las palabras favoritas mediante Preferences DataStore.
- Opción para agregar y eliminar favoritos.
- Animaciones en las transiciones, el detalle de palabras y otras interacciones.
- Organización mediante MVVM: Model, View y ViewModel.
- Gestión del estado con StateFlow y lógica de validación en los ViewModel.
- Nombre e ícono propios de la aplicación.

La grabación permite escuchar la voz del usuario. No traduce ni evalúa automáticamente su pronunciación.

## Pasos para ejecutar

### Requisitos

- Android Studio compatible con la configuración Gradle del proyecto.
- Android SDK 37 instalado.
- Teléfono o emulador con Android 7.0 (API 24) o superior.
- Micrófono para probar la grabación.
- Internet durante la configuración inicial para descargar las dependencias.

### Ejecución

1. Clonar el repositorio o descargarlo como ZIP y descomprimirlo.
2. Abrir Android Studio y seleccionar Open.
3. Elegir la carpeta principal del proyecto, donde se encuentra `settings.gradle.kts`.
4. Esperar a que finalice la sincronización de Gradle.
5. Conectar un teléfono Android con depuración USB habilitada o iniciar un emulador.
6. Seleccionar el dispositivo y la configuración `app`.
7. Presionar Run para instalar y abrir la aplicación.
8. Seleccionar una categoría y una palabra para consultar su traducción.
9. Autorizar el permiso del micrófono cuando se quiera utilizar la grabación.

La aplicación abre directamente en las categorías. El formulario se encuentra en “Mi perfil”.

### Compilación desde la terminal

En macOS o Linux:

    ./gradlew :app:assembleDebug

En Windows:

    gradlew.bat :app:assembleDebug