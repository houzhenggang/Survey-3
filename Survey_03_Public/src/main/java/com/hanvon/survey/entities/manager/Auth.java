package com.hanvon.survey.entities.manager;

import java.util.HashSet;
import java.util.Set;

public class Auth {
    private Integer authId;

    private String authName;
    
    private Set<Res> resSet = new HashSet<Res>();

    public Integer getAuthId() {
        return authId;
    }

    public void setAuthId(Integer authId) {
        this.authId = authId;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName == null ? null : authName.trim();
    }

	public Set<Res> getResSet() {
		return resSet;
	}

	public void setResSet(Set<Res> resSet) {
		this.resSet = resSet;
	}

	@Override
	public String toString() {
		return "Auth [authId=" + authId + ", authName=" + authName + "]";
	}
    
    
}