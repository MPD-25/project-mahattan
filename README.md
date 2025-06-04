# 🎮 Juego: Adivina la Palabra (Consola)

Un sencillo juego de adivinanza de palabras en Java. El jugador debe adivinar una palabra letra por letra, con un número limitado de intentos. El juego muestra el progreso y notifica si se ha ganado o perdido.

## 🧠 ¿Cómo funciona?

- El juego selecciona aleatoriamente una palabra de una lista.
- El jugador tiene **6 intentos** para adivinar la palabra.
- Por cada letra adivinada correctamente, se revela su posición en la palabra.
- Si se completa la palabra antes de quedarse sin intentos, el jugador gana.
- Si se agotan los intentos sin adivinarla, el jugador pierde y se muestra la palabra correcta.

## 🧩 Estructura del proyecto

- `Palabra.java`: Se encarga de la lógica de selección de la palabra, comprobación de letras, progreso y verificación del estado del juego.
- `JuegoAdivinanza.java`: Controla el juego, maneja los intentos y coordina las interacciones con la clase `Palabra`.
- `Main.java`: Proporciona la interfaz por consola con un menú principal, lectura del teclado, y ejecución del juego.

## ▶️ Ejecución

1. Asegúrate de tener instalado **Java 8 o superior**.
2. Compila los archivos:

   ```bash
   javac Palabra.java JuegoAdivinanza.java Main.java

ejecuta el programa: java Main
   
--- MENÚ ---
1. Iniciar juego
2. Acerca de
3. Salir
