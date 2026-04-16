# FavoriteStore

**FavoriteStore** es una aplicación Android moderna desarrollada en Kotlin que utiliza la [FakeStoreAPI](https://fakestoreapi.com) para mostrar productos y gestionar una lista de favoritos persistente. La app sigue los principios de **Clean Architecture** y las mejores prácticas de desarrollo recomendadas por Google.

## 🚀 Características

- **Listado de Productos**: Visualización de productos en un grid moderno con carga asíncrona de imágenes.
- **Detalle de Producto**: Información detallada de cada artículo, incluyendo descripción, precio y categoría.
- **Gestión de Favoritos**: Marcar/desmarcar productos como favoritos con persistencia local.
- **Perfil de Usuario**: Información del perfil obtenida de la API y estadísticas de favoritos.
- **Interfaz Adaptativa**: Soporte para diferentes tamaños de pantalla mediante `NavigationSuiteScaffold`.
- **Modo Offline**: Los productos marcados como favoritos se guardan localmente y están disponibles sin conexión.
- **Animaciones**: Transiciones fluidas entre pestañas y estados de carga.

## 🛠️ Stack Tecnológico

- **Lenguaje**: [Kotlin](https://kotlinlang.org/)
- **UI**: [Jetpack Compose](https://developer.android.com/jetpack/compose) con Material 3.
- **Arquitectura**: MVVM (Model-View-ViewModel) + Clean Architecture.
- **Inyección de Dependencias**: [Hilt](https://developer.android.com/training/dependency-injection/hilt-android).
- **Red**: [Retrofit](https://square.github.io/retrofit/) + Gson para el consumo de la API REST.
- **Persistencia**: [Room Database](https://developer.android.com/training/data-storage/room) para la base de datos local.
- **Imágenes**: [Coil](https://coil-kt.github.io/coil/) para la carga eficiente de imágenes.
- **Navegación**: Navigation Compose con animaciones personalizadas.
- **Testing**:
  - Unit Tests: MockK, Coroutines Test, Turbine.
  - Instrumentation Tests: Room In-Memory testing.

## 📐 Arquitectura

El proyecto está modularizado lógicamente en tres capas:

1.  **Data**: Implementación de repositorios, fuentes de datos remotas (Retrofit) y locales (Room).
2.  **Domain**: Modelos de negocio e interfaces de repositorios (independiente de librerías de Android).
3.  **Presentation**: ViewModels y pantallas de Compose organizadas por funcionalidades.


---
Desarrollado por **Donayd Correa Marrugo**.
