markdown

# Ultramarinos


## Tabla de Contenidos

- [Ultramarinos](#ultramarinos)
	- [Tabla de Contenidos](#tabla-de-contenidos)
	- [Características](#características)
	- [Tecnologías Utilizadas](#tecnologías-utilizadas)
	- [Estructura del Proyecto](#estructura-del-proyecto)
	- [Configuración](#configuración)

## Características

- **Galería de Productos**: Permite a los usuarios ver detalles de todos los productos de la tienda, así como 
- **Carrito de Compras**: Funcionalidad para añadir, eliminar y ver productos en el carrito.
- **Interfaz Multilingüe**: Opción para cambiar el idioma de la aplicación.
- **Integración con Redes Sociales**: Permite compartir productos y promociones a través de plataformas sociales.
- **Modo oscuro**: Permite al usuario cambiar los colores de la app.

## Tecnologías Utilizadas

- **Android**: Desarrollo nativo en Kotlin.
- **Firebase**: Gestión de datos en tiempo real y autenticación.
- **MVVM**: Arquitectura basada en el patrón Model-View-ViewModel.
- **Jetpack Compose**: Para la creación de la interfaz de usuario.
- **Corutinas**: Para manejo de operaciones asincrónicas.

## Estructura del Proyecto

La estructura del proyecto sigue las convenciones estándar de Android, con una separación clara entre los componentes de la UI, modelos, y lógica de negocio.

```
.
├── app
│   ├── build.gradle.kts
│   ├── google-services.json
│   ├── proguard-rules.pro
│   └── src
│       ├── androidTest
│       │   └── java
│       │       └── com
│       │           └── reto1
│       │               └── ultramarinos
│       │                   └── ExampleInstrumentedTest.kt
│       ├── main
│       │   ├── AndroidManifest.xml
│       │   ├── java
│       │   │   └── com
│       │   │       └── reto1
│       │   │           └── ultramarinos
│       │   │               ├── CambiarIdiomaClass.kt
│       │   │               ├── components
│       │   │               │   ├── BottomNavBar.kt
│       │   │               │   ├── Carousel.kt
│       │   │               │   ├── CartModal.kt
│       │   │               │   ├── FAB.kt
│       │   │               │   ├── GallerySelector.kt
│       │   │               │   ├── ProductDetailModal.kt
│       │   │               │   ├── ProductPreview.kt
│       │   │               │   ├── RedesSociales.kt
│       │   │               │   ├── TimerHomeProducts.kt
│       │   │               │   ├── ToolBar.kt
│       │   │               │   └── YotubePlayer.kt
│       │   │               ├── Globals.kt
│       │   │               ├── MainActivity.kt
│       │   │               ├── models
│       │   │               │   ├── CartModel.kt
│       │   │               │   ├── IdiomaModel.kt
│       │   │               │   └── ProjectModel.kt
│       │   │               ├── Register.kt
│       │   │               ├── ui
│       │   │               │   └── theme
│       │   │               ├── viewmodels
│       │   │               │   ├── CartViewModel.kt
│       │   │               │   ├── GalleryViewModel.kt
│       │   │               │   └── MainViewModel.kt
│       │   │               └── views
│       │   │                   ├── AboutView.kt
│       │   │                   ├── GalleryView.kt
│       │   │                   ├── HomeView.kt
│       │   │                   ├── LoginView.kt
│       │   │                   └── SettingsView.kt
│       │   └── res
│       │       ├── drawable
│       │       ├── mipmap
│       │       └── values
│       └── test
│           └── java
│               └── com
│                   └── reto1
│                       └── ultramarinos
│                           └── ExampleUnitTest.kt
├── build.gradle.kts
├── gradle
│   ├── libs.versions.toml
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradle.properties
├── gradlew
├── gradlew.bat
├── README.md
└── settings.gradle.kts
```


## Configuración

Para ejecutar el proyecto, sigue estos pasos:

1. Clona el repositorio:

   ```bash
   git clone <URL_DEL_REPOSITORIO>
   ```

    Navega al directorio del proyecto:

    ```bash
    cd Ultramarinos
```

    Abre el proyecto en Android Studio.

    Asegúrate de tener las dependencias necesarias y configura google-services.json para la integración con Firebase.

    Ejecuta la aplicación en un emulador o dispositivo físico.
