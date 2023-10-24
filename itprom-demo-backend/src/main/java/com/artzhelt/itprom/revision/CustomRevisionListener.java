package com.artzhelt.itprom.revision;

import org.hibernate.envers.RevisionListener;

/**
 * Created by Artyom Zheltyshev on 24.10.2023
 */
public class CustomRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        CustomRevisionEntity customRevisionEntity = (CustomRevisionEntity) revisionEntity;
        customRevisionEntity.setUsername("stub_username");
    }

}
