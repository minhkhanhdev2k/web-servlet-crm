package com.crm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import com.crm.connection.JDBCConection;
import com.crm.model.Project;

public class ProjectDao {

	private ArrayList<Project> projects;

	public int update(Project project) {
		int result = 0;
		String query = "update group_s set  group_name=?, description=? where group_id = ?;";
		try (Connection connection = JDBCConection.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, project.getName());
			statement.setString(2, project.getDescription());
			statement.setInt(3, project.getId());
			result = statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int delete(int id) {
		int result = 0;
		String query = "delete from group_s where group_id = ?;";
		try (Connection connection = JDBCConection.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			result = statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int insert(Project project) {
		int result = 0;
		String query = "INSERT INTO group_s (group_name, description) VALUES  (?,?);";
		try (Connection connection = JDBCConection.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, project.getName());
			statement.setString(2, project.getDescription());
			result = statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public ArrayList<Project> getAll() {
		projects = new ArrayList<>();
		String query = "SELECT * FROM group_s;";
		try (Connection conn = JDBCConection.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet res = statement.executeQuery();
			Project project;
			while (res.next()) {
				project = new Project();
				project.setId(res.getInt("group_id"));
				project.setName(res.getString("group_name"));
				project.setDescription(res.getString("description"));				
				projects.add(project);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return projects;
	}

	public Project getById(int id) {
		Project project = new Project();
		String query = "SELECT * FROM group_s WHERE group_id=? ;";
		try (Connection conn = JDBCConection.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet res = statement.executeQuery();
			while (res.next()) {
				project.setId(res.getInt("group_id"));
				project.setName(res.getString("group_name"));
				project.setDescription(res.getString("description"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return project;
	}

	public ArrayList<Project> search(String keyword, int index, int limit) {

		projects = new ArrayList<>();
		StringBuilder query = new StringBuilder("select * from group_s where group_name like '%");
		query.append(keyword).append("%' limit ").append(index).append(",").append(limit);

		try (Connection conn = JDBCConection.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(query.toString());
			ResultSet res = statement.executeQuery();
			Project project;
			
			while (res.next()) {
				project = new Project();
				project.setId(res.getInt("group_id"));
				project.setName(res.getString("group_name"));
				project.setDescription(res.getString("description"));
				projects.add(project);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return projects;
	}

	public int getTotalRecord(String keyword) {

		int totalRecord = 0;
		String query = "SELECT COUNT(*) AS total_record  FROM group_s WHERE group_name LIKE '%" + keyword + "%'";
		try (Connection conn = JDBCConection.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				totalRecord = resultSet.getInt("total_record");
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return totalRecord;
	}

}
