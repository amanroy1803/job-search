package com.aman.job_search.service;

import com.aman.job_search.model.Job;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class DatabaseService {

    private final Connection conn;

    public DatabaseService() throws Exception {

        conn = DriverManager.getConnection("jdbc:sqlite:jobs.db");
        Statement stmt = conn.createStatement();
        stmt.execute("""
                CREATE TABLE IF NOT EXISTS jobs (
                            id TEXT PRIMARY KEY,
                            title TEXT,
                            company TEXT,
                            url TEXT
                """);
    }

    public boolean exists(String id) throws Exception {
        PreparedStatement ps = conn.prepareStatement("SELECT id FROM jobs WHERE id=?");
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    public void save(Job job) throws Exception {

        PreparedStatement ps = conn.prepareStatement("INSERT INTO jobs VALUES(?,?,?,?)");
        ps.setString(1, job.getId());
        ps.setString(2, job.getTitle());
        ps.setString(3, job.getCompany());
        ps.setString(4, job.getUrl());

        ps.executeUpdate();
    }
}
