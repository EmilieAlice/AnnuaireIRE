package fr.afcepf.ai.ire.annuaire.test;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.JList;

import fr.afcepf.ai.ire.modele.Stagiaire;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Testimprime extends Application {
	private TableView<Stagiaire> table;
	private ObservableList<Stagiaire> list;

	public void createTable() {

	    table = new TableView<>();
	    table.setEditable(true);
	    list = FXCollections.observableArrayList(
	            new Stagiaire("Jacob", "Smith", "AI 95","75","2015"),
	            new Stagiaire("Isabella", "Johnson", "AI 95","75","2015"),
	            new Stagiaire("Ethan", "Williams", "AI 95","75","2015"),
	            new Stagiaire("Emma", "Jones", "AI 95","75","2015"),
	            new Stagiaire("Michael", "Brown", "AI 95","75","2015"));

	//associating data with the table columns
	    TableColumn firstNameCol = new TableColumn("Nom");
	    firstNameCol.setMinWidth(100);
	    firstNameCol.setCellValueFactory(
	            new PropertyValueFactory<Stagiaire, String>("Nom"));

	    TableColumn SurnameCol = new TableColumn("Prenom");
	    SurnameCol.setMinWidth(100);
	    SurnameCol.setCellValueFactory(
	            new PropertyValueFactory<Stagiaire, String>("Prenom"));
	    TableColumn promoCol = new TableColumn("Promo");
	    promoCol.setMinWidth(100);
	    promoCol.setCellValueFactory(
	            new PropertyValueFactory<Stagiaire, String>("Promo"));
	    TableColumn depCol = new TableColumn("Departement");
	    depCol.setMinWidth(100);
	    depCol.setCellValueFactory(
	            new PropertyValueFactory<Stagiaire, String>("Departement"));
	    TableColumn annCol = new TableColumn("Ann�e");
	    annCol.setMinWidth(100);
	    annCol.setCellValueFactory(
	            new PropertyValueFactory<Stagiaire, String>("Ann�e"));

	    table.setItems(list);
	    table.getColumns().addAll(firstNameCol, SurnameCol, promoCol, depCol,annCol);

	}

	@Override
	public void start(Stage primaryStage) {
		
		JList<Stagiaire> listeSwing = (JList<Stagiaire>) list;
	    StackPane root = new StackPane();
	    Scene scene = new Scene(root, 300, 250);
	    this.createTable();
	    Label label = new Label("Les Stagiaires");
	    Button button = new Button("Print");
	    Button btnPdf = new Button("cr�er PDF");
	    //printing
	    button.setOnAction(new EventHandler<ActionEvent>() {

	        @Override
	        public void handle(ActionEvent event) {
	            
	        	
	            PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
				PrinterJob printerJob = PrinterJob.getPrinterJob();
				PrintService[] services = PrinterJob.lookupPrintServices();
				if(services.length > 0){
					System.out.println(" imprimante selectionn�e : " + services[0]);
					
					try {
						
						printerJob.setPrintService(services[0]);
						printerJob.pageDialog(aset);
						if(printerJob.printDialog(aset)){
							printerJob.print(aset);
							
						}
					} catch (PrinterException e) {
						System.err.println(e);
						e.printStackTrace();
					}
			
					
				}
//	            PrinterJob printerJob = PrinterJob.createPrinterJob();
//	            if (printerJob.showPrintDialog(primaryStage) && printerJob.printPage(table))
//	            {
//	                printerJob.endJob();
//	                System.out.println("printed");
//	            }
	        }
	    });
	    
	    btnPdf.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				
				
			}
		});

	    final VBox vbox = new VBox();
	    vbox.setSpacing(5);
	    vbox.setPadding(new Insets(10, 0, 0, 10));
	    vbox.getChildren().addAll(label, table, button);
	    root.getChildren().add(vbox);

	    primaryStage.setTitle("Sample TableView");
	    primaryStage.setScene(scene);
	    primaryStage.show();

	}

	private void setOnAction(EventHandler<ActionEvent> eventHandler) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
	    launch(args);
	}


}
