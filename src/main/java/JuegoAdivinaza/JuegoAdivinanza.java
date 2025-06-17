package JuegoAdivinaza;


import java.util.Timer;
import java.util.TimerTask;

public class JuegoAdivinanza {
    private Palabra palabra;
    private int intentosRestantes;
    private GestorExcel gestorExcel;
    private int puntaje;
    private boolean sinErrores;
    private static final int TIEMPO_LIMITE_SEGUNDOS = 40;
    private boolean tiempoAgotado;
    
    

    public JuegoAdivinanza() {
        intentosRestantes = 6;
        gestorExcel = new GestorExcel();
        puntaje = 0;
    }

     public JuegoAdivinanza(GestorExcel gestorExcel) {
        this.intentosRestantes = 6;
        this.gestorExcel = gestorExcel; 
        this.puntaje = 0;
        this.sinErrores = true; 
        this.tiempoAgotado = false;
     }

    public void iniciarJuego(String categoria) {
        ItemAdivinanza item = gestorExcel.getItemAleatorio(categoria);
        if (item == null) {
            System.out.println("No hay palabras disponibles para la categoria: " + categoria);
            return;
        }

        palabra = new Palabra(item);
        intentosRestantes = 6;
        sinErrores = true;
        tiempoAgotado = false;
        puntaje = 0;

        System.out.println("¡Juego iniciado!");
        System.out.println("Categoria: " + categoria.toUpperCase());
        System.out.println("PISTA: " + palabra.getAdivinanza());
        System.out.println("Tienes 40 segundos para adivinar la palabra.");
        System.out.println("INSTRUCCIONES: Puedes escribir una letra o si sabes la palabra completa, escríbela toda.");
         
    }

    

    

    public void iniciarTemporizador() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                tiempoAgotado = true;
                System.out.println("\n⏰ Tiempo agotado.");
            }
        }, TIEMPO_LIMITE_SEGUNDOS * 1000);
    }

    public void adivinarLetra(char letra) {
        if (palabra.comprobarLetra(letra)) {
            puntaje += 10;
            System.out.println("¡Correcto! +10 puntos.");
        } else {
            intentosRestantes--;
            sinErrores = false;
            System.out.println("Incorrecto. Intentos restantes: " + intentosRestantes);
        }
    }

    public void adivinarPalabraCompleta(String palabraIntentada) {
        if (verificarPalabraCompleta(palabraIntentada)) {
            for (int i = 0; i < palabra.getPalabra().length(); i++) {
                palabra.comprobarLetra(palabra.getPalabra().charAt(i));
            }
            puntaje += 50;
            System.out.println("¡Excelente! +50 puntos por adivinar la palabra completa.");
        } else {
            intentosRestantes--;
            sinErrores = false;
            System.out.println("Palabra incorrecta. Intentos restantes: " + intentosRestantes);
        }
    }

    public void mostrarProgreso() {
        System.out.println("Progreso: " + palabra.mostrarConGuiones());
    }

    public boolean haGanado() {
        return palabra.estaCompleta();
    }

    public boolean haPerdido() {
        return intentosRestantes <= 0 || tiempoAgotado;
    }

    public boolean verificarPalabraCompleta(String palabraIntentada) {
        return palabra.getPalabra().equals(palabraIntentada.toLowerCase());
    }

    public int getPuntaje() {
        return sinErrores && haGanado() ? puntaje + 20 : puntaje;
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

    public void setGestorExcel(GestorExcel gestorExcel) {
        this.gestorExcel = gestorExcel;
    }

    public boolean isTiempoAgotado() {
        return tiempoAgotado;
    }

    public void setTiempoAgotado(boolean tiempoAgotado) {
        this.tiempoAgotado = tiempoAgotado;
    }

 
}