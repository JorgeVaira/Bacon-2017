package view;
import controller.Parto;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import java.time.ZoneId;
import java.util.Date;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import java.awt.*;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import java.net.URL;
import java.sql.Connection;
import controller.Cerda;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.stage.Stage;
import controller.Cerda;
import controller.Padrillo;
import controller.Servicio;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Button;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import model.DB_Connection;
import model.DB_Query;
import javafx.collections.FXCollections;
import javafx.event.EventType;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import model.DB_Insert;

public class Carga_Modificacion_Controller implements Initializable{
	//*****************************Declaración de Variables********************//
	private DB_Connection db=null;
	private Connection con=null;
	private DB_Query dbq=null;
        private DB_Insert dbi=null;
	private String sql=null;
	private ResultSet rs=null;
	private ResultSet rs2=null;
	private ObservableList<String> lista_Cerda;
	private ObservableList<String> lista_Padrillo;
	private ObservableList<String> tipo_alimento;
	private ObservableList<String> id_alimento;
        private ObservableList<String>listaux;
        private ObservableList<String> id_usuario;
        private ObservableList<Integer>group;
        private ObservableList<String>lista;
        private int ayu;
	//**********************Declaración Objetos FXML**************************//
	@FXML private DatePicker cerda_inicio;
	@FXML private DatePicker cerda_fin;
	@FXML private DatePicker padrillo_inicio;
	@FXML private DatePicker padrillo_fin;
	@FXML private DatePicker servicio_date;
	@FXML private DatePicker parto_date;
	@FXML private DatePicker preparto_date;
	@FXML private ComboBox cerda;
	@FXML private ComboBox cerda2;
	@FXML private ComboBox cerda3;
        @FXML private ComboBox cerda5;
        @FXML private ComboBox padrillo5;
	@FXML private ComboBox padrillo;
        @FXML private ComboBox fecha_serv;
	@FXML private ComboBox padrillo2;
	@FXML private ComboBox alimento;
	@FXML private ComboBox alimento3;
	@FXML private ComboBox grupo;
        @FXML private ComboBox parto;
        @FXML private ComboBox usuario;
	@FXML private TextArea obs_cerda;
	@FXML private TextArea obs_padrillo;
	@FXML private TextArea obs_servicio;
	@FXML private TextArea obs_parto;
	@FXML private TextArea descrip_alimento;
	@FXML private TextField vivos;
	@FXML private TextField muertos;
        @FXML private TextField newCaravana;
        @FXML private TextField newCaravan_padrillo;
        @FXML private TextField apellido;
        @FXML private TextField nombre;
        @FXML private TextField contrasenia;
        @FXML private TextField mail;
        @FXML private TextField cargo;
        @FXML private TextField documento;
        @FXML private TextField telefono;
        @FXML private Button buttoncerdas;
        @FXML private Button buttonpadrillos;
        @FXML private Button buttonservicios;
        @FXML private Button buttonpartos;
        @FXML private Button buttonalimentos;
        @FXML private Button cancelar;
        @FXML private Button ver;
        @FXML private Button ver2;
        @FXML private Button modificar;
        @FXML private TextField total_alim;
        @FXML private TextField used_alim;
	//************************************Declaración de Métodos de carga*******************//
	@FXML
	private void loadComboBox_Alimento() throws SQLException{
		tipo_alimento=javafx.collections.FXCollections.observableArrayList();
		id_alimento=javafx.collections.FXCollections.observableArrayList();
		db=DB_Connection.getInstance();
		con=db.getConnection();
		dbq=new DB_Query();
		sql="SELECT id_alimento, tipo FROM alimento ORDER BY id_alimento";
		rs=dbq.query(con,sql);
		rs.beforeFirst();
		while(rs.next()){
			tipo_alimento.add(rs.getString("tipo"));
			id_alimento.add(rs.getString("id_alimento"));
		}
		alimento.setItems(tipo_alimento);
		alimento3.setItems(tipo_alimento);
	}
        @FXML
	private void loadComboBox_Usuario(){	
                try{
                id_usuario=javafx.collections.FXCollections.observableArrayList();
                db=DB_Connection.getInstance();
		con=db.getConnection();
		dbq=new DB_Query();
		sql="SELECT * FROM usuario WHERE id_usuario=1";
		rs=dbq.query(con,sql);
		rs.beforeFirst();
		rs.next();
                id_usuario.add(rs.getString("apellido"));
		usuario.setItems(id_usuario);
                }catch (SQLException sqle){
                    System.out.println(sqle);
                }
	}
	@FXML
	private void loadComboBox_Caravanas()throws SQLException{
		lista_Cerda=javafx.collections.FXCollections.observableArrayList();
		lista_Padrillo=javafx.collections.FXCollections.observableArrayList();
		db=DB_Connection.getInstance();
		con=db.getConnection();
		dbq=new DB_Query();
		sql="SELECT caravana FROM cerda WHERE fecha_fin IS NULL ORDER BY caravana";
		rs=dbq.query(con,sql);
		sql="SELECT caravana FROM padrillo WHERE fecha_fin IS NULL ORDER BY caravana";
		rs2=dbq.query(con,sql);
		rs.beforeFirst();
		while(rs.next()){
			lista_Cerda.add(rs.getString("caravana"));
		}
		rs2.beforeFirst();
		while(rs2.next()){
			lista_Padrillo.add(rs2.getString("caravana"));
		}
		cerda.setItems(lista_Cerda);
		cerda2.setItems(lista_Cerda);
		cerda3.setItems(lista_Cerda);
                cerda5.setItems(lista_Cerda);
		padrillo.setItems(lista_Padrillo);
		padrillo2.setItems(lista_Padrillo);
                padrillo5.setItems(lista_Padrillo);
	}
    @FXML
    private void loadComboBox_Grupo()throws SQLException{
    	group=javafx.collections.FXCollections.observableArrayList();
    	group.add(1);
    	group.add(2);
        group.add(3);
        group.add(4);
        grupo.setItems(group);
    }
    @FXML
    private void load_campos_Cerdas()throws SQLException, ParseException{
        lista=javafx.collections.FXCollections.observableArrayList();
        listaux=javafx.collections.FXCollections.observableArrayList();
        db=DB_Connection.getInstance();
        con=db.getConnection();
        dbq=new DB_Query();
        String value;
        value = cerda.getValue().toString();
        sql="SELECT * FROM cerda WHERE caravana='"+value+"'";
        rs=dbq.query(con,sql);
        rs.beforeFirst();
        while(rs.next()){
             lista.add(rs.getString("caravana"));
             lista.add(rs.getString("fecha_inicio"));
             lista.add(rs.getString("fecha_fin"));
             lista.add(rs.getString("nro_grupo"));
             lista.add(rs.getString("observaciones"));
             lista.add(rs.getString("alimento_tipo"));
        }
        newCaravana.setText(lista.get(0).toString());
        obs_cerda.setText(lista.get(4).toString());
        grupo.setValue(Integer.parseInt(lista.get(3)));
        ayu=Integer.parseInt(lista.get(3));
        SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha= formatoDeFecha.parse(lista.get(1).toString());
        LocalDate date = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        cerda_inicio.setValue(date);
        if(lista.get(2)!=null){
            fecha=formatoDeFecha.parse(lista.get(2).toString());
            date=fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            cerda_fin.setValue(date);
        } else{
            fecha=formatoDeFecha.parse("1900-01-01");
            date=fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            cerda_fin.setValue(date);
        }
        sql="SELECT tipo FROM alimento WHERE id_alimento='"+lista.get(5)+"'";
        rs=dbq.query(con,sql);
        rs.beforeFirst();
        while(rs.next()){
             listaux.add(rs.getString("tipo"));
        }
        alimento.setValue(listaux.get(0));
    }
    @FXML
     private void load_Campos_Padrillos()throws SQLException, ParseException{
         lista=javafx.collections.FXCollections.observableArrayList();
         db=DB_Connection.getInstance();
         con=db.getConnection();
         dbq=new DB_Query();
         String value;
         value = padrillo.getValue().toString();
         sql="SELECT * FROM padrillo WHERE caravana='"+value+"'";
         rs=dbq.query(con,sql);
         rs.beforeFirst();
         while(rs.next()){
             lista.add(rs.getString("caravana"));
             lista.add(rs.getString("fecha_inicio"));
             lista.add(rs.getString("fecha_fin"));
             lista.add(rs.getString("observaciones"));
         }
         newCaravan_padrillo.setText(lista.get(0).toString());
         SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyy-MM-dd");
         Date fecha= formatoDeFecha.parse(lista.get(1).toString());
         LocalDate date = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
         padrillo_inicio.setValue(date);
         if(lista.get(2)!=null){
             fecha=formatoDeFecha.parse(lista.get(2).toString());
             date=fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
             padrillo_fin.setValue(date);
         }else{
             fecha=formatoDeFecha.parse("1900-01-01");
             date=fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
             padrillo_fin.setValue(date);
         }
         obs_padrillo.setText(lista.get(3).toString());
     }
     
