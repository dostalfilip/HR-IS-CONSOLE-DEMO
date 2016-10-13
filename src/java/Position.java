package java;
/*
 * Enum of every position in company
 */
public enum Position {
	BACKEND_DEVELOPER,
	FRONTEND_DEVELOPER,
	PRODUCT_OWNER,
	SCRUM_MASTER,
	CEO;
	
	
	public String toString(){
		switch(this){
			case BACKEND_DEVELOPER:
				return "Backend_Developer";
			case FRONTEND_DEVELOPER:
				return "Frontend_Developer";
			case PRODUCT_OWNER:
				return "Product_Owner";
			case SCRUM_MASTER:
				return "Scrum_Master";
			case CEO:
				return "CEO";
			default:
				return null;
			}
	}
}
