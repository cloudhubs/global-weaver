package data.service;

import data.domain.HarvesterData;

public interface DataService {

    HarvesterData getModelData();

    String processModelData();

}
