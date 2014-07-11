package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.EmployeeDAO;
import entity.Employee;

public class ActionServlet extends HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		// ���������Դ·��
		String uri = request.getRequestURI();
		System.out.println("uri:" + uri);
		// ����������Դ·�������ݷ����Ľ����������Ӧ��
		// ����
		String action = uri.substring(uri.lastIndexOf("/"), uri
				.lastIndexOf("."));
		System.out.println("action:" + action);
		if (action.equals("/add")) {
			String name = request.getParameter("name");
			String salary = request.getParameter("salary");
			String age = request.getParameter("age");
			/*
			 * ��ȡ���������֮��һ��Ҫ����֤�� ���磬���salary�ǲ���һ���Ϸ������֡� �˴��ԡ�
			 */
			// ��Ա������Ϣ���뵽���ݿ�
			try {
				EmployeeDAO dao = new EmployeeDAO();
				Employee e = new Employee();
				e.setName(name);
				e.setSalary(Double.parseDouble(salary));
				e.setAge(Integer.parseInt(age));
				dao.save(e);
				response.sendRedirect("list.do");
			} catch (Exception e) {
				e.printStackTrace();
//				ת����error.jsp
			}
		} else if (action.equals("/list")) {
			try {
				EmployeeDAO dao = new EmployeeDAO();
				List<Employee> employees = dao.findAll();
				// ��Ϊservlet���ó�չ�����ݣ�����ת��
				// ��jsp������
				//step1,�����ݵ�request�����ϡ�
				request.setAttribute("employees", employees);
				//step2,���ת����
				RequestDispatcher rd = 
					request.getRequestDispatcher("empList.jsp");
				//step3,ת��
				rd.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", 
						"ϵͳ��æ���Ժ�����");
				request.getRequestDispatcher("error.jsp")
				.forward(request, response);
			}
		} else if (action.equals("/del")) {
			int id = Integer.parseInt(request.getParameter("id"));
			// ����daoɾ��ָ��id�ļ�¼
			EmployeeDAO dao = new EmployeeDAO();
			try {
				dao.delete(id);
				response.sendRedirect("list.do");
			} catch (Exception e) {
				e.printStackTrace();
//				ת����error.jsp
			}
		} else if (action.equals("/load")) {
			int id = Integer.parseInt(request.getParameter("id"));
			try {
				EmployeeDAO dao = new EmployeeDAO();
				Employee e = dao.findById(id);
				if (e != null) {
					//ת����jsp,�ɸ�jsp����һ�������û�
					//�޸�
					request.setAttribute("e", e);
					request.getRequestDispatcher(
							"updateEmp.jsp")
							.forward(request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
//				ת����error.jsp
			}
		} else if (action.equals("/modify")) {
			String name = request.getParameter("name");
			String salary = request.getParameter("salary");
			String age = request.getParameter("age");
			int id = Integer.parseInt(request.getParameter("id"));
			EmployeeDAO dao = new EmployeeDAO();
			Employee e = new Employee();
			e.setName(name);
			e.setSalary(Double.parseDouble(salary));
			e.setAge(Integer.parseInt(age));
			e.setId(id);
			try {
				dao.update(e);
				response.sendRedirect("list.do");
			} catch (Exception e1) {
				e1.printStackTrace();
				//ת����error.jsp
			}
		}
	}
}
