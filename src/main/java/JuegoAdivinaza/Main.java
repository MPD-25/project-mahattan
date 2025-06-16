package JuegoAdivinaza;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        JuegoAdivinanza juego = new JuegoAdivinanza();
        int opcion;

        do {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Iniciar juego");
            System.out.println("2. Acerca de");
            System.out.println("3. Salir");
            System.out.println("4. Modo multijugador (versus)");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    jugarIndividual(scanner, juego);
                    break;
                case 2:
                    mostrarAcercaDe();
                    break;
                case 3:
                    System.out.println("Gracias por jugar.");
                    break;
                case 4:
                    jugarMultijugador(scanner);
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        } while (opcion != 3);

        scanner.close();
    }

    public static void jugarIndividual(Scanner scanner, JuegoAdivinanza juego) {
        String categoria = mostrarMenuCategorias(scanner, juego);
        if (categoria != null) {
            juego.iniciarJuego(categoria);
            juego.iniciarTemporizador();
            while (!juego.haGanado() && !juego.haPerdido()) {
                juego.mostrarProgreso();
                System.out.print("Introduce una letra o la palabra completa: ");
                String entrada = scanner.nextLine().trim();

                if (entrada.isEmpty()) continue;

                if (entrada.length() == 1 && Character.isLetter(entrada.charAt(0))) {
                    juego.adivinarLetra(Character.toLowerCase(entrada.charAt(0)));
                } else {
                    juego.adivinarPalabraCompleta(entrada);
                }
            }

            if (juego.haGanado()) {
                System.out.println("¡Felicidades! Has adivinado la palabra.");
                System.out.println("Puntuación total: " + juego.getPuntaje());
            } else {
                System.out.println("Has perdido. La palabra era: " + juego.getPalabra());
            }
        }
    }

    public static void jugarMultijugador(Scanner scanner) {
        System.out.print("Nombre del Jugador 1: ");
        Jugador jugador1 = new Jugador(scanner.nextLine().trim());
        System.out.print("Nombre del Jugador 2: ");
        Jugador jugador2 = new Jugador(scanner.nextLine().trim());

        JuegoAdivinanza juego = new JuegoAdivinanza();
        for (int ronda = 1; ronda <= 3; ronda++) {
            System.out.println("\nRONDA " + ronda + " - Turno de " + jugador1.getNombre());
            jugarTurno(scanner, juego, jugador1);

            System.out.println("\nRONDA " + ronda + " - Turno de " + jugador2.getNombre());
            jugarTurno(scanner, juego, jugador2);
        }

        System.out.println("\n--- RESULTADOS FINALES ---");
        System.out.println(jugador1.getNombre() + ": " + jugador1.getPuntaje() + " puntos");
        System.out.println(jugador2.getNombre() + ": " + jugador2.getPuntaje() + " puntos");

        if (jugador1.getPuntaje() > jugador2.getPuntaje()) {
            System.out.println("🏆 " + jugador1.getNombre() + " gana!");
        } else if (jugador2.getPuntaje() > jugador1.getPuntaje()) {
            System.out.println("🏆 " + jugador2.getNombre() + " gana!");
        } else {
            System.out.println("🤝 ¡Empate!");
        }
    }

    public static void jugarTurno(Scanner scanner, JuegoAdivinanza juego, Jugador jugador) {
        String categoria = mostrarMenuCategorias(scanner, juego);
        if (categoria != null) {
            juego.iniciarJuego(categoria);
            juego.iniciarTemporizador();

            while (!juego.haGanado() && !juego.haPerdido()) {
                juego.mostrarProgreso();
                System.out.print("Introduce una letra o la palabra completa: ");
                String entrada = scanner.nextLine().trim();

                if (entrada.isEmpty()) continue;

                if (entrada.length() == 1 && Character.isLetter(entrada.charAt(0))) {
                    juego.adivinarLetra(Character.toLowerCase(entrada.charAt(0)));
                } else {
                    juego.adivinarPalabraCompleta(entrada);
                }
            }

            if (juego.haGanado()) {
                System.out.println("¡Correcto! Has adivinado la palabra.");
            } else {
                System.out.println("Fallaste. La palabra era: " + juego.getPalabra());
            }

            jugador.sumarPuntos(juego.getPuntaje());
        }
    }

    public static String mostrarMenuCategorias(Scanner scanner, JuegoAdivinanza juego) {
        System.out.println("\n--- SELECCIONAR CATEGORIA ---");

        var categorias = juego.getGestorExcel().getCategorias();
        if (categorias.isEmpty()) {
            System.out.println("No hay categorias disponibles.");
            return null;
        }

        int indice = 1;
        String[] categoriasArray = categorias.toArray(new String[0]);

        for (String categoria : categoriasArray) {
            System.out.println(indice + ". " + categoria.toUpperCase());
            indice++;
        }

        System.out.print("Selecciona una categoria (1-" + categorias.size() + "): ");

        try {
            int seleccion = scanner.nextInt();
            scanner.nextLine();
            if (seleccion >= 1 && seleccion <= categorias.size()) {
                return categoriasArray[seleccion - 1];
            } else {
                System.out.println("Seleccion invalida.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Entrada invalida.");
            scanner.nextLine();

            return null;
        }
    }

    public static void mostrarAcercaDe() {
        System.out.println("\n--- Acerca de ---");
        System.out.println("Equipo: PROJECT MANHATTAN");
        System.out.println("Integrantes: SHAROON JAIMES, JOHAN MORA, JUAN VELASQUEZ");
        System.out.println("Eslogan: ¡Codificamos tu diversion!");
    }
}
