package JuegoAdivinaza;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        JuegoAdivinanza juego = new JuegoAdivinanza();
        boolean jugando = false;
        int opcion;

        do {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Iniciar juego");
            System.out.println("2. Acerca de");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    String categoriaSeleccionada = mostrarMenuCategorias(scanner, juego);
                    if (categoriaSeleccionada != null) {
                        juego.iniciarJuego(categoriaSeleccionada);
                        jugando = true;
                        while (jugando) {
                            juego.mostrarProgreso();
                            System.out.print("Introduce una letra o la palabra completa: ");
                            String entrada = scanner.nextLine().trim();
                            
                            if (entrada.isEmpty()) {
                                System.out.println("Por favor ingresa algo valido.");
                                continue;
                            }
                            
                            // Si es una sola letra
                            if (entrada.length() == 1 && Character.isLetter(entrada.charAt(0))) {
                                char letra = entrada.toLowerCase().charAt(0);
                                juego.adivinarLetra(letra);
                            }
                            // Si es una palabra completa
                            else if (entrada.length() > 1 && entrada.matches("[a-zA-ZáéíóúñÁÉÍÓÚÑ]+")) {
                                juego.adivinarPalabraCompleta(entrada);
                            }
                            // Entrada invalida
                            else {
                                System.out.println("Ingresa una sola letra o una palabra valida (solo letras).");
                                continue;
                            }

                            if (juego.haGanado()) {
                System.out.println("¡Felicidades! Has adivinado la palabra.");
                                jugando = false;
                            } else if (juego.haPerdido()) {
                                System.out.println("Has perdido. La palabra era: " + juego.getPalabra());
                                jugando = false;
                            }
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
                    System.out.println("Opcion no valida.");
            }
        } while (opcion != 3);

        scanner.close();
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
            scanner.nextLine(); // Limpiar buffer
            
            if (seleccion >= 1 && seleccion <= categorias.size()) {
                return categoriasArray[seleccion - 1];
            } else {
                System.out.println("Seleccion invalida.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Entrada invalida.");
            scanner.nextLine(); // Limpiar buffer
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

