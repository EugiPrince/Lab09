/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.borders;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="cmbBoxStati"
    private ComboBox<Country> cmbBoxStati; // Value injected by FXMLLoader

    @FXML // fx:id="btnVicini"
    private Button btnVicini; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	int anno;
    	try {
    		anno = Integer.parseInt(this.txtAnno.getText());
    	} catch(Throwable t) {
    		this.txtResult.appendText("Errore nell'inserimento dell'anno!");
    		return;
    	}
    	this.model.creaGrafo(anno);
    	this.txtResult.appendText("Grafo creato.\nElenco paesi:\n");
    	this.txtResult.appendText(this.model.elencoStati());
    	this.txtResult.appendText("Numero componenti connesse: "+model.numComponentiConnesse()+"\n");
    	
    	this.cmbBoxStati.getItems().addAll(this.model.allCountries());
    }

    @FXML
    void doTrovaVicini(ActionEvent event) {
    	Country partenza = this.cmbBoxStati.getValue();
    	
    	if(partenza == null) {
    		this.txtResult.appendText("Seleziona un paese!");
    		return;
    	}
    	
    	List<Country> vicini = this.model.trovaVicini(partenza);
    	this.txtResult.appendText("Gli stati vicini a "+partenza.getStateNme()+" sono:\n");
    	for(Country c : vicini)
    		this.txtResult.appendText(c+"\n");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbBoxStati != null : "fx:id=\"cmbBoxStati\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnVicini != null : "fx:id=\"btnVicini\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
