package it.polito.tdp.food;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

//controller turno B --> switchare al branch master_turnoA per turno A

public class FXMLController {
	
	private Model model;

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtPorzioni;

    @FXML
    private TextField txtK;

    @FXML
    private Button btnAnalisi;

    @FXML
    private Button btnGrassi;

    @FXML
    private Button btnSimula;

    @FXML
    private ComboBox<Food> boxFood;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCreaGrafo(ActionEvent event) {

		txtResult.clear();

		try {
    	int numeroPorzioni=Integer.parseInt(this.txtPorzioni.getText());
    	this.model.creaGrafo(numeroPorzioni);
 	     txtResult.appendText("Grafo creato con "+this.model.getNumeroVertici()+" vertici e con "+this.model.getNumeroArchi()+" archi \n");
 	     this.boxFood.getItems().addAll(this.model.getTendina());
		} catch (NumberFormatException e) {
			txtResult.setText("Inserire numero porzioni nel formato corretto.");
		} catch (RuntimeException e) {
			txtResult.setText("ERRORE DI CONNESSIONE AL DATABASE!");
		}
}

    @FXML
    void doGrassi(ActionEvent event) {
              if(this.boxFood.getValue()==null) {
            	  txtResult.setText("SELEZIONA UN CIBO");
              }else {
            	  txtResult.setText(this.model.getMigliori(this.boxFood.getValue()).toString());
 
              }
    }

    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();

		try {
    	int nStazioni=Integer.parseInt(this.txtK.getText());
    	  if(this.boxFood.getValue()==null) {
        	  txtResult.setText("SELEZIONA UN CIBO");
          }else {
        	
        	  this.model.simulazione(nStazioni, this.boxFood.getValue());
  			txtResult.appendText("Preparati "+this.model.getCibiPreparati()+" cibi in "+this.model.getTempoTotale()+" secondi");


          }
    	
		
		} catch (NumberFormatException e) {
			txtResult.setText("Inserire numero porzioni nel formato corretto.");
		} catch (RuntimeException e) {
			txtResult.setText("ERRORE DI CONNESSIONE AL DATABASE!");
		}

    }

    @FXML
    void initialize() {
        assert txtPorzioni != null : "fx:id=\"txtPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnGrassi != null : "fx:id=\"btnGrassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxFood != null : "fx:id=\"boxFood\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
