/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 *
 * @author Rhys Field
 */
public class UpdateTableView {

    
    public static void buildData(TableView tableview, String query) {
        
        //clear table 
        tableview.getColumns().clear();
        
        //database connection
        Connection c;
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        try {
            DBConn db = DBConn.getInstance();
            
            //SQL for selecting
            String SQL = query;
            //Result set
            ResultSet rs = db.getConn().createStatement().executeQuery(SQL);
            
            for(int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                   
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                             
                        return new SimpleStringProperty(param.getValue().get(j).toString());                       
                    }                   
                });

                
                tableview.getColumns().addAll(col);
                System.out.println("Column [" + i + "]");
            }
            
            //Add data to observable list
            while(rs.next()) {
                //Iterate rows
                ObservableList<String> row = FXCollections.observableArrayList();
                
                for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i).trim());
                }
                
                System.out.println("Row [1] added " + row);
                data.add(row);
            }
            
            //Add data to table
            tableview.setItems(data);

        } catch(Exception e) {
            
        }
    }
    
}
