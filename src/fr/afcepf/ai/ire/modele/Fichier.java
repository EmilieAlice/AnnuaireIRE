package fr.afcepf.ai.ire.modele;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class Fichier extends Reader{
	private InputStream isr;
	private InputStreamReader fr;
	private BufferedReader br;
	private RandomAccessFile fichierAStructurer;
	
	public Fichier(String emplacementAnnuaire){
		try {
			isr = new FileInputStream(emplacementAnnuaire);
			fr = new InputStreamReader(isr,
					StandardCharsets.UTF_8);
			br = new BufferedReader(fr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	public RandomAccessFile creerAnnuaire(String emplacementAnnuaireBinaire){
		try {
			fichierAStructurer = new RandomAccessFile(emplacementAnnuaireBinaire, "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return fichierAStructurer;
	}
	public InputStream getIsr() {
		return isr;
	}
	public void setIsr(InputStream isr) {
		this.isr = isr;
	}
	public InputStreamReader getFr() {
		return fr;
	}
	public void setFr(InputStreamReader fr) {
		this.fr = fr;
	}
	public BufferedReader getBr() {
		return br;
	}
	public void setBr(BufferedReader br) {
		this.br = br;
	}
	public RandomAccessFile getFichierAStructurer() {
		return fichierAStructurer;
	}
	public void setFichierAStructurer(RandomAccessFile fichierAStructurer) {
		this.fichierAStructurer = fichierAStructurer;
	}
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
