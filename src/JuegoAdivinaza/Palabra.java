package JuegoAdivinaza;

public class Palabra {
    private String palabraSeleccionada;
    private char[] progreso;

    private static final String[] LISTA_PALABRAS = {
            "moto", "celular", "flor", "arbol"
    };

    public Palabra() {
        palabraSeleccionada = LISTA_PALABRAS[(int)(Math.random() * LISTA_PALABRAS.length)];
        progreso = new char[palabraSeleccionada.length()];
        for (int i = 0; i < progreso.length; i++) {
            progreso[i] = '_';
        }
    }

    public String mostrarConGuiones() {
        return new String(progreso);
    }

    public boolean comprobarLetra(char letra) {
        boolean encontrada = false;
        for (int i = 0; i < palabraSeleccionada.length(); i++) {
            if (palabraSeleccionada.charAt(i) == letra) {
                progreso[i] = letra;
                encontrada = true;
            }
        }
        return encontrada;
    }

    public boolean estaCompleta() {
        return palabraSeleccionada.equals(new String(progreso));
    }

    public String getPalabra() {
        return palabraSeleccionada;
    }

    public String getProgresoActual() {
        return new String(progreso);
    }
}

