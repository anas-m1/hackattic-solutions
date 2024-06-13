package com.example.databasebackup;

import com.example.databasebackup.models.Input;
import com.example.databasebackup.models.Output;
import org.apache.tomcat.util.descriptor.web.SecurityRoleRef;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.zip.GZIPInputStream;

@SpringBootApplication
public class DatabaseBackupApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatabaseBackupApplication.class, args);
    }

    @Bean
    String solve(){
        String getUrl="https://hackattic.com/challenges/backup_restore/problem?access_token=80efb86e80666d6e";
        String postUrl="https://hackattic.com/challenges/backup_restore/solve?access_token=80efb86e80666d6e";

        RestTemplate restTemplate=new RestTemplate();

        ResponseEntity<Input>input= restTemplate.getForEntity(getUrl,Input.class);

        Output output= new Output();
        try {
            ArrayList<String> aliveSSNs=getAnswer(input.getBody().getDump());
            output.setAlive_ssns(aliveSSNs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ResponseEntity<Object>response= restTemplate.postForEntity(postUrl,output,Object.class);
        System.out.println(response+" :response");
        return "";
    }

    ArrayList<String> getAnswer(String input) throws IOException {
        System.out.println(input+" :input");
        byte[] byteArr=Base64.getDecoder().decode(input);

        FileOutputStream fos=new FileOutputStream("./file.sql.gz");
        fos.write(byteArr);

        fos.close();

        String gzippedFilePath="./file.sql.gz";
        String outputFilePath="./fileOut.sql";
        FileInputStream fileInputStream = new FileInputStream(gzippedFilePath);
        GZIPInputStream gzipInputStream = new GZIPInputStream(fileInputStream);
        FileOutputStream fileOutputStream = new FileOutputStream(outputFilePath);

        byte[] buffer = new byte[1024];
        int len;
        while ((len = gzipInputStream.read(buffer)) > 0) {
            fileOutputStream.write(buffer, 0, len);
        }

        String user = "postgres";
        String database="postgres";
        Connection connection=getConnection(user,database);

        System.out.println(connection+" :connection");

        String[] envVars = {"PGPASSWORD=postgres"};
        restoreDB(user,database,outputFilePath,envVars);
        ArrayList<String> aliveSSNs=getSSNs(user,database,envVars);

        try {
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return aliveSSNs;
    }

    ArrayList<String> getSSNs(String user,String database,String[] envVars){
        ArrayList<String> ssns=new ArrayList<>();
        String qry="SELECT ssn from public.criminal_records where status='alive'";
        String[] cmd1 = { "psql", "-h", "127.0.0.1", "-U", user, "-d", database,"-c",qry };
        Process p1= null;
        try {
            p1 = Runtime.getRuntime().exec(cmd1,envVars);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Read the output of the command
        int i=0;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(p1.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                i++;
                if(i>=3)
                    ssns.add(line.toString().trim());
            }
            ssns.remove(ssns.size()-1);
            ssns.remove(ssns.size()-1);

            for(int j=0;j<ssns.size();j++){
                System.out.println(ssns.get(j));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ssns;
    }


    void restoreDB(String user,String database,String outputFilePath, String[] envVars) {
        String[] cmd = {"psql", "-h", "127.0.0.1", "-U", user, "-d", database, "-f", outputFilePath};


        Process p;
        try {
            p = Runtime.getRuntime().exec(cmd, envVars);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            p.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    Connection getConnection(String user,String database){
        String url = "jdbc:postgresql://localhost:5432/"+database;

        String password = "postgres";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }

}
