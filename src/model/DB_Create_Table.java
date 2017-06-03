package model;

import java. sql.*;

public class DB_Create_Table{
            
    public DB_Create_Table (){
    }
    
    public static void create(Connection connection){
        boolean ntable = true;
        Statement stmt = null;       
        String sql = "CREATE TABLE IF NOT EXISTS alimento " +
                       "(id_alimento int NOT NULL AUTO_INCREMENT, " + 
                       " tipo VARCHAR(100) NOT NULL, " +
                       " cantidad_disponible DOUBLE NOT NULL, " + 
                       " cantidad_usada DOUBLE, " + 
                       " receta LONGTEXT NOT NULL, " + 
                       " PRIMARY KEY (`id_alimento`))";
        try{
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
        }
        catch(java.sql.SQLException e){
            System.out.println(e);
        }
        sql = "CREATE TABLE IF NOT EXISTS cerda " +
                "(id_cerda INT NOT NULL AUTO_INCREMENT, " +
                " caravana INT NOT NULL, " +
                " fecha_inicio DATE NOT NULL, " + 
                " fecha_fin DATE, " + 
                " nro_grupo TINYINT NOT NULL, " + 
                " observaciones LONGTEXT , " + 
                " alimento_tipo INT NOT NULL, " + 
                " INDEX `fk_cerda_alimento_idx` (`alimento_tipo` ASC), " +
                " PRIMARY KEY ( id_cerda ), " +
                " CONSTRAINT `fk_cerda_alimento` " +
                " FOREIGN KEY (`alimento_tipo`) " +
                " REFERENCES `mydb`.`alimento` (`id_alimento`) " +
                " ON DELETE NO ACTION ON UPDATE NO ACTION)";
        try{
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
        }
        catch(java.sql.SQLException e){
            System.out.println(e);
        }
        sql = "CREATE TABLE IF NOT EXISTS padrillo " +
                "(id_padrillo int NOT NULL AUTO_INCREMENT, " +
                " caravana INT NOT NULL, " +
                " fecha_inicio DATE NOT NULL, " + 
                " fecha_fin DATE, " + 
                " observaciones LONGTEXT , " + 
                " alimento_tipo INT NOT NULL, " + 
                " INDEX `fk_cerda_alimento_idx` (`alimento_tipo` ASC), " +
                " PRIMARY KEY ( id_padrillo ), " +
                " CONSTRAINT `fk_padrillo_alimento` " +
                " FOREIGN KEY (`alimento_tipo`) " +
                " REFERENCES `mydb`.`alimento` (`id_alimento`) " +
                " ON DELETE NO ACTION ON UPDATE NO ACTION)";
        try{
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
        }
        catch(java.sql.SQLException e){
            System.out.println(e);
        }
        sql = "CREATE TABLE IF NOT EXISTS grupo_lechon(" +
                " fecha_destete DATE, " +
                " id_caravana_madre INT, " +
                " cantidad INT UNSIGNED, " +
                " peso_inicial DOUBLE, " +
                " id_alimento INT, " + 
                " observaciones LONGTEXT, " + 

                " PRIMARY KEY ( fecha_destete,id_caravana_madre), " +
                " FOREIGN KEY (id_alimento) REFERENCES mydb.alimento (id_alimento) " +
                " ON DELETE NO ACTION ON UPDATE NO ACTION," +
                " FOREIGN KEY (id_caravana_madre) REFERENCES mydb.cerda (id_cerda) " +
                " ON DELETE NO ACTION ON UPDATE NO ACTION);";
        try{
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
        }
        catch(java.sql.SQLException e){
            System.out.println(e);
        }
        sql = "CREATE TABLE IF NOT EXISTS servicio " +
                "(id_servicio int NOT NULL AUTO_INCREMENT, " + 
                " id_cerda int NOT NULL, " +
                " id_padrillo int NOT NULL, " + 
                " fecha_servicio date NOT NULL, " + 
                " observaciones longtext, " + 
                " INDEX `fk_id_cerda_idx` (`id_cerda` ASC), " +
                " PRIMARY KEY (`id_servicio`), " +
                " CONSTRAINT `fk_id_cerda` " +
                " FOREIGN KEY (`id_cerda`) " +
                " REFERENCES `mydb`.`cerda` (`id_cerda`) " +
                " ON DELETE NO ACTION ON UPDATE NO ACTION," +
                " CONSTRAINT `fk_padrillos` FOREIGN KEY" + 
                " (`id_padrillo`) REFERENCES `padrillo` (`id_padrillo`)" + 
                " ON DELETE NO ACTION ON UPDATE NO ACTION);";
        try{
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
        }
        catch(java.sql.SQLException e){
            System.out.println(e);
            
        }
        sql = "CREATE TABLE IF NOT EXISTS parto " +
                "(id_parto INT NOT NULL AUTO_INCREMENT, "+
                " id_cerda INT NOT NULL, " + 
                " fecha_parto DATE NOT NULL, "+
                " fecha_paso_lactancia DATE, " + 
                " cantidad_lechones INT, " + 
                " cantidad_muertos INT, " + 
                " observaciones longtext, " + 
                " PRIMARY KEY ( id_parto )," +
                " CONSTRAINT `fk_cerda_id_cerda`" +
                " FOREIGN KEY (`id_cerda`)" +
                " REFERENCES `mydb`.`cerda` (`id_cerda`)" +
                " ON DELETE NO ACTION ON UPDATE NO ACTION);";
        try{
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
        }
        catch(java.sql.SQLException e){
            System.out.println(e);
            
        }
    }
}