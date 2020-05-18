package skelbimas.model;

public class Skelbimas {
    private int id;
    private String pavadinimas;
    private String skelbimoTipas;
    private String miestas;
    private int kaina;
    private String naujasNaudotas;
    private String kontaktai;
    private String aprasymas;

    public Skelbimas() {
    }

    public Skelbimas(int id, String pavadinimas, String skelbimoTipas, String miestas, int kaina, String naujasNaudotas, String kontaktai, String aprasymas) {
        this.id = id;
        this.pavadinimas = pavadinimas;
        this.skelbimoTipas = skelbimoTipas;
        this.miestas = miestas;
        this.kaina = kaina;
        this.naujasNaudotas = naujasNaudotas;
        this.kontaktai = kontaktai;
        this.aprasymas = aprasymas;
    }

    public Skelbimas(String pavadinimas, String skelbimoTipas, String miestas, int kaina, String naujasNaudotas, String kontaktai, String aprasymas) {
        this.pavadinimas = pavadinimas;
        this.skelbimoTipas = skelbimoTipas;
        this.miestas = miestas;
        this.kaina = kaina;
        this.naujasNaudotas = naujasNaudotas;
        this.kontaktai = kontaktai;
        this.aprasymas = aprasymas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPavadinimas() {
        return pavadinimas;
    }

    public void setPavadinimas(String pavadinimas) {
        this.pavadinimas = pavadinimas;
    }

    public String getSkelbimoTipas() {
        return skelbimoTipas;
    }

    public void setSkelbimoTipas(String skelbimoTipas) {
        this.skelbimoTipas = skelbimoTipas;
    }

    public String getMiestas() {
        return miestas;
    }

    public void setMiestas(String miestas) {
        this.miestas = miestas;
    }

    public int getKaina() {
        return kaina;
    }

    public void setKaina(int kaina) {
        this.kaina = kaina;
    }

    public String getNaujasNaudotas() {
        return naujasNaudotas;
    }

    public void setNaujasNaudotas(String naujasNaudotas) {
        this.naujasNaudotas = naujasNaudotas;
    }

    public String getKontaktai() {
        return kontaktai;
    }

    public void setKontaktai(String kontaktai) {
        this.kontaktai = kontaktai;
    }

    public String getAprasymas() {
        return aprasymas;
    }

    public void setAprasymas(String aprasymas) {
        this.aprasymas = aprasymas;
    }

    @Override
    public String toString() {
        return "SkelbimasDAO " +
                "pavadinimas='" + pavadinimas + '\'' +
                ", skelbimoTipas='" + skelbimoTipas + '\'' +
                ", miestas='" + miestas + '\'' +
                ", kaina=" + kaina +
                ", naujasNaudotas='" + naujasNaudotas + '\'' +
                ", kontaktai='" + kontaktai + '\'' +
                ", aprasymas='" + aprasymas + '\'' +
                '}';
    }
}