    @FXML
     private void load_Campos_Servicios() throws SQLException{
         lista=javafx.collections.FXCollections.observableArrayList();
         group=javafx.collections.FXCollections.observableArrayList();
         db=DB_Connection.getInstance();
         con=db.getConnection();
         dbq=new DB_Query();
         String value;
         value = cerda5.getValue().toString();
         String cad="SELECT id_cerda FROM cerda WHERE caravana='"+value+"'";
         rs=dbq.query(con, cad);
         rs.beforeFirst();
         while(rs.next()){
             group.add(rs.getInt("id_cerda"));
         }
         int val=(int)group.get(0);
         sql="SELECT id_padrillo FROM padrillo WHERE caravana='"+padrillo5.getValue().toString()+"'";
         rs=dbq.query(con,sql);
         if(rs.absolute(1)){
             rs.beforeFirst();
             while(rs.next()){
                 group.add(rs.getInt("id_padrillo"));
             }
         }else{
             JOptionPane.showMessageDialog(null,padrillo5.getValue().toString(), "Error!", JOptionPane.ERROR_MESSAGE);
         }
         int val2=(int)group.get(1);
         sql="SELECT fecha_servicio FROM servicio WHERE id_cerda='"+val+"' AND id_padrillo='"+val2+"'";
         rs=dbq.query(con, sql);
         if(rs.absolute(1)){
             rs.beforeFirst();
             while(rs.next()){
                 lista.add(rs.getString("fecha_servicio"));
             }
             fecha_serv.setItems(lista);
             fecha_serv.setValue(lista.get(0));
         }else{
             JOptionPane.showMessageDialog(null,"Pruebe con otra combinaciÃ³n. Con esta no hay fechas de servicio cargadas"  ,"Error!!!!",JOptionPane.WARNING_MESSAGE);
         }
     }
    @FXML
    private void load_Observaciones_Servicios() throws SQLException{
        db=DB_Connection.getInstance();
        con=db.getConnection();
        dbq=new DB_Query();
        group=javafx.collections.FXCollections.observableArrayList();
        lista=javafx.collections.FXCollections.observableArrayList();
        int v1=0;
        int v2=0;
        String cad1=cerda5.getValue().toString();
        String cad2=padrillo5.getValue().toString();
        sql="SELECT id_cerda FROM cerda WHERE caravana='"+cad1+"'";
        rs=dbq.query(con, sql);
        rs.beforeFirst();
        while(rs.next()){
            group.add(rs.getInt("id_cerda"));
        }
        sql="SELECT id_padrillo FROM padrillo WHERE caravana='"+cad2+"'";
        rs=dbq.query(con, sql);
        rs.beforeFirst();
        while(rs.next()){
            group.add(rs.getInt("id_padrillo"));
        }
        String cad3=fecha_serv.getValue().toString();
        sql="SELECT observaciones FROM servicio WHERE id_cerda='"+group.get(0)+"' AND id_padrillo='"+group.get(1)+"' AND fecha_servicio='"+cad3+"'";
        rs=dbq.query(con, sql);
        rs.beforeFirst();
        while(rs.next()){
            lista.add(rs.getString("observaciones"));
        }
        obs_servicio.setText(lista.get(0).toString());  
    }
    
