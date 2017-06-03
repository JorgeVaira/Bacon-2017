package view;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.sql.*;
import model.DB_Connection;
import model.DB_Query;
import com.itextpdf.text.PageSize;
/**
 *
 * @author Vallejin94
 */
public class Pdf {
    
    private String caravana;
    private String fecha_inicio;
    private String fecha_fin;
    private String nro_grupo;
    private String observaciones;
    private String alimento_tipo;
    
   // Se crea el documento
    public Pdf(){generarPdf();}
    
    public void generarPdf(){
    // Se crea el OutputStream para el fichero donde queremos dejar el pdf.
    Connection connection = null;
    PreparedStatement stmt;
    String tipo,cant_disp,cant_usada,receta;
    try{
      DB_Connection bd = DB_Connection.getInstance();
        connection = bd.getConnection();
        Document doc = new Document(PageSize.A4, 5, 5, 30, 5); //el tercer valor te da la referencia y
        stmt = connection.prepareStatement("SELECT * FROM cerda");
        ResultSet res;// stmt);
        
      //  Connection con=DB_con.getConnection();
        String sql="SELECT * FROM cerda";
        DB_Query q=new DB_Query();
        res=q.query(connection, sql);
        
        
        //res = DB_Query.query(connection,"SELECT * FROM cerda");//stmt);
        FileOutputStream pdf = new FileOutputStream("ejemplo3.pdf");
        PdfWriter.getInstance(doc, pdf);
        doc.open();
        doc.addTitle("Cerdos Cargados");
        PdfPCell celda_inicial = new PdfPCell(new Paragraph("Cerdos Cargado"));
        celda_inicial.setColspan(6);
        PdfPTable table = new PdfPTable(6);
        table.addCell(celda_inicial);
        table.addCell("Caravana");
        table.addCell("Fecha inicio");
        table.addCell("Fecha fin");
        table.addCell("Nro grupo");
        table.addCell("Observacion");
        table.addCell("Alimento");
        
        while(res.next()){//este va si o si te avanza a la primera fila
        this.caravana = res.getString("caravana");
        this.fecha_inicio = res.getString("fecha_inicio");
        this.fecha_fin = res.getString("fecha_fin");
        this.nro_grupo = res.getString("nro_grupo"); 
        this.observaciones = res.getString("observaciones"); 
        this.alimento_tipo = res.getString("alimento_tipo"); 

        table.addCell(this.caravana);
        table.addCell(this.fecha_inicio);
        table.addCell(this.fecha_fin);
        table.addCell(this.nro_grupo);
        table.addCell(this.observaciones);
        table.addCell(this.alimento_tipo);
        }
        // Si desea crear una celda de mas de una columna
        // Cree un objecto Cell y cambie su propiedad span
        PdfPCell celdaFinal = new PdfPCell(new Paragraph("Final de la tabla"));
         // Indicamos cuantas columnas ocupa la celda
        celdaFinal.setColspan(3);
        table.addCell(celdaFinal);
        // Agregamos la tabla al documento            
        doc.add(table);
        doc.close();
    }catch(Exception e){
            System.out.println("error no se puede crear el archivo");
    }
        // Se asocia el documento al OutputStream y se indica que el espaciado entre
        // lineas sera de 20. Esta llamada debe hacerse antes de abrir el documento
    }
}   
