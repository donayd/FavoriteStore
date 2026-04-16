<h1 align="center">FavoriteStore</h1>

<p align="center">
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-green.svg?style=flat"/></a>
  <a href="https://kotlinlang.org/"><img alt="Android" src="https://img.shields.io/badge/Android-Kotlin-purple.svg?style=flat&logo=kotlin"/></a>
  <a href="https://github.com/donayd"><img alt="GitHub" src="https://img.shields.io/badge/GitHub-Donayd-blue.svg?style=flat"/></a> 
</p>

**FavoriteStore** es una aplicación Android moderna desarrollada en Kotlin que utiliza la [FakeStoreAPI](https://fakestoreapi.com) para mostrar productos y gestionar una lista de favoritos persistente. La app sigue los principios de **Clean Architecture**.

<br/>

<div align="left">
  <img src="./preview/preview.gif" align="right" width="320" style="border-radius: 20px; margin: 10px 0 20px 20px; box-shadow: 0px 4px 10px rgba(0,0,0,0.3);" />

  <font size="4">🚀 <b>Características</b></font>
  <ul>
    <li><b>Listado de Productos</b>: Grid moderno con carga asíncrona.</li>
    <li><b>Detalle de Producto</b>: Información, precio y categoría.</li>
    <li><b>Gestión de Favoritos</b>: Persistencia local en Room.</li>
    <li><b>Perfil de Usuario</b>: Estadísticas obtenidas de la API.</li>
    <li><b>Interfaz Adaptativa</b>: Uso de <code>NavigationSuiteScaffold</code>.</li>
    <li><b>Modo Offline</b>: Favoritos disponibles sin conexión.</li>
  </ul>

  <br/>

  <font size="4">🛠️ <b>Stack Tecnológico</b></font>
  <ul>
    <li><b>UI</b>: Jetpack Compose con Material 3.</li>
    <li><b>Arquitectura</b>: MVVM + Clean Architecture.</li>
    <li><b>DI</b>: Hilt para inyección de dependencias.</li>
    <li><b>Red / DB</b>: Retrofit + Room Database.</li>
    <li><b>Testing</b>: MockK, Turbine e Instrumentation tests.</li>
  </ul>

  <br/>

  <font size="4">📐 <b>Arquitectura</b></font>
  <ul>
    <li><b>Data</b>: Repositorios, Retrofit y Room.</li>
    <li><b>Domain</b>: Modelos de negocio y lógica pura.</li>
    <li><b>Presentation</b>: ViewModels y pantallas de Compose.</li>
  </ul>

</div>

<br clear="right"/>

---
Desarrollado por **Donayd Correa Marrugo**.