package oop;

import javax.swing.JOptionPane;
import java.util.Random;

public class Masina {
    public String marka;
    public String modelis;
    public String krasa;
    public int gads;
    public int atrums;
    public int atrumaLimeni;
    public int maksimalaAtrums;
    public boolean atrumsUzlabots;
    public boolean vajagRemontu;
    public String[] bojajumi;
    public int bojajumuSkaits;
    private final Random nejauši = new Random();

    private final String[] bojajumuNosaukumi = {
        "Bremzes", "Akumulators", "Riepas", "Lukturi", "Motors"
    };

    private final int[] bojajumuCenas = {40, 90, 120, 45, 200};

    public Masina(String marka, String modelis, String krasa, int gads) {
        this.marka = marka;
        this.modelis = modelis;
        this.krasa = krasa;
        this.gads = gads;
        this.atrums = 0;
        this.atrumaLimeni = 0;
        this.maksimalaAtrums = 100 + nejauši.nextInt(61);
        this.atrumsUzlabots = false;
        this.bojajumi = new String[5];
        this.bojajumuSkaits = 0;
        this.vajagRemontu = false;
    }

    public String nosaukums() {
        return gads + " " + marka + " " + modelis;
    }

    public void pievienotBojajumu() {
        if (bojajumuSkaits < bojajumi.length) {
            String bojajums = bojajumuNosaukumi[nejauši.nextInt(bojajumuNosaukumi.length)];

            boolean jauIr = false;
            for (int i = 0; i < bojajumuSkaits; i++) {
                if (bojajumi[i].equals(bojajums)) {
                    jauIr = true;
                    break;
                }
            }

            if (!jauIr) {
                bojajumi[bojajumuSkaits++] = bojajums;
            }
        }
        atjaunotStavokli();
    }

    public void atjaunotStavokli() {
        vajagRemontu = bojajumuSkaits > 0;
        if (vajagRemontu) {
            atrums = 0;
        }
    }

    public String braukt() {
        if (vajagRemontu) {
            StringBuilder teksts = new StringBuilder("Mašīna ir bojāta!");
            for (int i = 0; i < bojajumuSkaits; i++) {
                teksts.append("\nBojātā daļa: ").append(bojajumi[i])
                      .append(".\nRemonta cena: ").append(bojajumaCena(bojajumi[i])).append(" EUR.");
            }
            return teksts.toString();
        }

        if (nejauši.nextInt(3) == 0) {
            String bojajums = pievienotAvarijasBojajumu();
            vajagRemontu = true;
            atrums = 0;
            if (bojajums == null) {
                return "Notika avārija, bet visas daļas jau ir bojātas.";
            }
            return "Notika avārija! Bojātā daļa: " + bojajums
                    + ".\nRemonta cena: " + bojajumaCena(bojajums) + " EUR.";
        }

        int minAtrums = 40;
        if (maksimalaAtrums < minAtrums) minAtrums = maksimalaAtrums;
        atrums = minAtrums + nejauši.nextInt(maksimalaAtrums - minAtrums + 1);

        return "Tu sasniedz ātrumu " + atrums + " km/h (maksimālais ātrums: " + maksimalaAtrums + " km/h).";
    }

    private String pievienotAvarijasBojajumu() {
        String[] brivasDalas = new String[bojajumuNosaukumi.length];
        int brivoSkaits = 0;

        for (String nosaukums : bojajumuNosaukumi) {
            boolean jauIr = false;
            for (int j = 0; j < bojajumuSkaits; j++) {
                if (bojajumi[j].equals(nosaukums)) {
                    jauIr = true;
                    break;
                }
            }
            if (!jauIr) {
                brivasDalas[brivoSkaits++] = nosaukums;
            }
        }

        if (brivoSkaits == 0) return null;

        String dala = brivasDalas[nejauši.nextInt(brivoSkaits)];
        bojajumi[bojajumuSkaits++] = dala;
        return dala;
    }

    public int bojajumaCena(String dala) {
        for (int i = 0; i < bojajumuNosaukumi.length; i++) {
            if (bojajumuNosaukumi[i].equals(dala)) {
                return bojajumuCenas[i];
            }
        }
        return 0;
    }

    public int atrumaCena() {
        return 120 + atrumaLimeni * 60;
    }

    public String informacija() {
        StringBuilder teksts = new StringBuilder("Mašīna: " + nosaukums()
                + "\nKrāsa: " + krasa
                + "\nMaksimālais ātrums: " + maksimalaAtrums + " km/h"
                + "\nBojājumi: ");

        if (bojajumuSkaits == 0) {
            teksts.append("nav");
        } else {
            for (int i = 0; i < bojajumuSkaits; i++) {
                teksts.append("\n- ").append(bojajumi[i]);
            }
        }

        return teksts.toString();
    }

    public void paraditInfo() {
        JOptionPane.showMessageDialog(null, informacija(), "MAŠĪNAS INFORMĀCIJA", JOptionPane.INFORMATION_MESSAGE);
    }
}