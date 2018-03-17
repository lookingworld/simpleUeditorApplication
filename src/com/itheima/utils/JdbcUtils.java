package com.itheima.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUtils {
	private static ComboPooledDataSource dataSource = new ComboPooledDataSource();
	//����һ��threadLocal
	private static ThreadLocal<Connection> local = new ThreadLocal<Connection>();
	public static DataSource getDataSource(){
		return dataSource;
	}
	/**
	 * ���ӵĻ�ȡ�����ݿ����ӳ�ȥ�á�
	 * @return
	 */
	public static Connection getConnection(){
		Connection con = null;
		try {
			//���ȴ�threadlocal��ȥ��ȡ����
			con = local.get();
			if(con==null){
				//��ʱ��service���һ�λ�ȡconnection
				con = dataSource.getConnection();
				//��һ�λ�ȡ��connection�����ŵ�threadLocal��
				local.set(con);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
	

	public static void release(Connection con,Statement st,ResultSet rs){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(st!=null){
			try {
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(con!=null){
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
