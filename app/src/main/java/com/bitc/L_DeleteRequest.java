package com.bitc;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class L_DeleteRequest extends StringRequest {

    //서버 URL 설정
    final static private String URL = "http://haun2.ivyro.net/Delete.php";
    private Map<String, String> parameters;

    public L_DeleteRequest(String userID, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);//Post방식임

        parameters = new HashMap<>();//해쉬맵 생성후 parameters 변수에 값을 넣어줌
        parameters.put("userID", userID);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
