package info.woody.so.bean;


public class RoleToPrivilegeBean extends BaseBean {
	private int roleId      ;
	private int privilegeId ;
	private int _privilegeId;
	public int getRoleId() {
		return roleId;
	}
	public int getPrivilegeId() {
		return privilegeId;
	}
	public int get_privilegeId() {
		return _privilegeId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public void setPrivilegeId(int privilegeId) {
		this.privilegeId = privilegeId;
	}
	public void set_privilegeId(int _privilegeId) {
		this._privilegeId = _privilegeId;
	}

}