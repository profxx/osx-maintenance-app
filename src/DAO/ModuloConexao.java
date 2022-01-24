package DAO;

import java.sql.*;

public class ModuloConexao {
    public static Connection conector(){
        java.sql.Connection conexao = null;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/dbinfox?characterEncoding=utf-8";
        String user = "root";
        String password = "";
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;            
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Falha ao conectar ao BD:");
            System.out.println(e);
            return null;
        }       
    }
}