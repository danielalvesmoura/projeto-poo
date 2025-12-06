package main.controller;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public abstract class FiltroGenerico<ClasseFiltrada> {
    public FilteredList<ClasseFiltrada> filteredList;

    public TextField campoString1;
    TextField campoString2;
    TextField campoString3;

    DatePicker campoLocalDateMinimo1;
    DatePicker campoLocalDateMaximo1;
    DatePicker campoLocalDateMinimo2;
    DatePicker campoLocalDateMaximo2;

    ChoiceBox campoChoice1;
    ChoiceBox campoChoice2;

    protected abstract void atribuiCampos();

    public ChangeListener<Object> criaFiltro() {
        atribuiCampos();

        return (obs, valorAntigo, valorNovo) -> {

            filteredList.setPredicate(item -> {

                String filtroString1 = campoString1.getText().toLowerCase();
                //String filtroString2 = campoString2.getText().toLowerCase();
                //String filtroString3 = campoString3.getText().toLowerCase();

                //LocalDate filtroLocalDateMinimo1 = campoLocalDateMinimo1.getValue();
                //LocalDate filtroLocalDateMaximo1 = campoLocalDateMaximo1.getValue();
                //LocalDate filtroLocalDateMinimo2 = campoLocalDateMinimo2.getValue();
                //LocalDate filtroLocalDateMaximo2 = campoLocalDateMaximo2.getValue();

                //String filtroChoice1 = campoChoice1.getValue().toString();
                //String filtroChoice2 = campoChoice2.getValue().toString();


                if (filtroString1.isEmpty()/* && filtroString2.isEmpty() && filtroString3.isEmpty() && filtroChoice1.isEmpty() && filtroChoice2.isEmpty() &&
                        filtroLocalDateMinimo1 == null && filtroLocalDateMaximo1 == null && filtroLocalDateMinimo2 == null && filtroLocalDateMaximo2 == null*/) {
                    return true;
                }

                if(!filtragem(filtroString1, item)) return false;

                return true;
            });
        };
    }

    protected abstract boolean filtragem(String filtroString1, ClasseFiltrada item);

}
