package JuegoAdivinaza;

public class ItemAdivinanza {
    private String palabra;
    private String adivinanza;
    private String categoria;

    public ItemAdivinanza(String palabra, String adivinanza, String categoria) {
        this.palabra = palabra.toLowerCase();
        this.adivinanza = adivinanza;
        this.categoria = categoria;
    }

    public String getPalabra() {
        return palabra;
    }

    public String getAdivinanza() {
        return adivinanza;
    }

    public String getCategoria() {
        return categoria;
    }

    @Override
    public String toString() {
        return "ItemAdivinanza{" +
                "palabra='" + palabra + '\'' +
                ", adivinanza='" + adivinanza + '\'' +
                ", categoria='" + categoria + '\'' +
                '}';
    }
} 