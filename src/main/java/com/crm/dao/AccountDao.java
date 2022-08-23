package com.crm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import com.crm.connection.JDBCConection;
import com.crm.model.Account;

public class AccountDao {

	private ArrayList<Account> accounts;

	public int update(Account account) {
		int result = 0;
		String query = "update accounts set fullname=?, email=?, phone=?, address=? where account_id = ?;";
		try (Connection connection = JDBCConection.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, account.getFullname());
			statement.setString(2, account.getEmail());
			statement.setString(3, account.getPhone());
			statement.setString(4, account.getAddress());
			statement.setInt(5, account.getAccount_id());
			result = statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int delete(int id) {
		int result = 0;
		String query = "delete from accounts where account_id = ?;";
		try (Connection connection = JDBCConection.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			result = statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int insert(Account account) {
		int result = 0;
		String query = "INSERT INTO accounts (fullname, email, password, phone, address) VALUES  ( ?,?,?,?,?);";
		try (Connection connection = JDBCConection.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, account.getFullname());
			statement.setString(2, account.getEmail());
			statement.setString(3, account.getPassword());
			statement.setString(4, account.getPhone());
			statement.setString(5, account.getAddress());
			result = statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public ArrayList<Account> getIdAndFullName() {
		accounts = new ArrayList<>();
		String query = "SELECT  account_id, fullname FROM accounts;";
		try (Connection conn = JDBCConection.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet res = statement.executeQuery();
			Account account;
			while (res.next()) {
				account = new Account();
				account.setAccount_id(res.getInt("account_id"));
				account.setFullname(res.getString("fullname"));
				accounts.add(account);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return accounts;
	}
	
	public ArrayList<Account> getAll() {
		accounts = new ArrayList<>();
		String query = "SELECT * FROM accounts;";
		try (Connection conn = JDBCConection.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet res = statement.executeQuery();
			Account account;
			while (res.next()) {
				account = new Account();
				account.setAccount_id(res.getInt("account_id"));
				account.setFullname(res.getString("fullname"));
				account.setEmail(res.getString("email"));
				account.setPassword(res.getString("password"));
				account.setAddress(res.getString("address"));
				account.setPhone(res.getString("phone"));
				accounts.add(account);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return accounts;
	}

	public Account getById(int id) {
		Account account = new Account();
		String query = "SELECT * FROM accounts WHERE account_id=? ;";
		try (Connection conn = JDBCConection.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet res = statement.executeQuery();
			while (res.next()) {
				account.setAccount_id(res.getInt("account_id"));
				account.setFullname(res.getString("fullname"));
				account.setEmail(res.getString("email"));
				account.setPassword(res.getString("password"));
				account.setAddress(res.getString("address"));
				account.setPhone(res.getString("phone"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return account;
	}

	public ArrayList<Account> search(String keyword, int index, int limit) {

		accounts = new ArrayList<>();
		StringBuilder query = new StringBuilder("select * from accounts where fullname like '%");
		query.append(keyword).append("%' limit ")
		.append(index).append(",")
		.append(limit);	
		
		try (Connection conn = JDBCConection.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(query.toString());
			ResultSet res = statement.executeQuery();
			Account account;
			while (res.next()) {
				account = new Account();
				account.setAccount_id(res.getInt("account_id"));
				account.setFullname(res.getString("fullname"));
				account.setEmail(res.getString("email"));
				account.setPassword(res.getString("password"));
				account.setAddress(res.getString("address"));
				account.setPhone(res.getString("phone"));
				accounts.add(account);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return accounts;
	}

	public int getTotalRecord(String keyword) {

		int totalRecord = 0;
		String query = "SELECT COUNT(*) AS total_record  FROM crm.accounts WHERE fullname LIKE '%" + keyword + "%'";
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