    @FXML
    private void load_Campos_Partos() throws SQLException{
        lista=javafx.collections.FXCollections.observableArrayList();
	group=javafx.collections.FXCollections.observableArrayList();
        db=DB_Connection.getInstance();
	con=db.getConnection();
	dbq=new DB_Query();
	sql="SELECT id_cerda FROM cerda WHERE caravana='"+cerda3.getValue().toString()+"' AND fecha_fin IS NULL";
	rs=dbq.query(con, sql);
	rs.beforeFirst();
	while(rs.next()){
	      group.add(rs.getInt("id_cerda"));
	}
	sql="SELECT fecha_parto FROM parto WHERE id_cerda='"+(int)group.get(0)+"'";
	rs=dbq.query(con, sql);
	rs.beforeFirst();
	while(rs.next()){
            lista.add(rs.getString("fecha_parto"));
	}
	parto.setItems(lista);
	parto.setValue(lista.get(0));
    }
    
    @FXML
    private void load_Campos_Partos2() throws SQLException, ParseException{
    group=javafx.collections.FXCollections.observableArrayList();
    lista=javafx.collections.FXCollections.observableArrayList();
    db=DB_Connection.getInstance();
    con=db.getConnection();
    dbq=new DB_Query();
    sql="SELECT id_cerda FROM cerda WHERE caravana='"+cerda3.getValue().toString()+"' AND fecha_fin IS NULL";
    rs=dbq.query(con, sql);
    rs.beforeFirst();
    while(rs.next()){
        group.add(rs.getInt("id_cerda"));
        }
    sql="SELECT * FROM parto WHERE id_cerda='"+(int)group.get(0)+"' AND fecha_parto='"+parto.getValue().toString()+"'";
    rs=dbq.query(con,sql);
    rs.beforeFirst();
    while(rs.next()){
        group.add(rs.getInt("cantidad_lechones"));
        group.add(rs.getInt("cantidad_muertos"));
        lista.add(rs.getString("fecha_paso_lactancia"));
        lista.add(rs.getString("observaciones"));
        }
    SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyy-MM-dd");
    Date fecha= formatoDeFecha.parse(parto.getValue().toString());
    LocalDate date = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    parto_date.setValue(date);
    fecha=formatoDeFecha.parse(lista.get(0).toString());
    date=fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    preparto_date.setValue(date);
    vivos.setText(String.valueOf((int)group.get(1)));
    muertos.setText(String.valueOf((int)group.get(2)));
    obs_parto.setText(lista.get(1).toString());
    }
    
