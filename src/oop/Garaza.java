package oop;

import javax.swing.JOptionPane;

public class Garaza {
	String[] darbibuSaraksts = {"Mašīnas izveide", "Parādīt visas mašīnas",
			"Meklēt mašīnu", "Veikt remontu", "Parādīt ieņēmumus",
			"Iziet"};
	
	Masina[] masinas;
	int masinuSkaits;
	remontDarbnica darbnica;
		
	public Garaza(int ietilpiba) {
		masinas = new Masina[ietilpiba];
		masinuSkaits = 0;
		darbnica = new remontDarbnica();
	}
	
	public void pievienotMasinu() {
		if(masinuSkaits >= masinas.length) {
			JOptionPane.showMessageDialog(null,
					"Garāža ir pilna!");
			return;
		}
		
		String marka = JOptionPane.showInputDialog("Ievadi marku: ");
		if(marka == null || marka.trim().isEmpty())
			return;
		
		String modelis = JOptionPane.showInputDialog("Ievadi modeli: ");
		if(modelis == null || modelis.trim().isEmpty())
			return;
		
		String krasa = JOptionPane.showInputDialog("Ievadi krāsu: ");
		if(krasa == null || krasa.trim().isEmpty())
			return;
		
		Masina masina = new Masina(
				marka, modelis, krasa, krasa, 0);
		
		masinas[masinuSkaits++] = masina;
		masinuSkaits++;
		
		JOptionPane.showMessageDialog(null,
				"Mašīna veiksmīgi pievienota");
	}
	
	public Masina mekletMasinu(String marka, String modelis) {
		for(int i = 0; i < masinuSkaits++; i++) {
			if(masinas[i].marka.equalsIgnoreCase(marka)
				&& masinas[i].modelis.equalsIgnoreCase(modelis)) {
				return masinas[i];
			}
		}
		return null;
	}
	
	public void mekletMasinu() {
		String marka = JOptionPane.showInputDialog("Ievadi marku:");
		if(marka == null)
			return;
		
		String modelis = JOptionPane.showInputDialog("Ievadi modeli:");
		if(modelis == null)
			return;
		
		Masina masina = mekletMasinu(marka, modelis);
		
		if(masina != null) {
			masina.paraditInfo();
		} else {
			JOptionPane.showMessageDialog(null,
					"Mašīna nav atrasta!",
					"Meklēšanas rezultāts",
					JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void paraditMasinas() {
		if(masinuSkaits == 0) {
			JOptionPane.showMessageDialog(null,
					"Garāža nav nevienas mašīnas");
			return;
		}
		
		for(int i = 0; i < masinuSkaits; i++) {
			masinas[i].paraditInfo();
		}
	}
	
	public void veiktRemontu() {
		String marka = JOptionPane.showInputDialog("Mašīnas marka:");
		if(marka == null)
			return;
		
		String modelis = JOptionPane.showInputDialog("Mašīnas modelis:");
		if(modelis == null)
			return;
		
		Masina masina = mekletMasinu(marka, modelis);
		
		if(masina == null) {
			JOptionPane.showMessageDialog(null,
					"Mašīna nav atrasta");
			return;
		}
		
		//darbnica.veiktRemontu(masina);
	}
	
	//public void paraditIenemumus() {
	//	JOptionPane.showMessageDialog(null,
		//		"Kopējie ieņēmumi: " +
				//String.format("%.2f", darbnica.getIenemumi()) + " EUR");
	//}
	
	public void palaist() {
		boolean darbojas = true;
		
		while (darbojas) {
			String izvele = (String) JOptionPane.showInputDialog(null,
					"Izvēlies darbību:",
					"Garāžas sistēma",
					JOptionPane.QUESTION_MESSAGE,
					null,
					darbibuSaraksts,
					darbibuSaraksts[0]);
			
			if(izvele == null || izvele.equals("Iziet")) {
				darbojas = false;
			} else {
				switch(izvele) {
					case "Pievienot mašīnu":
						pievienotMasinu();
						break;
						
					case "Parādīt visas mašīnas":
						paraditMasinas();
						break;
						
					case "Meklēt mašīnu":
						mekletMasinu();
						break;
						
					case "Veikt remontu":
						veiktRemontu();
						break;
						
					//case "Parādīt ieņēmumus":
					//	paraditIenemumus();
					//	break;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Garaza garaza = new Garaza(20);
		garaza.palaist();
	}
}
