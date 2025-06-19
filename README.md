# Project Manhattan - Juego de Adivinanzas 🎯

Este es un juego de adivinanzas desarrollado en Java por el equipo Project Manhattan.

## Equipo de Desarrollo
- **SHAROON JAIMES**
- **JOHÁN MORA**  
- **JUAN VELASQUEZ**

## Eslogan
*¡Codificamos tu diversión!*

## 🎮 Descripción
Un juego interactivo donde el jugador debe adivinar palabras letra por letra, similar al clásico juego del ahorcado. Ahora con **categorías temáticas**, **tiempo**, **puntos** y **pistas** para hacer el juego más divertido y educativo.

## ✨ Nuevas Características
- **5 Categorías temáticas**: Animales, Objetos, Ciudades, Naturaleza y Conceptos
- **Sistema de pistas**: Cada palabra viene con una adivinanza descriptiva
- **Archivo XLSX configurable**: Fácil de editar y expandir
- **Interfaz mejorada** con emojis y mejor experiencia de usuario
- **Sistema de intentos limitados** (6 oportunidades)
- **sitema de puntos** hay bonificación al adivinar la palabra 
- **temporizador** son 60 segundos para adivinar la palabra
- **modo multijugador** compite entre dos personas para ver quin hace mas puntos 

## 🎯 Cómo jugar
1. Ejecuta el programa
2. Selecciona "Iniciar juego" desde el menú principal
3. **Elige una categoría** de tu preferencia
4. Lee la **pista** que aparece en pantalla
5. Introduce letras una por una para adivinar la palabra
6. ¡Gana adivinando la palabra completa antes de quedarte sin intentos!

## 📁 Estructura del Proyecto
- `Main.java` - Clase principal con los menús del juego
- `JuegoAdivinanza.java` - Lógica principal del juego
- `Palabra.java` - Manejo de las palabras, progreso y adivinanzas
- `ItemAdivinanza.java` - Clase que representa una palabra con su pista
- `GestorExcel.java` - Manejo de la carga de datos desde XLSX
- `palabras_adivinanzas.xlsx` - Base de datos de palabras organizadas por categorías
- `juegador.java` - Representa a un jugador - Contiene nombre del jugador y su puntaje acumulados
- `JuegoAdivinanzaGUI.java` - Interfaz del juego 
## 📋 Categorías Disponibles

### 🐾 Animales
Fauna diversa desde mascotas hasta animales salvajes

### 🔧 Objetos
Elementos de uso cotidiano y tecnológico

### 🏙️ Ciudades
Capitales y ciudades famosas del mundo

### 🌿 Naturaleza
Elementos del mundo natural y geografía

### 💭 Conceptos
Ideas abstractas y valores fundamentales

## 🛠️ Configuración Personalizada

### Agregar nuevas palabras
Edita el archivo `palabras_adivinanzas.xlsx` con el formato:
```
Categoría,Palabra,Adivinanza
animales,tortuga,Reptil lento que lleva su casa a cuestas
```

### Crear nuevas categorías
Simplemente agrega entradas con un nuevo nombre de categoría en el XLSX.

## 💻 Requisitos Técnicos
- Java 23 o superior
- IDE compatible con Java (recomendado: Visual Studio Code)
- Archivo `palabras_adivinanzas.xlsx` en el directorio raíz del proyecto
- comando ejecución: java -cp "target/classes;target/dependency/*" JuegoAdivinaza.Main


## 🚀 Instalación y Ejecución
1. Clona el repositorio
2. Asegúrate de que `palabras_adivinanzas.xlsx` esté en el directorio raíz
3. Compila y ejecuta `Main.java`
4. ¡Disfruta del juego!

## 🔮 Futuras Mejoras
- mejorar más la interfaz

## ▶️ Ejemplo de Ejecución

```
--- MENÚ ---
1. Iniciar juego
2. Acerca de
3. Salir
4. Modo multijugador (versus)
Seleccione una opción: 1

--- SELECCIONAR CATEGORÍA ---
1. ANIMALES
2. OBJETOS
3. CIUDADES
4. NATURALEZA
5. CONCEPTOS
Selecciona una categoría (1-5): 1

¡Juego iniciado!
Categoria: ANIMALES
PISTA: Salta por el desierto australiano con una bolsa en su panza
Tienes 60 segundos para adivinar la palabra.
INSTRUCCIONES: Puedes escribir una letra o si sabes la palabra completa, escríbela toda.
Progreso: _______
Introduce una letra o la palabra completa: CANGURO
¡Excelente! +50 puntos por adivinar la palabra completa.
¡Felicidades! Has adivinado la palabra.
Puntuación total: 70