    @FXML
    private void habilitar(){
        if(alimento3.getValue()!=null){
            ver.setDisable(false);
        }
    }
    @FXML
    private void habilitar2(){ //aca supuestamente esta el problema y funciona podes creer?
        if(usuario.getValue()!=null){
            ver2.setDisable(false);
        }
    }
    @FXML
    private void view_nowUsuario(){ //nuevo
        try{
        modificar.setDisable(false);
        String value=usuario.getValue().toString();
        String apellidoaux,nombreaux,contraseniaaux,mailaux,telefonoaux,documentoaux,cargoaux;
        db=DB_Connection.getInstance();
        con=db.getConnection();
        dbq=new DB_Query();
        sql="SELECT * FROM usuario WHERE usuario.id_usuario=1";
        rs=dbq.query(con,sql);
        rs.beforeFirst();
        rs.next();
        apellidoaux=rs.getString("apellido");
        nombreaux=rs.getString("nombre");
        contraseniaaux=rs.getString("contrasenia");
        mailaux=rs.getString("mail");
        telefonoaux=rs.getString("telefono");
        documentoaux=rs.getString("documento");
        cargoaux=rs.getString("cargo");//modificar desde aca, cambiar los textfields
        apellido.setText(apellidoaux);
        nombre.setText(nombreaux);
        contrasenia.setText(contraseniaaux);
        mail.setText(mailaux);
        telefono.setText(telefonoaux);
        documento.setText(documentoaux);
        cargo.setText(cargoaux);
        }catch (SQLException sqle){
                    System.out.println(sqle);
        }
    }
    @FXML
    private void view_now()throws SQLException{
        buttonalimentos.setDisable(false);
        String value=alimento3.getValue().toString();
        String tipo,cantidad_disponible,cantidad_usada,receta;
        db=DB_Connection.getInstance();
        con=db.getConnection();
        dbq=new DB_Query();
        sql="SELECT * FROM alimento WHERE alimento.tipo = "+'"'+value+'"';
        rs=dbq.query(con,sql);
        rs.beforeFirst();
        rs.next();
        tipo=rs.getString("tipo");
        cantidad_disponible=rs.getString("cantidad_disponible");
        cantidad_usada=rs.getString("cantidad_usada");
        receta=rs.getString("receta");
        descrip_alimento.setText(receta);
        used_alim.setText(cantidad_usada);
        total_alim.setText(cantidad_disponible);
    }
    @FXML
    private void load_Update_Alimento(){
        try{
        db=DB_Connection.getInstance();
        PreparedStatement stmt;
        con=db.getConnection();
        dbq=new DB_Query();
        dbi=new DB_Insert();
        sql="SELECT id_alimento FROM alimento WHERE alimento.tipo = "+'"'+alimento3.getValue().toString()+'"';
        rs=dbq.query(con,sql);
        rs.beforeFirst();
        rs.next();
        sql="UPDATE alimento SET cantidad_disponible="+'"'+total_alim.getText()+'"'+", cantidad_usada="+'"'+used_alim.getText()+'"'+", receta="+'"'+descrip_alimento.getText()+'"'+" WHERE alimento.id_alimento="+'"'+rs.getString("id_alimento")+'"';
        stmt=con.prepareStatement(sql);
        dbi.insert(con,stmt);
        buttonalimentos.setDisable(true);
        vaciar_Alimento();
        }catch (SQLException sqle) {
            // generic exception handling
            System.out.println(sqle);
        }
    }
    @FXML
    private void load_Update_Usuario(){//nuevo
        try{
        db=DB_Connection.getInstance();
        PreparedStatement stmt;
        con=db.getConnection();
        dbq=new DB_Query();
        dbi=new DB_Insert();
        sql="SELECT id_usuario FROM usuario WHERE usuario.id_usuario = "+'"'+usuario.getValue().toString()+'"';
        rs=dbq.query(con,sql);
        rs.beforeFirst();
        rs.next();
        sql="UPDATE usuario SET apellido="+'"'+apellido.getText()+'"'+", nombre="+'"'+nombre.getText()+'"'+", mail="+'"'+mail.getText()+'"'+", contrasenia="+'"'+contrasenia.getText()+'"'+", telefono="+'"'+telefono.getText()+'"'+", documento="+'"'+documento.getText()+'"'+", cargo="+'"'+cargo.getText()+'"'+" WHERE usuario.id_usuario=1";
        stmt=con.prepareStatement(sql);
        dbi.insert(con,stmt);
        modificar.setDisable(true);
        ver2.setDisable(true);
        vaciar_Usuario();
        }catch (SQLException sqle) {
            // generic exception handling
            System.out.println(sqle);
        }      
    }
    @FXML
    private void vaciar_Usuario(){
        apellido.setText("");
        nombre.setText("");
        mail.setText("");
        contrasenia.setText("");
        telefono.setText("");
        documento.setText("");
        cargo.setText("");
        ver2.setCancelButton(true);
        //id_usuario.clear();
        loadComboBox_Usuario();
        //view_nowUsuario();
    }
    @FXML
    private void vaciar_Alimento()throws SQLException{
        total_alim.setText("");
        used_alim.setText("");
        descrip_alimento.setText("");
        ver.setCancelButton(true);
        loadComboBox_Alimento();
    }
    @FXML
    private void load_Update_Cerda()throws SQLException{
        tipo_alimento=javafx.collections.FXCollections.observableArrayList();
        id_alimento=javafx.collections.FXCollections.observableArrayList();
        db=DB_Connection.getInstance();
        con=db.getConnection();
        dbq=new DB_Query();
        //Cerda c=new Cerda("",0,"",0,"");
        String cad1="";
        int valor=0;
        String cad2="";
        String cad3="";
        int valor2=0;
        String cad4="";
        //Compruebo si los campos están llenos o no. En el último caso, a la cadena no le asigno nada. Esto es por el tema de la excepción de puntero nulo//
        if(newCaravana.getLength()!=0){
            cad1=newCaravana.getText();
        }  
        if(grupo.getValue()!=null){
            valor=(int)grupo.getValue();
        }
        if(alimento.getValue()!=null){
        	String aux=alimento.getValue().toString();
        	rs=dbq.query(con,"SELECT id_alimento, tipo FROM alimento ORDER BY id_alimento");
            rs.beforeFirst();
            while(rs.next()){
            	id_alimento.add(rs.getString("id_alimento"));
            	tipo_alimento.add(rs.getString("tipo"));
            }
            for(int i=0; i<id_alimento.size();i++)
            {
            	if(aux.equals(tipo_alimento.get(i))){
            		valor2=Integer.parseInt((String)id_alimento.get(i));
                        System.out.println(valor2);
            	}
            }
        }
        else{
            System.out.println("Que pashó??????? O.o"+alimento.getValue());
        }
        if(cerda_inicio.getValue()!=null){
            cad2=cerda_inicio.getValue().toString();
        }
        if(cerda_fin.getValue()!=null){
            cad3=cerda_fin.getValue().toString();
        }
        if(obs_cerda.getLength()!=0){
            cad4=obs_cerda.getText();
        }
        //Ahora lo envío a la clase específica para que realize la actualización correspondiente//
        Cerda.update_Cerda(con, cerda.getValue().toString(), cad1, valor, cad2, cad3, valor2, cad4);
        JOptionPane.showMessageDialog(null, "Datos Actualizados correctamente", "Aviso", JOptionPane.INFORMATION_MESSAGE);
    }
    @FXML
    private void load_Update_Padrillo()throws SQLException{
        db=DB_Connection.getInstance();
        con=db.getConnection();
        dbq=new DB_Query();
        String cad1="", cad2="", cad3="", cad4="";
        if(newCaravan_padrillo.getLength()!=0){
        	cad1=newCaravan_padrillo.getText();
        }
        if(padrillo_inicio.getValue()!=null){
            cad2=padrillo_inicio.getValue().toString();
        }
        if(padrillo_fin.getValue()!=null){
            cad3=padrillo_fin.getValue().toString();
        }
        if(obs_padrillo.getLength()!=0){
            cad4=obs_padrillo.getText();
        }
        Padrillo.update_Padrillo(con, padrillo.getValue().toString(), cad1, cad2, cad3, cad4);
    }
    
