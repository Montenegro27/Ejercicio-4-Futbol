//Universidad del Valle de Guatemala - POO - 2023
//Mauricio Enrique Montenegro González - 23679
//Ejercicio 4 el programa tiene la funcion de sirvir como una herramienta de gestión y análisis de estadísticas de jugadores de fútbol.

import java.util.Scanner;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // Inicializa una instancia de la clase Campeonato
            Campeonato campeonato = new Campeonato();
            String archivoDatos = "estadisticas.csv"; // Nombre del archivo de datos CSV

            // Cargar datos almacenados previamente desde el archivo CSV
            campeonato.cargarDatos(archivoDatos);

            while (true) {
                System.out.println("Seleccione una opción:");
                System.out.println("1. Agregar Jugador");
                System.out.println("2. Mostrar Jugadores");
                System.out.println("3. Mostrar Mejores Porteros");
                System.out.println("4. Contar Extremos Eficaces");
                System.out.println("5. Guardar y Salir");
                System.out.print("Opción: ");
                int opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        scanner.nextLine(); // Consumir la nueva línea pendiente
                        System.out.print("Ingrese el nombre del jugador: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Ingrese el país del jugador: ");
                        String pais = scanner.nextLine();
                        System.out.print("Ingrese la cantidad de faltas del jugador: ");
                        int faltas = scanner.nextInt();
                        System.out.print("Ingrese la cantidad de goles directos del jugador: ");
                        int golesDirectos = scanner.nextInt();
                        System.out.print("Ingrese la cantidad total de lanzamientos del jugador: ");
                        int totalLanzamientos = scanner.nextInt();

                        System.out.println("Seleccione el tipo de jugador:");
                        System.out.println("1. Portero");
                        System.out.println("2. Extremo");
                        int tipoJugador = scanner.nextInt();

                        if (tipoJugador == 1) {
                            System.out.print("Ingrese la cantidad de paradas efectivas del portero: ");
                            int paradasEfectivas = scanner.nextInt();
                            System.out.print("Ingrese la cantidad de goles recibidos por el portero: ");
                            int golesRecibidos = scanner.nextInt();
                            Portero portero = new Portero(nombre, pais, faltas, golesDirectos, totalLanzamientos, paradasEfectivas, golesRecibidos);
                            campeonato.agregarJugador(portero);
                        } else if (tipoJugador == 2) {
                            System.out.print("Ingrese la cantidad de pases del extremo: ");
                            int pases = scanner.nextInt();
                            System.out.print("Ingrese la cantidad de asistencias efectivas del extremo: ");
                            int asistenciasEfectivas = scanner.nextInt();
                            Extremo extremo = new Extremo(nombre, pais, faltas, golesDirectos, totalLanzamientos, pases, asistenciasEfectivas);
                            campeonato.agregarJugador(extremo);
                        } else {
                            System.out.println("Tipo de jugador no válido.");
                        }
                        break;

                    case 2:
                        // Muestra la información de todos los jugadores en el campeonato
                        campeonato.mostrarJugadores();
                        break;

                    case 3:
                        System.out.print("Ingrese la cantidad de mejores porteros a mostrar: ");
                        int cantidadPorteros = scanner.nextInt();
                        // Obtiene y muestra la lista de mejores porteros y su efectividad
                        List<Portero> mejoresPorteros = campeonato.mejoresPorteros(cantidadPorteros);
                        System.out.println("Los mejores porteros son:");
                        for (Portero portero : mejoresPorteros) {
                            System.out.println("Nombre: " + portero.nombre);
                            System.out.println("Efectividad: " + portero.calcularEfectividad());
                        }
                        break;

                    case 4:
                        System.out.print("Ingrese la efectividad mínima para considerar un extremo como eficaz (por ejemplo, 0.85 para 85%): ");
                        double efectividadMinima = scanner.nextDouble();
                        // Cuenta la cantidad de extremos eficaces y muestra el resultado
                        int extremosEficaces = campeonato.contarExtremosEficaces(efectividadMinima);
                        System.out.println("Cantidad de extremos eficaces: " + extremosEficaces);
                        break;

                    case 5:
                        // Guarda los datos de los jugadores en el archivo CSV y sale del programa
                        campeonato.guardarDatos(archivoDatos);
                        System.out.println("Saliendo del programa.");
                        System.exit(0);

                    default:
                        System.out.println("Opción no válida. Por favor, elija una opción válida.");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
