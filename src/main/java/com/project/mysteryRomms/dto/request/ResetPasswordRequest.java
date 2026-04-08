package com.project.mysteryRomms.dto.request;

// Esta clase representa la información que un usuario envía
// cuando quiere cambiar su contraseña.
public class ResetPasswordRequest {
    // Aquí guardamos la nueva contraseña que el usuario quiere usar
    private String newPassword;

    // Método para obtener la nueva contraseña
    public String getNewPassword() {
        return newPassword;
    }

    // Método para asignar la nueva contraseña
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
