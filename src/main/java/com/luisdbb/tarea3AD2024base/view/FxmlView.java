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
