package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;

import entity.Employee;

public class EmployeeDAO {
	public List<Employee> findAll() throws Exception{
		List<Employee> employees = 
			new ArrayList<Employee>();
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rst = null;
		try {
			conn = DBUtil.getConnection();
			stat = conn.prepareStatement(
					"select * from t_emp");
			rst = stat.executeQuery();
			while(rst.next()){
				int id = rst.getInt("id");
				String name = rst.getString("name");
				double salary = rst.getDouble("salary");
				int age = rst.getInt("age");
				Employee e = new Employee();
				e.setId(id);
				e.setName(name);
				e.setSalary(salary);
				e.setAge(age);
				employees.add(e);
			}
		} catch (Exception e) {
			//����־
			e.printStackTrace();
			//����ϵͳ�쳣��Ӧ��Ҫ��ʾ�û��Ժ�����,
			//��ֻ��һ�������ֻ࣬��Ҫ�׳����쳣���ɣ�
			throw e;
		}finally{
			//�ر�connection�����Ӧ��
			//statement���Զ��رգ�statement
			//��Ӧ��resultSetҲ���Զ��رա�
			//���ԣ�һ������£�ֻ��Ҫ�ر�connection
			//���ɡ�����,���ʹ�������ݿ����ӳأ�
			//�����ӳ��õ������ӣ���ʹ����֮��
			//�����������Ĺرգ����ԣ���Ӧ��statement,
			//resultSetҲ����رա�
			//�Ƚ��Ͻ���д��:Ӧ����ö��رա�
			DBUtil.close(conn);
		}
		return employees;
	}
	
	//��Ա����Ϣ���뵽���ݿ�
	public void save(Employee e) throws Exception{
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			conn = DBUtil.getConnection();
			stat = conn.prepareStatement(
					"insert into t_emp(name,salary,age) " +
					"values(?,?,?)");
			stat.setString(1, e.getName());
			stat.setDouble(2, e.getSalary());
			stat.setInt(3, e.getAge());
			stat.executeUpdate();
		} catch (Exception e1) {
			e1.printStackTrace();
			throw e1;
		}finally{
			DBUtil.close(conn);
		}
	}
	
	public void delete(int id) throws Exception{
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			conn = DBUtil.getConnection();
			stat = conn.prepareStatement(
					"delete from t_emp where id=?");
			stat.setInt(1, id);
			stat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			DBUtil.close(conn);
		}
	}
	
	//����id��ѯԱ������Ϣ
	public Employee findById(int id) throws Exception{
		Employee e = null;
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rst = null;
		try {
			conn = DBUtil.getConnection();
			stat = conn.prepareStatement(
					"select * from t_emp where id=?");
			stat.setInt(1, id);
			rst = stat.executeQuery();
			if(rst.next()){
				String name = rst.getString("name");
				double salary = rst.getDouble("salary");
				int age = rst.getInt("age");
				e = new Employee();
				e.setId(id);
				e.setName(name);
				e.setSalary(salary);
				e.setAge(age);
			}
		} catch (Exception e1) {
			//����־
			e1.printStackTrace();
			//����ϵͳ�쳣��Ӧ��Ҫ��ʾ�û��Ժ�����,
			//��ֻ��һ�������ֻ࣬��Ҫ�׳����쳣���ɣ�
			throw e1;
		}finally{
			DBUtil.close(conn);
		}
		return e;
	}
	
	//����Ա������Ϣ
	public void update(Employee e) throws Exception{
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			conn = DBUtil.getConnection();
			stat = conn.prepareStatement(
					"update t_emp set name=?," +
					"salary=?,age=? where id=?");
			stat.setString(1, e.getName());
			stat.setDouble(2, e.getSalary());
			stat.setInt(3, e.getAge());
			stat.setInt(4, e.getId());
			stat.executeUpdate();
		} catch (Exception e1) {
			e1.printStackTrace();
			throw e1;
		}finally{
			DBUtil.close(conn);
		}
	}
	
	
	
}
