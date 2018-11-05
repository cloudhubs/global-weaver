package harvester.service;

import harvester.domain.HarvesterData;

import java.util.ArrayList;

public interface HarvesterService {

    ArrayList<HarvesterData> getData(String path);

}
