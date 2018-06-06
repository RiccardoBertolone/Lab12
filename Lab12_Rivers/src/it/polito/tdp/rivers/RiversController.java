package it.polito.tdp.rivers;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.Model;
import it.polito.tdp.rivers.model.River;
import it.polito.tdp.rivers.model.SimResult;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class RiversController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<River> boxRiver;

    @FXML
    private TextField txtStartDate;

    @FXML
    private TextField txtEndDate;

    @FXML
    private TextField txtNumMeasurements;

    @FXML
    private TextField txtFMed;

    @FXML
    private TextField txtK;

    @FXML
    private Button btnSimula;

    @FXML
    private TextArea txtResult;
    
    private Model model;

    @FXML
    void doCompleta(ActionEvent event) {
    	
		txtResult.clear();
    	
    	River r = boxRiver.getValue() ;
    	if (r== null) {
    		txtResult.setText("Seleziona un fiume!");
    		return ;
    	}
    	
    	List<Flow> flows = r.getFlows() ;
    	Collections.sort(flows);
    	txtStartDate.setText(flows.get(0).getDay().toString());
    	txtEndDate.setText(flows.get(flows.size()-1).getDay().toString());
    	txtNumMeasurements.setText(flows.size()+"");
    	
    	double media = 0.0 ;
    	for (Flow f :flows) {
    		media+=f.getFlow() ;
    	}
    	media = media/flows.size() ;
    	txtFMed.setText(media+"");
    	
    }
    
    @FXML
    void doSimula(ActionEvent event) {
    	
    	txtResult.clear();
    	River r = boxRiver.getValue() ;
    	Double k = 0.0 ;
    	try {
    		k = Double.parseDouble(txtK.getText()) ;
    	} catch (Exception e) {
    		txtResult.setText("Inserisci un numero! (usare il punto come separatore decimale");
    		return ;
		}
    	if (r== null) {
    		txtResult.setText("Seleziona un fiume!");
    		return ;
    	}
    	
    	
    	SimResult result = model.simula(r, k) ;
    	
    	txtResult.appendText("Occupazione media: "+result.getcMedio()+"\n");
		txtResult.appendText("Numero di giorni in cui non si è potuta garantire l'erogazione minima: "+result.getGgNoErogazioneMinima());

    	
    }

    @FXML
    void initialize() {
        assert boxRiver != null : "fx:id=\"boxRiver\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtStartDate != null : "fx:id=\"txtStartDate\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtEndDate != null : "fx:id=\"txtEndDate\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtNumMeasurements != null : "fx:id=\"txtNumMeasurements\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtFMed != null : "fx:id=\"txtFMed\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Rivers.fxml'.";

    }

	public void setModel(Model model) {

		this.model = model ;
		
		List<River> rivers = model.getAllRivers() ;
		boxRiver.getItems().addAll(rivers) ;
		
	}
}
