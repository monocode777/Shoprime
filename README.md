# Shoprime - Aplicacion Movil de Ecommerce Premium

<p align="center">
  <img src="app/src/main/res/drawable/logo_shoprime.png" alt="Shoprime Logo" width="300">
</p>

## Descripcion del Proyecto
Shoprime es una plataforma de comercio electronico diseñada para dispositivos Android que redefine la experiencia de compra y venta. Enfocada en un segmento de mercado premium, la aplicacion combina una estetica visual sofisticada con funcionalidades avanzadas de interaccion entre usuarios.

## Identidad Visual
El diseño se basa en una paleta de colores de alto contraste que evoca exclusividad:
- Azul Oscuro Profundo (#0A1118): Color base que proporciona profundidad y elegancia.
- Oro Vibrante (#FFD700): Utilizado para elementos de accion y resaltado de marca.
- Plata Suave (#F0F0F0): Color secundario para textos y detalles tecnicos.

## Funcionalidades Principales

### Modulo de Usuarios
- Sistema de perfiles duales: Comprador y Vendedor.
- Persistencia de datos local mediante SharedPreferences y GSON.
- Gestion de perfiles con fotografia personalizada.

### Experiencia del Comprador
- Catalogo de productos con diseño de tarjetas Material Design.
- Carrito de compras con gestion de unidades y calculo de totales en tiempo real.
- Pasarela de pagos integrada para transacciones simuladas.
- Historial detallado de pedidos realizados.
- Chat directo con el vendedor tras la confirmacion de compra.

### Experiencia del Vendedor
- Panel de gestion de inventario (Publicar, Editar, Eliminar).
- Carga de imagenes de productos mediante camara o galeria del dispositivo.
- Seguimiento de ventas y notificaciones de nuevos pedidos.
- Canal de comunicacion directo con clientes para coordinacion de entregas.

## Especificaciones Tecnicas
- Lenguaje: Java / Android SDK.
- SDK de Compilacion: 36.
- SDK Minimo: 24 (Android 7.0).
- Librerias Clave:
    - Google GSON: Para serializacion y almacenamiento de datos.
    - Material Components: Para la interfaz de usuario de alta fidelidad.
    - Firebase: Analiticas e infraestructura base.

## Instalacion y Configuracion
1. Clonar el repositorio del proyecto.
2. Abrir con Android Studio Ladybug o superior.
3. Realizar la sincronizacion de Gradle (Gradle Sync).
4. Configurar un dispositivo virtual (AVD) o fisico con Android 7.0 o superior.
5. Compilar y ejecutar la aplicacion.

## Autor
Equipo de Desarrollo Shoprime - 2024
