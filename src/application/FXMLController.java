/**
 * @author  Zelalem Etsubneh Chekole
 * 
 */

package application;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.sql.*;



public class FXMLController implements Initializable{
	
	 @FXML
	    private TextField txtName;

	    @FXML
	    private TextField txtMobile;

	    @FXML
	    private TextField txtCourse;

	    @FXML
	    private TableView<Student> table;

	    @FXML
	    private TableColumn<Student,String> IDColumn;

	    @FXML
	    private TableColumn<Student,String> NameColumn;

	    @FXML
	    private TableColumn<Student,String> MobileColumn;

	    @FXML
	    private TableColumn<Student,String> CourseColumn;

	    @FXML
	    private Button btnAdd;

	    @FXML
	    private Button btnUpdate;

	    @FXML
	    private Button btnDelete;

	    @FXML
	    void Add(ActionEvent event) {
	    	String stname, mobile, course;
	    	stname = txtName.getText();
	    	mobile = txtMobile.getText();
	    	course = txtCourse.getText();
	    
	    	try {
	    		pst = con.prepareStatement("insert into registration(name,mobile,course) values(?,?,?)");
	    		pst.setString(1, stname);
	    		pst.setString(2, mobile);
	    		pst.setString(3, course);
	    		pst.executeUpdate();
	    		
	    		Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    		alert.setTitle("Test Connection");
	    		
	    		alert.setHeaderText("Student Registration");
	    		alert.setContentText("Added !!!");
	    		
	    		alert.showAndWait();
	    		
	    	table();
	    	
	    	txtName.setText("");
	    	txtMobile.setText("");
	    	txtCourse.setText("");
	    	txtName.requestFocus();
	    		
	    		
	    	}catch (SQLException ex) {
	    		Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE,null,ex);
	    	}

	    }
	    
	    public void table() {
	    	Connect();
	    	ObservableList<Student> students = FXCollections.observableArrayList();
	    	
	    	try {
	    		pst = con.prepareStatement("select id,name,mobile,course from registration");
	    		ResultSet rs = pst.executeQuery();
	    		
	    		{
	    			while(rs.next()) {
	    				Student st = new Student();
	    				st.setId(rs.getString("id"));
	    				st.setName(rs.getString("name"));
	    				st.setCourse(rs.getString("course"));
	    				st.setMobile(rs.getString("mobile"));
	    				students.add(st);
	    				
	    			}
	    		}
	    		
	    		table.setItems(students);
	    		IDColumn.setCellValueFactory(f->f.getValue().idProperty());
	    		NameColumn.setCellValueFactory(f->f.getValue().nameProperty());
	    		CourseColumn.setCellValueFactory(f->f.getValue().courseProperty());
	    		MobileColumn.setCellValueFactory(f->f.getValue().mobileProperty());
	    	}catch (SQLException ex) {
	    		Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE,null,ex);
	    	}
	    	
	    	table.setRowFactory(tv->{
	    		TableRow<Student> myRow = new TableRow<>();
	    		myRow.setOnMouseClicked(event->{
	    			if(event.getClickCount()==1 && (!myRow.isEmpty())) {
	    				myIndex = table.getSelectionModel().getSelectedIndex();
	    				
	    				id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));
	    				txtName.setText(table.getItems().get(myIndex).getName());
	    				txtMobile.setText(table.getItems().get(myIndex).getMobile());
	    				txtCourse.setText(table.getItems().get(myIndex).getCourse());
	    			}
	    		});
	    		return myRow;
	    	});
	    }

	    @FXML
	    void Delete(ActionEvent event) {
	    	myIndex = table.getSelectionModel().getSelectedIndex();
	    	
	    	id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));
	    	
	    	try{
	    	pst = con.prepareStatement("delete from registration where id= ?");
	    	pst.setInt(1,id);
	    	pst.executeUpdate();
    		
    		
    		Alert alert = new Alert(Alert.AlertType.INFORMATION);
    		alert.setTitle("Test Connection");
    		
    		alert.setHeaderText("Student Registration");
    		alert.setContentText("Deleted !!!");
    		
    		alert.showAndWait();
    		
    	table();
	    	
	    	
	    		
	    	}catch(SQLException ex) {
	    		Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE,null,ex);
	    	}

	    }

	    @FXML
	    void Update(ActionEvent event) {
	    	String stname, mobile, course;
	    	Connect();
	    	
	    	id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));
	    	stname = txtName.getText();
	    	mobile = txtMobile.getText();
	    	course = txtCourse.getText();
	    	
	    	try {
	    		
	    		pst = con.prepareStatement("update registration set name = ?,mobile = ?,course = ? where id= ?");
	    		pst.setString(1, stname);
	    		pst.setString(2, mobile);
	    		pst.setString(3, course);
	    		pst.setInt(4, id);
	    		pst.executeUpdate();
	    		
	    		
	    		Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    		alert.setTitle("Test Connection");
	    		
	    		alert.setHeaderText("Student Registration");
	    		alert.setContentText("Updated !!!");
	    		
	    		alert.showAndWait();
	    		
	    	table();
	    	
	    	}catch (SQLException ex) {
	    		Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE,null,ex);
	    	}

	    }
	    String username = "root";
		String password = "database-password";
	    Connection con;
	    PreparedStatement pst;
	    int myIndex;
	    int id;
	    
	    public void Connect() {
	    	try {
	    		Class.forName("com.mysql.jdbc.Driver");
	    		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentCruds",username,password);
	    	}catch (ClassNotFoundException ex){
	    		
	    	}catch (SQLException ex){
	    		ex.printStackTrace();
	    		
	    	}
	    }
	    
	    public void initialize(URL url, ResourceBundle rb) {
	    	Connect();
	    	table();
	    	
	    }

}
