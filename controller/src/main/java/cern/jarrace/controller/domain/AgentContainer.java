/**
 * © Copyright 2016 CERN. This software is distributed under the terms of the Apache License Version 2.0, copied
 * verbatim in the file “COPYING”. In applying this licence, CERN does not waive the privileges and immunities granted
 * to it by virtue of its status as an Intergovernmental Organization or submit itself to any jurisdiction.
 */
package cern.jarrace.controller.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * Responsible for the representation of a deployment to the controller. It couples all necessary information to
 * identify a single deployment and all the {@link Service}s provided by it and later execute them.
 * @author tiagomr
 */
public class AgentContainer {

    /**
     * Unique identifier of each AgentContainer, this field must always be unique
     */
    private String containerName;

    /**
     * Identifies the binary file sent in the deployment process
     */
    private String agentPath;

    /**
     * A list of services that is provided by this {@link AgentContainer}, and can be executed
     */
    private final Set<Service> services = new HashSet<>();

    public AgentContainer() {

    }

    public AgentContainer(String containerName, String agentPath) {
        this.containerName = containerName;
        this.agentPath = agentPath;
    }

    public String getContainerName() {
        return containerName;
    }

    public String getAgentPath() {
        return agentPath;
    }

    public Set<Service> getServices() {
        return services;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgentContainer that = (AgentContainer) o;
        return !(containerName != null ? !containerName.equals(that.containerName) : that.containerName != null);

    }

    @Override
    public int hashCode() {
        return containerName != null ? containerName.hashCode() : 0;
    }
}
