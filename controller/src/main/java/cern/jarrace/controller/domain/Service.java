/**
 * © Copyright 2016 CERN. This software is distributed under the terms of the Apache License Version 2.0, copied
 * verbatim in the file “COPYING”. In applying this licence, CERN does not waive the privileges and immunities granted
 * to it by virtue of its status as an Intergovernmental Organization or submit itself to any jurisdiction.
 */
package cern.jarrace.controller.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Domain class that represents a registered {@link Service}, this being an encapulation
 * of all the needed information to run a specific class/method in a remote agent. This class is identified only by
 * @author timartin
 */
public class Service {

    /**
     * Name of the agent that can execute the service
     */
    private String agentName;

    /**
     * Class used by the exposed {@link Service}
     */
    private String clazz;

    /**
     * {@link List} of entry points exposed by this service in the specific class
     */
    private final List<String> entryPoints = new ArrayList<>();

    public Service() {
    }

    public Service(String agentName, String clazz) {
        this.agentName = agentName;
        this.clazz = clazz;
    }

    public Service(String agentName, String clazz, List<String> entryPoints) {
        this.agentName = agentName;
        this.clazz = clazz;
        this.entryPoints.addAll(entryPoints);
    }

    public String getAgentName() {
        return agentName;
    }

    public String getClazz() {
        return clazz;
    }

    public List<String> getEntryPoints() {
        return new ArrayList<>(entryPoints);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        if (agentName != null ? !agentName.equals(service.agentName) : service.agentName != null) return false;
        return !(clazz != null ? !clazz.equals(service.clazz) : service.clazz != null);

    }

    @Override
    public int hashCode() {
        int result = agentName != null ? agentName.hashCode() : 0;
        result = 31 * result + (clazz != null ? clazz.hashCode() : 0);
        return result;
    }
}