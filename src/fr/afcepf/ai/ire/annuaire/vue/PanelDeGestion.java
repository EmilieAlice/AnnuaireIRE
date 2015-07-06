package fr.afcepf.ai.ire.annuaire.vue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PanelDeGestion extends GridPane {
	private Button btnEregistrerAnnuaire = new Button("Sauvegarder l'annuaire");
	RandomAccessFile fichierAStructurer;

	public PanelDeGestion () {
		
		this.add(btnEregistrerAnnuaire, 0, 0);

		btnEregistrerAnnuaire.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				
				
				FileChooser fc2 = new FileChooser();
				fc2.setTitle("Selectionnez un emplacement pour sauvegarder le fichier");
				fc2.setInitialDirectory(new File(System.getProperty("user.home")));
				fc2.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text bin(*.bin)", "*.bin"));           //mettre en bin par defaut
				
				File fichierSelestionne = fc2.showSaveDialog(new Stage());
				
				if(!fichierSelestionne.getName().contains(".")) {
					
					String cheminFichierASauvegarder = fichierSelestionne.getAbsolutePath().toString()+".bin";
					System.out.println(cheminFichierASauvegarder);
					try {
						fichierAStructurer = new RandomAccessFile(
								cheminFichierASauvegarder, "rw");
						fichierAStructurer.writeChars(" toi");
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						System.out.println("Fichier introuvable");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("Erreur IOE");
					}

					// verifier l'extension
					
					
					System.out.println("Sauvegarde effectu�e avec succ�s !");
				}
				else{
					System.out.println("Fichier vide");
				}
			}

		});

	}



	public Button getBtnEregistrerAnnuaire() {
		return btnEregistrerAnnuaire;
	}

	public void setBtnEregistrerAnnuaire(Button btnEregistrerAnnuaire) {
		this.btnEregistrerAnnuaire = btnEregistrerAnnuaire;
	}

}
