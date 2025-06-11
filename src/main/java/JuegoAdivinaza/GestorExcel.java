package JuegoAdivinaza;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class GestorExcel {
    private Map<String, List<ItemAdivinanza>> categorias;
    private static final String ARCHIVO_EXCEL = "palabras_adivinanzas.xlsx";

    public GestorExcel() {
        categorias = new HashMap<>();
        cargarDatosDesdeExcel();
    }

    private void cargarDatosDesdeExcel() {
        try (FileInputStream fis = new FileInputStream(ARCHIVO_EXCEL);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            boolean primeraFila = true;

            for (Row row : sheet) {
                if (primeraFila) {
                    primeraFila = false;
                    continue; // Saltar la fila de encabezados
                }

                if (row.getCell(0) == null || row.getCell(1) == null || row.getCell(2) == null) {
                    continue; // Saltar filas vacías
                }

                String categoria = getCellValueAsString(row.getCell(0));
                String palabra = getCellValueAsString(row.getCell(1));
                String adivinanza = getCellValueAsString(row.getCell(2));

                if (!categoria.isEmpty() && !palabra.isEmpty() && !adivinanza.isEmpty()) {
                    ItemAdivinanza item = new ItemAdivinanza(palabra, adivinanza, categoria);
                    
                    categorias.computeIfAbsent(categoria.toLowerCase(), k -> new ArrayList<>()).add(item);
                }
            }

        } catch (IOException e) {
            System.err.println("Error al leer el archivo Excel: " + e.getMessage());
            System.err.println("Asegurate de que el archivo 'palabras_adivinanzas.xlsx' este en la raiz del proyecto.");
            cargarDatosPorDefecto();
        }
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            case FORMULA:
                try {
                    return cell.getStringCellValue().trim();
                } catch (Exception e) {
                    return String.valueOf((int) cell.getNumericCellValue());
                }
            default:
                return "";
        }
    }

    private void cargarDatosPorDefecto() {
        System.out.println("Cargando datos por defecto...");
        
        List<ItemAdivinanza> objetos = Arrays.asList(
            new ItemAdivinanza("moto", "Vehículo de dos ruedas con motor", "objetos"),
            new ItemAdivinanza("celular", "Dispositivo móvil para comunicarse", "objetos"),
            new ItemAdivinanza("mesa", "Mueble con superficie plana y patas", "objetos")
        );
        
        List<ItemAdivinanza> animales = Arrays.asList(
            new ItemAdivinanza("perro", "El mejor amigo del hombre, ladra y mueve la cola", "animales"),
            new ItemAdivinanza("gato", "Felino doméstico que maúlla y ronronea", "animales"),
            new ItemAdivinanza("pez", "Animal acuático que respira por branquias", "animales")
        );
        
        categorias.put("objetos", objetos);
        categorias.put("animales", animales);
    }

    public Set<String> getCategorias() {
        return categorias.keySet();
    }

    public List<ItemAdivinanza> getItemsPorCategoria(String categoria) {
        return categorias.getOrDefault(categoria.toLowerCase(), new ArrayList<>());
    }

    public ItemAdivinanza getItemAleatorio(String categoria) {
        List<ItemAdivinanza> items = getItemsPorCategoria(categoria);
        if (items.isEmpty()) {
            return null;
        }
        return items.get((int) (Math.random() * items.size()));
    }

    public boolean existeCategoria(String categoria) {
        return categorias.containsKey(categoria.toLowerCase());
    }
} 