/**
 * Sample Skeleton for 'MetroDeParis.fxml' Controller Class
 */

package it.polito.tdp.metrodeparis;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.metrodeparis.model.Fermata;
import it.polito.tdp.metrodeparis.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class MetroDeParisController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="cmbPartenza"
    private ComboBox<Fermata> cmbPartenza; // Value injected by FXMLLoader

    @FXML // fx:id="cmbArrivo"
    private ComboBox<Fermata> cmbArrivo; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	
//    	this.cmbPartenza.getItems().clear();
//    	this.cmbArrivo.getItems().clear();
    	
    	Fermata partenza = this.cmbPartenza.getValue();
    	Fermata arrivo = this.cmbArrivo.getValue();
    	
    	if(partenza == null){
   		
   		txtResult.setText("Stazione di partenza non selezionata!");
    		return;
    		
    	}
    	else if(arrivo == null){
       		
       		txtResult.setText("Stazione di arrivo non selezionata!");
        	return;
        		
        	} 
    	 else if(partenza.equals(arrivo)){
       		
       		txtResult.setText("Percorso scelto non valido!");
        	return;
        	}
    	
    	
    	
    	try {
			// Calcolo il percorso tra le due stazioni
			model.calcolaPercorso(partenza, arrivo);

			// Ottengo il tempo di percorrenza
			int tempoTotaleInSecondi = (int) model.getPercorsoTempoTotale();
			int ore = tempoTotaleInSecondi / 3600;
			int minuti = (tempoTotaleInSecondi % 3600) / 60;
			int secondi = tempoTotaleInSecondi % 60;
			String timeString = String.format("%02d:%02d:%02d", ore, minuti, secondi);

			StringBuilder risultato = new StringBuilder();
			// Ottengo il percorso
			risultato.append(model.getPercorsoEdgeList());
			risultato.append("\n\nTempo di percorrenza stimato: " + timeString + "\n");

			// Aggiorno la TextArea
			txtResult.setText(risultato.toString());
			
		} catch (RuntimeException e) {
			txtResult.setText(e.getMessage());
		}
    	

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert cmbPartenza != null : "fx:id=\"cmbPartenza\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert cmbArrivo != null : "fx:id=\"cmbArrivo\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'MetroDeParis.fxml'.";

    }

	public void setModel(Model model) {
		// TODO Auto-generated method stub
		this.model= model;
		
		try {
			model.creaGrafo();
			
			List<Fermata> fermate = model.getAllFermate();
			this.cmbPartenza.getItems().addAll(fermate);
			this.cmbArrivo.getItems().addAll(fermate);
			
		} catch (RuntimeException e) {
			txtResult.setText(e.getMessage());
		}
		
		
		
		
		
		
	}
}
