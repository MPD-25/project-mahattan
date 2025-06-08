package JuegoAdivinaza;

public class Palabra {
    private String palabraSeleccionada;
    private String pista;
    private char[] progreso;

    private static final String[] LISTA_PALABRAS = {
            "moto", "celular", "flor", "arbol"
    };

    private static final String[] LISTA_PISTAS = {
            "Vehículo de dos ruedas.",
            "Dispositivo que usamos para comunicarnos.",
            "Planta colorida que se regala mucho.",
            "Ser vivo que tiene tronco, ramas y hojas."
    };

    public Palabra() {
        int indice = (int)(Math.random() * LISTA_PALABRAS.length);
        palabraSeleccionada = LISTA_PALABRAS[indice];
        pista = LISTA_PISTAS[indice];
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

    public String getPista() {
        return pista;
    }

    public String getProgresoActual() {
        return new String(progreso);
    }
}
