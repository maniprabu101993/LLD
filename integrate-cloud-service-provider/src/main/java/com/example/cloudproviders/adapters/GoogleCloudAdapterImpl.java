package com.example.cloudproviders.adapters;

import com.example.cloudproviders.libraries.google.GoogleApi;
import com.example.cloudproviders.libraries.google.GoogleConnectionResponse;
import com.example.cloudproviders.models.Connection;
import com.example.cloudproviders.models.ConnectionStatus;

public class GoogleCloudAdapterImpl implements CloudAdapter {

    public Connection createConnection(long userId) {
        GoogleConnectionResponse reponse = new GoogleApi().createConnection(userId);
        Connection conn = new Connection();
        conn.setConnectionId(reponse.getConnectionId());
        conn.setConnectionStatus(ConnectionStatus.valueOf(reponse.getConnectionStatus()));
        conn.setUserId(userId);
        return conn;
    }
}
