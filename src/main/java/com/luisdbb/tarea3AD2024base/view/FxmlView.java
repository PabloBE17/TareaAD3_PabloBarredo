package com.luisdbb.tarea3AD2024base.view;

import java.util.ResourceBundle;

public enum FxmlView {
	USER {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("user.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Usuario.fxml";
		}
	},
	LOGIN {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("login.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/InicioSesion.fxml";
		}
	},
	MENU_PEREGRINO {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("MenuPeregrino.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/MenuPeregrino.fxml";
		}
		},
	 AÑADIR_PEREGRINO {
	        @Override
	        public String getTitle() {
	            return getStringFromResourceBundle("AnadirPeregrino.title");
	        }

	        @Override
	        public String getFxmlFile() {
	            return "/fxml/AnadirPeregrino.fxml";
	        }
	    },
	 MENU_PARADA{
	        @Override
	        public String getTitle() {
	            return getStringFromResourceBundle("MenuParada.title");
	        }

	        @Override
	        public String getFxmlFile() {
	            return "/fxml/MenuParada.fxml";
	        }
	    },
	 PANTALLA_PARADA{
	    	@Override
	        public String getTitle() {
	            return getStringFromResourceBundle("PantallaParada.title");
	        }

	        @Override
	        public String getFxmlFile() {
	            return "/fxml/PantallaParada.fxml";
	        }
	    	
	    },
	 SELLAR_ALOJAR{
	    	@Override
	        public String getTitle() {
	            return getStringFromResourceBundle("SellarAlojar.title");
	        }

	        @Override
	        public String getFxmlFile() {
	            return "/fxml/SellarAlojar.fxml";
	        }
	    },
	 MENU_ADMIN{
	    	 @Override
		        public String getTitle() {
		            return getStringFromResourceBundle("MenuAdmin.title");
		        }

		        @Override
		        public String getFxmlFile() {
		            return "/fxml/MenuAdmin.fxml";
		        }
	    },
	 AÑADIR_SERVICIO {
	        @Override
	        public String getTitle() {
	            return getStringFromResourceBundle("AnadirServicio.title");
	        }

	        @Override
	        public String getFxmlFile() {
	            return "/fxml/AnadirServicio.fxml";
	        }
	    },
	 AÑADIR_PARADA{
	    	 @Override
		        public String getTitle() {
		            return getStringFromResourceBundle("AnadirParada.title");
		        }

		        @Override
		        public String getFxmlFile() {
		            return "/fxml/AnadirParada.fxml";
		        }
	    };
	

	public abstract String getTitle();

	public abstract String getFxmlFile();

	String getStringFromResourceBundle(String key) {
		return ResourceBundle.getBundle("Bundle").getString(key);
	}
}
