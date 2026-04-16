<h1 align="center">FavoriteStore</h1>

<p align="center">
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-green.svg?style=flat"/></a>
  <a href="https://kotlinlang.org/"><img alt="Android" src="https://img.shields.io/badge/Android-Kotlin-purple.svg?style=flat&logo=kotlin"/></a>
  <a href="https://github.com/donayd"><img alt="GitHub" src="https://img.shields.io/badge/GitHub-Donayd-blue.svg?style=flat"/></a> 
</p>

**FavoriteStore** es una aplicación Android moderna desarrollada en Kotlin que utiliza la [FakeStoreAPI](https://fakestoreapi.com) para mostrar productos y gestionar una lista de favoritos persistente. La app sigue los principios de **Clean Architecture** y las mejores prácticas de desarrollo recomendadas por Google.

<br/>

<table border="0">
  <tr>
    <td valign="top" style="border: none;">
      <br/>
      <img src="https://raw.githubusercontent.com/TarikSouto/TarikSouto/main/rocket.gif" width="20" /> <b><font size="5">Características</font></b>
      <br/><br/>
      <ul>
        <li><b>Listado de Productos</b>: Visualización en grid moderno con carga asíncrona de imágenes.</li>
        <li><b>Detalle de Producto</b>: Información detallada de cada artículo, precio y categoría.</li>
        <li><b>Gestión de Favoritos</b>: Marcar/desmarcar con persistencia local en Room.</li>
        <li><b>Perfil de Usuario</b>: Información obtenida de la API y estadísticas de favoritos.</li>
        <li><b>Interfaz Adaptativa</b>: Soporte para pantallas mediante <code>NavigationSuiteScaffold</code>.</li>
        <li><b>Modo Offline</b>: Los favoritos están disponibles sin conexión.</li>
        <li><b>Animaciones</b>: Transiciones fluidas entre pestañas y estados de carga.</li>
      </ul>
      <br/>
      <img src="https://raw.githubusercontent.com/TarikSouto/TarikSouto/main/gear.gif" width="20" /> <b><font size="5">Stack Tecnológico</font></b>
      <br/><br/>
      <ul>
        <li><b>Lenguaje</b>: Kotlin con Coroutines y Flow.</li>
        <li><b>UI</b>: Jetpack Compose con Material 3.</li>
        <li><b>Arquitectura</b>: MVVM + Clean Architecture.</li>
        <li><b>Inyección de Dependencias</b>: Hilt.</li>
        <li><b>Red / Persistencia</b>: Retrofit + Room Database.</li>
        <li><b>Imágenes</b>: Coil para carga eficiente.</li>
        <li><b>Testing</b>: MockK, Turbine e Instrumentation tests.</li>
      </ul>
      <br/>
      <img src="https://raw.githubusercontent.com/TarikSouto/TarikSouto/main/computer.gif" width="20" /> <b><font size="5">Arquitectura</font></b>
      <br/><br/>
      <ul>
        <li><b>Data</b>: Implementación de repositorios, Retrofit (Remote) y Room (Local).</li>
        <li><b>Domain</b>: Modelos de negocio y lógica pura (independiente de Android).</li>
        <li><b>Presentation</b>: ViewModels y pantallas de Compose por funcionalidades.</li>
      </ul>
    </td>
    <td valign="top" style="border: none; min-width: 330px;">
      <br/><br/>
      <img src="./preview/preview.gif" width="320" style="border-radius: 20px; box-shadow: 0px 4px 15px rgba(0,0,0,0.5);" />
    </td>
  </tr>
</table>

<br/>

---
Desarrollado por **Donayd Correa Marrugo**.