package JuegoAdivinaza;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        JuegoAdivinanza juego = new JuegoAdivinanza();
        boolean jugando = false;
        int opcion;

        do {
            System.out.println("\n--- MENÚ ---");
            System.out.println("1. Iniciar juego");
            System.out.println("2. Acerca de");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    juego.iniciarJuego();
                    jugando = true;
                    while (jugando) {
                        juego.mostrarProgreso();
                        System.out.print("Introduce una letra: ");
                        String entrada = scanner.nextLine();
                        if (entrada.length() != 1 || !Character.isLetter(entrada.charAt(0))) {
                            System.out.println("Ingresa una sola letra válida.");
                            continue;
                        }
                        char letra = entrada.toLowerCase().charAt(0);
                        juego.adivinarLetra(letra);

                        if (juego.haGanado()) {
                            System.out.println("🎉 ¡Felicidades! Has adivinado la palabra.");
                            jugando = false;
                        } else if (juego.haPerdido()) {
                            System.out.println("💀 Has perdido. La palabra era: " + juego.getPalabra());
                            jugando = false;
                        }
                    }
                    break;

                case 2:
                    mostrarAcercaDe();
                    break;

                case 3:
                    System.out.println("Gracias por jugar.");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 3);

        scanner.close();
    }

    public static void mostrarAcercaDe() {
        System.out.println("\n--- Acerca de ---");
        System.out.println("Equipo: PROJECT MANHATTAN");
        System.out.println("Integrantes: SHAROON JAIMES, JOHÁN MORA, JUAN VELASQUEZ");
        System.out.println("Eslogan: ¡Codificamos tu diversión!");
    }
}

