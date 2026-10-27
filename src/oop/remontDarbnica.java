package oop;

import javax.swing.JOptionPane;

public class remontDarbnica {
    private final Garaza garaza;

    public remontDarbnica(Garaza garaza) {
        this.garaza = garaza;
    }

    public void atvert() {
        Masina masina = garaza.izveletiesMasinu();
        if (masina == null) return;

        int remontaCena = 0;
        for (int i = 0; i < masina.bojajumuSkaits; i++) {
            remontaCena += masina.bojajumaCena(masina.bojajumi[i]);
        }

        String op1 = "1 - Salabot mašīnu" + (masina.vajagRemontu ? " (" + remontaCena + " EUR)" : " (Nav bojājumu)");
        String op2 = "2 - Remontēt klienta mašīnu (Nopelnīt naudu)";
        String op3 = "3 - Vairāk ātrums (5-20) km/h (" + masina.atrumaCena() + " EUR)";
        String op4 = "4 - Mainīt krāsu (30 EUR)";

        while (true) {
            String izvele = (String) JOptionPane.showInputDialog(
                    null,
                    "REMONTA DARBNĪCA\n"
                    + "Nauda: " + garaza.nauda + " EUR\n"
                    + "Mašīna: " + masina.nosaukums() + "\n\n"
                    + op1 + "\n"
                    + op2 + "\n"
                    + op3 + "\n"
                    + op4,
                    "REMONTA DARBNĪCA",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    null,
                    ""
            );

            if (izvele == null) return;

            if (izvele.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Lauks nedrīkst būt tukšs! Ievadiet opcijas numuru.", "Kļūda", JOptionPane.WARNING_MESSAGE);
                continue;
            }

            if (izvele.equals("1")) {
                remontet(masina);
                break;
            } else if (izvele.equals("2")) {
                remontetKlientaMasinu();
                break;
            } else if (izvele.equals("3")) {
                uzlabotAtrumu(masina);
                break;
            } else if (izvele.equals("4")) {
                mainitKrasu(masina);
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Nepareiza izvēle. Lūdzu, ievadiet ciparu no 1 līdz 4.", "Darbnīca", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void remontet(Masina masina) {
        if (!masina.vajagRemontu) {
            JOptionPane.showMessageDialog(null, "Mašīnai remonts nav vajadzīgs.", "Darbnīca", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int cena = 0;
        StringBuilder remonts = new StringBuilder("Jāsalabo:\n");
        for (int i = 0; i < masina.bojajumuSkaits; i++) {
            String dala = masina.bojajumi[i];
            int dalasCena = masina.bojajumaCena(dala);
            remonts.append("- ").append(dala).append(" — ").append(dalasCena).append(" EUR\n");
            cena += dalasCena;
        }
        remonts.append("\nKopā: ").append(cena).append(" EUR");

        int apstiprinat = JOptionPane.showConfirmDialog(null, remonts.toString(),
                "Remonts", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        if (apstiprinat != JOptionPane.OK_OPTION) return;

        if (garaza.nauda < cena) {
            JOptionPane.showMessageDialog(null, "Nepietiek naudas! Remonts maksā " + cena + " EUR.", "Darbnīca", JOptionPane.WARNING_MESSAGE);
            return;
        }

        garaza.nauda -= cena;
        masina.bojajumuSkaits = 0;
        masina.vajagRemontu = false;
        masina.atrums = 0;
        JOptionPane.showMessageDialog(null, "Mašīna salabota! Samaksāji " + cena + " EUR.", "Darbnīca", JOptionPane.INFORMATION_MESSAGE);
    }

    private void remontetKlientaMasinu() {
        String[] markas = {"Audi", "BMW", "Volkswagen", "Toyota", "Ford", "Opel"};
        String marka = markas[(int)(Math.random() * markas.length)];
        int gads = 2000 + (int)(Math.random() * 21);
        Masina klientaMasina = new Masina(marka, "Klienta auto", "Melna", gads);
        
        if (!klientaMasina.vajagRemontu) {
            klientaMasina.pievienotBojajumu();
        }

        int daluCena = 0;
        StringBuilder bojajumuTeksts = new StringBuilder();
        for (int i = 0; i < klientaMasina.bojajumuSkaits; i++) {
            String dala = klientaMasina.bojajumi[i];
            int cena = klientaMasina.bojajumaCena(dala);
            daluCena += cena;
            bojajumuTeksts.append("- ").append(dala).append(" (").append(cena).append(" EUR)\n");
        }

        int atlidziba = daluCena + 90;

        String zina = "Klienta mašīna: " + klientaMasina.nosaukums() + "\n\n"
                + "Bojājumi:\n" + bojajumuTeksts.toString() + "\n"
                + "Daļu izmaksas: " + daluCena + " EUR\n"
                + "Klienta maksa par remontu: " + atlidziba + " EUR\n\n"
                + "Vai vēlies pieņemt šo pasūtījumu?";

        int atbilde = JOptionPane.showConfirmDialog(null, zina, "Klienta remonts", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (atbilde == JOptionPane.OK_OPTION) {
            if (garaza.nauda < daluCena) {
                JOptionPane.showMessageDialog(null, "Nepietiek naudas daļu iegādei (" + daluCena + " EUR)!", "Darbnīca", JOptionPane.WARNING_MESSAGE);
                return;
            }

            garaza.nauda -= daluCena; 
            garaza.nauda += atlidziba; 
            
            JOptionPane.showMessageDialog(null, 
                    "Remonts pabeigts!\nKlienta mašīna salabota un nodota īpašniekam.\nNOPELNĪTĀ PEĻŅA: +" + (atlidziba - daluCena) + " EUR!", 
                    "Klienta remonts", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void uzlabotAtrumu(Masina masina) {
        if (masina.atrumsUzlabots) {
            JOptionPane.showMessageDialog(null, "Ātrums jau ir uzlabots. To var uzlabot tikai vienu reizi.", "Darbnīca", JOptionPane.WARNING_MESSAGE);
        } else if (garaza.nauda < masina.atrumaCena()) {
            JOptionPane.showMessageDialog(null, "Nepietiek naudas.", "Darbnīca", JOptionPane.WARNING_MESSAGE);
        } else {
            garaza.nauda -= masina.atrumaCena();
            masina.atrumaLimeni++;
            int pieaugums = 5 + (int)(Math.random() * 16);
            masina.maksimalaAtrums += pieaugums;
            masina.atrumsUzlabots = true;
            JOptionPane.showMessageDialog(null,
                    "Maksimālais ātrums palielināts par " + pieaugums + " km/h!\nJaunais maksimālais ātrums: " + masina.maksimalaAtrums + " km/h", "Darbnīca", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void mainitKrasu(Masina masina) {
        if (garaza.nauda < 30) {
            JOptionPane.showMessageDialog(null, "Nepietiek naudas.", "Darbnīca", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] krasas = {"Melna", "Balta", "Sarkana", "Zila", "Zaļa", "Dzeltena", "Pelēka"};
        String krasa = (String) JOptionPane.showInputDialog(null,
                "Izvēlies jaunu krāsu:", "Mašīnas krāsa",
                JOptionPane.QUESTION_MESSAGE, null, krasas, krasas[0]);
        if (krasa != null) {
            garaza.nauda -= 30;
            masina.krasa = krasa;
            JOptionPane.showMessageDialog(null, "Krāsa nomainīta!", "Darbnīca", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}