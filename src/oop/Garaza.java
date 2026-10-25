package oop;

import javax.swing.JOptionPane;

public class Garaza {
    private final Masina[] masinas = new Masina[20];
    private int skaits = 0;
    public double nauda = 1000;
    private final remontDarbnica darbnica = new remontDarbnica(this);

    public static void main(String[] args) {
        new Garaza().sakt();
    }

    public void sakt() {
        String[] izveles = {
            "Izveidot mašīnu",
            "Apskatīt mašīnas",
            "Braukt ar mašīnu",
            "Remonta darbnīca",
            "Pārdot mašīnu",
            "Beigt"
        };

        while (true) {
            String teksts = "GARĀŽA\n\n"
                    + "Nauda: " + nauda + " EUR\n"
                    + "Mašīnas garāžā: " + skaits + " / 20\n\n";

            int atbilde = JOptionPane.showOptionDialog(
                    null, teksts, "GARĀŽA",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, izveles, izveles[0]);

            if (atbilde == 0) izveidotMasinu();
            else if (atbilde == 1) apskatitMasinas();
            else if (atbilde == 2) brauktArMasinu();
            else if (atbilde == 3) darbnica.atvert();
            else if (atbilde == 4) pardotMasinu();
            else break;
        }
    }

    private void izveidotMasinu() {
        if (skaits >= masinas.length) {
            JOptionPane.showMessageDialog(null, "Garāža ir pilna!", "GARĀŽA", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nosaukums = null;
        while (true) {
            nosaukums = (String) JOptionPane.showInputDialog(
                    null, "Ieraksti marku un modeli:", "Mašīnas izveide",
                    JOptionPane.QUESTION_MESSAGE, null, null, "Peel P50");
            
            if (nosaukums == null) return;
            if (!nosaukums.trim().isEmpty()) break;
            
            JOptionPane.showMessageDialog(null, "Lauks nedrīkst būt tukšs!", "Kļūda", JOptionPane.WARNING_MESSAGE);
        }

        String[] vardi = nosaukums.trim().split("\\s+", 2);
        String marka = vardi[0];
        String modelis = vardi.length > 1 ? vardi[1] : "";

        String[] krasas = {"Melna", "Balta", "Sarkana", "Zila", "Zaļa", "Dzeltena", "Pelēka"};
        String krasa = (String) JOptionPane.showInputDialog(null,
                "Izvēlies krāsu:", "Mašīnas krāsa",
                JOptionPane.QUESTION_MESSAGE, null, krasas, krasas[0]);
        if (krasa == null) return;

        int gads = 0;
        while (true) {
            String gadsTeksts = (String) JOptionPane.showInputDialog(
                    null, "Ieraksti izlaiduma gadu:", "Izlaiduma gads",
                    JOptionPane.QUESTION_MESSAGE, null, null, "2005");
            
            if (gadsTeksts == null) return;
            
            if (gadsTeksts.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Lauks nedrīkst būt tukšs!", "Kļūda", JOptionPane.WARNING_MESSAGE);
                continue;
            }

            try {
                gads = Integer.parseInt(gadsTeksts.trim());
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Gadam jābūt skaitlim!", "GARĀŽA", JOptionPane.WARNING_MESSAGE);
            }
        }

        masinas[skaits++] = new Masina(marka, modelis, krasa, gads);
        JOptionPane.showMessageDialog(null,
                masinas[skaits - 1].nosaukums() + " pievienota garāžai!",
                "Mašīna pievienota", JOptionPane.INFORMATION_MESSAGE);
    }

    public Masina izveletiesMasinu() {
        if (skaits == 0) {
            JOptionPane.showMessageDialog(null, "Garāžā vēl nav mašīnu.", "GARĀŽA", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        String[] varianti = new String[skaits];
        for (int i = 0; i < skaits; i++) {
            varianti[i] = masinas[i].nosaukums();
        }

        String izvele = (String) JOptionPane.showInputDialog(null,
                "Izvēlies mašīnu:", "Mašīnas izvēle",
                JOptionPane.QUESTION_MESSAGE, null, varianti, varianti[0]);

        if (izvele == null) return null;

        for (int i = 0; i < skaits; i++) {
            if (varianti[i].equals(izvele)) {
                return masinas[i];
            }
        }
        return null;
    }

    private void apskatitMasinas() {
        Masina masina = izveletiesMasinu();
        if (masina != null) {
            masina.paraditInfo();
        }
    }

    private void brauktArMasinu() {
        Masina masina = izveletiesMasinu();
        if (masina == null) return;

        String rezultats = masina.braukt();
        int messageType = (masina.vajagRemontu || rezultats.contains("avārija") || rezultats.contains("bojāta"))
                ? JOptionPane.WARNING_MESSAGE : JOptionPane.INFORMATION_MESSAGE;
        JOptionPane.showMessageDialog(null, rezultats, "BRAUCIENS", messageType);
    }

    private void pardotMasinu() {
        Masina masina = izveletiesMasinu();
        if (masina == null) return;

        // Cena tagad ir atkarīga no izlaiduma gada un mašīnas maksimālā ātruma!
        int vertiba = 50 + (masina.gads - 2000) * 3 + (masina.maksimalaAtrums * 2);
        if (masina.vajagRemontu) {
            vertiba -= 50; // Ja mašīna ir bojāta, tās vērtība samazinās
        }
        if (vertiba < 30) vertiba = 30; // Minimālā cena

        int apstiprinat = JOptionPane.showConfirmDialog(null,
                "Vai tiešām vēlies pārdot " + masina.nosaukums() + " (Maksimālais ātrums: " + masina.maksimalaAtrums + " km/h) par " + vertiba + " EUR?",
                "Pārdot mašīnu", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (apstiprinat == JOptionPane.OK_OPTION) {
            nauda += vertiba;
            izdzestMasinu(masina);
            JOptionPane.showMessageDialog(null, "Mašīna veiksmīgi pārdota par " + vertiba + " EUR!", "GARĀŽA", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void izdzestMasinu(Masina m) {
        int index = -1;
        for (int i = 0; i < skaits; i++) {
            if (masinas[i] == m) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            System.arraycopy(masinas, index + 1, masinas, index, skaits - index - 1);
            masinas[--skaits] = null;
        }
    }
}