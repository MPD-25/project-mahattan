package JuegoAdivinaza;

public class JuegoAdivinanza {
        private Palabra palabra;
        private int intentosRestantes;

        public JuegoAdivinanza() {
            intentosRestantes = 6;
        }

        public void iniciarJuego() {
            palabra = new Palabra();
            intentosRestantes = 6;
            System.out.println("¡Juego iniciado!");
        }

        public void adivinarLetra(char letra) {
            if (palabra.comprobarLetra(letra)) {
                System.out.println("¡Correcto!");
            } else {
                intentosRestantes--;
                System.out.println("Incorrecto. Intentos restantes: " + intentosRestantes);
            }
        }

        public void mostrarProgreso() {
            System.out.println("Progreso: " + palabra.mostrarConGuiones());
        }

        public boolean haGanado() {
            return palabra.estaCompleta();
        }

        public boolean haPerdido() {
            return intentosRestantes <= 0;
        }

        public String getPalabra() {
            return palabra.getPalabra();
        }

        public int getIntentosRestantes() {
            return intentosRestantes;
        }

        public String getProgresoActual() {
            return palabra.getProgresoActual();
        }
}

