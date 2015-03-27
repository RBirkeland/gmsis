/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib.db;

import java.sql.Connection;
import java.sql.ResultSet;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import lib.db.DBConn;

/**
 *
 * @author Rhys Field
 */
public class UpdateTableView {

    
    public static void buildData(TableView tableview, String query) {
        
        //database connection
        Connection c;
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        try {
            DBConn db = DBConn.getInstance();
            c = db.getConn();
            
            //SQL for selecting
            String SQL = query;
            //Result set
            ResultSet rs = c.createStatement().executeQuery(SQL);
            
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
                    row.add(rs.getString(i));
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
