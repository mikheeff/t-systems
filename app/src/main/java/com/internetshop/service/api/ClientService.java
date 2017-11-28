package com.internetshop.service.api;

import com.internetshop.Exceptions.EmailExistException;
import com.internetshop.Exceptions.PasswordWrongException;
import com.internetshop.entities.ClientAddressEntity;
import com.internetshop.entities.ClientEntity;
import com.internetshop.entities.GoodsEntity;
import com.internetshop.model.Client;
import com.internetshop.model.PasswordField;
import org.apache.commons.codec.binary.Base64;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface ClientService {
    List<ClientEntity> getAllClients();
    List<Client> getBestClientsList(int amountOfBestClients);
    void addClient(Client client) throws EmailExistException;
    Client getUserByEmail(String email);
    void updateUser(Client client);
    Client getClientById(int id);
    String getEmailByConfirmationId(String id);
    void changePassword(PasswordField passwordField, Client client) throws PasswordWrongException;
    void confirmClientEmail(String email);
    void recoverConfirmationIdAndSendEmail(String email) throws UsernameNotFoundException;
    String resetConfirmationId(String email);
    Client convertClientToDTO(ClientEntity clientEntity);
    void uploadAvatar(Client client);
    void deleteAvatar(Client client);
    static String convertImgToBase64(byte[] img){
        if (img == null){
            return null;
        }
        byte[] encodeBase64 = Base64.encodeBase64(img);
        String base64Encoded = "";
        try {
            base64Encoded = new String(encodeBase64, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return base64Encoded;
    }

}
