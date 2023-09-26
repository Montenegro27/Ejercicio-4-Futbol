//Universidad del Valle de Guatemala - POO - 2023
//Mauricio Enrique Montenegro González - 23679
//Ejercicio 4 el programa tiene la funcion de sirvir como una herramienta de gestión y análisis de estadísticas de jugadores de fútbol.

import java.io.*;
import java.util.*;

// Definición de la clase Jugador
class Jugador {
    String nombre;
    String pais;
    int faltas;
    int golesDirectos;
    int totalLanzamientos;

    // Constructor de la clase Jugador
    public Jugador(String nombre, String pais, int faltas, int golesDirectos, int totalLanzamientos) {
        this.nombre = nombre;
        this.pais = pais;
        this.faltas = faltas;
        this.golesDirectos = golesDirectos;
        this.totalLanzamientos = totalLanzamientos;
    }

    // Método para convertir los datos del jugador en una cadena CSV
    public String toCsvString() {
        return nombre + "," + pais + "," + faltas + "," + golesDirectos + "," + totalLanzamientos;
    }
}

// Definición de la clase Portero que hereda de Jugador
class Portero extends Jugador {
    int paradasEfectivas;
    int golesRecibidos;

    // Constructor de la clase Portero
    public Portero(String nombre, String pais, int faltas, int golesDirectos, int totalLanzamientos,
                   int paradasEfectivas, int golesRecibidos) {
        super(nombre, pais, faltas, golesDirectos, totalLanzamientos);
        this.paradasEfectivas = paradasEfectivas;
        this.golesRecibidos = golesRecibidos;
    }

    // Método para calcular la efectividad del portero
    public double calcularEfectividad() {
        if (paradasEfectivas + golesRecibidos == 0) {
            return 0.0;
        }
        return (double) paradasEfectivas / (paradasEfectivas + golesRecibidos);
    }
}

// Definición de la clase Extremo que hereda de Jugador
class Extremo extends Jugador {
    int pases;
    int asistenciasEfectivas;

    // Constructor de la clase Extremo
    public Extremo(String nombre, String pais, int faltas, int golesDirectos, int totalLanzamientos,
                   int pases, int asistenciasEfectivas) {
        super(nombre, pais, faltas, golesDirectos, totalLanzamientos);
        this.pases = pases;
        this.asistenciasEfectivas = asistenciasEfectivas;
    }

    // Método para calcular la efectividad del extremo
    public double calcularEfectividad() {
        if (pases + asistenciasEfectivas == 0) {
            return 0.0;
        }
        return (double) asistenciasEfectivas / (pases + asistenciasEfectivas);
    }
}

// Definición de la clase Campeonato
class Campeonato {
    List<Jugador> jugadores;

    // Constructor de la clase Campeonato
    public Campeonato() {
        jugadores = new ArrayList<>();
    }

    // Método para agregar un jugador a la lista de jugadores
    public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
    }

    // Método para mostrar la información de todos los jugadores
    public void mostrarJugadores() {
        for (Jugador jugador : jugadores) {
            System.out.println("Nombre: " + jugador.nombre);
            System.out.println("País: " + jugador.pais);
            System.out.println("Faltas: " + jugador.faltas);
            System.out.println("Goles Directos: " + jugador.golesDirectos);
            System.out.println("Total de Lanzamientos: " + jugador.totalLanzamientos);
            
            // Verifica si el jugador es un portero o un extremo y muestra información específica
            if (jugador instanceof Portero) {
                Portero portero = (Portero) jugador;
                System.out.println("Tipo: Portero");
                System.out.println("Paradas Efectivas: " + portero.paradasEfectivas);
                System.out.println("Goles Recibidos: " + portero.golesRecibidos);
                System.out.println("Efectividad: " + portero.calcularEfectividad());
            } else if (jugador instanceof Extremo) {
                Extremo extremo = (Extremo) jugador;
                System.out.println("Tipo: Extremo");
                System.out.println("Pases: " + extremo.pases);
                System.out.println("Asistencias Efectivas: " + extremo.asistenciasEfectivas);
                System.out.println("Efectividad: " + extremo.calcularEfectividad());
            }
            System.out.println();
        }
    }

    // Método para obtener una lista de los mejores porteros
    public List<Portero> mejoresPorteros(int cantidad) {
        List<Portero> porteros = new ArrayList<>();
        for (Jugador jugador : jugadores) {
            if (jugador instanceof Portero) {
                porteros.add((Portero) jugador);
            }
        }
        porteros.sort(Comparator.comparingDouble(Portero::calcularEfectividad).reversed());
        return porteros.subList(0, Math.min(cantidad, porteros.size()));
    }

    // Método para contar la cantidad de extremos eficaces
    public int contarExtremosEficaces(double efectividadMinima) {
        return (int) jugadores.stream()
                .filter(jugador -> jugador instanceof Extremo)
                .map(jugador -> (Extremo) jugador)
                .filter(extremo -> extremo.calcularEfectividad() > efectividadMinima)
                .count();
    }

    // Método para guardar los datos de los jugadores en un archivo CSV
    public void guardarDatos(String archivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
            for (Jugador jugador : jugadores) {
                writer.println(jugador.toCsvString());
            }
            System.out.println("Datos guardados correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para cargar datos de un archivo CSV
    public void cargarDatos(String archivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String nombre = parts[0];
                    String pais = parts[1];
                    int faltas = Integer.parseInt(parts[2]);
                    int golesDirectos = Integer.parseInt(parts[3]);
                    int totalLanzamientos = Integer.parseInt(parts[4]);
                    Jugador jugador = new Jugador(nombre, pais, faltas, golesDirectos, totalLanzamientos);
                    jugadores.add(jugador);
                } else if (parts.length == 7) {
                    String nombre = parts[0];
                    String pais = parts[1];
                    int faltas = Integer.parseInt(parts[2]);
                    int golesDirectos = Integer.parseInt(parts[3]);
                    int totalLanzamientos = Integer.parseInt(parts[4]);
                    int paradasEfectivas = Integer.parseInt(parts[5]);
                    int golesRecibidos = Integer.parseInt(parts[6]);
                    Portero portero = new Portero(nombre, pais, faltas, golesDirectos, totalLanzamientos, paradasEfectivas, golesRecibidos);
                    jugadores.add(portero);
                } else if (parts.length == 8) {
                    String nombre = parts[0];
                    String pais = parts[1];
                    int faltas = Integer.parseInt(parts[2]);
                    int golesDirectos = Integer.parseInt(parts[3]);
                    int totalLanzamientos = Integer.parseInt(parts[4]);
                    int pases = Integer.parseInt(parts[5]);
                    int asistenciasEfectivas = Integer.parseInt(parts[6]);
                    Extremo extremo = new Extremo(nombre, pais, faltas, golesDirectos, totalLanzamientos, pases, asistenciasEfectivas);
                    jugadores.add(extremo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
