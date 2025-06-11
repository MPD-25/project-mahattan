package JuegoAdivinaza;

public class JuegoAdivinanza {
        private Palabra palabra;
        private int intentosRestantes;
        private GestorExcel gestorExcel;

        public JuegoAdivinanza() {
            intentosRestantes = 6;
            gestorExcel = new GestorExcel();
        }

        public void iniciarJuego(String categoria) {
            ItemAdivinanza item = gestorExcel.getItemAleatorio(categoria);
            if (item == null) {
                System.out.println("No hay palabras disponibles para la categoria: " + categoria);
                return;
            }
            
            palabra = new Palabra(item);
            intentosRestantes = 6;
            System.out.println("¡Juego iniciado!");
            System.out.println("Categoria: " + categoria.toUpperCase());
            System.out.println("PISTA: " + palabra.getAdivinanza());
            System.out.println("INSTRUCCIONES: Puedes escribir una letra o si sabes la palabra completa, escribela toda.");
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

        public boolean verificarPalabraCompleta(String palabraIntentada) {
            return palabra.getPalabra().equals(palabraIntentada.toLowerCase());
        }

        public void adivinarPalabraCompleta(String palabraIntentada) {
            if (verificarPalabraCompleta(palabraIntentada)) {
                // Completar la palabra automáticamente
                for (int i = 0; i < palabra.getPalabra().length(); i++) {
                    palabra.comprobarLetra(palabra.getPalabra().charAt(i));
                }
                System.out.println("¡Excelente! ¡Has adivinado la palabra completa!");
            } else {
                intentosRestantes--;
                System.out.println("Palabra incorrecta. Intentos restantes: " + intentosRestantes);
            }
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

        public GestorExcel getGestorExcel() {
            return gestorExcel;
        }
}

