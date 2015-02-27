package info.woody.so.bean;


public class RoleBean extends BaseBeanWithEntry<RoleToPrivilegeBean> {
	private String roleName  ;
	private String roleDesc  ;
	private String roleStatus;
	public String getRoleName() {
		return roleName;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public String getRoleStatus() {
		return roleStatus;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public void setRoleStatus(String roleStatus) {
		this.roleStatus = roleStatus;
	}

}