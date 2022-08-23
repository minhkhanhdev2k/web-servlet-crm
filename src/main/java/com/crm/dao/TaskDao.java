package com.crm.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import com.crm.connection.JDBCConection;
import com.crm.model.Task;

public class TaskDao {

	private ArrayList<Task> tasks;

	public int update(Task task) {
		int result = 0;
		String query = "update tasks set task_name=?, group_id=?, account_id=?, status_id=? where task_id = ?;";
		try (Connection connection = JDBCConection.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(query);
			// task_id, task_name, end_date, group_id, account_id, status_id
			statement.setString(1, task.getName());
			statement.setInt(2, task.getGroup_id());
			statement.setInt(3, task.getAccount_id());
			statement.setInt(4, task.getStatus_id());
			statement.setInt(5, task.getId());
			result = statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int delete(int id) {
		int result = 0;
		String query = "delete from tasks where task_id = ?;";
		try (Connection connection = JDBCConection.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			result = statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int insert(Task task) {
		int result = 0;
		String query = "INSERT INTO tasks (task_name, end_date, group_id, account_id, status_id) VALUES  (?,?,?,?,?);";
		try (Connection connection = JDBCConection.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, task.getName());
			statement.setDate(2, Date.valueOf(LocalDate.now()));
			statement.setInt(3, task.getGroup_id());
			statement.setInt(4, task.getAccount_id());
			statement.setInt(5, task.getStatus_id());
			result = statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public ArrayList<Task> getAll() {
		tasks = new ArrayList<>();
		String query = "SELECT * FROM tasks;";
		try (Connection conn = JDBCConection.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet res = statement.executeQuery();
			Task task;
			
			while (res.next()) {
				task = new Task();
				// task_id, task_name, end_date, group_id, account_id, status_id
				task.setId(res.getInt("task_id"));
				task.setName(res.getString("task_name"));
				task.setEnd_date(getDateFromResultSet("end_date", res));
				task.setGroup_id(res.getInt("group_id"));
				task.setAccount_id(res.getInt("account_id"));
				task.setStatus_id(res.getInt("status_id"));
				
				tasks.add(task);
			}
		
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tasks;
	}

	public Task getById(int id) {
		Task task = new Task();
		String query = "SELECT * FROM tasks WHERE task_id =? ;";
		try (Connection conn = JDBCConection.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet res = statement.executeQuery();
			if (res.next()) {
				task.setId(res.getInt("task_id"));
				task.setName(res.getString("task_name"));
				task.setEnd_date(getDateFromResultSet("end_date", res));
				task.setGroup_id(res.getInt("group_id"));
				task.setAccount_id(res.getInt("account_id"));
				task.setStatus_id(res.getInt("status_id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return task;
	}

	public ArrayList<Task> search(String keyword, int index, int limit) {

		tasks = new ArrayList<>();
		StringBuilder query = new StringBuilder("select * from tasks where task_name like '%");
		query.append(keyword).append("%' limit ")
		.append(index).append(",")
		.append(limit);	
		
		try (Connection conn = JDBCConection.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(query.toString());
			ResultSet res = statement.executeQuery();
			Task task;
			while (res.next()) {
				task = new Task();
				task.setId(res.getInt("task_id"));
				task.setName(res.getString("task_name"));
				task.setEnd_date(getDateFromResultSet("end_date", res));
				task.setGroup_id(res.getInt("group_id"));
				task.setAccount_id(res.getInt("account_id"));
				task.setStatus_id(res.getInt("status_id"));
				tasks.add(task);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tasks;
	}

	public int getTotalRecord(String keyword) {

		int totalRecord = 0;
		String query = "SELECT COUNT(*) AS total_record  FROM tasks WHERE task_name LIKE '%" + keyword + "%'";
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
	private LocalDate getDateFromResultSet(String columnName, ResultSet resultSet) {
        Date time;

        try {
            time = resultSet.getDate(columnName);
            return time == null ? null : time.toLocalDate();
        } catch (SQLException e) {
            return null;
        }
    }
	public ArrayList<Task> getTaskByAccountId(int accountid) {
		tasks = new ArrayList<>();
		String query = "SELECT task_name,end_date,status_id FROM tasks where account_id = ? ;";
		try (Connection conn = JDBCConection.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, accountid);
			ResultSet res = statement.executeQuery();
			Task task;
			while (res.next()) {
				task = new Task();
				task.setName(res.getString("task_name"));
				task.setEnd_date(getDateFromResultSet("end_date", res));
				task.setStatus_id(res.getInt("status_id"));
				tasks.add(task);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tasks;
	}

	public ArrayList<Task> getByProjectId(int projectId) {
		tasks = new ArrayList<>();
		String query = "SELECT * FROM tasks where group_id = ? ;";
		try (Connection conn = JDBCConection.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, projectId);
			ResultSet res = statement.executeQuery();
			Task task;
			ProjectDao pd = new ProjectDao();
			AccountDao aDao = new AccountDao();
			while (res.next()) {
				task = new Task();
				task.setId(res.getInt("task_id"));
				task.setName(res.getString("task_name"));
				task.setEnd_date(getDateFromResultSet("end_date", res));
				task.setGroup_id(res.getInt("group_id"));
				task.setGroup_name(query);
				task.setAccount_id(res.getInt("account_id"));
				task.setStatus_id(res.getInt("status_id"));
				
				task.setGroup_name(pd.getById(res.getInt("group_id")).getName());
				task.setAccount_name(aDao.getById(res.getInt("account_id")).getFullname());
				tasks.add(task);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tasks;
	}
}
