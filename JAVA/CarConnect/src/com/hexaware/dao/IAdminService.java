package com.hexaware.dao;



import com.hexaware.model.Admin;

public interface IAdminService {
	//Declaration of Non-Implemented Methods
	Admin getAdminByID(int adminID);
    Admin getAdminByUsername(String username);
    void registerAdmin(Admin admin);
    void updateAdmin(Admin admin);
    void deleteAdmin(int adminId);
}