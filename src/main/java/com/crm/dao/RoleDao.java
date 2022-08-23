package com.crm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.imageio.spi.ImageReaderWriterSpi;

import com.crm.connection.JDBCConection;
import com.crm.model.Account;
import com.crm.model.Role;

public class RoleDao {

	private ArrayList<Role> roles;

	public int update(Role role) {
		int result = 0;
		String query = "update roles set name=?,description=? where id=?;";
		try (Connection connection = JDBCConection.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, role.getName());
			statement.setString(2, role.getDescription());
			statement.setInt(3, role.getId());
			result = statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int delete(int id) {
		int result = 0;
		String query = "delete from roles where id = ?;";
		try (Connection connection = JDBCConection.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			result = statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int insert(Role role) {
		int result = 0;
		String query = "INSERT INTO roles ( name, description) VALUES  (?,?);";
		try (Connection connection = JDBCConection.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, role.getName());
			statement.setString(2, role.getDescription());
			result = statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public ArrayList<Role> getAll() {
		roles = new ArrayList<>();
		String query = "SELECT * FROM roles;";
		try (Connection conn = JDBCConection.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet res = statement.executeQuery();
			Role role;
			while (res.next()) {
				role = new Role();
				role.setId(res.getInt("id"));
				role.setName(res.getString("name"));
				role.setDescription(res.getString("description"));
				roles.add(role);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return roles;
	}

	public Role getById(int id) {
		Role role = new Role();
		String query = "SELECT * FROM roles WHERE id=? ;";
		try (Connection conn = JDBCConection.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet res = statement.executeQuery();

			if(res.next()) {
				role.setId(res.getInt("id"));
				role.setName(res.getString("name"));
				role.setDescription(res.getString("description"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return role;
	}

	public ArrayList<Role> search(String keyword, int index, int limit) {

		roles = new ArrayList<>();
		StringBuilder query = new StringBuilder("select * from roles where name like '%");
		query.append(keyword).append("%' limit ").append(index).append(",").append(limit);

		try (Connection conn = JDBCConection.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(query.toString());
			ResultSet res = statement.executeQuery();
			Role role ;
			while (res.next()) {
				role = new Role();
				role.setId(res.getInt("id"));
				role.setName(res.getString("name"));
				role.setDescription(res.getString("description"));
				roles.add(role);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roles;
	}

	public int getTotalRecord(String keyword) {
		int totalRecord = 0;
		String query = "SELECT COUNT(*) AS total_record  FROM roles WHERE name LIKE '%" + keyword + "%'";
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
