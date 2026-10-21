package oop;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Masina {
	String marka, modelis, krasa;
	int pasreizejaisAtrums;
	boolean vajagRemontu;
	ImageIcon bilde;
	
	public Masina(String marka, String modelis, String krasa, 
			String cels, int pasreizejaisAtrums) {
		this.marka = marka;
		this.modelis = modelis;
		this.krasa = krasa;
		this.pasreizejaisAtrums = pasreizejaisAtrums;
		this.vajagRemontu = false;
		bilde = masinasBildi(cels);
	}
	
	public ImageIcon masinasBildi(String cels) {
		if(!cels.endsWith(".png"))
			cels += ".png";
		
		URL resurss = getClass().getResource("/atteli/"+cels);
		if(resurss != null)
			return new ImageIcon(new ImageIcon(resurss).getImage().
					getScaledInstance(100, 100, Image.SCALE_SMOOTH));
		
		else {
			JOptionPane.showMessageDialog(null,
					"Nevar atrast bildi: " + cels, "Brīdinājums",
					JOptionPane.WARNING_MESSAGE);
			return null;
			}
		}
	
	void pasreizejaisAtrums(int atrums) {
		if(atrums >= 0) {
			pasreizejaisAtrums = atrums;
		}
	}
	
	public void paraditInfo() {
		JOptionPane.showMessageDialog(null,
				"Marka: " + marka +
				"\nModelis: " + modelis +
				"\nKrasa: " + krasa +
				"\nĀtrums: " + pasreizejaisAtrums + "km/h" +
				"\nVajag remontu: " + (vajagRemontu ? "Jā" : "Nē"),
				"Mašīnas informācija",
				JOptionPane.INFORMATION_MESSAGE,
				bilde);
	}
}
