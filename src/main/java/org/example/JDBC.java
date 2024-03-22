package org.example;

import java.sql.*;

public class JDBC {


    public void runApp(){
        try(Connection connection = DriverManager.getConnection("jdbc:h2:mem:test")){
            createTable(connection);
            fillTable(connection);
            showConnection(connection);
        }catch (SQLException e){
            System.out.println("Ошибка: " + e);
        };
    }

    private void showConnection(Connection connection){
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("select id, first_name, second_name, age from person where age < 21");

            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String secondName = resultSet.getString("second_name");
                int age = resultSet.getInt("age");

                System.out.println("id: " + id + ", firstName: " + firstName + ", secondName: " + secondName + ", age: " + age);
            }
        }catch (SQLException e){
            System.out.println("Ошибка показа");
        };
    }

    private void createTable(Connection connection){
        try(Statement statement = connection.createStatement()){
            statement.execute("""
                    create table person(
                    id bigint,
                    first_name varchar(30),
                    second_name varchar(30),
                    age int
                    )
                    """);
        }catch (SQLException e){
            throw new RuntimeException();
        };
    }

    private void fillTable(Connection connection){
        try(Statement statement = connection.createStatement()){
            int affectedRows = statement.executeUpdate("""
                    insert into person(id, first_name, second_name, age) values
                    (1, 'Ludwig', 'Beethoven', 20),
                    (2, 'Wolfgang', 'Mozart', 18),
                    (3, 'Franz', 'Schubert', 22),
                    (4, 'Frederik', 'Chopin', 25)
                    """
            );

            System.out.println("INSERT: affectedRows: " + affectedRows);
        }catch (SQLException e){
            e.printStackTrace();
        };
    }
}

