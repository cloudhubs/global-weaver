package data.service;

import data.domain.HarvesterData;

import java.util.List;

public interface DataService {

    HarvesterData getModelData();

    String processModelDataInconsistencies();

    String processModelDataValidation();

}
