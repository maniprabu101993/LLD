package com.example.cloudproviders.adapters;

import com.example.cloudproviders.libraries.aws.AWSApi;
import com.example.cloudproviders.libraries.aws.AWSConnectionResponse;
import com.example.cloudproviders.models.Connection;
import com.example.cloudproviders.models.ConnectionStatus;

public class AwsCloudAdapterImpl implements CloudAdapter {

    public Connection createConnection(long userId) {
        AWSConnectionResponse reponse = new AWSApi().createConnection(userId);
        Connection conn = new Connection();
        conn.setConnectionId(reponse.getConnectionId());
        conn.setConnectionStatus(ConnectionStatus.valueOf(reponse.getConnectionStatus()));
        conn.setUserId(userId);
        return conn;
    }
}