    @FXML
    private void load_Update_Servicio() throws SQLException{
        if(cerda2.getValue()!=null && padrillo2.getValue()!=null && servicio_date.getValue()!=null){
            db=DB_Connection.getInstance();
            con=db.getConnection();
            group=javafx.collections.FXCollections.observableArrayList();
            String sql="SELECT id_cerda FROM cerda WHERE caravana='"+cerda5.getValue().toString()+"' AND fecha_fin IS NULL";
            dbq=new DB_Query();
            ResultSet rs=dbq.query(con, sql);
            rs.beforeFirst();
            while(rs.next()){
                group.add(rs.getInt("id_cerda"));
            }
            sql="SELECT id_cerda FROM cerda WHERE caravana='"+cerda2.getValue().toString()+"' AND fecha_fin IS NULL";
            rs=dbq.query(con, sql);
            rs.beforeFirst();
            while(rs.next()){
                group.add(rs.getInt("id_cerda"));
            }
            sql="SELECT id_padrillo FROM padrillo WHERE caravana='"+padrillo5.getValue().toString()+"' AND fecha_fin IS NULL";
            rs=dbq.query(con,sql);
            rs.beforeFirst();
            while(rs.next()){
                group.add(rs.getInt("id_padrillo"));
            }
            sql="SELECT id_padrillo FROM padrillo WHERE caravana='"+padrillo2.getValue().toString()+"' AND fecha_fin IS NULL";
            rs=dbq.query(con, sql);
            rs.beforeFirst();
            while(rs.next()){
                group.add(rs.getInt("id_padrillo"));
            }
            Servicio.update_Service((int)group.get(0), (int)group.get(2),fecha_serv.getValue().toString(), (int)group.get(1),(int)group.get(3),servicio_date.getValue().toString(),obs_servicio.getText());
            JOptionPane.showMessageDialog(null, "Campos Modificados Correctamente", "Ã‰xito!", JOptionPane.INFORMATION_MESSAGE);  
        }else{
            JOptionPane.showMessageDialog(null, "Cargue todos los campos para su actualizaciÃ³n", "Error!",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @FXML
    private void load_Update_Parto() throws SQLException{
        if(cerda3.getValue()!=null && parto_date.getValue()!=null && preparto_date!=null && vivos.getText()!="" && muertos.getText()!=null){
            db=DB_Connection.getInstance();
            con=db.getConnection();
            dbq=new DB_Query();
            group=javafx.collections.FXCollections.observableArrayList();
            sql="SELECT id_cerda FROM cerda WHERE caravana='"+cerda3.getValue().toString()+"' AND fecha_fin IS NULL";
            rs=dbq.query(con,sql);
            rs.beforeFirst();
            while(rs.next()){
                group.add(rs.getInt("id_cerda"));
            }
            Parto.update_Parto((int)group.get(0), parto.getValue().toString(),parto_date.getValue().toString(), preparto_date.getValue().toString(), Integer.parseInt(vivos.getText()), Integer.parseInt(muertos.getText()), obs_parto.getText());
            JOptionPane.showMessageDialog(null, "Datos Actualizados Correctamente", "Ã‰xito", JOptionPane.INFORMATION_MESSAGE);
            
        }else{
            JOptionPane.showMessageDialog(null, "Ingrese Todos los campos para continuar","Error!", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @FXML
    private void cancelar(ActionEvent event) {
        try{
            Stage stage = (Stage) cancelar.getScene().getWindow();
            stage.close();
            
        }catch(Exception e){
            System.out.print(e);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
                loadComboBox_Alimento();
        	loadComboBox_Caravanas();
        	loadComboBox_Grupo();
                loadComboBox_Usuario();
        }
        catch(Exception e){
        	e.printStackTrace();
        }
    }
}