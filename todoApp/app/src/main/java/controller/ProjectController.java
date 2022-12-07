/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import util.ConnectionFactory;

/**
 *
 * @author damia
 */
public class ProjectController {

    public void save(Project project) {
        String sql = "INSERT INTO project ( name,"
                + "name,"
                + "description,"
                + "createdAt,"
                + "updatedAt"
                + ") VALUES (?,?,?,?)";
        Connection c = null;
        PreparedStatement statement = null;
        try {
            c = ConnectionFactory.getConnection();
            statement = c.prepareStatement(sql);
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));

        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar o projeto " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(c, statement);
        }
    }

    public void update(Project project) {
        String sql = "UPDATE projeect SET name = ?,"
                + "description = ?,"
                + "createdAt = ?,"
                + "updatedAt = ?"
                + "WHERE id = ?";
        Connection c = null;
        PreparedStatement statement = null;

        try {
            //estabbelecendo a conexao com banco de dados             
            c = ConnectionFactory.getConnection();

            // preparando a query
            statement = c.prepareStatement(sql);

            //setando os valores
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            statement.setInt(5, project.getId());

            //excutando a query
            statement.execute();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao tentar atualizar o projeto");
        } finally {
            ConnectionFactory.closeConnection(c, statement);
        }
    }

    public List<Project> getAll() {
        String sql = "SELECT * FROM projects";
        
        List<Project> projects = new ArrayList<Project>();

        Connection c = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;


        try {
            c = ConnectionFactory.getConnection();
            statement = c.prepareStatement(sql);
     
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));
                project.setCreatedAt(resultSet.getDate("createdAt"));
                project.setUpdatedAt(resultSet.getDate("updatedAt"));
                projects.add(project);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar a projeto" , e);
        } finally {
            ConnectionFactory.closeConnection(c, statement, resultSet);
        }
        return null;
    }

    public void removeById(int idProject) throws SQLException {
        String sql = "DELETE FROM tasks WHERE id = ?";

        Connection c = null;
        PreparedStatement statement = null;

        try {
            c = ConnectionFactory.getConnection();
            statement = c.prepareStatement(sql);
            statement.setInt(1, idProject);
            statement.execute();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar a projeto");
        } finally {
            ConnectionFactory.closeConnection(c, statement);
        }
    }
}
